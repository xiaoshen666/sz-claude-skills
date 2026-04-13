# 后端配置管理工具指南

## 概述

本指南详细说明了 CDS 后端项目的配置管理，包括公共服务配置、模块注册配置等核心配置类的实现和使用。

## 公共服务配置类

### {ModuleCode}PubServiceConfiguration.java

**位置**: `{moduleCode}-custom-common/src/main/java/com/supcon/nebule/{moduleCode}/custom/`

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

### 配置说明

#### @Configuration
- 标记该类为 Spring 配置类
- Spring 容器启动时会加载该类中定义的所有 Bean

#### @ComponentScan
- **必需配置**: 扫描自定义模块的包
- **基础包配置**:
  - `com.supcon.supfusion.module.registry`: 模块注册相关组件
  - `com.supcon.nebule.ecentity`: 实体相关组件
- **自定义扩展**: 可添加自定义的包扫描路径

#### @EnableFeignClients
- **Feign客户端配置**: 启用 Feign 客户端
- **基础配置**: `com.supcon.supfusion.module.registry.api`
- **自定义扩展**: 可添加自定义的 Feign 客户端包路径

#### @MapperScan
- **MyBatis配置**: 扫描 Mapper 接口
- **必需配置**: `com.supcon.nebule.{moduleCode}.custom.dao`
- **多模块支持**: 可配置多个包路径，用数组形式

## 模块注册初始化配置

### ModuleRegisterInitialize.java

**位置**: `{moduleCode}-custom-manager/src/main/java/com/supcon/nebule/{moduleCode}/custom/`

```java
package com.supcon.nebule.{moduleCode}.custom;

import com.supcon.supfusion.framework.cloud.common.context.RpcContext;
import com.supcon.supfusion.module.registry.api.ModuleRegistryApi;
import com.supcon.supfusion.module.registry.dto.AddModuleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 模块注册初始化类
 * 系统启动时自动执行，完成模块注册
 */
@Component
@Slf4j
@Order(value = 1)
public class ModuleRegisterInitialize implements CommandLineRunner {

    @Autowired
    private ModuleRegistryApi moduleRegistryApi;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 设置租户ID
            RpcContext.getContext().setTenantId("dt");

            // 注册模块到ModuleRegistry（必需）
            registerModule();

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
}
```

### 配置说明

#### @Component
- 标记该类为 Spring 组件
- 系统启动时自动实例化并管理

#### @Slf4j
- Lombok 注解，自动生成日志对象
- 用于记录模块注册过程中的日志信息

#### @Order(value = 1)
- 定义执行顺序，确保在其他组件之前执行
- 值越小，优先级越高

#### CommandLineRunner
- Spring Boot 启动时自动执行的接口
- `run` 方法在应用启动完成后执行

#### RpcContext
- 设置租户ID，确保模块在正确的租户环境下注册
- 多租户系统中的关键配置

## 扩展配置

### 菜单注册配置

```java
/**
 * 初始化菜单（可选）
 */
private void initMenu() {
    JSONHelper jsonHelper = new JSONHelper();
    String jsonMenu = jsonHelper.resolveJsonFileToString("initmenu{moduleCode}.json");
    menuInfoApiService.initModuleMenu(jsonMenu);
    log.info("菜单注册成功");
}
```

**菜单配置文件**: `initmenu{moduleCode}.json`
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

### 工作流页面注册配置

```java
/**
 * 注册工作流服务和页面（工作流需要时启用）
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
```

**页面配置文件**: `view.json`
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

## 配置最佳实践

### 1. 配置类设计原则

#### 单一职责原则
- 每个配置类只负责一个特定的配置领域
- 避免在一个配置类中处理过多不相关的配置

#### 模块化配置
- 按照功能模块划分配置类
- 便于维护和扩展

#### 配置分离
- 将配置代码与业务代码分离
- 便于环境切换和配置管理

### 2. 包扫描配置最佳实践

#### 精确扫描
```java
// 推荐：精确指定需要扫描的包
@ComponentScan(
    basePackages = {
        "com.supcon.nebule.{moduleCode}.custom.service",
        "com.supcon.nebule.{moduleCode}.custom.controller"
    }
)

// 不推荐：过于宽泛的包扫描
@ComponentScan("com.supcon.nebule")
```

#### Mapper扫描优化
```java
// 推荐：精确指定Mapper包
@MapperScan({
    "com.supcon.nebule.{moduleCode}.custom.dao",
    "com.supcon.nebule.{moduleCode}.dao"
})
```

### 3. 模块注册最佳实践

#### 错误处理
```java
@Override
public void run(String... args) throws Exception {
    try {
        // 注册逻辑
        registerModule();
    } catch (Exception e) {
        log.error("模块注册初始化失败", e);
        // 可根据需要决定是否抛出异常
        // throw e;
    }
}
```

#### 日志记录
```java
private void registerModule() {
    log.info("开始注册模块: {}", "{moduleCode}");
    
    AddModuleDTO moduleDTO = new AddModuleDTO();
    moduleDTO.setModuleId("{moduleCode}");
    moduleDTO.setModuleCode("{moduleCode}");
    moduleDTO.setNameOfI18nCode("{moduleCode}");
    
    moduleRegistryApi.addModule(moduleDTO);
    
    log.info("ModuleRegistry注册成功: {}", "{moduleCode}");
}
```

### 4. 配置验证

#### 配置完整性检查
```java
@PostConstruct
public void validateConfiguration() {
    // 验证必要的配置是否存在
    Assert.notNull(moduleRegistryApi, "ModuleRegistryApi 不能为空");
    
    // 验证模块编码配置
    Assert.hasText("{moduleCode}", "模块编码不能为空");
    
    log.info("配置验证通过");
}
```

## 配置调试

### 常见问题排查

#### 包扫描问题
- **症状**: Bean 无法注入，404 错误
- **检查**: `@ComponentScan` 配置是否正确
- **解决**: 确认包路径拼写正确，包含所有必要的包

#### Mapper扫描问题
- **症状**: MyBatis 映射文件无法加载
- **检查**: `@MapperScan` 配置是否正确
- **解决**: 确认 DAO 接口包路径正确配置

#### 模块注册问题
- **症状**: 模块功能无法正常使用
- **检查**: 日志中是否有注册成功信息
- **解决**: 检查 `ModuleRegisterInitialize` 是否正确执行

### 配置日志

#### 启用配置日志
```properties
# application.properties
logging.level.com.supcon.nebule.{moduleCode}.custom=DEBUG
logging.level.org.springframework.context.annotation=DEBUG
logging.level.org.mybatis.spring=DEBUG
```

#### 关键日志信息
```
# 配置类加载日志
2023-01-01 10:00:00.000  INFO  [main] c.s.n.{moduleCode}.custom.{ModuleCode}PubServiceConfiguration  : Starting {ModuleCode}PubServiceConfiguration

# 模块注册日志
2023-01-01 10:00:01.000  INFO  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : ModuleRegistry注册成功: {moduleCode}

# Mapper扫描日志
2023-01-01 10:00:02.000  INFO  [main] o.m.spring.mapper.ClassPathMapperScanner          : Identified candidate component class
```

## 配置扩展

### 自定义配置类

```java
@Configuration
@ConditionalOnProperty(name = "{moduleCode}.feature.enabled", havingValue = "true")
public class {ModuleCode}FeatureConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public {ModuleCode}CustomService {moduleCode}CustomService() {
        return new {ModuleCode}CustomServiceImpl();
    }
    
    @Bean
    public {ModuleCode}EventListener {moduleCode}EventListener() {
        return new {ModuleCode}EventListener();
    }
}
```

### 环境特定配置

```java
@Configuration
@Profile("dev")
public class {ModuleCode}DevConfiguration {
    // 开发环境特定配置
}

@Configuration
@Profile("prod")
public class {ModuleCode}ProdConfiguration {
    // 生产环境特定配置
}
```

## 使用指南

### 配置检查清单

- [ ] `{ModuleCode}PubServiceConfiguration.java` 已创建
- [ ] `@ComponentScan` 配置正确
- [ ] `@MapperScan` 配置正确
- [ ] `ModuleRegisterInitialize.java` 已创建
- [ ] 模块注册逻辑正确实现
- [ ] 必要的依赖已添加
- [ ] 配置日志已启用

### 配置验证步骤

1. **编译检查**: 确保配置类能够正常编译
2. **启动检查**: 应用启动时检查配置日志
3. **功能检查**: 验证模块功能是否正常工作
4. **日志检查**: 确认关键配置步骤都有日志记录