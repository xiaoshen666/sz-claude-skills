# {moduleCode} 项目规则文档索引

> 本文档提供项目规则文档的索引和使用指南

## 一、文档结构

```
skil-md/
├── README.md                               # 项目规则文档索引（本文档）
│
├── 📚 基础文档（项目通用）
│   ├── skill-project-structure.md          # 基础项目结构说明
│   ├── skill-core-variables.md             # 核心变量与命名规范
│   ├── skill-custom-pom-template.md        # 自定义模块POM文件生成规范
│   ├── skill-database-sql-template.md      # 数据库初始化SQL脚本生成规范
│   ├── skill-common-config-template.md     # 公共配置与工具类生成规范
│   ├── skill-i18n-template.md              # 国际化资源文件生成规范
│   ├── skill-menu-register-template.md     # 菜单注册配置生成规范
│   ├── skill-module-register-template.md   # 模块注册与初始化配置生成规范
│   └── skill-frontend-template.md          # 前端页面模板
│
├── 📚 单表工作流模板（单表+工作流场景）
│   ├── skill-single-workflow-entity-template.md       # Entity生成规范
│   ├── skill-single-workflow-dao-template.md          # DAO与Query生成规范
│   ├── skill-single-workflow-service-template.md      # Service接口生成规范
│   ├── skill-single-workflow-serviceimpl-template.md  # Service实现类生成规范
│   ├── skill-single-workflow-controller-template.md   # Controller生成规范
│   ├── skill-single-workflow-bo-template.md           # BO生成规范
│   ├── skill-single-workflow-vo-template.md           # VO生成规范
│   ├── skill-single-workflow-mapper-template.md       # MyBatis映射文件生成规范
│   └── skill-workflow-integration-template.md         # 工作流集成规范
│
└── 📚 工作流接口模板（以接口功能为维度）
    ├── skill-workflow-common-dependencies.md            # 通用POM依赖和Import语句（必须先执行）
    │
    ├── 🔹 基础接口
    │   ├── skill-workflow-save-interface.md             # 保存接口
    │   ├── skill-workflow-page-query-interface.md       # 分页查询接口
    │   ├── skill-workflow-export-interface.md           # 导出接口
    │   ├── skill-workflow-delete-interface.md           # 删除接口
    │   └── skill-workflow-mne-interface.md              # 助记码接口
    │
    ├── 🔹 工作流操作接口
    │   ├── skill-workflow-submit-interface.md           # 提交接口
    │   ├── skill-workflow-revocation-pending-interface.md  # 撤回待办接口
    │   ├── skill-workflow-cancel-process-interface.md   # 取消流程接口
    │   └── skill-workflow-restart-process-interface.md  # 弃审工作流接口
    │
    ├── 🔹 工作流查询接口
    │   ├── skill-workflow-get-by-pending-id-interface.md      # 根据待办ID获取模型数据接口
    │   ├── skill-workflow-get-task-name-list-interface.md     # 获取单据状态列表接口
    │   └── skill-workflow-get-pending-task-interface.md       # 根据EntityID获取待办详情列表接口
    │
    ├── 🔹 工作流批量操作接口
    │   └── skill-workflow-submit-pending-batch-interface.md  # 工作流待办批量提交接口
    │
    └── 🔹 工作流流程管理接口
        ├── skill-workflow-save-process-interface.md         # 工作流流程保存接口
        └── skill-workflow-generate-formdata-interface.md    # 组装数据接口
```

## 二、文档说明

### 2.1 project_structure.md - 基础项目结构说明

**用途**: 提供项目基础结构概述，用于指导AI生成代码时的目录组织

**主要内容**:
- 项目层级结构
- 目录结构说明
- 核心变量说明
- 数据库表命名规则
- 类命名规则
- 代码存放规则
- 技术栈说明

**使用场景**: 
- 了解项目整体结构
- 确定代码存放位置
- 了解核心变量和命名规范

### 2.2 skill-core-variables.md - 核心变量与命名规范

**用途**: 定义项目中必须使用的变量和命名规范

**主要内容**:
- 核心变量说明 ({moduleCode}, {acronym}, {modelCode})
- 变量转换规则
- 数据库表命名规范
- 类命名规则
- 包命名规范
- 文件命名规范
- Key命名规范（国际化）
- API路径命名规范
- 工作流相关命名

**使用场景**:
- 生成代码时使用正确的变量
- 确保命名一致性
- 生成数据库表名和类名

### 2.3 skill-entity-dao-template.md - Entity与DAO模板

**用途**: 提供Entity和DAO的生成规范

**主要内容**:
- Entity生成规范
- DAO生成规范
- Query生成规范
- 字段类型映射
- 工作流支持
- 完整示例

**使用场景**:
- 生成Entity类
- 生成DAO接口
- 生成Query类
- 确保Entity和DAO符合规范

### 2.4 skill-service-controller-template.md - Service与Controller模板

**用途**: 提供Service和Controller的生成规范

**主要内容**:
- Service接口生成规范
- Service实现类生成规范
- Controller生成规范
- VO/BO转换
- 完整示例

**使用场景**:
- 生成Service接口
- 生成Service实现类
- 生成Controller
- 确保Service和Controller符合规范

### 2.5 skill-module-register-template.md - 模块注册与初始化配置

**用途**: 提供模块注册和初始化的配置规范

**主要内容**:
- ModuleRegisterInitialize类生成规范
- 必需方法说明
- 配置文件规范
- 完整示例

**使用场景**:
- 生成ModuleRegisterInitialize类
- 配置模块注册
- 配置菜单注册
- 配置工作流页面注册

### 2.6 skill-common-config-template.md - 公共配置与工具类

**用途**: 提供公共配置类和工具类的生成规范

**主要内容**:
- {ModuleCode}PubServiceConfiguration配置类
- 枚举类生成规范
- 工具类生成规范
- 配置类生成规范
- BO/VO/DTO生成规范
- 完整示例

**使用场景**:
- 生成公共配置类
- 生成枚举类
- 生成工具类
- 生成BO/VO/DTO类

### 2.7 skill-i18n-template.md - 国际化资源模板

**用途**: 提供国际化资源文件的生成规范

**主要内容**:
- 国际化资源文件结构
- Key命名规范
- 完整示例
- Key分类说明
- 生成规范
- 检查清单

**使用场景**:
- 生成国际化资源文件
- 配置字段显示名称
- 配置页面标题
- 配置按钮名称

### 2.8 skill-mybatis-mapper-template.md - MyBatis映射文件模板

**用途**: 提供MyBatis映射文件的生成规范

**主要内容**:
- 映射文件结构
- 文件命名规范
- 基础结构
- 自定义SQL示例
- 数据库特定SQL
- 完整示例

**使用场景**:
- 生成MyBatis映射文件
- 生成自定义SQL
- 配置数据库特定SQL
- 确保映射文件符合规范

### 2.9 skill-frontend-template.md - 前端页面模板

**用途**: 提供前端页面的生成规范

**主要内容**:
- 前端页面目录结构
- 页面文件规范
- API调用规范
- 页面功能示例
- CSS样式规范
- 检查清单

**使用场景**:
- 生成前端页面
- 调用后端API
- 实现页面功能
- 确保前端页面符合规范

### 2.10 skill-workflow-integration-template.md - 工作流集成规范

**用途**: 提供工作流集成的生成规范

**主要内容**:
- 工作流Entity规范
- Service接口规范
- Service实现类规范
- Controller规范
- 工作流注册规范
- 完整示例

**使用场景**:
- 生成工作流Entity
- 生成工作流Service
- 生成工作流Controller
- 配置工作流注册

## 三、使用指南

### 3.1 开发流程

#### 第一步：了解项目结构
- 阅读 `project_structure.md`
- 了解项目目录结构
- 确定代码存放位置

#### 第二步：获取核心变量
- 阅读 `skill-core-variables.md`
- 获取 {moduleCode}, {acronym}, {modelCode}
- 确定命名规范

#### 第三步：生成Entity和DAO
- 阅读 `skill-entity-dao-template.md`
- 生成Entity类
- 生成DAO接口
- 生成Query类

#### 第四步：生成Service和Controller
- 阅读 `skill-service-controller-template.md`
- 生成Service接口
- 生成Service实现类
- 生成Controller

#### 第五步：配置模块注册
- 阅读 `skill-module-register-template.md`
- 生成ModuleRegisterInitialize类
- 配置菜单注册
- 配置工作流页面注册

#### 第六步：生成公共配置
- 阅读 `skill-common-config-template.md`
- 生成公共配置类
- 生成枚举类
- 生成工具类

#### 第七步：配置国际化
- 阅读 `skill-i18n-template.md`
- 生成国际化资源文件
- 配置字段显示名称
- 配置页面标题

#### 第八步：生成MyBatis映射
- 阅读 `skill-mybatis-mapper-template.md`
- 生成MyBatis映射文件
- 生成自定义SQL

#### 第九步：生成前端页面
- 阅读 `skill-frontend-template.md`
- 生成前端页面
- 调用后端API

#### 第十步：配置工作流（如需要）
- 阅读 `skill-workflow-integration-template.md`
- 生成工作流Entity
- 生成工作流Service
- 生成工作流Controller

### 3.2 选择性使用

根据项目需求，可以选择性使用以下文档：

#### 不需要工作流
- 跳过 `skill-workflow-integration-template.md`
- ModuleRegisterInitialize中不注册工作流

#### 不需要前端页面
- 跳过 `skill-frontend-template.md`
- 不生成前端页面

#### 不需要国际化
- 跳过 `skill-i18n-template.md`
- 不生成国际化资源文件

#### 只需要基础功能
- 只使用 `project_structure.md`
- 只使用 `skill-core-variables.md`
- 只使用 `skill-entity-dao-template.md`
- 只使用 `skill-service-controller-template.md`

### 3.3 变量替换

在使用模板时，需要替换以下变量：

| 变量 | 说明 | 示例 |
|------|------|------|
| `{moduleCode}` | 模块编码 | `aiprocts` |
| `{ModuleCode}` | 模块编码（首字母大写） | `Aiprocts` |
| `{acronym}` | 模块缩略码 | `aipro` |
| `{modelCode}` | 模型编码 | `Qjdst` |
| `{ModelCode}` | 模型编码（首字母大写） | `Qjdst` |
| `{entityCode}` | 实体编码 | `qjdst` |
| `{EntityName}` | 实体名 | `Qjdst` |
| `{tableName}` | 表名 | `aipro_qjdst` |

## 四、检查清单

### 4.1 代码生成检查清单

#### Entity和DAO
- [ ] Entity类名格式: `Custom{ModuleCode}{ModelCode}Entity`
- [ ] Entity继承 `AbstractConfigurationWorkflowEntity`（如需要工作流）
- [ ] 所有必需注解已添加
- [ ] 表名格式: `{acronym}_{modelCode小写}`
- [ ] DAO接口继承 `BaseDao<Entity>`
- [ ] DAO接口添加 `@Mapper` 注解
- [ ] DAO包含分页查询方法

#### Service和Controller
- [ ] Service接口名格式: `Custom{ModuleCode}{ModelCode}Service`
- [ ] 继承 `IECWorkflowBaseService<Entity>`（如需要工作流）
- [ ] 类名格式: `Custom{ModuleCode}{ModelCode}ServiceImpl`
- [ ] 添加 `@Service`、`@Validated`、`@Slf4j` 注解
- [ ] Controller类名格式: `Custom{ModuleCode}{ModelCode}Controller`
- [ ] 添加 `@InternalApi` 注解，路径正确
- [ ] 所有方法参数正确使用 `@RequestBody` 和 `@Validated`

#### 模块注册
- [ ] 类名格式: `Module{moduleCode}RegisterInitialize`
- [ ] 添加 `@Component`、`@Slf4j`、`@Order(value = 1)` 注解
- [ ] `registerModule` 方法存在且正确实现
- [ ] `initMenu` 方法存在（如需要菜单）
- [ ] `initWorkflowPages` 方法存在（如需要工作流）

#### 国际化
- [ ] 文件名格式: `{moduleCode}_{语言码}.properties`
- [ ] 文件存放在正确的路径
- [ ] 所有字段配置已添加
- [ ] 页面标题配置已添加
- [ ] 按钮名称配置已添加

#### MyBatis映射
- [ ] 文件名格式: `custom{ModuleCode}{EntityName}Mapper.xml`
- [ ] 文件存放在正确的数据库类型目录
- [ ] 包含所有必需的SQL元素

#### 前端页面
- [ ] 目录命名格式: `{moduleCode}_custom_{功能名}`
- [ ] 包含必需的子目录 (css/, imgs/, js/)
- [ ] 包含必需的文件 (entry.js, {功能名}.html)

### 4.2 项目结构检查清单

- [ ] 项目目录结构正确
- [ ] 所有必需的模块已创建
- [ ] 代码存放在正确的目录
- [ ] 配置文件存放在正确的目录
- [ ] 国际化资源文件已生成

## 五、注意事项

1. **变量替换**: 使用模板时，必须替换所有变量
2. **命名一致性**: 确保所有命名符合规范
3. **代码存放**: AI生成的代码必须放在 `{moduleCode}-custom` 目录下
4. **平台生成目录**: 禁止修改平台生成目录下的文件
5. **工作流支持**: 如需要工作流功能，Entity必须继承 `AbstractConfigurationWorkflowEntity`
6. **国际化**: 如需要国际化支持，必须生成对应的资源文件
7. **MyBatis映射**: 如需要自定义SQL，必须生成MyBatis映射文件
8. **前端页面**: 如需要前端页面，必须生成对应的HTML和JS文件

## 六、技术支持

如遇到问题，请参考以下资源：

1. **项目规则文档**: 查看 `project_structure.md` 和 `skill-core-variables.md`
2. **模板文档**: 查看对应的模板文档
3. **示例代码**: 查看模板文档中的完整示例
4. **检查清单**: 使用检查清单确保所有必需项已配置

## 七、版本信息

- **版本**: 1.0.0
- **更新日期**: 2026-03-23
- **适用项目**: {moduleCode} 项目
