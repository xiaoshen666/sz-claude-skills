# 后端项目结构工具指南

## 概述

本指南详细说明了 CDS 后端项目的标准目录结构和模块划分，帮助后端工程师快速理解和构建符合 CDS 规范的项目结构。

## CDS 标准目录结构

### 整体项目结构

```
{moduleCode}-V1.0.0/
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

### {moduleCode}-resource/META-INF/metadata 目录详解

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
│       └── persistence.xml     # JPA持久化配置（如需要）
└── pom.xml
```

**用途**: 存放数据访问层相关的代码，包括实体类、DAO接口、查询对象等。

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

## 包结构规范

### 标准包结构

```
com.supcon.nebule.{moduleCode}.custom/
├── config/                    # 配置类
├── controller/                # 控制器
├── dao/                       # 数据访问接口
├── entity/                    # 实体类
├── bo/                        # 业务对象
├── vo/                        # 视图对象
├── query/                     # 查询对象
├── service/                   # 服务接口
│   └── impl/                  # 服务实现
├── manager/                   # 管理器
├── utils/                     # 工具类
└── dto/                       # 数据传输对象
```

### 包命名规则

- **基础包名**: `com.supcon.nebule.{moduleCode}`
- **自定义扩展**: `com.supcon.nebule.{moduleCode}.custom`
- **子包**: 按照功能模块划分，使用小写字母


## 启动引导模块

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

## 前端编译之后页面资源目录规范

### 静态资源目录

```
static/{moduleCode}/
└── cds-template/dist/*
```

### API调用规范

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

## 使用指南

### 新建模块步骤

1. **创建自定义目录结构**
   ```bash
   # 创建必要的目录
   mkdir -p {moduleCode}-custom-api/src/main/java/com/supcon/nebule/{moduleCode}/custom/api
   mkdir -p {moduleCode}-custom-common/src/main/java/com/supcon/nebule/{moduleCode}/custom
   mkdir -p {moduleCode}-custom-dao/src/main/java/com/supcon/nebule/{moduleCode}/custom/entity
   mkdir -p {moduleCode}-custom-service/src/main/java/com/supcon/nebule/{moduleCode}/custom/service
   mkdir -p {moduleCode}-custom-webapi/src/main/java/com/supcon/nebule/{moduleCode}/custom/controller
   mkdir -p {moduleCode}-custom-manager/src/main/java/com/supcon/nebule/{moduleCode}/custom
   ```

2. **配置公共服务**
   - 创建 `{ModuleCode}PubServiceConfiguration.java`
   - 配置包扫描和Mapper扫描

3. **配置模块注册**
   - 创建 `ModuleRegisterInitialize.java`
   - 配置模块注册逻辑

4. **添加Maven依赖**
   - 配置各个子模块的 `pom.xml`
   - 确保依赖关系正确

### 注意事项

1. ✅ **代码存放**: 所有AI生成的代码必须放在 `{moduleCode}-custom` 目录下
2. ✅ **国际化**: 国际化资源文件可放在 `{moduleCode}-resource/META-INF/i18N/`
3. ❌ **禁止修改**: `{moduleCode}-api/common/dao/service/webapi` 等平台生成目录禁止修改
4. ❌ **修改无效**: 修改平台生成目录的文件会在CDS安装时被覆盖

### 常见问题

**Q: 如何添加新的子模块？**
A: 在 `{moduleCode}-custom` 目录下创建新的子模块，并确保在 `{ModuleCode}PubServiceConfiguration.java` 中正确配置包扫描。

**Q: 如何扩展平台生成的功能？**
A: 在对应的自定义目录中创建新的类，继承或组合平台生成的类，不要直接修改平台生成的代码。

**Q: 如何配置多模块依赖？**
A: 在 `pom.xml` 中正确配置模块间的依赖关系，确保编译顺序正确。