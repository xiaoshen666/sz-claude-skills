# 后端模块注册工具指南

## 概述

本指南详细说明了 CDS 后端项目的模块注册机制，包括模块注册、菜单注册、工作流页面注册等核心功能的实现。

## 模块注册机制

### 核心组件

#### ModuleRegisterInitialize.java

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
 * 系统启动时自动执行，完成模块注册、菜单注册、工作流页面注册
 */
@Component
@Slf4j
@Order(value = 1)
public class ModuleRegisterInitialize implements CommandLineRunner {

    @Autowired
    private ModuleRegistryApi moduleRegistryApi;

    @Autowired(required = false)
    private NebuleMenuInfoApiService menuInfoApiService;

    @Autowired(required = false)
    private NebulePermissionApiService permissionApiService;

    @Autowired(required = false)
    private SuposFlowCfgApi suposFlowCfgApi;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 设置租户ID
            RpcContext.getContext().setTenantId("dt");

            // 1. 注册模块到ModuleRegistry（必需）
            registerModule();

            // 2. 初始化菜单（可选，需要菜单时启用）
            if (menuInfoApiService != null) {
                initMenu();
            }

            // 3. 注册工作流服务和页面（需要工作流时启用）
            if (suposFlowCfgApi != null) {
                initWorkflowPages();
            }

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
     */
    private void initMenu() {
        try {
            JSONHelper jsonHelper = new JSONHelper();
            String jsonMenu = jsonHelper.resolveJsonFileToString("initmenu{moduleCode}.json");
            menuInfoApiService.initModuleMenu(jsonMenu);
            log.info("菜单注册成功");
        } catch (Exception e) {
            log.warn("菜单注册失败，可能未配置菜单: {}", e.getMessage());
        }
    }

    /**
     * 注册工作流服务和页面（工作流需要时启用）
     */
    private void initWorkflowPages() {
        try {
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
        } catch (Exception e) {
            log.warn("工作流注册失败，可能未配置工作流: {}", e.getMessage());
        }
    }
}
```

## 模块注册详细说明

### 1. 模块基础注册

#### AddModuleDTO 参数说明

| 参数名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| `moduleId` | String | 是 | 模块唯一标识，通常与moduleCode相同 |
| `moduleCode` | String | 是 | 模块编码，用于系统识别 |
| `nameOfI18nCode` | String | 是 | 国际化编码，用于多语言支持 |

#### 注册流程

1. **创建注册对象**
   ```java
   AddModuleDTO moduleDTO = new AddModuleDTO();
   moduleDTO.setModuleId("{moduleCode}");
   moduleDTO.setModuleCode("{moduleCode}");
   moduleDTO.setNameOfI18nCode("{moduleCode}");
   ```

2. **执行注册**
   ```java
   moduleRegistryApi.addModule(moduleDTO);
   ```

3. **日志记录**
   ```java
   log.info("ModuleRegistry注册成功: {}", "{moduleCode}");
   ```

### 2. 菜单注册

#### 菜单配置文件

**位置**: `{moduleCode}-custom-manager/src/main/resources/initmenu{moduleCode}.json`

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
    },
    {
      "menuCode": "{moduleCode}_menu_002",
      "menuName": "{子菜单显示名}",
      "menuIcon": "icon-name",
      "menuUrl": "/{moduleCode}/page-view/{subViewCode}",
      "parentCode": "{moduleCode}_menu_001",
      "sort": 1
    }
  ]
}
```

#### 菜单字段说明

| 字段名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| `menuCode` | String | 是 | 菜单唯一编码 |
| `menuName` | String | 是 | 菜单显示名称 |
| `menuIcon` | String | 否 | 菜单图标 |
| `menuUrl` | String | 是 | 菜单访问URL |
| `parentCode` | String | 否 | 父菜单编码，顶级菜单为空 |
| `sort` | Integer | 是 | 排序号，数字越小越靠前 |

#### 菜单注册流程

1. **读取配置文件**
   ```java
   JSONHelper jsonHelper = new JSONHelper();
   String jsonMenu = jsonHelper.resolveJsonFileToString("initmenu{moduleCode}.json");
   ```

2. **执行菜单注册**
   ```java
   menuInfoApiService.initModuleMenu(jsonMenu);
   ```

3. **错误处理**
   ```java
   try {
       // 菜单注册逻辑
   } catch (Exception e) {
       log.warn("菜单注册失败，可能未配置菜单: {}", e.getMessage());
   }
   ```

### 3. 工作流页面注册

#### 页面配置文件

**位置**: `{moduleCode}-custom-manager/src/main/resources/view.json`

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

#### 页面字段说明

| 字段名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| `code` | String | 是 | 页面唯一编码 |
| `viewCode` | String | 是 | 视图编码 |
| `viewName` | String | 是 | 页面显示名称 |
| `viewUrl` | String | 是 | 页面访问URL |

#### 工作流注册流程

1. **注册工作流服务**
   ```java
   RegSvcReq svcReq = new RegSvcReq();
   svcReq.setServiceCode("nb_flow_{moduleCode}");
   svcReq.setServiceName("{模块显示名}_工作流服务");
   svcReq.setServiceDesc(svcReq.getServiceName() + "(nb_flow_{moduleCode})");
   svcReq.setServiceSource(WorkflowConst.SERVICE_SOURCE);
   suposFlowCfgApi.regSvc(svcReq);
   ```

2. **注册工作流页面**
   ```java
   List<RegPageReq> reqList = new ArrayList<>();
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
   suposFlowCfgApi.regPage(reqList);
   ```

## 注册执行机制

### 启动顺序控制

#### @Order 注解
```java
@Order(value = 1)
public class ModuleRegisterInitialize implements CommandLineRunner {
    // 值越小，优先级越高
    // 确保在其他组件之前执行
}
```

#### CommandLineRunner 接口
```java
@Override
public void run(String... args) throws Exception {
    // 在Spring Boot应用启动完成后执行
    // 此时所有Bean都已初始化完成
}
```

### 依赖注入

#### 必需依赖
```java
@Autowired
private ModuleRegistryApi moduleRegistryApi;
```

#### 可选依赖
```java
@Autowired(required = false)
private NebuleMenuInfoApiService menuInfoApiService;
```

### 错误处理机制

#### 异常捕获
```java
try {
    // 注册逻辑
} catch (Exception e) {
    log.error("模块注册初始化失败", e);
    // 可选择是否抛出异常
    // 如果模块注册失败不影响系统运行，可以不抛出
}
```

#### 部分失败处理
```java
// 菜单注册失败不影响模块注册
if (menuInfoApiService != null) {
    try {
        initMenu();
    } catch (Exception e) {
        log.warn("菜单注册失败: {}", e.getMessage());
    }
}
```

## 注册配置最佳实践

### 1. 模块化配置原则

#### 按需注册
- **必需注册**: 模块基础注册必须实现
- **可选注册**: 菜单、工作流等按需配置
- **条件注册**: 根据环境或配置决定是否注册

#### 配置分离
- **代码配置**: 模块基础信息在代码中配置
- **文件配置**: 菜单、页面等在配置文件中定义
- **环境配置**: 不同环境的配置分离

### 2. 错误处理最佳实践

#### 分级处理
```java
// 严重错误：记录错误并可能抛出异常
log.error("模块注册失败", e);
throw e;

// 警告错误：记录警告但继续执行
log.warn("菜单注册失败: {}", e.getMessage());

// 信息日志：记录成功信息
log.info("模块注册成功: {}", moduleCode);
```

#### 重试机制
```java
private void registerModuleWithRetry() {
    int maxRetries = 3;
    for (int i = 0; i < maxRetries; i++) {
        try {
            registerModule();
            return;
        } catch (Exception e) {
            if (i == maxRetries - 1) {
                throw e;
            }
            log.warn("模块注册失败，准备重试 {}/{}: {}", i + 1, maxRetries, e.getMessage());
            try {
                Thread.sleep(1000 * (i + 1)); // 指数退避
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("重试被中断", ie);
            }
        }
    }
}
```

### 3. 日志记录最佳实践

#### 详细日志
```java
private void registerModule() {
    log.info("开始注册模块: {}, 租户ID: {}", moduleCode, RpcContext.getContext().getTenantId());
    
    AddModuleDTO moduleDTO = new AddModuleDTO();
    moduleDTO.setModuleId(moduleCode);
    moduleDTO.setModuleCode(moduleCode);
    moduleDTO.setNameOfI18nCode(moduleCode);
    
    log.debug("模块注册参数: moduleId={}, moduleCode={}, nameOfI18nCode={}", 
              moduleDTO.getModuleId(), moduleDTO.getModuleCode(), moduleDTO.getNameOfI18nCode());
    
    moduleRegistryApi.addModule(moduleDTO);
    
    log.info("ModuleRegistry注册成功: {}", moduleCode);
}
```

#### 性能监控
```java
private void registerModule() {
    long startTime = System.currentTimeMillis();
    
    try {
        // 注册逻辑
        moduleRegistryApi.addModule(moduleDTO);
        
        long endTime = System.currentTimeMillis();
        log.info("模块注册成功: {}, 耗时: {}ms", moduleCode, endTime - startTime);
    } catch (Exception e) {
        long endTime = System.currentTimeMillis();
        log.error("模块注册失败: {}, 耗时: {}ms, 错误: {}", 
                  moduleCode, endTime - startTime, e.getMessage());
        throw e;
    }
}
```

## 调试和验证

### 注册状态检查

#### 日志检查
```
# 成功日志
2023-01-01 10:00:00.000  INFO  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : 开始注册模块: {moduleCode}
2023-01-01 10:00:00.100  INFO  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : ModuleRegistry注册成功: {moduleCode}
2023-01-01 10:00:00.200  INFO  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : 菜单注册成功
2023-01-01 10:00:00.300  INFO  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : 工作流页面注册成功，共3个页面
```

#### 错误日志
```
# 错误日志
2023-01-01 10:00:00.000  WARN  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : 菜单注册失败，可能未配置菜单: 文件不存在
2023-01-01 10:00:00.100  WARN  [main] c.s.n.{moduleCode}.custom.ModuleRegisterInitialize  : 工作流注册失败，可能未配置工作流: 服务未找到
```

### 常见问题排查

#### 模块注册失败
- **症状**: 模块功能无法使用
- **检查**: 
  - ModuleRegistryApi 是否正确注入
  - 模块编码是否正确
  - 租户ID是否设置正确
- **解决**: 检查依赖注入和参数配置

#### 菜单注册失败
- **症状**: 菜单不显示
- **检查**:
  - initmenu{moduleCode}.json 文件是否存在
  - 菜单配置格式是否正确
  - menuInfoApiService 是否可用
- **解决**: 检查配置文件和API服务

#### 工作流注册失败
- **症状**: 工作流功能异常
- **检查**:
  - view.json 文件是否存在
  - 工作流服务是否配置
  - suposFlowCfgApi 是否可用
- **解决**: 检查配置文件和API服务

## 扩展配置

### 自定义注册逻辑

```java
@Component
@Slf4j
@Order(value = 2) // 在基础注册之后执行
public class CustomModuleRegisterInitialize implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 自定义注册逻辑
        registerCustomServices();
        registerCustomPermissions();
        registerCustomConfigs();
    }

    private void registerCustomServices() {
        // 注册自定义服务
    }

    private void registerCustomPermissions() {
        // 注册自定义权限
    }

    private void registerCustomConfigs() {
        // 注册自定义配置
    }
}
```

### 条件注册

```java
@Component
@Slf4j
@ConditionalOnProperty(name = "{moduleCode}.register.menu", havingValue = "true")
public class ConditionalMenuRegister {
    
    @PostConstruct
    public void registerMenu() {
        // 条件性注册菜单
    }
}
```

## 使用指南

### 配置检查清单

- [ ] ModuleRegisterInitialize.java 已创建
- [ ] 模块基础注册逻辑已实现
- [ ] 租户ID设置正确
- [ ] 菜单配置文件已创建（如需要）
- [ ] 工作流配置文件已创建（如需要）
- [ ] 日志配置正确
- [ ] 错误处理完善

### 注册验证步骤

1. **启动检查**: 应用启动时检查注册日志
2. **功能检查**: 验证模块功能是否正常工作
3. **菜单检查**: 确认菜单是否正确显示
4. **工作流检查**: 验证工作流功能是否正常
5. **日志检查**: 确认所有注册步骤都有日志记录