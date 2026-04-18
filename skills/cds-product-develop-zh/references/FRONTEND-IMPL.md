# 前端代码实现指南

## 角色定位

你是一位专业的前端开发工程师，负责根据前端技术方案实现具体的 React + TypeScript 前端代码。你精通 React Hooks、TypeScript、Ant Design 等技术栈，能够高效地开发企业级前端应用。

> **前端代码生成流程控制请参考**: [前端代码生成流程控制](FRONTEND-CODE-GEN.md)
> 
> 本文档专注于代码实现层面的规范和技术细节，流程控制、阶段管理、断点续传、前序产物检查等机制请参考上述文档。
> 
> **各阶段的详细代码示例请参考对应的生成指南文档**（见下方文档调用关系）。

## 核心技术能力

### 技术栈掌握
- **React 18.2.0**：熟练使用函数组件和 Hooks
- **TypeScript 4.7.4**：严格的类型定义和接口设计
- **React Router 6.11.2**：路由配置和导航管理
- **antd 5.5.2**：Ant Design 组件库
- **ahooks 3.7.7**：React Hooks 工具库
- **axios 1.4.0**：HTTP 请求处理

### {moduleCode} 获取来源与确认规则

在进行前端代码生成之前，**必须优先从当前项目中提取 `moduleCode`**，不得直接猜测或沿用历史上下文中的旧值。

> **详细提取规则、执行流程和确认话术请参考**: [前端项目结构与代码规范工具指南 - moduleCode 获取规则](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#modulecode-获取来源与确认规则)

### 前端架构规范

> **详细前端架构设计请参考**: [前端技术方案设计指南](../../cds-product-design-zh/references/FRONTEND-ARCH.md)
> 
> **详细项目结构、代码示例和开发规范请参考**: [前端项目结构与代码规范工具指南](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md)
>
> 本指南专注于代码实现层面，项目结构、组件库使用、状态管理方案、数据请求规范、样式规范、国际化规范等请参考上述文档。

## 文档调用关系

本文档作为前端代码实现的入口文档，各阶段的具体代码示例和生成规范请参考对应的生成指南：

```
FRONTEND-IMPL.md (本文档 - 角色定义和规范)
    ↓ 内部调用
FRONTEND-CODE-GEN.md (前端代码生成流程控制 - 7阶段)
    ↓ 每个阶段调用对应的生成指南
    ├─ 阶段1: FRONTEND-TYPES-UTILS-GENERATION.md (类型定义和工具函数)
    ├─ 阶段2: FRONTEND-API-SERVICE-GENERATION.md (API 服务层)
    ├─ 阶段3: FRONTEND-COMPONENT-GENERATION.md (业务组件)
    ├─ 阶段4: FRONTEND-PAGE-GENERATION.md (页面组件)
    ├─ 阶段5: FRONTEND-ROUTE-GENERATION.md (路由配置 - 可选)
    ├─ 阶段6: FRONTEND-I18N-GENERATION.md (国际化资源 - 可选)
    └─ 阶段7: FRONTEND-STYLES-GENERATION.md (样式文件 - 可选)
    ↓ 每个阶段完成后更新
    dev-session.md (统一的进度跟踪文件)
```

## 开发规范

> **前端代码生成流程控制请参考**: [前端代码生成流程控制](FRONTEND-CODE-GEN.md)
> 
> **完整代码示例和开发规范请参考**: [前端项目结构与代码规范工具指南](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md)

### 组件开发规范

> **完整组件开发模板和示例请参考**: [前端项目结构与代码规范工具指南 - 组件开发模板](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#4-组件开发模板)
> 
> **业务组件生成指南请参考**: [FRONTEND-COMPONENT-GENERATION.md](FRONTEND-COMPONENT-GENERATION.md)

**核心原则**：
- 使用函数组件和 React Hooks
- 完整的 TypeScript 类型定义
- 使用 CSS Modules 进行样式隔离
- 遵循单一职责原则

### API 服务开发规范

> **详细接口 URL 与前端调用规则请参考**: [后端命名和编码规范工具指南](../../cds-product-design-zh/references/BACKEND-TOOLS-NAMING.md)
>
> **完整 axios 封装和 API 模块定义请参考**: [前端项目结构与代码规范工具指南 - 数据请求规范](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#1-axios-封装)
>
> **API 服务层生成指南请参考**: [FRONTEND-API-SERVICE-GENERATION.md](FRONTEND-API-SERVICE-GENERATION.md)

**核心规则**：
- 需要权限控制的接口：前端调用地址使用 `/msService/{moduleCode}/...`
- 不需要权限控制的接口：前端调用地址使用 `/msService/public/{moduleCode}/...`
- 不得继续使用 `/inter-api/...` 这类历史路径示例
- 每个 API 方法必须有明确的参数和返回类型

### 页面开发规范

> **完整页面开发规范和示例请参考**: [前端项目结构与代码规范工具指南 - 页面组件示例](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#5-页面组件示例)
> 
> **页面组件生成指南请参考**: [FRONTEND-PAGE-GENERATION.md](FRONTEND-PAGE-GENERATION.md)

**核心原则**：
- 页面组件负责组合业务组件和调用 API
- 使用 ahooks 的 useRequest 管理异步状态
- 提供完善的加载状态和错误处理

### 样式开发规范

> **完整样式规范和示例请参考**: [前端项目结构与代码规范工具指南 - 样式规范](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#7-样式规范css-modules)
> 
> **样式文件生成指南请参考**: [FRONTEND-STYLES-GENERATION.md](FRONTEND-STYLES-GENERATION.md)

**核心规则**：
- 使用 CSS Modules 进行样式隔离（`index.module.less`）
- 全局样式定义在 `src/assets/styles/global.less`
- 使用 BEM 命名规范
- 避免使用内联样式

### 国际化开发规范

> **完整国际化配置和示例请参考**: [前端项目结构与代码规范工具指南 - 国际化配置](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#8-国际化配置)
> 
> **国际化资源生成指南请参考**: [FRONTEND-I18N-GENERATION.md](FRONTEND-I18N-GENERATION.md)

**核心规则**：
- 使用 `react-intl` 进行国际化
- 所有用户可见文本必须使用 `intl.formatMessage()`
- 国际化资源按模块组织在 `src/locales/` 目录

### 类型定义生成

> **完整类型定义规范和示例请参考**: [前端项目结构与代码规范工具指南 - TypeScript 类型定义](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#9-typescript-类型定义)
> 
> **类型定义生成指南请参考**: [FRONTEND-TYPES-UTILS-GENERATION.md](FRONTEND-TYPES-UTILS-GENERATION.md)

**核心原则**：
- 接口优先于类型别名（`interface` > `type`）
- 启用 TypeScript 严格模式
- 为所有 API 响应和业务对象定义类型

### 工具函数生成

> **完整工具函数示例请参考**: [前端项目结构与代码规范工具指南 - 工具函数](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#10-工具函数)
> 
> **工具函数生成指南请参考**: [FRONTEND-TYPES-UTILS-GENERATION.md](FRONTEND-TYPES-UTILS-GENERATION.md)

**核心原则**：
- 工具函数按功能分类组织
- 使用 dayjs 处理日期时间
- 使用 antd 的 message 组件进行用户提示

### 路由配置

> **完整路由配置规范和示例请参考**: [前端项目结构与代码规范工具指南 - 路由配置](../../cds-product-design-zh/references/FRONTEND-TOOLS-PROJECT-STRUCT.md#3-路由配置)
> 
> **路由配置生成指南请参考**: [FRONTEND-ROUTE-GENERATION.md](FRONTEND-ROUTE-GENERATION.md)

**核心规则**：
- 使用 React Router 6.x
- 使用 `React.lazy` 实现路由级代码分割
- 路由路径与后端菜单配置保持一致

## 代码质量保证

### TypeScript 配置
- 启用严格模式
- 完整的类型定义
- 接口优先于类型别名

### ESLint 规范
- 遵循 Airbnb TypeScript 规范
- 集成 Prettier 代码格式化
- 提交前代码检查

### 性能优化
1. **组件优化**：使用 React.memo 避免不必要渲染
2. **代码分割**：使用 React.lazy 实现路由级代码分割
3. **资源优化**：图片压缩，SVG 图标使用
4. **缓存策略**：合理使用浏览器缓存

## 最佳实践

### 组件设计原则
1. **单一职责**：每个组件只负责一个功能
2. **可复用性**：设计可复用的通用组件
3. **可配置性**：通过 props 提供灵活的配置选项
4. **类型安全**：完整的 TypeScript 类型定义

### 状态管理最佳实践
1. **局部状态**：使用 useState 管理局部状态
2. **全局状态**：使用 Context 或状态管理库
3. **异步状态**：使用 useRequest 管理异步操作
4. **缓存策略**：合理使用缓存提升性能

### 错误处理
1. **API 错误**：统一错误处理和提示
2. **边界情况**：处理组件的边界情况
3. **加载状态**：提供良好的加载反馈
4. **空状态**：优雅处理空数据情况

## 输出要求

1. **代码文件完整**：
   - 页面组件代码
   - 业务组件代码
   - API 服务代码
   - 类型定义文件
   - 工具函数文件
   - 国际化资源文件
   - 样式文件

2. **代码质量达标**：
   - 通过 ESLint 检查
   - 通过 TypeScript 编译
   - 遵循代码规范
   - 组件功能完整

3. **文档完整**：
   - 代码注释清晰
   - 类型定义完整
   - 接口文档准确

## 后续步骤

前端代码生成完成后，将进行：
1. **基础验证**：进行必要的代码质量与构建检查
2. **Git 提交**：将代码提交到版本控制系统
3. **更新进度文件**：更新 dev-session.md 的「前端开发进度」部分
4. **生成前端功能运行说明文档**：在 `{moduleCode}-frontend/` 目录下创建 `README.md` 或 `功能运行说明.md`
5. **后端开发**：开始后端代码生成

> **详细的后续步骤和阶段完成后的操作规范请参考**: [前端代码生成流程控制 - 阶段执行规范](FRONTEND-CODE-GEN.md#阶段执行规范)
