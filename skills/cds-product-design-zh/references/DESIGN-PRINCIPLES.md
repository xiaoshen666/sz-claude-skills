# 设计原则与工具指南

## 设计工作分工

- **UI稿设计工作**：由 DESIGN.md（UI-DESIGN.md）承接，输出页面UI稿.pen和UI设计规范
- **前端设计工作**：由 FRONTEND-ARCH.md 承接，输出前端架构设计文档和组件设计文档  
- **数据架构设计、业务流程设计**：由 BACKEND-ARCH.md 承接，输出实体字段表、数据关系图、页面业务流程图、交互逻辑说明
- **后端设计工作**：由 BACKEND-ARCH.md 承接，输出后端架构设计文档和API设计文档

## 前端工具指南

### 前端架构师工具文档
| 文件 | 用途 | 使用场景 |
|------|------|----------|
| FRONTEND-TOOLS-PROJECT-STRUCT.md | 前端项目结构创建和模板使用指南 | 新建前端项目、理解项目结构时 |

## 后端架构师工具文档

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

### CDS标准UI库文件
- **位置**: `references/ui/`
- **组件库文件**: `cds-components.lib.pen`
- **设计规范文件**: `cds-design-libraries-readme.md`

### 使用规则
1. 当项目根目录不存在 `.lib.pen` 和 `design-libraries-readme.md` 时
2. 自动使用 `references/ui/cds-components.lib.pen` 作为默认组件库
3. 自动使用 `references/ui/cds-design-libraries-readme.md` 作为默认设计规范
4. 确保新项目能快速使用CDS标准UI库进行原型设计
