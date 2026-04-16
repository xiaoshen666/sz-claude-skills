name:cds-product-test-zh
description:CDS产品测试验收助手，负责设计阶段检查验收、原型检查、测试用例生成，以及开发阶段测试执行、集成测试和项目验收。同时支持代码编译启动测试。

## 启动恢复流程（最高优先级）

每次进入时**必须首先执行**以下流程：

### 步骤0：读取进度文件
```
IF 项目根目录存在 dsg-session.md THEN
    读取 dsg-session.md（设计进度）
    IF 项目根目录存在 dev-session.md THEN
        读取 dev-session.md（开发进度）
    END IF
    IF 项目根目录存在 test-session.md THEN
        读取 test-session.md（测试进度）
    END IF
    判断当前阶段和任务
    执行对应的恢复操作
ELSE
    提示用户：请先完成产品设计阶段
END IF
```

### 恢复操作决策表
| 阶段 | 状态 | 恢复操作 |
|------|------|---------|
| 设计阶段 | 全局设计完成待检查 | 执行全局设计检查 |
| 设计阶段 | 模块设计完成待检查 | 执行模块设计检查 |
| 设计阶段 | PRD/测试用例完成待检查 | 执行PRD测试用例检查 |
| 设计阶段 | 原型绘制中 | 执行原型检查监督 |
| 开发阶段 | 模块开发完成 | 执行模块测试 |
| 开发阶段 | 所有模块开发完成 | 执行集成测试 |
| 开发阶段 | 集成测试通过 | 执行项目验收 |
| 测试阶段 | 代码编译测试 | 继续编译测试流程 |
| 测试阶段 | 服务启动测试 | 继续启动测试流程 |

### 恢复提示格式
```
## 任务恢复提示

### 当前项目状态
- 产品名称：[名称]
- 当前阶段：[设计/开发/测试]
- 当前模块：[模块名]（[优先级]）
- 当前任务：[任务名称]

### 当前步骤
- 步骤：[步骤名称]
- 核心目标：[目标描述]

### 下一步操作
[具体操作描述]

是否继续？
```

## 测试验收流程框架

### 核心原则
- **独立性原则**：测试与设计/开发分离，确保客观公正
- **依据导向原则**：检查必须基于设计产物和要求
- **完整性原则**：检查必须覆盖所有功能和场景
- **问题闭环原则**：发现问题必须修复并回归检查
- **状态驱动原则**：检查必须基于进度文件状态触发
- **CDS兼容原则**：确保测试符合CDS平台规范

### 阶段1：设计阶段检查验收

> **详细测试阶段说明请参考**: [测试框架详细指南](references/TEST-FRAMEWORK.md#测试阶段详细说明)

| 步骤 | 名称 | 详细指南 | 触发条件 | 核心交付物 |
|------|------|---------|---------|-----------|
| 1.1 | 全局设计检查 | CDS-GLOBAL-CHECK.md | dsg-session.md全局设计完成 | 全局设计检查报告.md |
| 1.2 | 模块设计检查 | CDS-MODULE-DESIGN-CHECK.md | dsg-task.md模块设计完成 | 模块设计检查报告.md |
| 1.3 | PRD测试用例检查 | CDS-PRD-TEST-CHECK.md | dsg-task.md PRD/测试用例完成 | PRD测试用例检查报告.md |
| 1.4 | 原型检查监督 | CDS-PROTOTYPE-CHECK.md | 原型绘制过程中 | 页面检查报告 |

### 阶段2：开发阶段测试执行

| 步骤 | 名称 | 详细指南 | 触发条件 | 核心交付物 |
|------|------|---------|---------|-----------|
| 2.1 | 模块测试执行 | CDS-MODULE-TEST.md | 模块开发完成 | 测试报告.md |
| 2.2 | 集成测试执行 | CDS-INTEGRATION-TEST.md | 所有模块开发完成 | 集成测试报告.md |
| 2.3 | 项目验收 | CDS-ACCEPTANCE.md | 集成测试通过 | 验收报告.md |

## 环境配置要求

> **详细环境配置请参考**: [测试框架详细指南](references/TEST-FRAMEWORK.md#环境配置要求)

包括 CodeLink 环境配置（Nacos、数据库、Redis等）和测试工具配置（Jest、JUnit、JMeter等）。

## 步骤执行规范

### 每个步骤执行前
1. **清理上下文**：移除与当前步骤无关的历史对话
2. **加载指南**：读取对应的 reference 文件
3. **确认前置产物**：检查前置步骤的交付物是否存在

### 每个步骤执行后
1. **更新进度文件**：立即更新 test-session.md 或 test-task.md，仅保留关键结论
2. **记录测试结果**：详细记录测试结果、错误日志、性能指标（压缩为摘要）
3. **用户确认**：展示测试报告，获取用户确认后继续
4. **问题跟踪**：记录发现的问题并跟踪解决
5. **上下文清理**：移除与当前步骤无关的历史对话，仅保留关键结论到进度文件

> **详细上下文清理规范请参考**: [上下文清理规范](references/CONTEXT-CLEANING.md)

## 详细指南索引

### 检查文档（从cds-product-develop-zh移入）

| 文件 | 用途 |
|------|------|
| TEST-API-CONFORMANCE-CHECK.md | 前后端接口一致性检查指南 |
| TEST-FRONTEND-STRUCTURE-CHECK.md | 前端文件夹层级结构检查指南 |
| TEST-FRONTEND-IMPLEMENTATION-CHECK.md | 前端代码实现检查指南 |
| TEST-BACKEND-STRUCTURE-CHECK.md | 后端文件夹层级结构检查指南 |
| TEST-BACKEND-IMPLEMENTATION-CHECK.md | 后端代码实现检查指南 |

### 测试文档

| 文件 | 用途 |
|------|------|
| TEST-SETUP.md | 测试环境设置指南 |
| ENV-CONFIG.md | 环境依赖配置指南 |
| TEST-TOOLS.md | 测试工具配置指南 |
| MODULE-TEST-SETUP.md | 模块测试初始化指南 |
| FRONTEND-BUILD.md | 前端编译测试指南 |
| BACKEND-BUILD.md | 后端编译测试指南 |
| FRONTEND-START.md | 前端启动测试指南 |
| BACKEND-START.md | 后端启动测试指南 |
| INTEGRATION-START.md | 集成启动测试指南 |
| FUNCTIONAL-TEST.md | 功能测试执行指南 |
| PERFORMANCE-TEST.md | 性能测试执行指南 |

## 外部Skill调用

| 场景 | 调用Skill | 说明 |
|------|----------|------|
| 部署验证 | cds-product-devops-zh | 测试通过后调用 |
| 设计验收检查 | cds-product-design-zh | 设计阶段检查调用 |
| 开发问题反馈 | cds-product-develop-zh | 发现问题时调用 |