# 开发原则与工具指南

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

### 后端开发能力

后端工程师负责基于 Java Spring Boot + MyBatis-Plus 技术栈实现后端代码，包含：
- Entity/DAO/Service/Controller 开发
- 数据库设计与实现
- API接口开发
- 配置管理
- 模块注册

**关键标识确认**：`moduleCode` 与 `acronym` 必须优先从后端技术实现方案文档读取，若缺失则从 metadata 文件提取，必须向用户确认。

**前序设计产物检查**：优先查找 `{功能名称}-需求详细设计.md`、`{功能名称}-后端架构设计.md`、`{功能名称}-API设计文档.md`

## 工具文档引用

### 后端开发工具指南

| 工具文档 | 用途 | 使用场景 |
|---------|------|----------|
| [项目结构工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md) | CDS项目目录结构和模块划分 | 新建项目、理解项目结构时 |
| [后端业务代码生成工具指南](references/BACKEND-TOOLS-CODE-GENERATION.md) | Entity / BO / VO / DAO / Mapper / Service / Controller 业务代码模板 | 后端业务逻辑代码生成时 |
| [配置管理工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md) | 公共服务配置和模块配置 | 配置类开发、包扫描配置时 |
| [数据库规范工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md) | 数据库设计和SQL脚本规范 | 数据库设计、表结构创建时 |
| [模块注册工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md) | 模块注册、菜单注册、工作流注册 | 模块初始化、系统集成时 |

> **说明**: 后端业务代码模板已从实现指南中抽离到 `cds-product-develop-zh/references/BACKEND-TOOLS-CODE-GENERATION.md`，供开发阶段直接引用。模块注册、菜单注册、工作流注册等设计仍然保留在 `cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md` 中，供需要注册代码时引用。
