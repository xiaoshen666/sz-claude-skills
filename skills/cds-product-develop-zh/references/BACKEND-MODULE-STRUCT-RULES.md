# 后端模块代码目录查找规则

## 概述

本指南定义了后端模块代码目录的查找规则和定位方法，帮助开发人员快速找到和创建后端模块代码。

## {moduleCode} 获取来源与确认规则

在进行后端模块代码目录查找和创建之前，**必须优先从当前项目中提取 `moduleCode`**，不得直接猜测或沿用历史上下文中的旧值。

### 优先提取来源

优先从当前项目中的以下文件提取：

- `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json`
- `{moduleCode}-resource/src/main/resources/META-INF/appdata/App.json`

**{moduleCode}.json 关键字段**：
```json
{
  "module": {
    "moduleCode": "{moduleCode}",      // 模块编码
    "moduleName": "{模块名称}",
    "acronym": "{acronym}"             // 模块缩略码
  }
}
```

**App.json 关键字段**：
```json
{
  "code": "{moduleCode}",              // 应用编码
  "modules": "{moduleCode}",           // 模块编码
  "name": "{应用名称}"
}
```

### 执行规则

1. 若当前项目中存在对应文件，则先读取并提取 `moduleCode`
2. 若成功提取，也**必须先向用户展示提取结果并确认是否正确**，确认无误后才能继续
3. 若用户确认正确，则后续所有代码生成、目录命名、API 路径命名都必须使用确认后的值
4. 若未找到文件、未能明确提取出 `moduleCode`，或用户反馈提取结果不正确，则**必须主动向用户询问并要求提供正确的 `moduleCode`**，不得继续使用默认值、猜测值或未确认值
5. 用户补充或更正后，后续生成内容必须严格使用用户最终确认的值

### 标准确认提问话术

#### 情况1：成功从项目文件中提取

我已从项目文件中提取到 `moduleCode`：

- **提取来源**：`{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json`
- **提取结果**：`moduleCode = {实际值}`

请确认该值是否正确？
- 若回复 **正确**：我将使用该值继续定位后端模块代码目录
- 若回复 **不正确**：请提供正确的 `moduleCode` 值

#### 情况2：未找到文件或提取失败

当前未找到 `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json` 或 `App.json` 文件，无法自动提取 `moduleCode`。

请提供正确的 `moduleCode` 值，用于定位后端模块代码目录。

## 后端项目目录结构

### CDS 标准目录结构

```
{moduleCode}-xxx/
├── {moduleCode}/
│   ├── {moduleCode}-api/                    # 平台生成的API接口
│   ├── {moduleCode}-common/                 # 平台生成的公共类
│   ├── {moduleCode}-dao/                    # 平台生成的DAO层
│   ├── {moduleCode}-service/                # 平台生成的Service层
│   ├── {moduleCode}-webapi/                 # 平台生成的Controller层
│   ├── {moduleCode}-resource/               # 平台生成的资源文件
│   └── {moduleCode}-custom/                 # ✅ AI自定义代码目录
│       ├── {moduleCode}-custom-api/         # 自定义Feign API接口
│       ├── {moduleCode}-custom-common/      # 自定义公共配置
│       ├── {moduleCode}-custom-dao/         # 自定义DAO和Entity
│       ├── {moduleCode}-custom-service/     # 自定义Service实现
│       ├── {moduleCode}-custom-webapi/      # 自定义Controller
│       ├── {moduleCode}-custom-manager/     # 自定义管理器
│       └── {moduleCode}-custom-resource/    # 自定义资源文件
├── {appCode}-bootstrap/                     # 应用启动模块
└── maven/                                   # 自定义Maven依赖
```

## 平台生成目录说明（禁止修改）

### 平台生成模块目录

| 目录 | 用途 | 是否可修改 |
|------|------|------------|
| `{moduleCode}-api` | 平台生成的API接口和DTO | ❌ 禁止修改 |
| `{moduleCode}-common` | 平台生成的公共类、VO、Query | ❌ 禁止修改 |
| `{moduleCode}-dao` | 平台生成的Entity、DAO | ❌ 禁止修改 |
| `{moduleCode}-service` | 平台生成的Service接口和实现 | ❌ 禁止修改 |
| `{moduleCode}-webapi` | 平台生成的Controller | ❌ 禁止修改 |

## {moduleCode}-custom 目录详解

### {moduleCode}-custom-api

**路径**: `{moduleCode}/{moduleCode}-custom-api/`

```
{moduleCode}-custom-api/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── api/                    # 自定义Feign API接口
│   └── dto/                    # 自定义DTO
└── pom.xml
```

**用途**: 存放自定义的 Feign 客户端接口和相关的数据传输对象。

### {moduleCode}-custom-common

**路径**: `{moduleCode}/{moduleCode}-custom-common/`

```
{moduleCode}-custom-common/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── {ModuleCode}PubServiceConfiguration.java  # 公共服务配置（必需）
│   ├── config/                 # 其他配置类
│   ├── enums/                  # 枚举类
│   └── utils/                  # 工具类
└── pom.xml
```

**关键文件**: `{ModuleCode}PubServiceConfiguration.java`

由于启动类在 `{appCode}-bootstrap` 模块中（平台生成，不可修改），需要在 `{moduleCode}-custom-common` 中创建配置类来完成包扫描。

### {moduleCode}-custom-dao

**路径**: `{moduleCode}/{moduleCode}-custom-dao/`

```
{moduleCode}-custom-dao/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── dao/                    # 自定义DAO接口
│   ├── entity/                 # 自定义Entity
│   ├── po/                     # 自定义PO
│   └── query/                  # 自定义Query
├── src/main/resources/
│   └── META-INF/
│       ├── oracle/             # Oracle数据库初始化SQL
│       ├── sqlserver/          # SQL Server数据库初始化SQL
│       ├── mariadb/            # MariaDB数据库初始化SQL
│       ├── dm/                 # DM数据库初始化SQL
│       └── persistence.xml     # JPA持久化配置（如需要）
└── pom.xml
```

**关键文件**：
- **初始化SQL**: `src/main/resources/META-INF/{databaseType}/{moduleCode}CustomInit.sql`
- 按数据库类型分目录存放建表SQL和初始化数据SQL

**用途**: 存放数据访问层相关的代码，包括实体类、DAO接口、查询对象以及数据库初始化SQL脚本。

### {moduleCode}-custom-service

**路径**: `{moduleCode}/{moduleCode}-custom-service/`

```
{moduleCode}-custom-service/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── bo/                     # 自定义BO
│   ├── event/                  # 事件处理
│   └── service/
│       ├── impl/               # Service实现
│       ├── biz/                # 业务逻辑
│       └── core/               # 核心服务
└── pom.xml
```

**用途**: 存放业务逻辑层相关的代码，包括业务对象、服务接口和实现。

### {moduleCode}-custom-webapi

**路径**: `{moduleCode}/{moduleCode}-custom-webapi/`

```
{moduleCode}-custom-webapi/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── controller/             # 自定义Controller
│   └── vo/                     # 自定义VO
└── pom.xml
```

**用途**: 存放表现层相关的代码，包括控制器和视图对象。

### {moduleCode}-custom-manager

**路径**: `{moduleCode}/{moduleCode}-custom-manager/`

```
{moduleCode}-custom-manager/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── ModuleRegisterInitialize.java  # 模块注册初始化（必需）
│   ├── dto/                    # 管理DTO
│   └── manager/                # 管理器类
└── src/main/resources/
    ├── initmenu{moduleCode}.json   # 菜单初始化配置（可选）
    └── view.json                   # 视图配置（工作流页面需要）
```

**关键文件**: `ModuleRegisterInitialize.java`

系统启动时自动执行，完成模块注册、菜单注册、工作流页面注册。

### {moduleCode}-custom-resource

**路径**: `{moduleCode}/{moduleCode}-custom-resource/`

```
{moduleCode}-custom-resource/
├── src/main/resources/
│   ├── META-INF/
│   │   ├── i18N/               # 国际化资源文件
│   │   └── metadata/           # 元数据配置（可选自定义）
│   ├── mapper/                # MyBatis映射文件
│   └── config/                # 配置文件
└── pom.xml
```

**用途**: 存放资源文件，包括国际化配置、MyBatis映射文件等。

## 模块代码定位规则

### 1. 基于 metadata 文件推导路径

当从 `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json` 文件中读取到 `moduleCode` 和 `acronym` 时：

- **推导规则**：从该 metadata 文件位置向上回溯 6 级目录，即可到达 `{moduleCode}-custom/` 目录
- **相对路径**：`../../../{moduleCode}-custom/`
- **完整路径示例**：若 metadata 文件位于 `{moduleCode}-resource/src/main/resources/META-INF/metadata/{moduleCode}.json`，则对应的 custom 目录为 `{moduleCode}/{moduleCode}-custom/`
- **目录结构对应关系**：
  ```
  {moduleCode}.json (metadata文件)
  ↓ 向上6级
  {moduleCode}-custom/ (自定义代码根目录)
  ├── {moduleCode}-custom-api/         # 自定义Feign API接口
  ├── {moduleCode}-custom-common/      # 自定义公共配置
  ├── {moduleCode}-custom-dao/         # 自定义DAO和Entity
  ├── {moduleCode}-custom-service/     # 自定义Service实现
  ├── {moduleCode}-custom-webapi/      # 自定义Controller
  ├── {moduleCode}-custom-manager/     # 自定义管理器
  └── {moduleCode}-custom-resource/    # 自定义资源文件
  ```

### 2. 基于项目目录搜索定位

**优先搜索规则**：在当前项目目录结构中搜索 `{moduleCode}-custom/` 目录

搜索路径优先级：
1. **项目根目录**：直接搜索 `{moduleCode}-custom/`
2. **模块目录下**：搜索 `{moduleCode}/{moduleCode}-custom/`
3. **递归搜索**：在项目根目录下递归查找 `{moduleCode}-custom/` 目录

**确认规则**：
- 找到目录后，验证目录内是否包含标准子模块（custom-api、custom-common、custom-dao等）
- 若目录不存在，则按标准结构创建

### 3. 目录位置记录

**记录到 dev-task.md**：

在确认后端代码生成目录后，必须将以下信息记录到 `dev-task.md` 中：

```markdown
## 后端代码目录信息

- **moduleCode**: {实际值}
- **acronym**: {实际值}
- **custom目录路径**: {相对项目根目录的路径}
- **完整路径**: {绝对路径或相对路径}
- **目录状态**: [已存在 / 新创建]
- **记录时间**: {时间戳}
```

**用途**：该信息将用于后续 SKILL.md 中第4节点（后端代码生成）步骤，确保代码生成到正确的目录位置。

### 4. 基于功能名称定位

当需要根据功能名称定位后端代码时：

- **控制器**：查找 `{moduleCode}-custom-webapi/src/main/java/com/supcon/nebule/{moduleCode}/custom/controller/` 目录
- **服务层**：查找 `{moduleCode}-custom-service/src/main/java/com/supcon/nebule/{moduleCode}/custom/service/` 目录
- **数据访问层**：查找 `{moduleCode}-custom-dao/src/main/java/com/supcon/nebule/{moduleCode}/custom/dao/` 目录

### 5. 基于模块编码定位

当需要根据 `moduleCode` 定位后端代码时：

- **自定义代码根目录**：`{moduleCode}/{moduleCode}-custom/`
- **包名规范**：`com.supcon.nebule.{moduleCode}.custom`
- **类命名规范**：使用 PascalCase，如 `{ModuleCode}Controller`

### 6. 新增模块代码创建

当需要为新增功能创建后端代码时：

1. **确认 moduleCode**：按照上述规则提取并确认 `moduleCode`
2. **创建包结构**：在对应模块下创建功能包
3. **创建控制器**：在 `controller/` 包下创建控制器类
4. **创建服务层**：在 `service/` 包下创建服务接口和实现
5. **创建数据访问层**：在 `dao/` 包下创建DAO接口
6. **创建实体类**：在 `entity/` 包下创建实体类

## 查找顺序

### 后端模块代码查找优先级

1. **精确匹配**：完全匹配功能名称或模块编码
2. **模糊匹配**：部分匹配功能名称或模块编码
3. **默认位置**：在标准 `{moduleCode}-custom` 目录下查找
4. **手动创建**：若未找到，则按规范创建

## 验证规则

### 目录存在性验证

在开始开发前，必须验证以下目录是否存在：

- [ ] `{moduleCode}-custom/` 根目录存在
- [ ] `{moduleCode}-custom-api/` API模块存在
- [ ] `{moduleCode}-custom-common/` 公共模块存在
- [ ] `{moduleCode}-custom-dao/` DAO模块存在
- [ ] `{moduleCode}-custom-service/` Service模块存在
- [ ] `{moduleCode}-custom-webapi/` WebAPI模块存在
- [ ] `{moduleCode}-custom-manager/` Manager模块存在

### 代码完整性验证

在模块代码查找完成后，验证以下文件是否存在：

- [ ] 控制器类文件 (`*Controller.java`)
- [ ] 服务接口和实现 (`*Service.java`, `*ServiceImpl.java`)
- [ ] DAO接口 (`*Dao.java`)
- [ ] 实体类 (`*Entity.java`)
- [ ] 配置文件 (`*Configuration.java`)

## 注意事项

1. ✅ **严格遵循 moduleCode**：所有目录命名必须使用确认后的 `moduleCode`
2. ✅ **保持结构一致**：新创建的模块代码必须遵循标准目录结构
3. ❌ **禁止修改平台代码**：不得修改 `{moduleCode}-api/common/dao/service/webapi` 等平台生成目录
4. ❌ **避免硬编码**：不得使用硬编码路径，应使用相对路径或配置