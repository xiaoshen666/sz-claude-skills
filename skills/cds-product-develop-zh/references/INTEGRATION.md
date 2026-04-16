# 模块集成测试调度指南

## 概述

本指南用于调度模块开发完成后的各项检查工作。集成测试包含多个独立的检查项，用户可以根据需要选择执行。

## 检查流程

```
模块开发完成（选项A和B已执行）
  ↓
步骤1：前后端接口一致性检查（必选）
  ├─ 检查前端接口调用与后端接口定义的一致性
  ├─ 检查URL规范符合性
  └─ 输出报告到 design-docs/testing-validation/
  ↓
询问用户：是否继续进行前端检查？
  ├─ 是 → 步骤2：前端文件夹层级结构检查
  │        └─ 输出报告到 design-docs/testing-validation/
  │        ↓
  │      询问用户：是否继续进行前端代码实现检查？
  │        ├─ 是 → 步骤3：前端代码实现检查
  │        │        └─ 输出报告到 design-docs/testing-validation/
  │        └─ 否 → 跳到步骤4
  └─ 否 → 跳到步骤4
  ↓
步骤4：后端文件夹层级结构检查
  ├─ 检查后端包结构和类文件位置
  └─ 输出报告到 design-docs/testing-validation/
  ↓
询问用户：是否继续进行后端代码实现检查？
  ├─ 是 → 步骤5：后端代码实现检查
  │        └─ 输出报告到 design-docs/testing-validation/
  └─ 否 → 完成
  ↓
生成集成测试总结报告
  ↓
Git提交 + 更新进度文件
```

## 检查项列表

### 必选检查

| 序号 | 检查项 | 文档 | 说明 |
|------|--------|------|------|
| 1 | 前后端接口一致性检查 | [TEST-API-CONFORMANCE-CHECK.md](../../cds-product-test-zh/references/TEST-API-CONFORMANCE-CHECK.md) | 检查前后端接口是否一致，URL是否符合规范 |

### 前端检查（可选）

| 序号 | 检查项 | 文档 | 说明 |
|------|--------|------|------|
| 2 | 前端文件夹层级结构检查 | [TEST-FRONTEND-STRUCTURE-CHECK.md](../../cds-product-test-zh/references/TEST-FRONTEND-STRUCTURE-CHECK.md) | 检查前端项目结构是否符合前端设计 |
| 3 | 前端代码实现检查 | [TEST-FRONTEND-IMPLEMENTATION-CHECK.md](../../cds-product-test-zh/references/TEST-FRONTEND-IMPLEMENTATION-CHECK.md) | 检查前端代码是否符合前端设计文档 |

### 后端检查（可选）

| 序号 | 检查项 | 文档 | 说明 |
|------|--------|------|------|
| 4 | 后端文件夹层级结构检查 | [TEST-BACKEND-STRUCTURE-CHECK.md](../../cds-product-test-zh/references/TEST-BACKEND-STRUCTURE-CHECK.md) | 检查后端包结构和类文件位置是否符合后端设计 |
| 5 | 后端代码实现检查 | [TEST-BACKEND-IMPLEMENTATION-CHECK.md](../../cds-product-test-zh/references/TEST-BACKEND-IMPLEMENTATION-CHECK.md) | 检查后端代码实现、类命名、初始化语句是否符合设计要求 |

## 检查执行流程

### 步骤1：前后端接口一致性检查

**执行文档**：[TEST-API-CONFORMANCE-CHECK.md](../../cds-product-test-zh/references/TEST-API-CONFORMANCE-CHECK.md)

**检查内容**：
1. 收集前端接口调用
2. 收集后端接口定义
3. 接口对比分析
4. URL规范符合性检查（遵循 [BACKEND-TOOLS-NAMING.md](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md) 规则）
5. 生成检查报告

**报告输出**：`design-docs/testing-validation/{功能名称}-接口一致性检查报告.md`

**完成后询问**：
```markdown
接口一致性检查已完成，报告已保存到 design-docs/testing-validation/ 目录。

是否继续进行前端文件夹层级结构检查？
- 回复 **是**：继续检查前端项目结构
- 回复 **否**：跳过后端检查
```

### 步骤2：前端文件夹层级结构检查

**执行文档**：[TEST-FRONTEND-STRUCTURE-CHECK.md](../../cds-product-test-zh/references/TEST-FRONTEND-STRUCTURE-CHECK.md)

**检查内容**：
1. 读取前端架构设计文档
2. 检查实际前端项目结构
3. 基础目录结构检查
4. 功能模块目录检查
5. 组件组织检查
6. 路由配置检查
7. API文件组织检查
8. 生成检查报告

**报告输出**：`design-docs/testing-validation/{功能名称}-前端文件夹结构检查报告.md`

**完成后询问**：
```markdown
前端文件夹结构检查已完成，报告已保存到 design-docs/testing-validation/ 目录。

是否继续进行前端代码实现检查？
- 回复 **是**：继续检查前端代码实现
- 回复 **否**：跳过后端检查
```

### 步骤3：前端代码实现检查

**执行文档**：[TEST-FRONTEND-IMPLEMENTATION-CHECK.md](../../cds-product-test-zh/references/TEST-FRONTEND-IMPLEMENTATION-CHECK.md)

**检查内容**：
1. 读取前端设计文档
2. 检查组件实现
3. 检查样式实现
4. 检查API调用实现
5. 检查状态管理
6. 检查代码质量
7. 生成检查报告

**报告输出**：`design-docs/testing-validation/{功能名称}-前端代码实现检查报告.md`

**完成后**：继续执行步骤4（后端检查）

### 步骤4：后端文件夹层级结构检查

**执行文档**：[TEST-BACKEND-STRUCTURE-CHECK.md](../../cds-product-test-zh/references/TEST-BACKEND-STRUCTURE-CHECK.md)

**检查内容**：
1. 读取后端架构设计文档
2. 检查实际后端项目结构
3. 基础包结构检查
4. 功能模块包检查
5. 配置文件检查
6. 资源文件检查
7. 关键类文件检查
8. 生成检查报告

**报告输出**：`design-docs/testing-validation/{功能名称}-后端文件夹结构检查报告.md`

**完成后询问**：
```markdown
后端文件夹结构检查已完成，报告已保存到 design-docs/testing-validation/ 目录。

是否继续进行后端代码实现检查？
- 回复 **是**：继续检查后端代码实现
- 回复 **否**：完成集成测试
```

### 步骤5：后端代码实现检查

**执行文档**：[TEST-BACKEND-IMPLEMENTATION-CHECK.md](../../cds-product-test-zh/references/TEST-BACKEND-IMPLEMENTATION-CHECK.md)

**检查内容**：
1. 读取后端设计文档
2. 检查类命名规范
3. 检查文件位置
4. 检查注解使用
5. 检查初始化语句
6. 检查API实现
7. 生成检查报告

**报告输出**：`design-docs/testing-validation/{功能名称}-后端代码实现检查报告.md`

**完成后**：生成集成测试总结报告

## 生成集成测试总结报告

**输出位置**：`design-docs/testing-validation/`

**报告文件名**：`{功能名称}-集成测试总结报告.md`

**报告模板**：

```markdown
# {功能名称} - 集成测试总结报告

## 基本信息

- **模块名称**：{功能模块名称}
- **moduleCode**：{模块编码}
- **测试时间**：{YYYY-MM-DD HH:mm:ss}
- **测试人员**：{测试人员}

## 检查概述

### 执行的检查项

| 序号 | 检查项 | 是否执行 | 检查结果 | 报告文件 |
|------|--------|---------|---------|---------|
| 1 | 前后端接口一致性检查 | ✅ 已执行 | ✅ 通过 / ❌ 不通过 | {功能名称}-接口一致性检查报告.md |
| 2 | 前端文件夹层级结构检查 | ✅ 已执行 / ❌ 未执行 | ✅ 通过 / ❌ 不通过 | {功能名称}-前端文件夹结构检查报告.md |
| 3 | 前端代码实现检查 | ✅ 已执行 / ❌ 未执行 | ✅ 通过 / ❌ 不通过 | {功能名称}-前端代码实现检查报告.md |
| 4 | 后端文件夹层级结构检查 | ✅ 已执行 / ❌ 未执行 | ✅ 通过 / ❌ 不通过 | {功能名称}-后端文件夹结构检查报告.md |
| 5 | 后端代码实现检查 | ✅ 已执行 / ❌ 未执行 | ✅ 通过 / ❌ 不通过 | {功能名称}-后端代码实现检查报告.md |

### 检查统计

| 检查项 | 问题总数 | 高严重程度 | 中严重程度 | 低严重程度 |
|--------|---------|-----------|-----------|-----------|
| 接口一致性检查 | X | X | X | X |
| 前端文件夹结构检查 | X | X | X | X |
| 前端代码实现检查 | X | X | X | X |
| 后端文件夹结构检查 | X | X | X | X |
| 后端代码实现检查 | X | X | X | X |
| **总计** | **X** | **X** | **X** | **X** |

## 问题汇总

### 高严重程度问题

| 序号 | 检查项 | 问题描述 | 修复建议 | 状态 |
|------|--------|---------|---------|------|
| 1 | 接口一致性 | 前端调用 /update，后端定义为 /modify | 统一为 /update | 待修复 |
| 2 | 后端代码实现 | PermissionController 未实现 | 创建权限管理Controller | 待修复 |

### 中严重程度问题

| 序号 | 检查项 | 问题描述 | 修复建议 | 状态 |
|------|--------|---------|---------|------|
| 1 | 前端代码实现 | 使用硬编码颜色值 | 改用主题变量 | 待修复 |

### 低严重程度问题

| 序号 | 检查项 | 问题描述 | 修复建议 | 状态 |
|------|--------|---------|---------|------|
| 1 | 前端文件夹结构 | 部分文件缺少注释 | 补充注释 | 待修复 |

## 测试结论

### 整体评估

- **接口一致性**：✅ 通过 / ❌ 不通过
- **前端结构**：✅ 通过 / ❌ 不通过 / ⚪ 未检查
- **前端实现**：✅ 通过 / ❌ 不通过 / ⚪ 未检查
- **后端结构**：✅ 通过 / ❌ 不通过 / ⚪ 未检查
- **后端实现**：✅ 通过 / ❌ 不通过 / ⚪ 未检查

### 修复建议

**P0（必须修复）**：
1. {问题描述}

**P1（建议修复）**：
1. {问题描述}

**P2（可选修复）**：
1. {问题描述}

### 测试结论

✅ **集成测试通过**：模块开发完成，可以进入下一阶段

或

❌ **集成测试不通过**：需要修复高严重程度问题后重新测试
```

## 检查执行规范

### 检查前准备

1. **清理上下文**：移除与集成测试无关的历史对话
2. **加载指南**：读取本调度指南
3. **确认前置条件**：确认前后端代码已生成完成
4. **确认moduleCode**：从 dev-task.md 或 metadata 文件读取

### 检查执行中

1. **按顺序执行**：按照步骤1-5逐一执行（用户可选择跳过）
2. **记录结果**：详细记录每个检查项的结果
3. **问题标记**：对发现的问题标记严重程度
4. **用户确认**：每个检查项完成后询问用户是否继续

### 检查完成后

1. **生成总结报告**：将所有检查报告汇总为集成测试总结报告
2. **更新进度文件**：将测试结论和关键问题写入 dev-task.md
3. **Git提交**：提交所有检查报告和总结报告
4. **上下文清理**：移除测试过程中的中间信息，仅保留关键结论
5. **阶段完成**：标记模块开发完成，询问用户是否继续下一个模块

## 相关文档

- [前后端接口一致性检查指南](../../cds-product-test-zh/references/TEST-API-CONFORMANCE-CHECK.md)
- [前端文件夹层级结构检查指南](../../cds-product-test-zh/references/TEST-FRONTEND-STRUCTURE-CHECK.md)
- [前端代码实现检查指南](../../cds-product-test-zh/references/TEST-FRONTEND-IMPLEMENTATION-CHECK.md)
- [后端文件夹层级结构检查指南](../../cds-product-test-zh/references/TEST-BACKEND-STRUCTURE-CHECK.md)
- [后端代码实现检查指南](../../cds-product-test-zh/references/TEST-BACKEND-IMPLEMENTATION-CHECK.md)
- [后端命名和编码规范工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md)
- [上下文清理规范](CONTEXT-CLEANING.md)
