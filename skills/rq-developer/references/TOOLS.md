# 开发工具参考

## 前端技术栈

| 类别 | 轻量级 | 企业级 |
|------|--------|--------|
| 框架 | Vue3 | React |
| 构建 | Vite | Vite |
| 路由 | Vue Router | React Router |
| 状态管理 | Pinia | Redux Toolkit |
| 表单验证 | VeeValidate | React Hook Form |
| HTTP请求 | Axios | Axios |
| UI组件 | ShadCN UI / Tailwind CSS | ShadCN UI / Tailwind CSS |

## 后端技术栈

| 类别 | 轻量级 | 企业级 |
|------|--------|--------|
| 框架 | Node.js Express | Python FastAPI |
| ORM | better-sqlite3 | SQLAlchemy |
| 数据验证 | Joi / Zod | Pydantic |
| 认证 | jsonwebtoken | python-jose |
| API文档 | 手写 | 自动生成 |

## 数据库

| 类别 | 轻量级 | 企业级 |
|------|--------|--------|
| 数据库 | SQLite | MySQL |
| 迁移工具 | 手写SQL脚本 | Alembic |
| 连接方式 | 文件路径 | 连接字符串 |

## 测试工具

| 类别 | 工具 |
|------|------|
| 前端单元测试 | Vitest / Jest |
| 后端单元测试 | Pytest / Jest |
| 端到端测试 | Playwright / Cypress |

## 动画库

| 工具 | 用途 |
|------|------|
| GSAP | 高性能动画 |
| Motion One | 轻量级动画 |
| Lottie | JSON动画 |
| Three.js | 3D动画 |

## 运维工具

| 类别 | 工具 |
|------|------|
| 版本控制 | Git |
| 容器化 | Docker |
| CI/CD | GitHub Actions / GitLab CI |
| 反向代理 | Nginx |

## 小型项目技术选型建议

### 前端推荐方案
1. **Vue3 + Vite + Pinia + Element Plus**
   - 适合：快速开发，学习曲线平缓
   - 优点：官方生态完善，社区活跃
   - 适用：中小型管理后台、工具类应用

2. **React + Vite + Zustand + Ant Design**
   - 适合：需要更好类型支持的项目
   - 优点：TypeScript支持好，组件库成熟
   - 适用：数据密集型应用、需要复杂状态管理的项目

### 后端推荐方案
1. **Node.js + Express + Prisma + SQLite**
   - 适合：快速原型、简单业务逻辑
   - 优点：开发速度快，部署简单
   - 适用：个人项目、小型API服务

2. **Python + FastAPI + SQLAlchemy + PostgreSQL**
   - 适合：需要数据科学能力或复杂业务逻辑
   - 优点：代码简洁，异步支持好
   - 适用：数据分析应用、机器学习服务

### 数据库选择指南
1. **SQLite**：单文件数据库，无需服务，适合个人项目、原型验证
2. **PostgreSQL**：功能完整的关系型数据库，适合需要复杂查询和事务的项目
3. **MongoDB**：文档型数据库，适合数据结构变化频繁的项目

## 开发工具配置

### 版本控制
- **Git**：基础版本控制
- **Git Flow**：简化版分支策略（main, develop, feature/*）
- **提交规范**：使用约定式提交（Conventional Commits）

### 代码质量
- **ESLint**：JavaScript/TypeScript代码检查
- **Prettier**：代码格式化
- **Husky**：Git钩子，提交前检查

### 构建部署
- **Vite**：前端构建工具
- **Docker**：容器化部署
- **GitHub Actions**：CI/CD流水线

## 小型项目最佳实践

### 架构简化
1. 避免过度设计，使用简单的分层架构
2. 优先考虑开发效率，而不是架构完美
3. 使用成熟的技术栈，避免实验性技术

### 代码组织
1. 按功能模块组织代码，而不是按技术类型
2. 保持文件结构扁平，避免过深嵌套
3. 使用清晰的命名约定

### 部署运维
1. 选择简单的部署方案（如Vercel、Railway）
2. 配置基础的健康检查和监控
3. 建立简单的备份策略