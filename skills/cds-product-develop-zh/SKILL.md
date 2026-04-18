name:cds-product-develop-zh
description:CDS产品开发助手，依据已有的UI稿和前端技术实现方案生成前端代码，依据已有的后端技术实现方案实现后端代码，生成启动类代码，包含git提交管理方便回退代码与模块集成能力。

## 启动恢复流程（最高优先级）

每次进入时**必须首先执行**以下流程：

### 步骤0：读取进度文件
```
IF 项目根目录存在 dev-session.md THEN
    读取 dev-session.md
    判断当前开发阶段状态
    判断当前模块状态
    执行对应的恢复操作
ELSE
    进入"开发环境初始化"步骤
END IF
```

### 恢复操作决策表
| dev-session.md状态 | current_module | current_phase | 恢复操作 |
|-------------------|----------------|--------------|---------|
| 文件不存在 | - | - | 执行步骤0：开发环境初始化 |
| 环境未配置 | - | - | 执行开发环境配置 |
| 环境已配置 | 有值且状态为"进行中" | 前端 | 继续前端开发 |
| 环境已配置 | 有值且状态为"进行中" | 后端 | 继续后端开发 |
| 环境已配置 | 有值且状态为"进行中" | 集成 | 继续集成开发 |
| 环境已配置 | 无值或状态非"进行中" | - | 选择优先级最高的"未开始"模块 |
| 所有模块已完成 | - | - | 提示用户项目已完成，询问后续操作 |

## 开发流程框架

### 核心原则
- **代码质量原则**：所有代码在进入集成前必须完成必要的质量检查
- **git管理原则**：每个功能点独立提交，便于回退
- **前后端分离原则**：前端后端独立开发，最后集成
- **产物导向原则**：每步只关注当前步骤的交付物

### 第一阶段：开发环境准备与模块开发

**步骤0：模块开发初始化**（必选）
- 详细指南：MODULE-SETUP.md
- 核心交付物：dev-session.md

**可选择执行的开发方案**（用户可选择一个或多个，选中后按顺序执行）：

| 选项 | 包含步骤 | 名称 | 详细指南 | 核心交付物 |
|------|---------|------|---------|-----------|
| **A** | 步骤1+2 | 前端开发 | references/FRONTEND-MODULE-STRUCT-RULES.md + FRONTEND-IMPL.md（含FRONTEND-CODE-GEN.md流程控制） | 前端项目结构、前端组件代码、前端服务代码、前端路由配置 |
| **B** | 步骤3+4+5 | 后端开发 | references/BACKEND-MODULE-STRUCT-RULES.md + BACKEND-IMPL.md（含BACKEND-CODE-GEN.md流程控制） + ../cds-product-design-zh/references/BACKEND-TOOLS-BOOTSTRAP.md | 后端项目结构、后端Controller/Service/DAO代码、启动类代码（按需） |
| **C** | 步骤6 | 模块集成测试 | INTEGRATION.md | 集成测试报告、模块开发完成确认 |

**步骤编号对照表**：

| 步骤编号 | 步骤名称 | 能力说明 | 所属选项 |
|---------|---------|---------|---------|  
| 步骤0 | 模块开发初始化 | 创建dev-session.md，确认模块信息和目录位置 | 必选 |
| 步骤1 | 前端项目结构创建 | 创建前端目录结构、基础配置文件 | 选项A |
| 步骤2 | 前端代码生成 | 生成前端组件、服务、路由代码（按FRONTEND-CODE-GEN.md的7阶段流程） | 选项A |
| 步骤3 | 后端项目结构创建 | 创建后端目录结构、包结构 | 选项B |
| 步骤4 | 后端代码生成 | 生成后端Controller、Service、DAO代码（按BACKEND-CODE-GEN.md的9阶段流程） | 选项B |
| 步骤5 | 启动类代码生成 | 按需生成启动类、配置文件、依赖注入配置 | 选项B |
| 步骤6 | 模块集成测试 | 前后端集成测试、功能验证 | 选项C |

**执行规则**：
1. 用户可以选择任意一个或多个选项（A、B、C）
2. 选中选项后，按照步骤顺序依次执行（如选择A+B：步骤1→2→3→4→5）
3. 选项B中的步骤5（启动类代码生成）需要单独确认三项内容（应用启动类、配置文件、依赖注入配置）

## 步骤执行规范

### 开发方案选择确认

在步骤0（模块开发初始化）完成后，必须向用户确认开发方案选择：

```markdown
### 请选择本次要执行的开发方案（可多选）：

- [ ] **选项A：前端开发**（包含步骤1：前端项目结构创建 + 步骤2：前端代码生成）
- [ ] **选项B：后端开发**（包含步骤3：后端项目结构创建 + 步骤4：后端代码生成 + 步骤5：启动类代码生成）
- [ ] **选项C：模块集成测试**（步骤6：前后端集成测试）

请勾选需要执行的选项，或说明您的选择。
```

**选择规则**：
1. 用户可以选择任意一个或多个选项（A、B、C）
2. 选中选项后，按照步骤顺序依次执行（如选择A+B：步骤1→2→3→4→5）
3. 选项B中的步骤5（启动类代码生成）需要单独确认三项内容（应用启动类、配置文件、依赖注入配置）

### 每个步骤执行前
1. **清理上下文**：移除与当前步骤无关的历史对话
2. **加载指南**：读取对应的 reference 文件
3. **确认前置产物**：检查前置步骤的交付物是否存在
4. **前序设计产物确认**：若当前步骤涉及前端或后端代码生成，必须优先查找对应前序设计产物；前端优先查找 `.pen` 文件、`UI设计规范.md`、`{功能名称}-前端架构设计.md`、`{功能名称}-需求详细设计.md 、{功能名称}-API设计文档.md`，后端优先查找 `{功能名称}-需求详细设计.md`、`{功能名称}-后端架构设计.md`、`{功能名称}-API设计文档.md`；若文档不完整，至少确认存在一个 `{功能名称}-需求详细设计.md`，并先询问用户是否允许基于现有资料合理发挥
5. **关键标识确认**：若当前步骤涉及后端代码生成、后端建表、类命名或注解参数，必须优先从后端技术实现方案文档读取 `moduleCode` 与 `acronym`；若文档缺失这两个值，则转为从当前项目 metadata 文件提取；无论从文档还是 metadata 读取到，都必须先向用户确认是否正确，若不正确或未提取到，则要求用户明确提供这两个值后再继续
6. **启动类生成按需确认**：当执行“启动类代码生成”步骤前，必须先分开询问用户以下功能项各自是否需要生成，不得打包成一个笼统问题默认生成：
   - 应用启动类是否需要？
   - 配置文件是否需要？
   - 依赖注入配置是否需要？
   用户可以选择其中任意一项、多项，或明确说明这三项都不需要；只有在用户确认需要的前提下，才生成对应内容

### 每个步骤执行后
1. **更新进度文件**：立即更新 dev-session.md，仅保留关键结论
2. **git提交**：完成代码后立即git提交，记录清晰的提交信息
3. **记录执行摘要**：记录关键决策、变更、用户确认（压缩为摘要）
4. **用户确认**：展示产物，获取用户确认后继续
5. **⚠️ 强制上下文清理**：移除与当前步骤无关的历史对话，仅保留关键结论到进度文件
   - 如果对话超过 30 轮，**必须立即清理**
   - 如果接近 token 限制（>80000 tokens），**必须立即清理**
   - 清理前必须完成 6 项检查（参考 CONTEXT-CLEANING.md）
   - **必须执行 `/clear` 指令**，不得跳过

> **详细上下文清理规范请参考**: [上下文清理规范](references/CONTEXT-CLEANING.md)

## 文档调用关系说明

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
dev-session.md (统一的进度跟踪文件)
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
dev-session.md (统一的进度跟踪文件)
```

### 关键说明

1. **IMPL.md 是入口文档**：
   - FRONTEND-IMPL.md 和 BACKEND-IMPL.md 是 SKILL.md 直接调用的入口
   - 它们定义了角色定位、技术栈、开发规范等基础信息

2. **CODE-GEN.md 是流程控制文档**：
   - FRONTEND-CODE-GEN.md 和 BACKEND-CODE-GEN.md 被 IMPL.md 内部调用
   - 它们定义了阶段化的执行流程、断点续传机制、跟踪文件更新规则

3. **跟踪文件在项目根目录**：
   - `frontend-code-gen-tracker.md` 和 `backend-code-gen-tracker.md` 创建在项目根目录
   - 不在 references 目录中，便于跨会话恢复

4. **阶段生成指南在 references 目录**：
   - 各阶段的具体生成指南（如 BACKEND-ENTITY-BO-VO-GENERATION.md）在 references 目录
   - 由 CODE-GEN.md 按阶段调用



## 详细指南索引

| 文件 | 用途 |
|------|------|
| FRONTEND-IMPL.md | 前端代码实现指南（基于CDS模板），包含前序设计产物检查与自由发挥确认流程 |
| BACKEND-IMPL.md | 后端工程师角色实现指南，包含技术栈、开发规范、最佳实践，以及 `moduleCode` / `acronym` 的文档读取、metadata 提取、前序设计产物检查与用户确认流程 |
| ../cds-product-design-zh/references/BACKEND-TOOLS-BOOTSTRAP.md | 启动类代码生成指南 |
| INTEGRATION.md | 集成测试指南 |

## 开发者能力文档

> **详细能力说明请参考**: [开发原则与工具指南](references/DEVELOP-PRINCIPLES.md#开发者能力文档)

### 前端开发能力
| 文件 | 用途 |
|------|------|
| FRONTEND-IMPL.md | 前端工程师角色实现指南，包含技术栈、开发规范、最佳实践，以及前序设计产物检查与自由发挥确认流程 |

### 后端开发能力  
| 文件 | 用途 |
|------|------|
| BACKEND-IMPL.md | 后端工程师角色实现指南，包含技术栈、开发规范、最佳实践，以及 `moduleCode` / `acronym` 的文档读取、metadata 提取、前序设计产物检查与用户确认流程 |

### 工具文档引用

> **详细工具文档请参考**: [开发原则与工具指南](references/DEVELOP-PRINCIPLES.md#工具文档引用)

#### 后端开发工具指南
| 工具文档 | 用途 | 使用场景 |
|---------|------|----------|
| [项目结构工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-PROJECT-STRUCTURE.md) | CDS项目目录结构和模块划分 | 新建项目、理解项目结构时 |
| [后端代码生成流程控制](references/BACKEND-CODE-GEN.md) | 后端代码生成9阶段流程控制和断点续传 | 后端业务逻辑代码生成时 |
| [配置管理工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-CONFIGURATION.md) | 公共服务配置和模块配置 | 配置类开发、包扫描配置时 |
| [数据库规范工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-DATABASE.md) | 数据库设计和SQL脚本规范 | 数据库设计、表结构创建时 |
| [模块注册工具指南](../cds-product-design-zh/references/BACKEND-TOOLS-MODULE-REGISTER.md) | 模块注册、菜单注册、工作流注册 | 模块初始化、系统集成时 |

## 外部Skill调用

| 场景 | 调用Skill | 说明 |
|------|----------|------|
| 代码编译测试 | cds-product-test-zh | 代码生成后调用 |
| 集成测试执行 | cds-product-test-zh | 集成完成后调用 |
| 部署验证 | cds-product-devops-zh | 开发完成后调用 |