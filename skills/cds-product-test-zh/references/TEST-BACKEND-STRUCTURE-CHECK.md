# 后端文件夹层级结构检查指南

## 概述

本指南用于检查后端项目的文件夹层级结构是否符合后端设计文档的要求。检查完成后，结果输出到 `design-docs/testing-validation/` 目录。

## 检查前提

1. ✅ 后端代码已生成完成（选项B已执行）
2. ✅ 后端架构设计文档已存在
3. ✅ 已确认 `moduleCode` 和 `acronym`

## 检查步骤

### 步骤1：读取后端架构设计文档

**查找位置**：
- `design-docs/backend-architecture/{功能名称}后端架构设计.md`
- `design-docs/backend-architecture/BACKEND-ARCH.md`

**提取内容**：
- 期望的目录结构
- 包结构规范
- 关键类位置
- 配置文件位置

### 步骤2：检查实际后端项目结构

**查找位置**：
- `{moduleCode}-custom/` 目录

**检查内容**：

#### 2.1 基础包结构

```markdown
### 基础包结构检查

| 包路径 | 是否必需 | 实际存在 | 路径正确 | 备注 |
|--------|---------|---------|---------|------|
| com.supcon.nebule.{moduleCode}.custom | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.controller | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.service | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.service.impl | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.dao | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.entity | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.bo | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.vo | ✅ 是 | ✅ 是 | ✅ 是 | - |
| com.supcon.nebule.{moduleCode}.custom.dto | ⚠️ 可选 | ✅ 是 | ✅ 是 | Feign接口需要 |
| com.supcon.nebule.{moduleCode}.custom.api | ⚠️ 可选 | ✅ 是 | ✅ 是 | Feign接口需要 |
```

#### 2.2 功能模块包

根据后端架构设计文档中定义的功能模块，检查对应包是否存在：

```markdown
### 功能模块包检查

| 功能模块 | 期望包路径 | 实际存在 | 结构正确 | 备注 |
|---------|-----------|---------|---------|------|
| 用户管理 | custom.controller.user | ✅ 是 | ✅ 是 | - |
| 角色管理 | custom.controller.role | ✅ 是 | ✅ 是 | - |
| 权限管理 | custom.controller.permission | ❌ 否 | - | 缺失包 |
```

#### 2.3 配置文件

```markdown
### 配置文件检查

| 配置文件 | 期望位置 | 实际位置 | 是否符合 | 备注 |
|---------|---------|---------|---------|------|
| application.yml | {moduleCode}-custom/src/main/resources/ | 正确位置 | ✅ 符合 | - |
| bootstrap.properties | {moduleCode}-bootstrap/src/main/resources/ | 正确位置 | ✅ 符合 | - |
| mybatis-config.xml | {moduleCode}-custom/src/main/resources/ | 正确位置 | ✅ 符合 | - |
```

#### 2.4 资源文件

```markdown
### 资源文件检查

| 资源类型 | 期望位置 | 实际位置 | 是否符合 | 备注 |
|---------|---------|---------|---------|------|
| Mapper XML | src/main/resources/mapper/ | 正确位置 | ✅ 符合 | - |
| SQL脚本 | src/main/resources/sql/ | 正确位置 | ✅ 符合 | - |
| 配置文件 | src/main/resources/config/ | 正确位置 | ✅ 符合 | - |
```

### 步骤3：检查关键类文件

#### 3.1 Controller类

```markdown
### Controller类检查

| 类名 | 期望位置 | 实际位置 | 命名规范 | 注解正确 | 是否符合 |
|------|---------|---------|---------|---------|---------|
| UserController | custom.controller | ✅ 存在 | ✅ 符合 | ✅ 正确 | ✅ 符合 |
| RoleController | custom.controller | ✅ 存在 | ✅ 符合 | ✅ 正确 | ✅ 符合 |
| PermissionController | custom.controller | ❌ 缺失 | - | - | ❌ 不符合 |
```

#### 3.2 Service类

```markdown
### Service类检查

| 类名 | 期望位置 | 实际位置 | 接口+实现 | 命名规范 | 是否符合 |
|------|---------|---------|----------|---------|---------|
| UserService | custom.service | ✅ 存在 | ✅ 有 | ✅ 符合 | ✅ 符合 |
| RoleService | custom.service | ✅ 存在 | ✅ 有 | ✅ 符合 | ✅ 符合 |
| PermissionService | custom.service | ❌ 缺失 | - | - | ❌ 不符合 |
```

#### 3.3 DAO/Mapper类

```markdown
### DAO/Mapper类检查

| 类名 | 期望位置 | 实际位置 | Mapper XML | 命名规范 | 是否符合 |
|------|---------|---------|-----------|---------|---------|
| UserDAO | custom.dao | ✅ 存在 | ✅ 有 | ✅ 符合 | ✅ 符合 |
| RoleDAO | custom.dao | ✅ 存在 | ✅ 有 | ✅ 符合 | ✅ 符合 |
| PermissionDAO | custom.dao | ❌ 缺失 | - | - | ❌ 不符合 |
```

#### 3.4 Entity/BO/VO类

```markdown
### Entity/BO/VO类检查

| 类名 | 类型 | 期望位置 | 实际位置 | 命名规范 | 注解完整 | 是否符合 |
|------|------|---------|---------|---------|---------|---------|
| UserEntity | Entity | custom.entity | ✅ 存在 | ✅ 符合 | ✅ 完整 | ✅ 符合 |
| UserBO | BO | custom.bo | ✅ 存在 | ✅ 符合 | ✅ 完整 | ✅ 符合 |
| UserVO | VO | custom.vo | ✅ 存在 | ✅ 符合 | ✅ 完整 | ✅ 符合 |
```

### 步骤4：对比分析

**对比规则**：
- 实际包结构应与后端架构设计文档中定义的结构一致
- 关键类和文件必须存在
- 文件命名应符合设计规范
- 包层级深度应符合规范

**记录格式**：

```markdown
### 结构对比分析

**设计文档要求的结构**：
```
{moduleCode}-custom/
└── src/main/java/
    └── com/supcon/nebule/{moduleCode}/custom/
        ├── controller/
        │   ├── UserController.java
        │   ├── RoleController.java
        │   └── PermissionController.java
        ├── service/
        │   ├── UserService.java
        │   ├── UserServiceImpl.java
        │   └── ...
        ├── dao/
        │   ├── UserDAO.java
        │   └── ...
        ├── entity/
        ├── bo/
        └── vo/
```

**实际存在的结构**：
```
{moduleCode}-custom/
└── src/main/java/
    └── com/supcon/nebule/{moduleCode}/custom/
        ├── controller/
        │   ├── UserController.java ✅
        │   ├── RoleController.java ✅
        │   └── PermissionController.java ❌ 缺失
        ├── service/
        │   ├── UserService.java ✅
        │   ├── UserServiceImpl.java ✅
        │   └── ... ✅
        ├── dao/
        │   ├── UserDAO.java ✅
        │   └── ... ✅
        ├── entity/ ✅
        ├── bo/ ✅
        └── vo/ ✅
```

**差异分析**：
- 缺失类：PermissionController.java
- 缺失包：custom.controller.permission/
- 多余类：无
- 多余包：无
```

### 步骤5：生成检查报告

**输出位置**：`design-docs/testing-validation/`

**报告文件名**：`{功能名称}-后端文件夹结构检查报告.md`

**报告模板**：

```markdown
# {功能名称} - 后端文件夹层级结构检查报告

## 基本信息

- **模块名称**：{功能模块名称}
- **moduleCode**：{模块编码}
- **acronym**：{模块缩略码}
- **检查时间**：{YYYY-MM-DD HH:mm:ss}
- **检查人员**：{检查人员}

## 检查概述

### 结构符合性

- **必需包**：X个，存在X个，缺失X个
- **可选包**：X个，存在X个，缺失X个
- **必需类**：X个，存在X个，缺失X个
- **包层级**：最大X层

### 检查统计

| 检查项 | 总数 | 符合 | 不符合 | 符合率 |
|--------|------|------|--------|-------|
| 基础包结构 | X | X | X | X% |
| 功能模块包 | X | X | X | X% |
| 配置文件 | X | X | X | X% |
| 资源文件 | X | X | X | X% |
| Controller类 | X | X | X | X% |
| Service类 | X | X | X | X% |
| DAO/Mapper类 | X | X | X | X% |
| Entity/BO/VO类 | X | X | X | X% |
| **总计** | **X** | **X** | **X** | **X%** |

## 详细检查结果

### 基础包结构检查

（插入步骤2.1的检查表格）

### 功能模块包检查

（插入步骤2.2的检查表格）

### 配置文件检查

（插入步骤2.3的检查表格）

### 资源文件检查

（插入步骤2.4的检查表格）

### 关键类文件检查

（插入步骤3的检查表格）

### 结构对比

（插入步骤4的对比分析）

## 发现的问题

| 序号 | 问题类型 | 问题描述 | 严重程度 | 修复建议 | 状态 |
|------|---------|---------|---------|---------|------|
| 1 | 类缺失 | PermissionController.java 不存在 | 高 | 创建权限管理Controller | 待修复 |
| 2 | 包缺失 | custom.controller.permission/ 包不存在 | 高 | 创建权限管理包 | 待修复 |
| 3 | 命名不规范 | userServiceImpl.java 应为 UserServiceImpl.java | 中 | 重命名类文件 | 待修复 |

**问题统计**：
- 高严重程度：X个
- 中严重程度：X个
- 低严重程度：X个
- 总计：X个

## 检查结论

### 整体评估

- **基础包结构**：✅ 通过 / ❌ 不通过
- **功能模块包**：✅ 通过 / ❌ 不通过
- **配置文件**：✅ 通过 / ❌ 不通过
- **资源文件**：✅ 通过 / ❌ 不通过
- **关键类文件**：✅ 通过 / ❌ 不通过

### 修复建议

**P0（必须修复）**：
1. {问题描述}

**P1（建议修复）**：
1. {问题描述}

**P2（可选修复）**：
1. {问题描述}

### 检查结论

✅ **后端文件夹结构检查通过**：可以继续进行其他检查

或

❌ **后端文件夹结构检查不通过**：需要修复问题后重新检查
```

## 检查执行规范

### 检查前准备

1. **清理上下文**：移除与后端结构检查无关的历史对话
2. **加载指南**：读取本检查指南
3. **读取设计文档**：读取后端架构设计文档
4. **确认moduleCode和acronym**：从 dev-task.md 或 metadata 文件读取

### 检查完成后

1. **生成报告**：将检查报告保存到 `design-docs/testing-validation/{功能名称}-后端文件夹结构检查报告.md`
2. **更新进度文件**：将检查结论写入 dev-task.md
3. **Git提交**：提交检查报告
4. **上下文清理**：仅保留关键结论到进度文件
5. **后续选择**：询问用户是否继续进行后端代码实现检查

## 相关文档

- [后端架构设计文档](design-docs/backend-architecture/{功能名称}后端架构设计.md)
- [后端项目结构指南](../../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md)
- [上下文清理规范](CONTEXT-CLEANING.md)
