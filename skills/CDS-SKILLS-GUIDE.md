# CDS Skills 使用指南

## 概述

这是一套完整的 CDS（Content Delivery System）产品协同技能集，包含四个核心 skill，覆盖了从**页面需求设计**到**代码开发**、**测试验收**、**部署上线**的完整流程。

这四个 skill 的协作并不是简单串行，而是通过以下机制形成闭环：

- **阶段分工**：设计、开发、测试、运维分别承担不同职责
- **产物流转**：上游文档和代码产物作为下游执行输入
- **状态驱动**：通过 session / task 文件判断当前阶段并恢复工作
- **跨 skill 协作**：在关键节点调用其他 skill 完成检查、回流、上线

如果你希望先理解这套体系为什么这样拆分、页面设计与开发流程如何在四个 skill 间流转，建议先阅读 [CDS 四个 Skill 协同原理说明](CDS-SKILLS-COLLABORATION-PRINCIPLES.md)。

## 技能列表

### 1. cds-product-design-zh (产品设计)
**功能：** 根据需求概要文档进行页面功能级别设计，输出需求详细设计、UI稿、前端架构设计资料和后端架构设计资料。

**主要流程：**
- 需求概要文档确认
- 需求详细设计
- 数据架构设计
- 业务流程设计
- UI稿生成
- 前端架构设计
- 后端架构设计
- 设计验收检查

**使用方法：** `/cds-product-design-zh`

### 2. cds-product-develop-zh (产品开发)
**功能：** 依据已有的UI稿和前端技术实现方案生成前端代码，依据已有的后端技术实现方案实现后端代码，生成启动类代码，包含git提交管理方便回退代码与模块集成能力。

**主要流程：**
- 模块开发初始化
- 前端开发（项目结构创建、代码生成）
- 后端开发（项目结构创建、代码生成、启动类代码生成）
- 模块集成测试

**使用方法：** `/cds-product-develop-zh`

### 3. cds-product-test-zh (产品测试)
**功能：** 负责设计阶段检查验收、原型检查、测试用例生成，以及开发阶段测试执行、集成测试和项目验收。同时支持代码编译启动测试。

**主要流程：**
- 设计阶段检查验收（全局设计检查、模块设计检查、PRD测试用例检查、原型检查监督）
- 开发阶段测试执行（模块测试执行、集成测试执行、项目验收）

**使用方法：** `/cds-product-test-zh`

### 4. cds-product-devops-zh (产品运维)
**功能：** 依据已有的项目结构编译构建，通过之后打包代码，压缩之后调用cds接口上传，成功之后触发远程构建安装启动，webhook方式通知企微机器人。

**主要流程：**
- 运维环境准备（运维环境初始化、构建环境配置、部署环境配置、通知系统配置）
- 模块部署（模块部署初始化、项目编译构建、代码打包压缩、CDS接口上传、远程构建触发、远程安装启动、企微通知发送、部署结果验证）

**使用方法：** `/cds-product-devops-zh`

## 使用流程

### 完整页面交付流程
1. **页面设计阶段**：使用 `cds-product-design-zh` 完成需求详细设计、UI 稿、前后端架构设计和 API 设计
2. **页面开发阶段**：使用 `cds-product-develop-zh` 基于设计产物生成和实现代码
3. **页面测试阶段**：使用 `cds-product-test-zh` 进行设计检查、编译启动测试、功能测试和集成测试
4. **页面部署阶段**：使用 `cds-product-devops-zh` 进行构建、上传、远程部署和结果验证

### 页面级协作原理

#### 1. 设计是开发输入，不是孤立文档
设计阶段产出的以下内容，会直接成为开发阶段输入：

- 需求详细设计
- UI 稿 `.pen`
- 前端架构设计文档
- 后端架构设计文档
- API 设计文档

#### 2. 开发是设计落地，不是自由猜测
开发阶段必须优先检查设计产物是否齐全，特别是：

- 前端代码生成依赖 UI 稿、UI 规范、前端架构设计
- 后端代码生成依赖需求详细设计、后端架构设计、API 设计文档
- 若关键输入缺失，需先确认是否允许合理发挥

### 2.1 前端代码生成控制逻辑（7阶段流程）

前端代码生成采用**7阶段流程控制机制**，支持断点续传和状态跟踪：

**阶段划分**：
1. **阶段1：类型定义和工具函数生成**（必选）
   - 生成 TypeScript 类型定义文件
   - 生成通用工具函数
   - 交付物：`src/types/{模块名}.ts`、`src/utils/{功能}.ts`

2. **阶段2：API 服务层生成**（必选）
   - 根据 API 设计文档生成 API 模块
   - 使用 `/msService/{moduleCode}/...` 路径格式
   - 交付物：`src/api/modules/{模块名}.ts`

3. **阶段3：业务组件生成**（必选）
   - 根据前端架构设计识别可复用组件
   - 生成业务组件文件和样式
   - 交付物：`src/components/{组件名}/index.tsx`

4. **阶段4：页面组件生成**（必选）
   - 根据 UI 原型和需求文档生成页面
   - 集成业务组件和 API 服务
   - 交付物：`src/pages/{模块名}/{页面名}.tsx`

5. **阶段5：路由配置生成**（可选）
   - 配置路由懒加载和路由守卫
   - 交付物：`src/routes/index.tsx`

6. **阶段6：国际化资源生成**（可选）
   - 提取文案并生成多语言资源文件
   - 交付物：`src/locales/zh-CN.ts`、`src/locales/en-US.ts`

7. **阶段7：样式文件生成**（可选）
   - 生成全局样式和主题配置
   - 交付物：`src/assets/styles/global.less`

**控制机制**：
- **跟踪文件**：`frontend-code-gen-tracker.md`（项目根目录）
- **断点续传**：每个阶段完成后更新跟踪文件，中断后可从任意阶段恢复
- **前序产物检查**：开始前必须检查设计文档完整性，至少需要 `{功能名称}-需求详细设计.md`
- **阶段顺序**：必须按 1→2→3→4 顺序执行，5→6→7 为可选阶段
- **用户确认**：每个阶段完成后展示生成文件清单，获取确认后继续

### 2.2 后端代码生成控制逻辑（9阶段流程）

后端代码生成采用**9阶段流程控制机制**，支持断点续传和按需生成：

**阶段划分**：
1. **阶段1：VO、BO和Entity生成**（必选）
   - 生成 Entity 类（原生 MyBatis-Plus 实现）
   - 生成 BO 类和 VO 类
   - 交付物：`Custom{ModuleCode}{ModelCode}Entity.java`、`BO.java`、`VO.java`

2. **阶段2：Controller生成**（必选）
   - 根据 API 设计文档生成 Controller
   - 实现对象转换（手动 setter 方式）
   - 交付物：`Custom{ModuleCode}{ModelCode}Controller.java`

3. **阶段3：Service和ServiceImpl生成**（必选）
   - 生成 Service 接口（继承 IService）
   - 生成 ServiceImpl 实现类
   - 实现业务方法（saveBO、info、delete 等）
   - 交付物：`Custom{ModuleCode}{ModelCode}Service.java`、`ServiceImpl.java`

4. **阶段4：Dao和Mapper生成**（必选）
   - 生成 Dao 接口（继承 BaseMapper）
   - 生成 Mapper XML 文件（如需要）
   - 交付物：`Custom{ModuleCode}{ModelCode}Dao.java`、`Mapper.xml`

5. **阶段5：初始化SQL生成**（可选）
   - 根据 Entity 生成建表 SQL
   - 支持多种数据库：Oracle（默认）、SQL Server、MariaDB、DM（达梦）
   - 交付物：`init_{moduleCode}_{modelCode}.sql`

6. **阶段6：Feign API接口生成**（可选）
   - 生成跨服务调用接口
   - 生成 DTO 对象
   - 交付物：`Custom{ModuleCode}{ModelCode}Api.java`、`DTO.java`

7. **阶段7：启动类代码生成**（可选）
   - 生成 Bootstrap 启动类
   - 配置 bootstrap.properties
   - 交付物：`Bootstrap.java`、`bootstrap.properties`

8. **阶段8：菜单注册配置生成**（可选）
   - 生成 ModuleRegisterInitialize
   - 生成菜单注册 SQL
   - 交付物：`{ModuleCode}ModuleRegisterInitialize.java`、`menu_init_{moduleCode}.sql`

9. **阶段9：依赖注入配置生成**（可选）
   - 生成 PubServiceConfiguration 配置类
   - 配置 @EnableFeignClients 和 @MapperScan 包扫描
   - 交付物：`{ModuleCode}PubServiceConfiguration.java`

**控制机制**：
- **跟踪文件**：`backend-code-gen-tracker.md`（项目根目录）
- **断点续传**：每个阶段完成后更新跟踪文件，中断后可从任意阶段恢复
- **可选功能确认**：开始前必须向用户确认阶段5-9是否需要生成
- **前序产物检查**：开始前必须检查设计文档，至少需要 `{功能名称}-需求详细设计.md`
- **关键标识确认**：必须确认 `moduleCode` 和 `acronym`（从文档或 metadata 提取后需用户确认）
- **阶段顺序**：必须按 1→2→3→4 顺序执行，5→6→7→8→9 为可选阶段
- **用户确认**：每个阶段完成后展示生成文件清单，获取确认后继续
- **对象转换规范**：所有阶段都必须使用手动 setter 方式，禁止使用工具类拷贝

#### 3. 测试负责独立验收与问题回流
测试阶段既检查设计，也检查代码与运行结果：

- 设计完成后可做设计检查验收
- 开发完成后可做编译、启动、功能、集成测试
- 发现问题后可回流到开发阶段修复并重新验证

#### 4. 运维负责将通过验证的结果发布到环境
运维阶段承接的是已经可交付的项目结构和构建产物，而不是直接承接需求文档：

- 项目编译构建
- 代码打包压缩
- CDS 接口上传
- 远程构建与安装启动
- 部署验证与通知

### 模块化开发流程
每个 skill 都支持按模块逐步推进，可以按页面或模块循环执行：

1. 设计一个页面/模块
2. 开发一个页面/模块
3. 测试一个页面/模块
4. 部署一个页面/模块
5. 重复以上步骤直到全部完成

### 关键产物流转表

| 上游 Skill | 核心产物 | 下游 Skill | 用途 |
|------------|----------|------------|------|
| `cds-product-design-zh` | 需求详细设计 | `cds-product-develop-zh` / `cds-product-test-zh` | 作为实现与验收依据 |
| `cds-product-design-zh` | UI 稿、前端架构设计 | `cds-product-develop-zh` | 作为前端开发输入 |
| `cds-product-design-zh` | 后端架构设计、API 设计文档 | `cds-product-develop-zh` / `cds-product-test-zh` | 作为后端实现与接口验证依据 |
| `cds-product-develop-zh` | 前端代码（类型定义、API服务、组件、页面） | `cds-product-test-zh` / `cds-product-devops-zh` | 作为前端测试和部署输入 |
| `cds-product-develop-zh` | 后端代码（Entity、Controller、Service、DAO） | `cds-product-test-zh` / `cds-product-devops-zh` | 作为后端测试和部署输入 |
| `cds-product-develop-zh` | 启动类代码、配置文件 | `cds-product-test-zh` / `cds-product-devops-zh` | 作为应用启动入口 |
| `cds-product-develop-zh` | 模块集成产物 | `cds-product-test-zh` | 作为模块集成测试输入 |
| `cds-product-test-zh` | 全局设计检查报告 | `cds-product-design-zh` | 作为设计产物改进依据 |
| `cds-product-test-zh` | 模块设计检查报告 | `cds-product-design-zh` | 作为模块设计改进依据 |
| `cds-product-test-zh` | PRD测试用例检查报告 | `cds-product-design-zh` | 作为测试用例完善依据 |
| `cds-product-test-zh` | 原型检查报告 | `cds-product-design-zh` | 作为原型优化依据 |
| `cds-product-test-zh` | 模块测试报告 | `cds-product-develop-zh` | 作为模块代码修复依据 |
| `cds-product-test-zh` | 集成测试报告 | `cds-product-develop-zh` | 作为集成问题修复依据 |
| `cds-product-test-zh` | 验收报告 | `cds-product-devops-zh` | 作为上线部署依据 |
| `cds-product-devops-zh` | 构建记录、部署结果、通知记录 | 用户 / `cds-product-test-zh` | 作为上线结果和回溯依据 |

### 状态管理机制

四个 skill 都通过进度文件维持长流程状态，使工作可以中断、恢复和跨阶段衔接：

| Skill | session 文件 | task 文件 | 主要作用 |
|------|--------------|-----------|----------|
| `cds-product-design-zh` | `dsg-session.md` | `dsg-task.md` | 记录设计阶段与页面设计任务 |
| `cds-product-develop-zh` | `dev-session.md` | `dev-task.md` | 记录开发阶段与模块开发任务 |
| `cds-product-test-zh` | `test-session.md` | `test-task.md` | 记录测试执行和问题跟踪 |
| `cds-product-devops-zh` | `devops-session.md` | `devops-task.md` | 记录部署进度和部署结果 |

## 核心特性

### 1. 进度持久化
- 每个 skill 都有自己的 session 管理机制
- 支持随时中断和恢复工作
- 详细记录当前阶段、当前模块和执行状态

### 2. 产物导向
- 每个步骤都有明确交付物
- 产物检查机制确保下游输入可用
- 文档、代码、测试报告、部署记录形成完整链路

### 3. 外部 skill 协作
- skill 之间可以在关键节点相互调用
- 设计检查、测试验证、部署监控等环节解耦执行
- 支持问题回流和结果闭环

### 4. 页面级流程驱动
- 围绕单个页面或模块持续推进
- 支持页面设计、页面开发、页面测试、页面上线逐步闭环
- 适合分模块、分批次交付复杂系统

### 5. 输入约束与确认机制
- 关键步骤执行前必须检查前置产物
- 涉及后端命名、建表和生成代码时，需要确认 `moduleCode` 与 `acronym`
- 输入不足时，必须先向用户确认是否允许合理发挥

## 文件结构

每个 skill 通常包含以下结构：
```
skill-name/
├── SKILL.md              # 技能主文档
└── references/           # 参考文档目录
    ├── TASK-MANAGEMENT.md    # 任务管理指南
    ├── DESIGN-FLOW.md        # 设计流程指南
    ├── FRONTEND-IMPL.md      # 前端实现指南
    ├── BACKEND-IMPL.md       # 后端实现指南
    └── ...                   # 其他详细指南
```

## 推荐阅读顺序

1. 先阅读本文，了解四个 skill 的总体定位
2. 再阅读 [CDS 四个 Skill 协同原理说明](CDS-SKILLS-COLLABORATION-PRINCIPLES.md)，理解页面设计与开发流程如何跨 skill 流转
3. 然后按需进入具体 skill：
   - 设计：`cds-product-design-zh/SKILL.md`
   - 开发：`cds-product-develop-zh/SKILL.md`
   - 测试：`cds-product-test-zh/SKILL.md`
   - 运维：`cds-product-devops-zh/SKILL.md`

## 最佳实践

### 1. 按阶段推进
建议按照**设计 → 开发 → 测试 → 部署**的顺序使用，确保每个阶段都完成后再进入下一阶段。

### 2. 以产物为交接依据
不要只基于口头描述切换阶段，应优先检查文档、代码、测试报告、部署记录是否齐全。

### 3. 及时确认关键标识与关键缺口
涉及后端实现时，及时确认 `moduleCode`、`acronym` 以及前序设计产物是否完整，避免后续返工。

### 4. 通过测试与运维形成闭环
不要把开发完成视为流程结束，应继续通过测试 skill 验证，再通过运维 skill 完成交付。

### 5. 善用状态文件恢复上下文
定期查看 session / task 文件，明确当前阶段、当前模块和未完成步骤。

## 注意事项

1. **环境依赖**：测试和部署 skill 需要配置 CodeLink 环境信息及相关依赖地址
2. **权限要求**：部署 skill 需要相应服务器权限和 CDS 接口权限
3. **网络要求**：确保网络连接正常，尤其是远程构建、远程安装和通知场景
4. **产物完整性**：设计阶段的文档缺失会直接影响开发与测试质量
5. **回流机制**：测试和部署过程中发现问题时，应及时回流到开发阶段修复，而不是跳过问题继续推进

## 相关文档

- [CDS 四个 Skill 协同原理说明](CDS-SKILLS-COLLABORATION-PRINCIPLES.md)
- `cds-product-design-zh/SKILL.md`
- `cds-product-develop-zh/SKILL.md`
- `cds-product-test-zh/SKILL.md`
- `cds-product-devops-zh/SKILL.md`

---

如需了解更多详情，请参考各个 skill 目录下的 `SKILL.md` 文件和 `references/` 目录下的详细指南。