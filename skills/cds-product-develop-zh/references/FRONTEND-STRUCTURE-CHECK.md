# 前端文件夹层级结构检查指南

## 概述

本指南用于检查前端项目的文件夹层级结构是否符合前端设计文档的要求。检查完成后，结果输出到 `design-docs/testing-validation/` 目录。

## 检查前提

1. ✅ 前端代码已生成完成（选项A已执行）
2. ✅ 前端架构设计文档已存在
3. ✅ 已确认 `moduleCode`

## 检查步骤

### 步骤1：读取前端架构设计文档

**查找位置**：
- `design-docs/frontend-architecture/{功能名称}前端架构设计.md`
- `design-docs/frontend-architecture/FRONTEND-ARCH.md`

**提取内容**：
- 期望的目录结构
- 关键文件位置
- 组件组织方式
- 路由配置位置

### 步骤2：检查实际前端项目结构

**查找位置**：
- `{moduleCode}-frontend/` 目录

**检查内容**：

#### 2.1 基础目录结构

```markdown
### 基础目录结构检查

| 目录/文件 | 是否必需 | 实际存在 | 路径正确 | 备注 |
|----------|---------|---------|---------|------|
| src/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| src/api/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| src/components/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| src/views/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| src/router/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| src/store/ | ⚠️ 可选 | ✅ 是 | ✅ 是 | - |
| src/utils/ | ⚠️ 可选 | ✅ 是 | ✅ 是 | - |
| src/assets/ | ✅ 是 | ✅ 是 | ✅ 是 | - |
| package.json | ✅ 是 | ✅ 是 | ✅ 是 | - |
```

#### 2.2 功能模块目录

根据前端架构设计文档中定义的功能模块，检查对应目录是否存在：

```markdown
### 功能模块目录检查

| 功能模块 | 期望路径 | 实际存在 | 结构正确 | 备注 |
|---------|---------|---------|---------|------|
| 用户管理 | src/views/user/ | ✅ 是 | ✅ 是 | - |
| 角色管理 | src/views/role/ | ✅ 是 | ✅ 是 | - |
| 权限管理 | src/views/permission/ | ❌ 否 | - | 缺失目录 |
```

#### 2.3 组件组织

```markdown
### 组件组织检查

| 组件类型 | 期望位置 | 实际位置 | 是否符合 | 备注 |
|---------|---------|---------|---------|------|
| 公共组件 | src/components/common/ | src/components/common/ | ✅ 符合 | - |
| 业务组件 | src/components/business/ | src/components/business/ | ✅ 符合 | - |
| 布局组件 | src/components/layout/ | src/components/layout/ | ✅ 符合 | - |
```

#### 2.4 路由配置

```markdown
### 路由配置检查

| 路由文件 | 期望位置 | 实际位置 | 是否符合 | 备注 |
|---------|---------|---------|---------|------|
| 主路由 | src/router/index.js | src/router/index.js | ✅ 符合 | - |
| 模块路由 | src/router/modules/ | src/router/modules/ | ✅ 符合 | - |
| 路由常量 | src/router/constants.js | src/router/constants.js | ✅ 符合 | - |
```

#### 2.5 API文件组织

```markdown
### API文件组织检查

| API模块 | 期望位置 | 实际位置 | 是否符合 | 备注 |
|---------|---------|---------|---------|------|
| 用户API | src/api/user.js | src/api/user.js | ✅ 符合 | - |
| 角色API | src/api/role.js | src/api/role.js | ✅ 符合 | - |
| 权限API | src/api/permission.js | ❌ 缺失 | - | 缺失文件 |
```

### 步骤3：对比分析

**对比规则**：
- 实际目录结构应与前端架构设计文档中定义的结构一致
- 关键目录和文件必须存在
- 文件命名应符合设计规范
- 目录层级深度应符合规范（建议不超过4层）

**记录格式**：

```markdown
### 结构对比分析

**设计文档要求的结构**：
```
{moduleCode}-frontend/
├── src/
│   ├── api/
│   │   ├── user.js
│   │   ├── role.js
│   │   └── permission.js
│   ├── components/
│   │   ├── common/
│   │   ├── business/
│   │   └── layout/
│   ├── views/
│   │   ├── user/
│   │   ├── role/
│   │   └── permission/
│   ├── router/
│   │   ├── index.js
│   │   ├── modules/
│   │   └── constants.js
│   ├── store/
│   ├── utils/
│   └── assets/
└── package.json
```

**实际存在的结构**：
```
{moduleCode}-frontend/
├── src/
│   ├── api/
│   │   ├── user.js ✅
│   │   ├── role.js ✅
│   │   └── permission.js ❌ 缺失
│   ├── components/
│   │   ├── common/ ✅
│   │   ├── business/ ✅
│   │   └── layout/ ✅
│   ├── views/
│   │   ├── user/ ✅
│   │   ├── role/ ✅
│   │   └── permission/ ❌ 缺失
│   ├── router/
│   │   ├── index.js ✅
│   │   ├── modules/ ✅
│   │   └── constants.js ✅
│   ├── store/ ✅
│   ├── utils/ ✅
│   └── assets/ ✅
└── package.json ✅
```

**差异分析**：
- 缺失目录：src/views/permission/
- 缺失文件：src/api/permission.js
- 多余目录：无
- 多余文件：无
```

### 步骤4：生成检查报告

**输出位置**：`design-docs/testing-validation/`

**报告文件名**：`{功能名称}-前端文件夹结构检查报告.md`

**报告模板**：

```markdown
# {功能名称} - 前端文件夹层级结构检查报告

## 基本信息

- **模块名称**：{功能模块名称}
- **moduleCode**：{模块编码}
- **检查时间**：{YYYY-MM-DD HH:mm:ss}
- **检查人员**：{检查人员}

## 检查概述

### 结构符合性

- **必需目录**：X个，存在X个，缺失X个
- **可选目录**：X个，存在X个，缺失X个
- **必需文件**：X个，存在X个，缺失X个
- **目录层级**：最大X层（建议≤4层）

### 检查统计

| 检查项 | 总数 | 符合 | 不符合 | 符合率 |
|--------|------|------|--------|-------|
| 基础目录 | X | X | X | X% |
| 功能模块目录 | X | X | X | X% |
| 组件组织 | X | X | X | X% |
| 路由配置 | X | X | X | X% |
| API文件组织 | X | X | X | X% |
| **总计** | **X** | **X** | **X** | **X%** |

## 详细检查结果

### 基础目录结构检查

（插入步骤2.1的检查表格）

### 功能模块目录检查

（插入步骤2.2的检查表格）

### 组件组织检查

（插入步骤2.3的检查表格）

### 路由配置检查

（插入步骤2.4的检查表格）

### API文件组织检查

（插入步骤2.5的检查表格）

### 结构对比

（插入步骤3的对比分析）

## 发现的问题

| 序号 | 问题类型 | 问题描述 | 严重程度 | 修复建议 | 状态 |
|------|---------|---------|---------|---------|------|
| 1 | 目录缺失 | src/views/permission/ 目录不存在 | 高 | 创建权限管理目录 | 待修复 |
| 2 | 文件缺失 | src/api/permission.js 文件不存在 | 高 | 创建权限API文件 | 待修复 |
| 3 | 命名不规范 | src/components/UserList.vue 应为 user-list.vue | 中 | 重命名为 kebab-case | 待修复 |

**问题统计**：
- 高严重程度：X个
- 中严重程度：X个
- 低严重程度：X个
- 总计：X个

## 检查结论

### 整体评估

- **基础目录结构**：✅ 通过 / ❌ 不通过
- **功能模块目录**：✅ 通过 / ❌ 不通过
- **组件组织**：✅ 通过 / ❌ 不通过
- **路由配置**：✅ 通过 / ❌ 不通过
- **API文件组织**：✅ 通过 / ❌ 不通过

### 修复建议

**P0（必须修复）**：
1. {问题描述}

**P1（建议修复）**：
1. {问题描述}

**P2（可选修复）**：
1. {问题描述}

### 检查结论

✅ **前端文件夹结构检查通过**：可以继续进行其他检查

或

❌ **前端文件夹结构检查不通过**：需要修复问题后重新检查
```

## 检查执行规范

### 检查前准备

1. **清理上下文**：移除与前端结构检查无关的历史对话
2. **加载指南**：读取本检查指南
3. **读取设计文档**：读取前端架构设计文档
4. **确认moduleCode**：从 dev-task.md 或 metadata 文件读取

### 检查完成后

1. **生成报告**：将检查报告保存到 `design-docs/testing-validation/{功能名称}-前端文件夹结构检查报告.md`
2. **更新进度文件**：将检查结论写入 dev-task.md
3. **Git提交**：提交检查报告
4. **上下文清理**：仅保留关键结论到进度文件
5. **后续选择**：询问用户是否继续进行前端代码实现检查或后端检查

## 相关文档

- [前端架构设计文档](design-docs/frontend-architecture/{功能名称}前端架构设计.md)
- [前端项目结构指南](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md)
- [上下文清理规范](CONTEXT-CLEANING.md)
