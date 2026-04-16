# 开发原则与工具指南

## 概述

本文档提供 CDS 产品开发的能力概述和工具文档索引，是 SKILL.md 中"开发者能力文档"和"工具文档引用"章节的详细参考。

> **本文档在整体流程中的位置**：
> - 被 SKILL.md 引用，作为能力概览和工具索引
> - 不提供具体的执行流程，而是提供高层次的能力说明和文档引用
> - 具体的执行细节请参考对应的 IMPL.md 和 CODE-GEN.md 文档

## 开发者能力文档

### 前端开发能力

前端工程师负责基于 React + TypeScript + Ant Design 技术栈实现前端代码，包含：
- 组件开发与优化
- API服务封装
- 路由配置
- 状态管理
- 样式实现
- 国际化支持

**前序设计产物检查**：优先查找 `.pen` 文件、`UI设计规范.md`、`{功能名称}-前端架构设计.md`、`{功能名称}-需求详细设计.md`

**流程控制机制**：前端代码生成采用 7 阶段流程控制机制
- 阶段1：类型定义和工具函数生成
- 阶段2：API 服务层生成
- 阶段3：业务组件生成
- 阶段4：页面组件生成
- 阶段5：路由配置生成（可选）
- 阶段6：国际化资源生成（可选）
- 阶段7：样式文件生成（可选）

**跟踪文件**：`frontend-code-gen-tracker.md`（项目根目录）

**详细文档**：
- 入口文档：[FRONTEND-IMPL.md](FRONTEND-IMPL.md)
- 流程控制：[FRONTEND-CODE-GEN.md](FRONTEND-CODE-GEN.md)
- 上下文清理：[CONTEXT-CLEANING.md](CONTEXT-CLEANING.md)

### 后端开发能力

后端工程师负责基于 Java Spring Boot + MyBatis-Plus 技术栈实现后端代码，包含：
- Entity/DAO/Service/Controller 开发
- 数据库设计与实现
- API接口开发
- 配置管理
- 模块注册

**关键标识确认**：`moduleCode` 与 `acronym` 必须优先从后端技术实现方案文档读取，若缺失则从 metadata 文件提取，必须向用户确认。

**前序设计产物检查**：优先查找 `{功能名称}-需求详细设计.md`、`{功能名称}-后端架构设计.md`、`{功能名称}-API设计文档.md`

**流程控制机制**：后端代码生成采用 9 阶段流程控制机制
- 阶段1：VO、BO和Entity生成
- 阶段2：Controller生成
- 阶段3：Service和ServiceImpl生成
- 阶段4：Dao和Mapper生成
- 阶段5：初始化SQL生成（可选）
- 阶段6：Feign API接口生成（可选）
- 阶段7：启动类代码生成（可选）
- 阶段8：菜单注册配置生成（可选）
- 阶段9：依赖注入配置生成（可选）

**跟踪文件**：`backend-code-gen-tracker.md`（项目根目录）

**详细文档**：
- 入口文档：[BACKEND-IMPL.md](BACKEND-IMPL.md)
- 流程控制：[BACKEND-CODE-GEN.md](BACKEND-CODE-GEN.md)
- 上下文清理：[CONTEXT-CLEANING.md](CONTEXT-CLEANING.md)

## 文档调用关系

### 前端文档调用链

```
SKILL.md (步骤2：前端代码生成)
    ↓ 触发
FRONTEND-IMPL.md (前端代码实现指南)
    ↓ 内部调用
FRONTEND-CODE-GEN.md (前端代码生成流程控制 - 7阶段)
    ↓ 每个阶段调用对应的生成指南
    ├─ 阶段1: FRONTEND-TYPES-UTILS-GENERATION.md
    ├─ 阶段2: FRONTEND-API-SERVICE-GENERATION.md
    ├─ 阶段3: FRONTEND-COMPONENT-GENERATION.md
    ├─ 阶段4: FRONTEND-PAGE-GENERATION.md
    ├─ 阶段5: FRONTEND-ROUTE-GENERATION.md (可选)
    ├─ 阶段6: FRONTEND-I18N-GENERATION.md (可选)
    └─ 阶段7: FRONTEND-STYLES-GENERATION.md (可选)
    ↓ 每个阶段完成后更新
frontend-code-gen-tracker.md (项目根目录的跟踪文件)
```

### 后端文档调用链

```
SKILL.md (步骤4：后端代码生成)
    ↓ 触发
BACKEND-IMPL.md (后端工程师角色实现指南)
    ↓ 内部调用
BACKEND-CODE-GEN.md (后端代码生成流程控制 - 9阶段)
    ↓ 每个阶段调用对应的生成指南
    ├─ 阶段1: BACKEND-ENTITY-BO-VO-GENERATION.md
    ├─ 阶段2: BACKEND-CONTROLLER-GENERATION.md
    ├─ 阶段3: BACKEND-SERVICE-GENERATION.md
    ├─ 阶段4: BACKEND-DAO-MAPPER-GENERATION.md
    ├─ 阶段5: BACKEND-INIT-SQL-GENERATION.md (可选)
    ├─ 阶段6: BACKEND-FEIGN-API-GENERATION.md (可选)
    ├─ 阶段7: BACKEND-TOOLS-BOOTSTRAP.md (可选)
    ├─ 阶段8: BACKEND-TOOLS-MODULE-REGISTER.md (可选)
    └─ 阶段9: BACKEND-TOOLS-CONFIGURATION.md (可选)
    ↓ 每个阶段完成后更新
backend-code-gen-tracker.md (项目根目录的跟踪文件)
```

## 工具文档引用

### 前端开发工具指南

| 工具文档 | 用途 | 使用场景 |
|---------|------|----------|
| [前端代码生成流程控制](FRONTEND-CODE-GEN.md) | 前端代码生成7阶段流程控制和断点续传 | 前端业务逻辑代码生成时 |
| [前端项目结构与代码规范](../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md) | 前端项目目录结构、代码示例和开发规范 | 前端项目初始化、代码开发时 |
| [前端技术方案设计指南](../cds-product-design-zh/references/FRONTEND-ARCH.md) | 前端架构设计和组件拆分方案 | 前端架构设计时 |

### 后端开发工具指南

| 工具文档 | 用途 | 使用场景 |
|---------|------|----------|
| [后端代码生成流程控制](BACKEND-CODE-GEN.md) | 后端代码生成9阶段流程控制和断点续传 | 后端业务逻辑代码生成时 |
| [项目结构工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md) | CDS项目目录结构和模块划分 | 新建项目、理解项目结构时 |
| [配置管理工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md) | 公共服务配置和模块配置 | 配置类开发、包扫描配置时 |
| [数据库规范工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md) | 数据库设计和SQL脚本规范 | 数据库设计、表结构创建时 |
| [模块注册工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md) | 模块注册、菜单注册、工作流注册 | 模块初始化、系统集成时 |
| [启动引导模块工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-BOOTSTRAP.md) | Spring Boot启动类设计和配置 | 启动类代码生成时 |

> **说明**：
> 1. 前端代码生成已采用7阶段流程控制机制，详见 [FRONTEND-CODE-GEN.md](FRONTEND-CODE-GEN.md)
> 2. 后端代码生成已采用9阶段流程控制机制，详见 [BACKEND-CODE-GEN.md](BACKEND-CODE-GEN.md)
> 3. 模块注册、菜单注册、工作流注册等设计保留在 `cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md` 中，供需要注册代码时引用
> 4. 所有跟踪文件（frontend-code-gen-tracker.md、backend-code-gen-tracker.md）创建在项目根目录，便于跨会话恢复

## 上下文清理规范

每个阶段完成后必须执行上下文清理，详见 [CONTEXT-CLEANING.md](CONTEXT-CLEANING.md)。

**清理时机**：
- 每个阶段完成后立即执行
- 对话历史超过50轮时
- 切换到新模块时

**清理内容**：
- 保留：关键结论、阶段状态、生成的文件清单、Git提交记录
- 清理：代码生成过程、调试信息、试错过程、临时文件路径
