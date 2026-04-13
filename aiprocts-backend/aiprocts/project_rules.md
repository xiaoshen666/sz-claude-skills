# {moduleCode} 项目规则文档

> 本文档用于指导AI生成代码时遵循项目结构和规范
> 
> ⚠️ **核心原则**: 所有AI生成的代码只能放在 `{moduleCode}-custom` 目录下，国际化资源可放在 `{moduleCode}-resource`，其他平台生成目录禁止修改！

---

## 一、项目概述

### 1.1 CDS平台层级结构

```
应用 (Application)
├── productCode: 应用编码（如：3e7494978e11423d8fa2440a54571c3f）
├── productName: 应用名称（如：AI提效基础示例模块）
└── 模块 (Module)
    ├── moduleCode: 模块编码（如：aiprocts）
    ├── acronym: 模块缩略码（如：aipro）用于建表前缀
    └── 模型 (Model)
        └── modelCode: 模型编码（如：Qjdst）
```

### 1.2 APP应用配置信息

**配置文件**: `{moduleCode}-resource/src/main/resources/META-INF/appdata/App.json`

| 字段 | 说明 | 示例值 |
|------|------|--------|
| `code` | APP编码 | `aiproctsap` |
| `name` | APP名称 | `AI提效示例APP` |
| `modules` | 关联模块编码 | `aiprocts` |
| `memory` | JVM堆内存大小（单位：M） | `1024`（表示1G堆内存） |
| `cpuCores` | Pod启动CPU核心数（单位：毫核） | `1000`（表示1核） |
| `appVersion` | APP版本 | `1.0.0` |
| `appState` | APP状态 | `NOT_INSTALL_STATE` |

> ⚠️ **警告**: App.json由CDS平台生成，除`memory`和`cpuCores`外，其他字段禁止直接修改！

### 1.3 CDS初始化配置文件

| 文件 | 说明 | 是否可修改 |
|------|------|------------|
| `product.json` | 记录应用和模块信息 | ❌ 禁止修改 |
| `productPrd.json` | 应用PRD配置信息 | ❌ 禁止修改 |
| `productVersion.json` | 应用版本迭代信息 | ❌ 禁止修改 |

> ⚠️ **警告**: 以上三个文件由CDS平台生成，修改会影响应用上载过程！

### 1.4 核心变量说明

| 变量 | 说明 | 示例 | 使用场景 |
|------|------|------|----------|
| `{moduleCode}` | 模块编码 | `aiprocts` | 包名、目录名、类名前缀 |
| `{ModuleCode}` | 模块编码（首字母大写） | `Aiprocts` | 类名前缀 |
| `{acronym}` | 模块缩略码 | `aipro` | 数据库表名前缀 |
| `{modelCode}` | 模型编码 | `Qjdst` | 实体类名、表名后缀 |
| `{ModelCode}` | 模型编码（首字母大写） | `Qjdst` | 类名 |

### 1.5 数据库表命名规则

```
表名 = {acronym}_{modelCode小写}

示例:
- 模块缩略码: aipro
- 模型编码: Qjdst
- 表名: aipro_qjdst

同一模块下其他模型:
- 模型编码: Order
- 表名: aipro_order
```

### 1.6 类命名规则

```
Entity类名 = Custom{ModuleCode}{ModelCode}Entity

示例:
- 模块编码: aiprocts → Aiprocts
- 模型编码: Qjdst
- Entity类名: CustomAiproctsQjdstEntity
```

### 1.7 技术栈

| 类别 | 技术 |
|------|------|
| **开发语言** | Java (JDK 1.8) |
| **Web框架** | Spring Boot 2.1.5.RELEASE |
| **ORM框架** | MyBatis 3.5.6, MyBatis-Plus 3.4.2 |
| **前端技术** | React ant.design, echarts |
| **数据库** | Oracle11g, MySQL, MariaDB, SQL Server, Kingbase |
| **构建工具** | Maven 3.x |
| **云服务框架** | Spring Cloud Greenwich.SR1 |
| **微服务框架** | OpenFeign |
| **连接池** | Druid |
| **缓存** | Ehcache, Redis |
| **消息中间件** | Kafka |
| **分布式事务** | Atomikos |
| **序列化** | Kryo, Jackson, Fastjson |
| **日志** | SLF4J + Logback |
| **安全框架** | Spring Security |
| **RESTful框架** | Resteasy |

### 1.8 核心依赖

- **nebule-framework-common**: 框架公共组件
- **nebule-framework-runtime**: 运行时组件
- **nebule-logicflow-engine**: 逻辑流引擎
- **nebule-workflow-sdk**: 工作流SDK
- **nebule-excel-service**: Excel导出服务

### 1.9 自定义Maven仓库依赖

**路径**: `{moduleCode}/maven/`

**用途**: 存放自定义代码区（`{moduleCode}-custom`）中AI代码在CDS平台编译时所需的Maven仓库依赖。

**目录结构示例**:
```
{moduleCode}/maven/
└── com/supcon/supfusion/
    ├── framework-bom/1.0.3.RELEASE/
    │   ├── framework-bom-1.0.3.RELEASE.pom
    │   └── framework-bom-1.0.3.RELEASE.pom.sha1
    └── module/registry/
        ├── module-registry-api/1.0.2.RELEASE/
        │   ├── module-registry-api-1.0.2.RELEASE.jar
        │   ├── module-registry-api-1.0.2.RELEASE.pom
        │   └── ...
        └── module-registry-parent/3.5.2.1-R1-T1/
            ├── module-registry-parent-3.5.2.1-R1-T1.pom
            └── ...
```

**使用规则**:

| 规则 | 说明 |
|------|------|
| ✅ 必须放全路径 | 依赖必须按照Maven仓库标准目录结构存放（groupId/artifactId/version/） |
| ❌ 禁止放入不需要的jar | 不需要的依赖禁止放入，会增加整个应用包大小 |
| 📦 CDS平台处理 | CDS平台会将此目录拷贝到对应仓库，编译模块代码时使用 |

> ⚠️ **注意**: 仅当自定义代码区使用了平台默认仓库中不存在的依赖时，才需要在此目录添加。常见依赖如framework-bom、module-registry-api等。

---

## 二、代码存放规则（重要）

### 2.1 代码分离原则

| 模块类型 | 路径 | 用途 | 是否可修改 |
|----------|------|------|------------|
| **自定义代码** | `{moduleCode}-custom/*` | AI生成代码、开发者自定义业务逻辑 | ✅ **可以修改** |
| **国际化资源** | `{moduleCode}-resource/META-INF/i18N` | 国际化配置文件 | ✅ **可以修改** |
| **平台生成模块** | `{moduleCode}-api/common/dao/service/webapi` | 设计器自动生成 | ❌ **禁止修改** |
| **平台Resource** | `{moduleCode}-resource/*` (除i18N外) | 平台生成的资源文件 | ❌ **禁止修改** |

> ⚠️ **警告**: 修改平台生成目录下的文件无效！CDS平台安装启动时会删除并重新覆盖这些文件。

### 2.2 AI代码生成目标目录

```
{moduleCode}-V1.0.0/{moduleCode}/{moduleCode}-custom/     ← ✅ AI代码只能放这里
{moduleCode}-V1.0.0/{moduleCode}/{moduleCode}-resource/META-INF/i18N/  ← ✅ 国际化可放这里
```

---

## 三、{moduleCode}-custom 目录详解（重点）

**路径**: `{moduleCode}/{moduleCode}-custom/`

**用途**: 开发者自定义业务扩展代码，**这是AI生成代码的唯一目标目录**

### 3.1 子模块结构

#### 3.1.1 {moduleCode}-custom-api
```
{moduleCode}-custom-api/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── api/                    # 自定义Feign API接口
│   └── dto/                    # 自定义DTO
└── pom.xml
```

#### 3.1.2 {moduleCode}-custom-common

**目录结构**:
```
{moduleCode}-custom-common/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── {ModuleCode}PubServiceConfiguration.java  # 公共服务配置（必需）
│   ├── config/                 # 其他配置类
│   ├── enums/                  # 枚举类
│   └── utils/                  # 工具类
└── pom.xml
```

**{ModuleCode}PubServiceConfiguration.java（必需）**:

由于启动类在 `{appCode}-bootstrap` 模块中（平台生成，不可修改），需要在 `{moduleCode}-custom-common` 中创建配置类来完成包扫描：

```java
package com.supcon.nebule.{moduleCode}.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {moduleCode}模块公共服务配置类
 * 用于扫描自定义模块的包，替代启动类中的包扫描配置
 */
@Configuration
@ComponentScan(
    basePackages = {
        "com.supcon.supfusion.module.registry",
        "com.supcon.nebule.ecentity"
    }
)
@EnableFeignClients("com.supcon.supfusion.module.registry.api")
@MapperScan({"com.supcon.nebule.{moduleCode}.custom.dao"})
public class {ModuleCode}PubServiceConfiguration {
}
```

> **说明**: 
> - 此类为**必需**，用于扫描 `{moduleCode}-custom-dao` 中的Mapper接口
> - `@MapperScan` 必须包含 `com.supcon.nebule.{moduleCode}.custom.dao` 路径
> - 启动类会自动加载此配置类

#### 3.1.3 {moduleCode}-custom-dao
```
{moduleCode}-custom-dao/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── dao/                    # 自定义DAO接口
│   ├── entity/                 # 自定义Entity
│   ├── po/                     # 自定义PO
│   └── query/                  # 自定义Query
├── src/main/resources/
│   └── META-INF/
│       └── persistence.xml     # JPA持久化配置（如需要）
└── pom.xml
```

**Entity示例**:
```java
package com.supcon.nebule.{moduleCode}.custom.entity;

@Data
@TableName("{tableName}")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym="{acronym}", srcAcronym="")
@AuditModel(module = "{moduleCode}", model = "{modelCode}")
public class Custom{EntityName}Entity extends AbstractConfigurationWorkflowEntity {
    @ApiModelProperty("{字段描述}")
    @TableField("{fieldName}")
    private {fieldType} {propertyName};
}
```

#### 3.1.4 {moduleCode}-custom-service
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

**Service接口示例**:
```java
public interface Custom{EntityName}Service extends IECWorkflowBaseService<Custom{EntityName}Entity> {
    Boolean delete(Long id);
    Page<Custom{EntityName}BO> pageQueryBO(PageQuery<Custom{EntityName}Query> pageQuery);
    Long saveBO(@Valid Custom{EntityName}BO bo);
    Custom{EntityName}BO getByIdBO(Long id);
    Long submit(@Valid WorkFlow<Custom{EntityName}BO> workFlowBO);
    Boolean revocationPending(WorkFlow<Custom{EntityName}BO> workFlowBO);
    Boolean cancelProcess(WorkFlow<Custom{EntityName}BO> workFlowBO);
}
```

**Service实现示例**:
```java
@Service
@Validated
@Slf4j
public class Custom{EntityName}ServiceImpl extends ECWorkflowBaseService<Custom{EntityName}Dao, Custom{EntityName}Entity> 
        implements Custom{EntityName}Service {
    // 实现方法
}
```

#### 3.1.5 {moduleCode}-custom-webapi
```
{moduleCode}-custom-webapi/
├── src/main/java/com/supcon/nebule/{moduleCode}/custom/
│   ├── controller/             # 自定义Controller
│   └── vo/                     # 自定义VO
└── pom.xml
```

**Controller示例**:
```java
@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{entityCode}")
public class Custom{EntityName}Controller {
    @Autowired
    private Custom{EntityName}Service service;

    @PostMapping("/save")
    @ApiOperation("保存记录")
    @ModifiedSerialization
    @AuditLog
    public Result<Long> save(@RequestBody Custom{EntityName}VO vo) {
        Custom{EntityName}BO bo = PojoUtil.copy(vo, Custom{EntityName}BO.class);
        return Result.success(service.saveBO(bo));
    }
}
```

#### 3.1.6 {moduleCode}-custom-manager

**目录结构**:
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

**ModuleRegisterInitialize.java 代码示例**:
```java
package com.supcon.nebule.{moduleCode}.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supcon.nebule.apisvr.api.NebuleMenuInfoApiService;
import com.supcon.nebule.apisvr.api.NebulePermissionApiService;
import com.supcon.nebule.ec.util.JSONHelper;
import com.supcon.nebule.fr.config.syncViewData.View;
import com.supcon.nebule.workflow.sdk.api.SuposFlowCfgApi;
import com.supcon.nebule.workflow.sdk.constant.WorkflowConst;
import com.supcon.nebule.workflow.sdk.dto.RegPageReq;
import com.supcon.nebule.workflow.sdk.dto.RegSvcReq;
import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.supfusion.module.registry.api.ModuleRegistryApi;
import com.supcon.supfusion.module.registry.dto.AddModuleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块注册初始化类
 * 系统启动时自动执行，完成模块注册、菜单注册、工作流页面注册
 */
@Component
@Slf4j
@Order(value = 1)
public class Module{moduleCode}RegisterInitialize implements CommandLineRunner {

    @Autowired
    private ModuleRegistryApi moduleRegistryApi;
    
    @Autowired
    private NebuleMenuInfoApiService menuInfoApiService;
    
    @Autowired
    private NebulePermissionApiService permissionApiService;
    
    @Autowired
    private SuposFlowCfgApi suposFlowCfgApi;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 设置租户ID
            RpcContext.getContext().setTenantId("dt");
            
            // 1. 注册模块到ModuleRegistry（必需）
            registerModule();
            
            // 2. 初始化菜单（可选，需要菜单时启用）
            // initMenu();
            
            // 3. 注册工作流服务和页面（需要工作流时启用）
            // initWorkflowPages();
            
        } catch (Exception e) {
            log.error("模块注册初始化失败", e);
        }
    }

    /**
     * 注册模块到ModuleRegistry（必需）
     */
    private void registerModule() {
        AddModuleDTO moduleDTO = new AddModuleDTO();
        moduleDTO.setModuleId("{moduleCode}");
        moduleDTO.setModuleCode("{moduleCode}");
        moduleDTO.setNameOfI18nCode("{moduleCode}");
        moduleRegistryApi.addModule(moduleDTO);
        log.info("ModuleRegistry注册成功: {}", "{moduleCode}");
    }

    /**
     * 初始化菜单（可选）
     * 需要菜单时，在resources目录下创建initmenu{moduleCode}.json文件
     */
    private void initMenu() {
        JSONHelper jsonHelper = new JSONHelper();
        String jsonMenu = jsonHelper.resolveJsonFileToString("initmenu{moduleCode}.json");
        menuInfoApiService.initModuleMenu(jsonMenu);
        log.info("菜单注册成功");
    }

    /**
     * 注册工作流服务和页面（工作流需要时启用）
     * 需要工作流时，在resources目录下创建view.json文件
     */
    private void initWorkflowPages() throws IOException {
        // 注册工作流服务
        RegSvcReq svcReq = new RegSvcReq();
        svcReq.setServiceCode("nb_flow_{moduleCode}");
        svcReq.setServiceName("{模块显示名}_工作流服务");
        svcReq.setServiceDesc(svcReq.getServiceName() + "(nb_flow_{moduleCode})");
        svcReq.setServiceSource(WorkflowConst.SERVICE_SOURCE);
        suposFlowCfgApi.regSvc(svcReq);
        log.info("工作流服务注册成功");

        // 注册页面到工作流
        JSONHelper jsonHelper = new JSONHelper();
        String viewJson = jsonHelper.resolveJsonFileToString("view.json");
        JSONObject jsonObject = JSON.parseObject(viewJson);
        JSONArray viewArray = jsonObject.getJSONArray("view");
        
        List<RegPageReq> reqList = new ArrayList<>();
        if (viewArray != null) {
            for (int i = 0; i < viewArray.size(); i++) {
                JSONObject viewObj = viewArray.getJSONObject(i);
                RegPageReq req = new RegPageReq();
                req.setServiceCode("nb_flow_{moduleCode}");
                req.setServiceSource(WorkflowConst.SERVICE_SOURCE);
                req.setPageCode(viewObj.getString("code"));
                req.setPageName(viewObj.getString("viewName"));
                req.setAccessUrl(viewObj.getString("viewUrl"));
                req.setEmbedded(0);
                reqList.add(req);
            }
        }
        suposFlowCfgApi.regPage(reqList);
        log.info("工作流页面注册成功，共{}个页面", reqList.size());
    }
}
```

**initmenu{moduleCode}.json 菜单配置（可选）**:
```json
{
  "menus": [
    {
      "menuCode": "{moduleCode}_menu_001",
      "menuName": "{菜单显示名}",
      "menuIcon": "icon-name",
      "menuUrl": "/{moduleCode}/page-view/{viewCode}",
      "parentCode": "parent_menu_code",
      "sort": 1
    }
  ]
}
```

**view.json 工作流页面配置（工作流需要时）**:
```json
{
  "view": [
    {
      "code": "{moduleCode}_custom_{entityCode}list",
      "viewCode": "{moduleCode}_{entityCode}list",
      "viewName": "{实体名}列表",
      "viewUrl": "/{moduleCode}/page-view/{entityCode}list"
    },
    {
      "code": "{moduleCode}_custom_{entityCode}edit",
      "viewCode": "{moduleCode}_{entityCode}edit",
      "viewName": "{实体名}编辑",
      "viewUrl": "/{moduleCode}/page-view/{entityCode}edit"
    },
    {
      "code": "{moduleCode}_custom_{entityCode}add",
      "viewCode": "{moduleCode}_{entityCode}add",
      "viewName": "{实体名}新增",
      "viewUrl": "/{moduleCode}/page-view/{entityCode}add"
    }
  ]
}
```

> **说明**:
> - **模块注册** (`registerModule`): **必需**，所有模块都必须注册到ModuleRegistry
> - **菜单注册** (`initMenu`): **可选**，需要自定义菜单时启用，配置initmenu{moduleCode}.json
> - **页面注册** (`initWorkflowPages`): **可选**，只有页面需要用于工作流时才需要注册，配置view.json

#### 3.1.7 {moduleCode}-custom-resource

**目录结构**:
```
{moduleCode}-custom-resource/
├── src/main/resources/
│   ├── mapper/                 # 自定义MyBatis映射
│   │   ├── kingbase/           # 人大金仓数据库
│   │   ├── mariadb/            # MariaDB数据库
│   │   ├── oracle/             # Oracle数据库
│   │   └── sqlserver/          # SQL Server数据库
│   └── static/{moduleCode}/    # 静态资源
│       └── {moduleCode}_custom_{功能名}/
│           ├── css/
│           ├── imgs/
│           ├── js/
│           │   └── entry.js    # 页面入口JS
│           └── {功能名}.html
└── pom.xml
```

**MyBatis映射文件命名规范**:
```
mapper/{dbType}/
└── custom{ModuleCode}{EntityName}Mapper.xml

示例:
mapper/mariadb/
└── customAiproctsQjdstMapper.xml        # 自定义请假单映射
└── customAiproctsQjdlistMapper.xml      # 自定义请假列表映射
```

> **命名规则**: `custom` + `{ModuleCode}`(首字母大写) + `{EntityName}` + `Mapper.xml`

**数据库SQL初始化脚本规范**:

**文件命名**:
```
{moduleCode}CustomInit.sql

示例:
aiproctsCustomInit.sql        # 请假单模块初始化脚本
salesCustomInit.sql           # 销售模块初始化脚本
```

**版本控制（文件内部）**:
```sql
## 1.0.0
CREATE TABLE {TABLE_NAME}(
    -- 初始表结构
);

## 1.0.1
ALTER TABLE {TABLE_NAME} ADD COLUMN xxx;

## 1.1.0
CREATE INDEX idx_xxx ON {TABLE_NAME}(xxx);
```

**版本规范说明**:

| 版本格式 | 说明 | 示例 |
|----------|------|------|
| `## X.Y.Z` | 语义化版本 | `## 1.0.0`, `## 1.0.1`, `## 1.1.0` |
| `X` | 主版本号，重大变更 | 表结构重构 |
| `Y` | 次版本号，功能新增 | 新增字段/索引 |
| `Z` | 修订号，问题修复 | 修复数据问题 |

> **注意**: 
> - SQL文件统一命名为 `{moduleCode}CustomInit.sql`
> - 版本通过文件内的 `## X.Y.Z` 注释控制
> - 每次新增SQL在文件末尾追加新的版本区块

**静态资源规范**:
- 目录命名: `{moduleCode}_custom_{功能名}`
- 每个功能目录包含: `css/`, `imgs/`, `js/`, `{功能名}.html`
- JS入口文件统一命名为 `entry.js`

> **注意**: 当 `{moduleCode}` 用于类名时，需要转换为 `{ModuleCode}`（首字母大写）
> - 包名/路径: `{moduleCode}` → `aiprocts`
> - 类名: `{ModuleCode}` → `Aiprocts`

---

## 四、{moduleCode}-resource 国际化资源

**路径**: `{moduleCode}/{moduleCode}-resource/src/main/resources/META-INF/i18N/`

**用途**: 存放国际化资源文件，**这是唯一可以修改的resource目录**

**文件命名**:
```
{moduleCode}_zh_CN.properties   # 中文
{moduleCode}_en_US.properties   # 英文
{moduleCode}_zh_HK.properties   # 繁体中文
{moduleCode}_de_DE.properties   # 德文
{moduleCode}_id_ID.properties   # 印尼文
```

**Key命名规范**:
```properties
{moduleCode}.model.{ModelName}.field.{fieldName}={字段显示名}
{moduleCode}.view.{viewName}.title={页面标题}
{moduleCode}.view.{viewName}.buttonName.{id}={按钮名称}
```

---

## 五、平台生成目录说明（禁止修改）

以下目录由平台设计器自动生成，**禁止手动修改**：

### 5.1 平台生成模块目录

| 目录 | 用途 |
|------|------|
| `{moduleCode}-api` | 平台生成的API接口和DTO |
| `{moduleCode}-common` | 平台生成的公共类、VO、Query |
| `{moduleCode}-dao` | 平台生成的Entity、DAO |
| `{moduleCode}-service` | 平台生成的Service接口和实现 |
| `{moduleCode}-webapi` | 平台生成的Controller |

### 5.2 {moduleCode}-resource/META-INF/metadata 目录详解

**路径**: `{moduleCode}-resource/src/main/resources/META-INF/metadata/`

| 文件 | 说明 | 是否可修改 |
|------|------|------------|
| `{moduleCode}.json` | 模块元数据信息（包含moduleCode、acronym、模型定义等） | ❌ 禁止修改 |
| `nb_flow_{moduleCode}_com.supcon.icds.data` | CDS平台配置的SupOS工作流数据 | ❌ 禁止修改 |
| `menu.json` | CDS平台配置的菜单信息 | ❌ 禁止修改 |
| `systemCode.json` | CDS平台配置的系统编码 | ❌ 禁止修改 |

**{moduleCode}.json 关键字段说明**:
```json
{
  "module": {
    "moduleCode": "{moduleCode}",      // 模块编码
    "moduleName": "{模块名称}",
    "acronym": "{acronym}",            // 模块缩略码，用于建表前缀
    "enableWorkflow": 1,               // 是否启用工作流
    "workFlowSvcCode": "nb_flow_{moduleCode}"  // 工作流服务编码
  }
}
```

> ⚠️ **重要说明**:
> - 以上文件由CDS平台自动生成和更新
> - 需要修改菜单和系统编码时，**必须通过自定义代码区实现**，不能直接修改这些文件
> - 修改这些文件在CDS平台安装时会被覆盖

### 5.3 其他平台生成资源

| 目录 | 用途 |
|------|------|
| `{moduleCode}-resource/META-INF/appdata` | 平台生成的应用数据 |
| `{moduleCode}-resource/META-INF/component` | 平台生成的组件配置 |
| `{moduleCode}-resource/mapper` | 平台生成的MyBatis映射 |

> ⚠️ **再次强调**: 修改以上目录下的文件无效，CDS平台安装时会重新覆盖！

---

## 六、编码规范

### 6.1 命名约定

#### 包命名
- 基础包名: `com.supcon.nebule.{moduleCode}`
- 自定义扩展: `com.supcon.nebule.{moduleCode}.custom`

#### 类命名
| 类型 | 命名规则 | 示例 |
|------|----------|------|
| 自定义Entity | Custom{名称}Entity | Custom{moduleCode}QjdstEntity |
| DAO | {名称}Dao | {moduleCode}QjdstDao |
| Service接口 | {名称}Service | {moduleCode}QjdstService |
| Service实现 | {名称}ServiceImpl | {moduleCode}QjdstServiceImpl |
| Controller | {名称}Controller | {moduleCode}QjdstController |
| VO | {名称}VO | {moduleCode}QjdstVO |
| BO | {名称}BO | {moduleCode}QjdstBO |

#### 方法命名
- 获取方法以get开头，如 `getUserName()`
- 设置方法以set开头，如 `setUserName()`
- 判断方法以is/has/can开头，如 `isEnabled()`
- 业务方法使用动词开头，如 `saveUser()`, `deleteUser()`

### 6.2 代码风格
- 使用4空格缩进（不要使用Tab）
- 每行代码不超过120个字符
- 类必须有JavaDoc注释
- 公共方法必须有JavaDoc注释
- 注释使用中文

---

## 七、工作流集成规范

### 7.1 Service接口继承

```java
public interface Custom{名称}Service extends IECWorkflowBaseService<Custom{名称}Entity> {
    Long submit(WorkFlow<Custom{名称}BO> workFlowBO);
    Boolean revocationPending(WorkFlow<Custom{名称}BO> workFlowBO);
    Boolean cancelProcess(WorkFlow<Custom{名称}BO> workFlowBO);
    Custom{名称}BO getByPendingIdBO(Long pendingId);
}
```

### 7.2 工作流注册

在 `{moduleCode}-custom-manager` 的 `ModuleRegisterInitialize.java` 中注册:

```java
// 注册工作流服务
RegSvcReq req = new RegSvcReq();
req.setServiceCode("nb_flow_{moduleCode}");
req.setServiceName("{模块名}_工作流服务");
suposFlowCfgApi.regSvc(req);

// 注册页面到工作流
RegPageReq pageReq = new RegPageReq();
pageReq.setServiceCode("nb_flow_{moduleCode}");
pageReq.setPageCode("页面编码");
pageReq.setPageName("页面名称");
pageReq.setAccessUrl("页面URL");
suposFlowCfgApi.regPage(Arrays.asList(pageReq));
```

---

## 八、前端页面规范

### 8.1 静态资源目录

```
static/{moduleCode}/
└── {moduleCode}_custom_{功能名}/
    ├── css/
    ├── imgs/
    ├── js/
    │   └── entry.js
    └── {功能名}.html
```

### 8.2 API调用规范

```javascript
// 获取认证Token
const token = localStorage.getItem('ticket') || '';

// 请求头
const headers = {
    'Authorization': 'Bearer ' + token,
    'x-nebule-model-code': '{modelCode}',
    'x-nebule-module-code': '{moduleCode}'
};
```

---

## 九、代码生成模板

### 9.1 完整Entity模板

```java
package com.supcon.nebule.{moduleCode}.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.supcon.nebule.fr.annotation.EnCodeField;
import com.supcon.nebule.framework.common.annotation.audit.AuditModel;
import com.supcon.nebule.framework.common.entity.AbstractConfigurationWorkflowEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@TableName("{tableName}")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@EnCodeField(module = "{moduleCode}", model = "{modelCode}", acronym="{acronym}", srcAcronym="")
@AuditModel(module = "{moduleCode}", model = "{modelCode}")
public class Custom{EntityName}Entity extends AbstractConfigurationWorkflowEntity {

    @ApiModelProperty("{字段描述}")
    @TableField("{fieldName}")
    private {fieldType} {propertyName};
}
```

### 9.2 完整DAO模板

```java
package com.supcon.nebule.{moduleCode}.custom.dao;

import com.supcon.nebule.{moduleCode}.custom.entity.Custom{EntityName}Entity;
import com.supcon.nebule.{moduleCode}.custom.query.Custom{EntityName}Query;
import com.supcon.nebule.framework.common.constant.SysConstants;
import com.supcon.nebule.framework.common.dao.BaseDao;
import com.supcon.nebule.framework.common.entity.PageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface Custom{EntityName}Dao extends BaseDao<Custom{EntityName}Entity> {
    List<Custom{EntityName}Entity> pageQuery(@Param(SysConstants.QUERY) PageQuery<Custom{EntityName}Query> query);
    Long countPageQuery(@Param(SysConstants.QUERY) PageQuery<Custom{EntityName}Query> query);
}
```

### 9.3 完整Controller模板

```java
package com.supcon.nebule.{moduleCode}.custom.controller;

import com.supcon.nebule.{moduleCode}.custom.bo.Custom{EntityName}BO;
import com.supcon.nebule.{moduleCode}.custom.service.Custom{EntityName}Service;
import com.supcon.nebule.{moduleCode}.custom.vo.Custom{EntityName}VO;
import com.supcon.nebule.framework.common.annotation.audit.AuditLog;
import com.supcon.nebule.framework.common.annotation.nullupdate.ModifiedSerialization;
import com.supcon.nebule.framework.common.entity.Page;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import com.supcon.nebule.workflow.sdk.bo.WorkFlow;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@InternalApi(path = HttpConstants.URL_SPLITER + "{moduleCode}" + HttpConstants.URL_SPLITER + "{entityCode}")
public class Custom{EntityName}Controller {

    @Autowired
    private Custom{EntityName}Service service;

    @PostMapping("/save")
    @ApiOperation("保存记录")
    @ModifiedSerialization
    @AuditLog
    public Result<Long> save(@RequestBody Custom{EntityName}VO vo) {
        Custom{EntityName}BO bo = PojoUtil.copy(vo, Custom{EntityName}BO.class);
        return Result.success(service.saveBO(bo));
    }

    @PostMapping("/page")
    @ApiOperation("分页查询")
    public Result<Page<Custom{EntityName}VO>> pageQuery(@RequestBody PageQuery<Custom{EntityName}Query> pageQuery) {
        Page<Custom{EntityName}BO> page = service.pageQueryBO(pageQuery);
        return Result.success(PojoUtil.copyPage(page, Custom{EntityName}VO.class));
    }

    @GetMapping("/info")
    @ApiOperation("根据ID获取")
    public Result<Custom{EntityName}VO> getById(@RequestParam("id") Long id) {
        Custom{EntityName}BO bo = service.getByIdBO(id);
        return Result.success(PojoUtil.copy(bo, Custom{EntityName}VO.class));
    }

    @PostMapping("/submit")
    @ModifiedSerialization
    @ApiOperation("提交工作流")
    @AuditLog
    public Result<Long> submit(@RequestBody WorkFlow<Custom{EntityName}VO> workFlowBO) {
        WorkFlow<Custom{EntityName}BO> wBO = PojoUtil.copy(workFlowBO, WorkFlow.class);
        Custom{EntityName}BO bo = PojoUtil.copy(workFlowBO.getEntity(), Custom{EntityName}BO.class);
        wBO.setEntity(bo);
        return Result.success(service.submit(wBO));
    }
}
```

---

## 十、启动引导模块

**路径**: `{appCode}-bootstrap/`

**启动类**:
```java
@SpringBootApplication(scanBasePackages={
    "com.supcon.nebule.{appCode}",
    "com.supcon.nebule.framework.starter",
    "com.supcon.nebule.api.workflow",
    "com.supcon.nebule.{moduleCode}"
})
@MapperScan({
    "com.supcon.nebule.{moduleCode}.dao",
    "com.supcon.nebule.{moduleCode}.custom.dao"
})
@EnableScheduling
@EnableFeignClients({...})
public class Bootstrap {
    public static void main(String[] args) {
        ApusicEnvUtil.initBeforeStart();
        SpringApplication.run(Bootstrap.class, args);
    }
}
```

---

## 十一、注意事项

1. ✅ **代码存放**: 所有AI生成的代码必须放在 `{moduleCode}-custom` 目录下
2. ✅ **国际化**: 国际化资源文件可放在 `{moduleCode}-resource/META-INF/i18N/`
3. ❌ **禁止修改**: `{moduleCode}-api/common/dao/service/webapi` 等平台生成目录禁止修改
4. ❌ **修改无效**: 修改平台生成目录的文件会在CDS安装时被覆盖
5. **多数据库**: MyBatis映射文件需为每种数据库提供对应版本
6. **工作流**: Entity继承 `AbstractConfigurationWorkflowEntity` 才能使用工作流功能
7. **日志**: 使用 `@AuditLog` 注解记录操作日志
8. **API认证**: 所有API调用需携带 `Authorization` 头和模块相关头信息

---

*文档版本: 1.0.0*
*最后更新: 2026-03-19*
