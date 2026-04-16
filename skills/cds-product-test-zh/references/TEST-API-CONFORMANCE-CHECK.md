# 前后端接口一致性检查指南

## 概述

本指南用于检查前端接口调用与后端接口定义的一致性，以及URL规范符合性。检查完成后，结果输出到 `design-docs/testing-validation/` 目录。

## 检查前提

1. ✅ 前端代码已生成完成（选项A已执行）
2. ✅ 后端代码已生成完成（选项B已执行）
3. ✅ 已确认 `moduleCode` 和 `acronym`

## 检查步骤

### 步骤1：收集前端接口调用

**查找位置**：
- `{moduleCode}-frontend/src/api/` 目录下的API文件
- `{moduleCode}-frontend/src/services/` 目录下的服务文件
- 组件中的 `axios` 或 `fetch` 调用

**记录内容**：
```markdown
### 前端接口调用列表

| 序号 | 功能说明 | 前端调用URL | 请求方法 | 参数 | 所在文件 |
|------|---------|------------|---------|------|---------|
| 1 | 保存数据 | /msService/{moduleCode}/save | POST | DTO对象 | src/api/xxx.js |
| 2 | 查询列表 | /msService/{moduleCode}/list | POST | 查询条件 | src/api/xxx.js |
| 3 | 删除数据 | /msService/{moduleCode}/delete | GET | id | src/api/xxx.js |
```

### 步骤2：收集后端接口定义

**查找位置**：
- `{moduleCode}-custom/src/main/java/.../controller/` 目录下的Controller类
- `@RequestMapping`、`@GetMapping`、`@PostMapping` 等注解

**记录内容**：
```markdown
### 后端接口定义列表

| 序号 | 功能说明 | 后端接口URL | 请求方法 | 参数 | 所在文件 |
|------|---------|------------|---------|------|---------|
| 1 | 保存数据 | /{moduleCode}/save | POST | DTO对象 | XxxController.java |
| 2 | 查询列表 | /{moduleCode}/list | POST | 查询条件 | XxxController.java |
| 3 | 删除数据 | /{moduleCode}/delete | GET | id | XxxController.java |
```

### 步骤3：接口对比分析

**对比规则**：
- 前端调用URL去掉 `/msService` 前缀后，应与后端接口URL一致
- 请求方法（GET/POST/PUT/DELETE）必须一致
- 接口数量必须一致
- 参数类型和数量应一致

**记录格式**：
```markdown
### 接口一致性检查结果

| 前端URL（去网关前缀） | 后端URL | 前端方法 | 后端方法 | 是否一致 | 问题描述 |
|---------------------|---------|---------|---------|---------|---------|
| /{moduleCode}/save | /{moduleCode}/save | POST | POST | ✅ 一致 | - |
| /{moduleCode}/list | /{moduleCode}/list | POST | POST | ✅ 一致 | - |
| /{moduleCode}/delete | /{moduleCode}/delete | GET | GET | ✅ 一致 | - |

**检查结果**：
- 接口总数：X个
- 一致接口：X个
- 不一致接口：X个（详见问题列表）
```

### 步骤4：URL规范符合性检查

根据 [后端命名和编码规范工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md#url-设计规则)：

#### 规则检查表

| 规则编号 | 规则内容 | 检查结果 |
|---------|---------|---------|
| 规则1 | 不得直接使用无模块前缀的裸路径 | ✅ 通过 / ❌ 未通过 |
| 规则2 | 公共开放接口必须使用 `/public/{moduleCode}/` 前缀 | ✅ 通过 / ❌ 未通过 |
| 规则3 | 需要权限控制的接口使用 `/{moduleCode}/` 前缀 | ✅ 通过 / ❌ 未通过 |
| 规则4 | 前端调用统一使用 `/msService` 作为网关前缀 | ✅ 通过 / ❌ 未通过 |
| 规则5 | 同一功能模块内的接口路径命名应保持风格一致 | ✅ 通过 / ❌ 未通过 |

#### 详细检查结果

```markdown
### 规则1：无模块前缀的裸路径检查

| 接口URL | 是否符合 | 问题描述 | 修复建议 |
|---------|---------|---------|---------|
| /{moduleCode}/save | ✅ 符合 | - | - |
| /save | ❌ 不符合 | 缺少模块前缀 | 改为 /{moduleCode}/save |

### 规则2：公共接口 /public/ 前缀检查

| 接口URL | 是否需要权限 | 是否使用/public/ | 是否符合 | 问题描述 |
|---------|------------|-----------------|---------|---------|
| /public/{moduleCode}/health | 否 | 是 | ✅ 符合 | - |
| /{moduleCode}/health | 否 | 否 | ❌ 不符合 | 应改为 /public/{moduleCode}/health |

### 规则3：权限控制接口前缀检查

| 接口URL | 是否需要权限 | 是否使用{moduleCode}/ | 是否符合 | 问题描述 |
|---------|------------|---------------------|---------|---------|
| /{moduleCode}/save | 是 | 是 | ✅ 符合 | - |
| /api/save | 是 | 否 | ❌ 不符合 | 应改为 /{moduleCode}/save |

### 规则4：前端网关前缀检查

| 前端调用URL | 是否包含/msService | 是否符合 | 问题描述 |
|------------|-------------------|---------|---------|
| /msService/{moduleCode}/save | 是 | ✅ 符合 | - |
| /{moduleCode}/save | 否 | ❌ 不符合 | 应改为 /msService/{moduleCode}/save |

### 规则5：接口命名风格一致性检查

| 接口URL | 命名风格 | 是否一致 | 问题描述 |
|---------|---------|---------|---------|
| /{moduleCode}/save | 动词 | ✅ 一致 | - |
| /{moduleCode}/list | 动词 | ✅ 一致 | - |
| /{moduleCode}/deleteData | 动词+名词 | ❌ 不一致 | 应改为 /{moduleCode}/delete |
```

### 步骤5：生成检查报告

**输出位置**：`design-docs/testing-validation/`

**报告文件名**：`{功能名称}-接口一致性检查报告.md`

**报告模板**：

```markdown
# {功能名称} - 前后端接口一致性检查报告

## 基本信息

- **模块名称**：{功能模块名称}
- **moduleCode**：{模块编码}
- **检查时间**：{YYYY-MM-DD HH:mm:ss}
- **检查人员**：{检查人员}

## 检查概述

### 接口一致性

- **接口总数**：X个
- **一致接口**：X个（X%）
- **不一致接口**：X个（X%）

### URL规范符合性

| 规则 | 符合数量 | 不符合数量 | 符合率 |
|------|---------|-----------|-------|
| 规则1：无裸路径 | X | X | X% |
| 规则2：公共接口前缀 | X | X | X% |
| 规则3：权限接口前缀 | X | X | X% |
| 规则4：前端网关前缀 | X | X | X% |
| 规则5：命名风格一致 | X | X | X% |
| **总计** | **X** | **X** | **X%** |

## 详细检查结果

### 接口一致性检查

（插入步骤3的对比表格）

### URL规范检查

（插入步骤4的详细检查结果）

## 发现的问题

| 序号 | 问题类型 | 问题描述 | 严重程度 | 修复建议 | 状态 |
|------|---------|---------|---------|---------|------|
| 1 | 接口不一致 | 前端调用 /update，后端定义为 /modify | 高 | 统一为 /update | 待修复 |
| 2 | URL规范 | 接口 /save 缺少模块前缀 | 高 | 改为 /{moduleCode}/save | 待修复 |
| 3 | URL规范 | 前端缺少 /msService 网关前缀 | 中 | 添加 /msService 前缀 | 待修复 |

**问题统计**：
- 高严重程度：X个
- 中严重程度：X个
- 低严重程度：X个
- 总计：X个

## 检查结论

### 整体评估

- **接口一致性**：✅ 通过 / ❌ 不通过
- **URL规范性**：✅ 通过 / ❌ 不通过

### 修复建议

**P0（必须修复）**：
1. {问题描述}

**P1（建议修复）**：
1. {问题描述}

**P2（可选修复）**：
1. {问题描述}

### 检查结论

✅ **接口一致性检查通过**：可以继续进行其他检查

或

❌ **接口一致性检查不通过**：需要修复问题后重新检查
```

## 检查执行规范

### 检查前准备

1. **清理上下文**：移除与接口检查无关的历史对话
2. **加载指南**：读取本检查指南
3. **确认前置条件**：确认前后端代码已生成完成
4. **确认moduleCode**：从 dev-task.md 或 metadata 文件读取

### 检查完成后

1. **生成报告**：将检查报告保存到 `design-docs/testing-validation/{功能名称}-接口一致性检查报告.md`
2. **更新进度文件**：将检查结论写入 dev-task.md
3. **Git提交**：提交检查报告
4. **上下文清理**：仅保留关键结论到进度文件
5. **后续选择**：询问用户是否继续进行前端文件夹层级结构检查或后端检查

## 相关文档

- [后端命名和编码规范工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md)
- [前端代码实现指南](FRONTEND-IMPL.md)
- [后端代码实现指南](BACKEND-IMPL.md)
- [上下文清理规范](CONTEXT-CLEANING.md)
