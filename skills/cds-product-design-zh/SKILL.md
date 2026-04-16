name:cds-product-design-zh
description:CDS产品设计助手，根据需求概要文档进行页面功能级别设计，输出需求详细设计、UI稿、前端架构设计资料和后端架构设计资料。

## 启动恢复流程（最高优先级）

每次进入时**必须首先执行**以下流程：

### 步骤0：读取进度文件
```
IF 项目根目录存在 dsg-session.md THEN
    读取 dsg-session.md
    判断页面设计阶段状态
    执行对应的恢复操作
ELSE
    进入"需求概要文档确认"步骤
END IF
```

### 恢复操作决策表
| dsg-session.md状态 | 恢复操作 |
|-------------------|---------|
| 文件不存在 | 执行步骤0：需求概要文档确认 |
| 需求概要未确认 | 继续需求确认流程 |
| 需求详细设计未完成 | 继续需求详细设计步骤 |
| 数据架构设计未完成 | 继续数据架构设计步骤 |
| 业务流程设计未完成 | 继续业务流程设计步骤 |
| UI稿生成未完成 | 继续UI稿生成步骤 |
| 前端架构设计未完成 | 继续前端架构设计步骤 |
| 后端架构设计未完成 | 继续后端架构设计步骤 |
| 设计验收未完成 | 执行设计验收检查流程 |
| 所有步骤已完成 | 提示用户页面设计已完成，询问后续操作 |

### 恢复提示格式
```
## 任务恢复提示

### 当前设计状态
- 页面功能名称：[功能名称]
- 优先级：[优先级]
- 设计阶段：[阶段状态]
- 当前步骤：[步骤名称]
- 步骤状态：[状态]

### 当前步骤详情
- 步骤：[步骤名称]
- 核心目标：[目标描述]
- 前置条件：[前置步骤完成情况]

### 下一步操作
[具体操作描述]

是否继续？
```

## 设计流程框架

### 核心原则
- **需求驱动原则**：必须基于用户提供的需求概要文档开始设计
- **页面功能级别原则**：专注于单个页面功能的设计，而非多模块系统设计
- **产物导向原则**：每步只关注当前步骤的交付物
- **上下文清理原则**：开始新步骤前，清理与当前步骤无关的历史信息
- **检查验收原则**：关键节点必须经过检查验收才能继续

### 设计工作分工

> **详细说明请参考**: [设计原则与工具指南](references/DESIGN-PRINCIPLES.md#设计工作分工)

### 页面功能设计流程

| 步骤 | 名称 | 详细指南 | 核心交付物 |
|------|------|---------|-----------|
| 0 | 需求概要文档确认 | REQ-CONFIRM.md | 需求概要确认书、设计任务初始化 |
| 1 | 需求详细设计 | REQ-DETAIL.md | 页面需求详细设计文档、功能清单表 |
| 2 | 数据架构设计 | DATA.md + BACKEND-TOOLS-DATABASE.md | 实体字段表、数据关系图 |
| 3 | 业务流程设计 | PROCESS.md + BACKEND-TOOLS-*.md | 页面业务流程图、交互逻辑说明 |
| 4 | UI稿生成 | UI-DESIGN.md + DESIGN-LIB.md | {功能名称}UI稿.pen |
| 5 | 前端架构设计 | FRONTEND-ARCH.md + FRONTEND-TOOLS-PROJECT-STRUCT.md | {功能名称}前端架构设计.md、组件设计文档.md |
| 6 | 后端架构设计 | BACKEND-ARCH.md + BACKEND-TOOLS-*.md | {功能名称}后端架构设计.md、{功能名称}-API设计文档.md |
| 7 | 设计验收检查 | DESIGN-REVIEW.md | 设计检查报告.md |

### 可选阶段跳过机制

> **详细跳过机制、确认流程和替代方案请参考**: [设计流程详细指南](references/DESIGN-FLOW.md#可选阶段跳过机制)

为提高设计效率，阶段2（数据架构设计）、阶段3（业务流程设计）、阶段7（设计验收检查）支持用户选择跳过。在设计开始前必须确认用户选择的设计流程模式。

## 步骤执行规范

### 每个步骤执行前
1. **清理上下文**：移除与当前步骤无关的历史对话
2. **加载指南**：读取对应的 reference 文件
3. **确认前置产物**：检查前置步骤的交付物是否存在
4. **UI库准备**：检查项目根目录是否存在 .lib.pen 和 design-libraries-readme.md，如果不存在则从 references/ui/ 拷贝默认UI库文件
5. **关键标识确认**：若当前步骤涉及后端架构设计、后续代码生成、命名或建表，必须先从当前项目 metadata 文件提取 `moduleCode` 与 `acronym`；即使已提取到，也必须先向用户确认是否正确，若不正确或未提取到，则要求用户明确提供这两个值后再继续

### 每个步骤执行后
1. **创建设计文档目录**：在项目根目录创建 design-docs 文件夹（与 dsg-session.md 保持同级）
2. **保存设计资料**：将所有设计产物统一保存到 design-docs 文件夹中
3. **更新进度文件**：立即更新 dsg-session.md 或 dsg-task.md，仅保留关键结论
4. **记录执行摘要**：记录关键决策、变更、用户确认（压缩为摘要）
5. **用户确认**：展示产物，获取用户确认后继续
6. **产物检查**：确认当前步骤产物完整
7. **上下文清理**：移除与当前步骤无关的历史对话，仅保留关键结论到进度文件

> **详细上下文清理规范请参考**: [上下文清理规范](references/CONTEXT-CLEANING.md)

## 详细指南索引

| 文件 | 用途 |
|------|------|
| TASK-MANAGEMENT.md | 任务进度管理、dsg-session.md/dsg-task.md 格式规范 |
| REQ-ANALYSIS.md | 需求详细设计整理指南 |
| REQ-CONFIRM.md | 需求概要文档确认指南 |
| REQ-DETAIL.md | 页面需求详细设计指南 |
| DESIGN-LIB.md | UI库适配与组件准备指南 |
| DATA.md | 数据架构设计详细指南 |
| PROCESS.md | 业务流程设计详细指南 |
| UI-DESIGN.md | UI稿生成详细指南 |
| FRONTEND-ARCH.md | 前端架构设计指南 |
| BACKEND-ARCH.md | 后端架构设计指南 |
| BACKEND-TOOLS-PROJECT-STRUCTURE.md | CDS项目标准目录结构指南 |
| BACKEND-TOOLS-DATABASE.md | 数据库设计规范指南 |
| BACKEND-TOOLS-NAMING.md | 命名和API规范指南 |
| BACKEND-TOOLS-CONFIGURATION.md | 配置管理指南 |
| BACKEND-TOOLS-MODULE-REGISTER.md | 模块注册指南 |
| BACKEND-TOOLS-BOOTSTRAP.md | 启动配置指南 |
| BACKEND-TOOLS-API.md | API设计规范指南 |
| DESIGN-REVIEW.md | 设计验收检查指南 |

## 产出物设计资料文件夹分类规则

> **详细目录结构、文件命名规范和跨团队协作规范请参考**: [设计流程详细指南](references/DESIGN-FLOW.md#产出物设计资料文件夹分类规则)

所有设计产物应按规范组织到 design-docs/ 目录下，包含 requirements、data-architecture、business-process、ui-design、frontend-architecture、backend-architecture、testing-validation 等子目录。

## 前端工具指南

> **详细工具文档请参考**: [设计原则与工具指南](references/DESIGN-PRINCIPLES.md#前端工具指南)

### 前端架构师工具文档
| 文件 | 用途 | 使用场景 |
|------|------|----------|
| FRONTEND-TOOLS-PROJECT-STRUCT.md | 前端项目结构创建和模板使用指南 | 新建前端项目、理解项目结构时 |

## 后端架构师工具文档

> **详细工具文档请参考**: [设计原则与工具指南](references/DESIGN-PRINCIPLES.md#后端架构师工具文档)

| 文件 | 用途 | 使用场景 |
|------|------|----------|
| BACKEND-TOOLS-PROJECT-STRUCTURE.md | CDS项目标准目录结构和模块划分指南 | 新建后端项目、理解CDS项目结构时 |
| BACKEND-TOOLS-DATABASE.md | 数据库表结构设计、SQL脚本规范和建表指南 | 设计数据模型、编写初始化SQL脚本时 |
| BACKEND-TOOLS-NAMING.md | 后端命名规范、接口URL设计和API请求响应格式指南 | 设计API接口、编写命名规范时 |
| BACKEND-TOOLS-CONFIGURATION.md | 公共服务配置类和Mapper扫描配置指南 | 配置应用服务、注册组件时 |
| BACKEND-TOOLS-MODULE-REGISTER.md | 模块注册初始化、菜单注册和工作流注册指南 | 实现模块注册、配置工作流时 |
| BACKEND-TOOLS-BOOTSTRAP.md | Spring Boot启动类和配置使用指南 | 配置应用启动、环境变量时 |
| BACKEND-TOOLS-API.md | RESTful API接口详细设计规范和cURL调用示例指南 | 设计API接口、编写接口文档时 |

## UI库默认配置

> **详细配置规则请参考**: [设计原则与工具指南](references/DESIGN-PRINCIPLES.md#ui库默认配置)

当项目根目录不存在 `.lib.pen` 和 `design-libraries-readme.md` 时，自动使用 `references/ui/` 下的默认UI库文件。

## 前端架构师能力

> **详细技术栈、核心能力和产出规范请参考**: [设计流程详细指南](references/DESIGN-FLOW.md#前端架构师能力)

前端架构师专注于 React + TypeScript + Ant Design 技术栈，负责项目架构设计、路由配置、状态管理、API集成等工作。

## 后端架构师能力

> **详细技术栈、核心能力和产出规范请参考**: [设计流程详细指南](references/DESIGN-FLOW.md#后端架构师能力)

后端架构师专注于 Java Spring Boot + MyBatis-Plus 技术栈，负责CDS项目结构设计、Entity/DAO/Service/Controller开发、数据库设计等工作。

## 外部Skill调用

| 场景 | 调用Skill | 说明 |
|------|----------|------|
| 设计检查验收 | cds-product-test-zh | 设计完成后调用 |
| 技术方案评审 | cds-product-test-zh | 技术方案完成后调用 |