# 后端模块注册引用说明

## 说明

本文件仅作为 `cds-product-develop-zh` 中的兼容占位说明，不再承载业务代码模板。

## 正确引用方式

### 模块注册相关代码

若当前后端实现任务涉及以下内容，请引用 `cds-product-design-zh` 中的模块注册设计文档：

- ModuleRegisterInitialize
- 模块注册
- 菜单注册
- 工作流服务注册
- 工作流页面注册

对应文档：`../../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md`

### 业务逻辑代码

若当前后端实现任务涉及以下业务代码生成，请引用新的代码生成工具文档：

- Entity
- BO / VO
- DAO / Mapper
- Service 接口与实现
- Controller

对应文档：`BACKEND-TOOLS-CODE-GENERATION.md`

## 使用原则

1. 模块注册与业务代码模板分离维护
2. 设计阶段模块注册规则保持引用 `cds-product-design-zh`
3. 开发阶段业务代码模板统一引用 `cds-product-develop-zh/references/BACKEND-TOOLS-CODE-GENERATION.md`
