# 前端模块代码目录查找规则

## 概述

本指南定义了前端模块代码目录的查找规则和定位方法，帮助开发人员快速找到和创建前端模块代码。

## {moduleCode} 获取来源与确认规则

在进行前端模块代码目录查找和创建之前，**必须优先从当前项目中提取 `moduleCode`**，不得直接猜测或沿用历史上下文中的旧值。

### 优先提取来源

优先从当前项目中的以下文件提取：

- `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json`
- `{moduleCode}-resource/src/main/resources/META-INF/appdata/App.json`

**{moduleCode}.json 关键字段**：
```json
{
  "module": {
    "moduleCode": "{moduleCode}",      // 模块编码
    "moduleName": "{模块名称}",
    "acronym": "{acronym}"             // 模块缩略码
  }
}
```

**App.json 关键字段**：
```json
{
  "code": "{moduleCode}",              // 应用编码
  "modules": "{moduleCode}",           // 模块编码
  "name": "{应用名称}"
}
```

### 执行规则

1. 若当前项目中存在对应文件，则先读取并提取 `moduleCode`
2. 若成功提取，也**必须先向用户展示提取结果并确认是否正确**，确认无误后才能继续
3. 若用户确认正确，则后续所有代码生成、目录命名、API 路径命名都必须使用确认后的值
4. 若未找到文件、未能明确提取出 `moduleCode`，或用户反馈提取结果不正确，则**必须主动向用户询问并要求提供正确的 `moduleCode`**，不得继续使用默认值、猜测值或未确认值
5. 用户补充或更正后，后续生成内容必须严格使用用户最终确认的值

### 标准确认提问话术

#### 情况1：成功从项目文件中提取

我已从项目文件中提取到 `moduleCode`：

- **提取来源**：`{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json`
- **提取结果**：`moduleCode = {实际值}`

请确认该值是否正确？
- 若回复 **正确**：我将使用该值继续定位前端模块代码目录
- 若回复 **不正确**：请提供正确的 `moduleCode` 值

#### 情况2：未找到文件或提取失败

当前未找到 `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json` 或 `App.json` 文件，无法自动提取 `moduleCode`。

请提供正确的 `moduleCode` 值，用于定位前端模块代码目录。

## 前端项目目录结构

### 标准目录结构

> **说明**：以下 `{moduleCode}` 需替换为实际模块编码（如 `aiprocts`、`usermgmt` 等）

```
{moduleCode}-frontend/                # 前端项目根目录
├── public/                   # 静态资源目录
│   ├── index.html           # HTML 模板
│   └── assets/              # 公共资源（图片、字体等）
├── src/                     # 源代码目录
│   ├── api/                 # API 接口定义
│   │   ├── request.ts       # axios 封装
│   │   └── modules/         # 按模块组织的 API
│   │       └── user.ts      # 用户模块 API
│   ├── assets/              # 项目静态资源
│   │   ├── images/          # 图片资源
│   │   └── styles/          # 全局样式
│   │       └── global.less  # 全局样式文件
│   ├── components/          # 公共组件
│   │   └── Layout/          # 布局组件
│   │       ├── index.tsx
│   │       └── index.module.less
│   ├── hooks/               # 自定义 Hooks
│   │   └── useUserList.ts   # 示例自定义 Hook
│   ├── pages/               # 页面组件
│   │   ├── Home/            # 首页
│   │   │   ├── index.tsx
│   │   │   └── index.module.less
│   │   └── User/            # 用户管理
│   │       ├── List.tsx     # 用户列表
│   │       └── index.module.less
│   ├── routes/              # 路由配置
│   │   └── index.tsx        # 路由定义
│   ├── store/               # 状态管理
│   │   └── index.ts         # 状态管理入口
│   ├── types/               # TypeScript 类型定义
│   │   └── user.ts          # 用户相关类型
│   ├── utils/               # 工具函数
│   │   └── format.ts        # 格式化工具
│   ├── App.tsx              # 应用根组件
│   └── main.tsx             # 应用入口
├── .eslintrc.js             # ESLint 配置
├── .prettierrc              # Prettier 配置
├── tsconfig.json            # TypeScript 配置
├── webpack.config.js        # Webpack 配置
└── package.json             # 项目依赖
```

## 模块代码定位规则

### 1. 基于 metadata 文件推导路径

当从 `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json` 或 `App.json` 文件中读取到 `moduleCode` 时：

- **推导规则**：前端项目通常与后端项目在同一工作空间，目录命名为 `{moduleCode}-frontend/`
- **相对路径**：从项目根目录查找 `{moduleCode}-frontend/`
- **完整路径示例**：`{moduleCode}-frontend/src/`
- **目录结构对应关系**：
  ```
  {moduleCode}-frontend/ (前端项目根目录)
  ├── public/                   # 静态资源目录
  ├── src/                      # 源代码目录
  │   ├── api/                  # API 接口定义
  │   ├── pages/                # 页面组件
  │   ├── components/           # 公共组件
  │   ├── routes/               # 路由配置
  │   └── ...
  └── package.json              # 项目依赖
  ```

### 2. 基于项目目录搜索定位

**优先搜索规则**：在当前项目目录结构中搜索 `{moduleCode}-frontend/` 目录

搜索路径优先级：
1. **工作空间根目录**：直接搜索 `{moduleCode}-frontend/`
2. **相邻目录**：在与 `{moduleCode}` 后端项目同级的位置搜索
3. **递归搜索**：在工作空间根目录下递归查找 `{moduleCode}-frontend/` 目录

**确认规则**：
- 找到目录后，验证目录内是否包含标准文件（package.json、src/目录等）
- 若目录不存在，则按标准结构创建

### 3. 目录位置记录

**记录到 dev-task.md**：

在确认前端代码生成目录后，必须将以下信息记录到 `dev-task.md` 中：

```markdown
## 前端代码目录信息

- **moduleCode**: {实际值}
- **frontend目录路径**: {相对工作空间根目录的路径}
- **完整路径**: {绝对路径或相对路径}
- **目录状态**: [已存在 / 新创建]
- **记录时间**: {时间戳}
```

**用途**：该信息将用于后续 SKILL.md 中第3节点（前端代码生成）步骤，确保代码生成到正确的目录位置。

### 4. 基于功能名称定位

当需要根据功能名称定位前端代码时：

- **页面组件**：查找 `src/pages/{功能名称}/` 目录
- **API接口**：查找 `src/api/modules/{功能名称}.ts` 文件
- **路由配置**：检查 `src/routes/index.tsx` 中对应路由

### 5. 基于模块编码定位

当需要根据 `moduleCode` 定位前端代码时：

- **项目根目录**：`{moduleCode}-frontend/`
- **API基础路径**：`/msService/{moduleCode}/`
- **组件命名规范**：使用 PascalCase，如 `{ModuleCode}Component`

### 6. 新增模块代码创建

当需要为新增功能创建前端代码时：

1. **确认 moduleCode**：按照上述规则提取并确认 `moduleCode`
2. **创建页面目录**：在 `src/pages/` 下创建功能目录
3. **创建API文件**：在 `src/api/modules/` 下创建对应API文件
4. **更新路由**：在 `src/routes/index.tsx` 中添加路由配置
5. **注册组件**：确保组件在应用中正确注册

## 查找顺序

### 前端模块代码查找优先级

1. **精确匹配**：完全匹配功能名称或模块编码
2. **模糊匹配**：部分匹配功能名称或模块编码
3. **默认位置**：在标准目录下查找
4. **手动创建**：若未找到，则按规范创建

## 验证规则

### 目录存在性验证

在开始开发前，必须验证以下目录是否存在：

- [ ] `{moduleCode}-frontend/` 根目录存在
- [ ] `src/` 源代码目录存在
- [ ] `src/pages/` 页面目录存在
- [ ] `src/api/` API目录存在
- [ ] `src/routes/` 路由目录存在

### 代码完整性验证

在模块代码查找完成后，验证以下文件是否存在：

- [ ] 页面组件文件 (`index.tsx`)
- [ ] 样式文件 (`index.module.less`)
- [ ] API接口文件 (`modules/{功能}.ts`)
- [ ] 路由配置 (`routes/index.tsx`)

## 注意事项

1. ✅ **严格遵循 moduleCode**：所有目录命名必须使用确认后的 `moduleCode`
2. ✅ **保持结构一致**：新创建的模块代码必须遵循标准目录结构
3. ❌ **禁止随意更改**：不得随意更改已建立的目录结构
4. ❌ **避免硬编码**：不得使用硬编码路径，应使用相对路径或别名