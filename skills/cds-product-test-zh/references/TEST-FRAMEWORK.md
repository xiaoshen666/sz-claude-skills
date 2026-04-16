# 测试框架详细指南

## 环境配置要求

### CodeLink环境配置
- Nacos地址配置
- 数据库连接配置
- 国际化服务地址配置
- Redis地址配置（如需要）
- Kafka地址配置（如需要）
- 其他微服务依赖配置

### 测试工具配置
- 前端测试工具：Jest、Cypress等
- 后端测试工具：JUnit、TestNG等
- 性能测试工具：JMeter、LoadRunner等
- 接口测试工具：Postman、Swagger等

## 测试阶段详细说明

### 阶段1：设计阶段检查验收

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

### 阶段3：代码编译启动测试

| 步骤 | 名称 | 详细指南 | 核心交付物 |
|------|------|---------|-----------|
| 3.1 | 测试环境初始化 | TEST-SETUP.md | test-session.md、测试环境配置 |
| 3.2 | CDS环境依赖配置 | ENV-CONFIG.md | CDS环境配置文件、服务连接信息 |
| 3.3 | 测试工具安装 | TEST-TOOLS.md | 测试工具配置完成、依赖安装完成 |
| 3.4 | 前端编译测试 | FRONTEND-BUILD.md | 前端编译报告、静态资源生成 |
| 3.5 | 后端编译测试 | BACKEND-BUILD.md | 后端编译报告、jar包/war包生成 |
| 3.6 | 前端启动测试 | FRONTEND-START.md | 前端启动报告、服务健康检查 |
| 3.7 | 后端启动测试 | BACKEND-START.md | 后端启动报告、服务健康检查、API连通性测试 |
| 3.8 | CDS集成启动测试 | INTEGRATION-START.md | CDS集成启动报告、端到端连通性测试 |
| 3.9 | 功能测试执行 | FUNCTIONAL-TEST.md | 功能测试报告、测试用例执行结果 |
| 3.10 | 性能测试执行 | PERFORMANCE-TEST.md | 性能测试报告、性能指标数据 |
