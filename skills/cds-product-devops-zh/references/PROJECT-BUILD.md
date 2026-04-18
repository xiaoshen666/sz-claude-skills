# 项目编译构建指南

## 概述

本文档定义了 CDS 项目的编译构建流程，包括前端资源构建和后端资源部署。整个流程自动化完成前端打包、资源拷贝到后端静态资源目录的操作。

## 前置条件

在执行构建前，必须确认：

- [ ] 已从 `devops-task.md` 中获取 `moduleCode` 和 `appCode`
- [ ] 已确认前端项目目录位置（参考 FRONTEND-MODULE-STRUCT-RULES.md）
- [ ] 已确认后端项目目录位置（参考 BACKEND-MODULE-STRUCT-RULES.md）
- [ ] Node.js 环境可用（执行 `node --version` 验证）
- [ ] npm 环境可用（执行 `npm --version` 验证）

## 构建流程

### 步骤 1：定位前端项目目录

**执行规则**：

1. 从 `devops-task.md` 中读取 `moduleCode`
2. 按照 FRONTEND-MODULE-STRUCT-RULES.md 中的定位规则查找前端项目目录
3. 优先搜索路径：
   - 工作空间根目录下的 `{moduleCode}-frontend/`
   - 与后端项目同级目录下的 `{moduleCode}-frontend/`
   - 递归搜索工作空间根目录查找 `{moduleCode}-frontend/`

**确认提问话术**：

```
我已找到前端项目目录：

- **moduleCode**: {实际值}
- **前端项目路径**: {相对工作空间的路径}
- **完整路径**: {绝对路径}

请确认该路径是否正确？
- 若回复 **正确**：我将继续执行前端构建
- 若回复 **不正确**：请提供正确的前端项目路径
```

### 步骤 2：执行前端构建

#### 2.1 进入前端项目目录

```bash
cd {frontend-project-path}
```

#### 2.2 安装依赖（首次构建或 package-lock.json 变更时）

```bash
npm install
```

#### 2.3 执行构建命令

```bash
npm run build
```

**构建过程说明**：
- 使用 TypeScript 编译代码 (`tsc`)
- 使用 Vite 打包所有资源 (`vite build`)
- 生成优化后的生产环境代码

#### 2.4 验证构建产物

构建完成后，验证 `dist/` 目录是否存在且包含必要文件：

```
{moduleCode}-frontend/
└── dist/                    # 打包生成的目录
    ├── index.html           # 入口HTML
    ├── assets/              # 静态资源
    │   ├── index-xxxxx.js   # 编译后的JS文件
    │   ├── index-xxxxx.css  # 编译后的CSS文件
    │   └── ...              # 其他资源文件
    └── vite.svg             # Vite 图标（可选）
```

**验证检查项**：
- [ ] `dist/` 目录存在
- [ ] `dist/index.html` 存在
- [ ] `dist/assets/` 目录存在且包含 JS/CSS 文件

### 步骤 3：定位后端静态资源目录

**执行规则**：

1. 从 `devops-task.md` 中读取 `moduleCode` 和 `appCode`
2. 按照 BACKEND-MODULE-STRUCT-RULES.md 中的定位规则查找后端项目目录
3. 后端静态资源目标路径：

```
{moduleCode}-custom/
└── {moduleCode}-custom-resource/
    └── src/main/resources/
        └── static/
            └── {moduleCode}/        # 前端资源部署目录
                ├── index.html
                └── assets/
```

**完整路径示例**：
```
{backend-project-root}/
├── {moduleCode}/
│   └── {moduleCode}-custom/
│       └── {moduleCode}-custom-resource/
│           └── src/main/resources/
│               └── static/
│                   └── {moduleCode}/
└── ...
```

**确认提问话术**：

```
我已找到后端静态资源目录：

- **moduleCode**: {实际值}
- **后端项目路径**: {相对工作空间的路径}
- **静态资源目标路径**: {moduleCode}-custom/{moduleCode}-custom-resource/src/main/resources/static/{moduleCode}/
- **完整路径**: {绝对路径}

请确认该路径是否正确？
- 若回复 **正确**：我将继续执行资源拷贝
- 若回复 **不正确**：请提供正确的后端静态资源路径
```

### 步骤 4：拷贝前端资源到后端目录

**跨平台拷贝策略**：

根据操作系统选择对应的拷贝命令：

#### Windows (PowerShell)

```powershell
# 删除旧文件
Remove-Item -Recurse -Force "{backend-static-path}\*" -ErrorAction SilentlyContinue

# 创建目标目录（如果不存在）
New-Item -ItemType Directory -Force -Path "{backend-static-path}"

# 复制新文件
Copy-Item -Recurse "{frontend-dist-path}\*" "{backend-static-path}\"
```

#### Mac/Linux (Bash)

```bash
# 删除旧文件
rm -rf "{backend-static-path}"/*

# 创建目标目录（如果不存在）
mkdir -p "{backend-static-path}"

# 复制新文件
cp -r "{frontend-dist-path}"/* "{backend-static-path}/"
```

#### 跨平台兼容方案（推荐）

使用 Node.js 脚本实现跨平台拷贝：

```javascript
// copy-frontend-to-backend.js
const fs = require('fs');
const path = require('path');

function copyDir(src, dest) {
  // 创建目标目录
  if (!fs.existsSync(dest)) {
    fs.mkdirSync(dest, { recursive: true });
  }
  
  // 读取源目录
  const entries = fs.readdirSync(src, { withFileTypes: true });
  
  for (const entry of entries) {
    const srcPath = path.join(src, entry.name);
    const destPath = path.join(dest, entry.name);
    
    if (entry.isDirectory()) {
      // 递归复制子目录
      copyDir(srcPath, destPath);
    } else {
      // 复制文件
      fs.copyFileSync(srcPath, destPath);
    }
  }
}

// 从命令行参数获取路径
const frontendDistPath = process.argv[2];
const backendStaticPath = process.argv[3];

if (!frontendDistPath || !backendStaticPath) {
  console.error('用法: node copy-frontend-to-backend.js <frontend-dist-path> <backend-static-path>');
  process.exit(1);
}

console.log(`开始拷贝前端资源...`);
console.log(`源目录: ${frontendDistPath}`);
console.log(`目标目录: ${backendStaticPath}`);

// 清空目标目录
if (fs.existsSync(backendStaticPath)) {
  fs.rmSync(backendStaticPath, { recursive: true, force: true });
}

// 执行拷贝
copyDir(frontendDistPath, backendStaticPath);

console.log('✅ 前端资源拷贝完成！');
```

**执行命令**：

```bash
node copy-frontend-to-backend.js "{frontend-dist-path}" "{backend-static-path}"
```

### 步骤 5：验证资源部署

拷贝完成后，验证后端静态资源目录：

```
{backend-static-path}/
├── index.html
└── assets/
    ├── index-xxxxx.js
    ├── index-xxxxx.css
    └── ...
```

**验证检查项**：
- [ ] 目标目录存在
- [ ] `index.html` 文件存在
- [ ] `assets/` 目录存在
- [ ] 文件数量与 `dist/` 目录一致
- [ ] 文件大小合理（无 0 字节文件）

### 步骤 6：更新构建记录

将构建信息记录到 `devops-task.md`：

```markdown
## 构建记录

### 前端构建
- **构建时间**: {YYYY-MM-DD HH:mm:ss}
- **moduleCode**: {实际值}
- **前端项目路径**: {相对路径}
- **dist 目录路径**: {相对路径}
- **构建状态**: ✅ 成功 / ❌ 失败
- **构建产物大小**: {xxx MB}

### 资源部署
- **后端静态资源路径**: {相对路径}
- **部署状态**: ✅ 成功 / ❌ 失败
- **文件数量**: {xx} 个文件
- **部署时间**: {YYYY-MM-DD HH:mm:ss}
```

## 错误处理

### 常见问题及解决方案

#### 1. 前端构建失败

**症状**：`npm run build` 执行失败

**排查步骤**：
1. 检查 Node.js 版本是否符合要求（建议 >= 16.0.0）
2. 检查 `package.json` 中的依赖是否完整
3. 执行 `npm install` 重新安装依赖
4. 查看详细错误日志

#### 2. dist 目录不存在

**症状**：构建完成后找不到 `dist/` 目录

**排查步骤**：
1. 检查 `vite.config.ts` 中的 `build.outDir` 配置
2. 确认构建命令是否正确执行
3. 检查是否有权限问题

#### 3. 后端目录不存在

**症状**：找不到后端静态资源目录

**排查步骤**：
1. 确认 `moduleCode` 是否正确
2. 按照 BACKEND-MODULE-STRUCT-RULES.md 重新定位后端项目
3. 检查目录结构是否符合 CDS 标准

#### 4. 拷贝失败

**症状**：文件拷贝过程中报错

**排查步骤**：
1. 检查目标目录是否有写权限
2. 检查磁盘空间是否充足
3. 检查是否有文件被占用（Windows 常见）
4. 尝试使用跨平台 Node.js 脚本方案

## 自动化脚本（可选）

为提高效率，可以创建自动化构建脚本：

### build-and-deploy.sh (Mac/Linux)

```bash
#!/bin/bash

# 参数
MODULE_CODE=$1
WORKSPACE_ROOT=$2

if [ -z "$MODULE_CODE" ] || [ -z "$WORKSPACE_ROOT" ]; then
  echo "用法: ./build-and-deploy.sh <moduleCode> <workspace-root>"
  exit 1
fi

# 路径定义
FRONTEND_PATH="$WORKSPACE_ROOT/${MODULE_CODE}-frontend"
BACKEND_STATIC_PATH="$WORKSPACE_ROOT/${MODULE_CODE}/${MODULE_CODE}-custom/${MODULE_CODE}-custom-resource/src/main/resources/static/${MODULE_CODE}"

echo "🚀 开始构建和部署流程..."
echo "模块编码: $MODULE_CODE"
echo "工作空间: $WORKSPACE_ROOT"

# 1. 前端构建
echo "📦 步骤1: 前端构建..."
cd "$FRONTEND_PATH"
npm install
npm run build

# 2. 验证构建产物
if [ ! -d "dist" ]; then
  echo "❌ 构建失败：dist 目录不存在"
  exit 1
fi

echo "✅ 前端构建成功"

# 3. 资源部署
echo "📂 步骤2: 部署前端资源到后端..."
rm -rf "$BACKEND_STATIC_PATH"/*
mkdir -p "$BACKEND_STATIC_PATH"
cp -r dist/* "$BACKEND_STATIC_PATH/"

echo "✅ 资源部署完成"
echo "🎉 构建和部署流程完成！"
```

### build-and-deploy.ps1 (Windows)

```powershell
param(
  [string]$ModuleCode,
  [string]$WorkspaceRoot
)

if (-not $ModuleCode -or -not $WorkspaceRoot) {
  Write-Host "用法: .\build-and-deploy.ps1 -ModuleCode <moduleCode> -WorkspaceRoot <workspace-root>"
  exit 1
}

# 路径定义
$FrontendPath = Join-Path $WorkspaceRoot "${ModuleCode}-frontend"
$BackendStaticPath = Join-Path $WorkspaceRoot "${ModuleCode}\${ModuleCode}-custom\${ModuleCode}-custom-resource\src\main\resources\static\${ModuleCode}"

Write-Host "🚀 开始构建和部署流程..."
Write-Host "模块编码: $ModuleCode"
Write-Host "工作空间: $WorkspaceRoot"

# 1. 前端构建
Write-Host "📦 步骤1: 前端构建..."
Set-Location $FrontendPath
npm install
npm run build

# 2. 验证构建产物
if (-not (Test-Path "dist")) {
  Write-Host "❌ 构建失败：dist 目录不存在"
  exit 1
}

Write-Host "✅ 前端构建成功"

# 3. 资源部署
Write-Host "📂 步骤2: 部署前端资源到后端..."
Remove-Item -Recurse -Force "$BackendStaticPath\*" -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $BackendStaticPath | Out-Null
Copy-Item -Recurse "dist\*" "$BackendStaticPath\"

Write-Host "✅ 资源部署完成"
Write-Host "🎉 构建和部署流程完成！"
```

## 注意事项

1. ✅ **使用相对路径**：所有路径必须相对于工作空间根目录，确保跨机器可用
2. ✅ **跨平台兼容**：使用 Node.js 脚本或提供多平台脚本
3. ✅ **构建前清理**：每次构建前清理旧的 dist 目录
4. ✅ **部署前备份**：可选备份旧的前端资源（用于快速回滚）
5. ❌ **禁止硬编码**：不得使用绝对路径
6. ❌ **跳过验证**：不得跳过构建产物验证步骤
7. ⚠️ **权限问题**：确保对目标目录有写权限

## 参考资料

- [前端模块代码目录查找规则](../../cds-product-develop-zh/references/FRONTEND-MODULE-STRUCT-RULES.md)
- [后端模块代码目录查找规则](../../cds-product-develop-zh/references/BACKEND-MODULE-STRUCT-RULES.md)
- [打包编译说明](../../../打包编译.md)
