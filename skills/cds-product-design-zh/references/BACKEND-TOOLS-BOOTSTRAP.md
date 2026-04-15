# 后端启动引导模块工具指南

## 概述

本指南详细说明了 CDS 后端项目的启动引导模块结构、启动类实现、配置管理以及相关使用规范。启动引导模块是整个后端应用的入口，负责应用初始化、包扫描、数据源配置、服务注册等核心功能。

## appCode 获取来源与确认规则

在生成启动引导模块前，必须先确认当前项目使用的 `appCode`。

### 获取优先顺序

1. **优先扫描项目中的 App.json 文件**
   - 查找路径：`{moduleCode}-resource/src/main/resources/META-INF/appdata/App.json`
   - 提取字段：`code` 字段的值即为 `appCode`
   - 示例：`{"code":"aiproctsap", ...}` → `appCode = aiproctsap`

2. **若 App.json 存在且成功提取**
   - 向用户展示读取到的 `appCode` 并确认是否正确
   - 确认无误后才能继续生成启动类

3. **若 App.json 不存在或未找到 `code` 字段**
   - 必须向用户询问 `appCode`
   - 说明该值将用于生成启动类工程名称、健康检查接口路径等

### 标准确认提问话术

#### 情况1：从 App.json 中拿到了 appCode

```
我已从 App.json 文件中读取到以下应用标识：
- `appCode`: `{已读取到的appCode}`

该值将用于：
- 启动类工程名称：`{appCode}-bootstrap`
- 健康检查接口路径：`/{appCode}/health`
- 启动类包名：`com.supcon.nebule.{appCode}`

请确认是否正确：
- 若回复 **正确**：我将继续使用该值生成启动类
- 若回复 **不正确**：请直接提供正确的 `appCode`
```

#### 情况2：App.json 不存在或未提取到 appCode

```
当前项目中未找到 App.json 文件或未提取到 `code` 字段。

请提供当前应用的 `appCode`：
- `appCode` 是什么？

我需要使用该值生成：
- 启动类工程：`{appCode}-bootstrap`
- 启动类：`Bootstrap.java`（包名：`com.supcon.nebule.{appCode}`）
- 健康检查控制器：`{appCode}HealthController.java`
- 健康检查接口：`/{appCode}/health`

为避免生成错误，请确认后我再继续。
```

### appCode 用途说明

| 用途 | 示例 | 说明 |
|------|------|------|
| 启动类工程名 | `{appCode}-bootstrap` | Maven 模块名称 |
| 启动类包名 | `com.supcon.nebule.{appCode}` | Bootstrap.java 所在包 |
| 健康检查控制器 | `{appCode}HealthController.java` | 健康检查类名 |
| 健康检查接口 | `/{appCode}/health` | Kubernetes 探针检测路径 |
| 配置文件 | `bootstrap.properties` | 中的应用标识配置 |

## 启动引导模块结构

**路径**: `{appCode}-bootstrap/`

> **📌 重要**：`{appCode}` 必须通过上述 **appCode 获取来源与确认规则** 获取并确认后才能使用。

```
{appCode}-bootstrap/
├── src/main/java/com/supcon/nebule/{appCode}/
│   ├── {appCode}HealthController.java  # 健康检查接口
│   └── Bootstrap.java                # 启动类
├── src/main/resources/
│   ├── bootstrap.properties          # 启动配置文件
│   └── logback-spring.xml            # 日志配置
└── pom.xml                           # Maven 依赖配置
```

### 目录说明

| 文件 | 用途 |
|------|------|
| `{appCode}HealthController.java` | 应用健康检测接口，用于 Kubernetes 健康探针检测 |
| `Bootstrap.java` | Spring Boot 启动类，包含包扫描、Mapper 扫描、Feign 客户端配置 |
| `bootstrap.properties` | 应用启动配置，包含 Nacos、数据库、Kafka、国际化等配置 |
| `logback-spring.xml` | 日志框架配置，定义日志输出格式和级别 |
| `pom.xml` | Maven 依赖配置，声明应用依赖模块 |

## 启动类实现规范

### 标准启动类模板

```java
package com.supcon.nebule.{appCode};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.supcon.nebule.framework.common.utils.ApusicEnvUtil;

/**
 * 项目启动类
 */
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
@EnableFeignClients({
    "com.supcon.supfusion.*.api",
    "com.supcon.supfusion.file.server.api",
    "com.supcon.supfusion.i18n.service.api",
    "com.supcon.nebule.ec.api",
    "com.supcon.nebule.api",
    "com.supcon.nebule.npc.api",
    "com.supcon.nebule.apisvr.api",
    "com.supcon.nebule.app.stand.api",
    "com.supcon.nebule.tenantmanager.api"
})
public class Bootstrap {
    public static void main(String[] args) {
        ApusicEnvUtil.initBeforeStart();
        SpringApplication.run(Bootstrap.class, args);
    }
}
```

### 注解说明

| 注解 | 用途 |
|------|------|
| `@SpringBootApplication` | Spring Boot 启动注解，`scanBasePackages` 指定扫描的基础包路径 |
| `@MapperScan` | MyBatis Mapper 扫描注解，指定平台生成 DAO 和自定义 DAO 的包路径 |
| `@EnableScheduling` | 启用 Spring 定时任务功能 |
| `@EnableFeignClients` | 启用 Feign 客户端，声明需要调用的外部服务 API 包路径 |

### 包扫描规则

**scanBasePackages 必须包含**：
- `com.supcon.nebule.{appCode}`：当前应用包
- `com.supcon.nebule.framework.starter`：CDS 框架启动包
- `com.supcon.nebule.api.workflow`：工作流 API 包
- `com.supcon.nebule.{moduleCode}`：当前模块包

**MapperScan 必须包含**：
- `com.supcon.nebule.{moduleCode}.dao`：平台生成的 DAO 包
- `com.supcon.nebule.{moduleCode}.custom.dao`：自定义 DAO 包

## 健康检查接口

### 标准健康检查类模板

```java
package com.supcon.nebule.{appCode};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 蓝卓app健康检测接口
 */
@RestController
public class {appCode}HealthController {

    @GetMapping("/{appCode}/health")
    public String health() {
        return "success";
    }
}
```

### 健康检查规范

1. **路径规范**：`/{appCode}/health`
2. **返回格式**：纯文本 `success`
3. **用途**：Kubernetes 健康探针检测、负载均衡健康检查
4. **权限**：该接口应配置为无需权限控制，可在 `bootstrap.properties` 中配置忽略路径

## 配置文件规范

### bootstrap.properties 核心配置项

```properties
# 应用基础配置
spring.application.name={appCode}
server.port=8080
nebule.app.name={应用名称}
nebule.app.version=1.0.0

# Nacos 配置中心
spring.cloud.nacos.config.server-addr=http://${SUPOS_NACOS_ADDRESS:nacos.default:8848}
spring.cloud.nacos.config.username=${SUPOS_NACOS_USERNAME:nacos}
spring.cloud.nacos.config.password=${SUPOS_NACOS_PASSWORD:nacos}
spring.cloud.nacos.discovery.username=${SUPOS_NACOS_USERNAME:nacos}
spring.cloud.nacos.discovery.password=${SUPOS_NACOS_PASSWORD:nacos}
spring.cloud.nacos.config.max-retry=${NACOS_CONFIG_MAX_RETRY:500}

# 模块配置
nebule.app.moduleCodes={moduleCode}
nebule.app.modules[0].code={moduleCode}
nebule.app.modules[0].name={模块名称Base64编码}
nebule.app.modules[0].version=1.0.0

# MyBatis Plus 配置
mybatis-plus.mapper-locations=classpath*:mapper/${supfusion.cloud.datasource.connect.system.db-type}/*.xml

# Feign 配置
feign.client.default-config=default
feign.client.default-to-properties=true
feign.client.config.default.connect-timeout=30000
feign.client.config.default.read-timeout=180000

# 健康检查配置
management.endpoints.web.base-path=/actuator
management.endpoints.web.exposure.include=*
management.endpoint.health.probes.enabled=true
management.endpoint.health.show-details=ALWAYS
```

### 配置项说明

| 配置项 | 说明 |
|--------|------|
| `spring.application.name` | 应用名称，对应 `{appCode}` |
| `nebule.app.moduleCodes` | 模块编码，对应 `{moduleCode}` |
| `mybatis-plus.mapper-locations` | Mapper XML 文件路径，按数据库类型区分 |
| `feign.client.config.default.connect-timeout` | Feign 连接超时时间（毫秒） |
| `feign.client.config.default.read-timeout` | Feign 读取超时时间（毫秒） |

## Maven 依赖配置

### pom.xml 标准结构

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.supcon.nebule</groupId>
        <artifactId>nebule-bom</artifactId>
        <version>1.2.6.0.RELEASE</version>
        <relativePath />
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>{appCode}-bootstrap</artifactId>
    <version>1.0.0.0</version>
    
    <dependencies>
        <!-- CDS 框架启动依赖 -->
        <dependency>
            <groupId>com.supcon.nebule</groupId>
            <artifactId>nebule-framework-starter</artifactId>
            <version>${nebule.framework.starter.version}</version>
        </dependency>
        
        <!-- 平台生成模块 WebAPI -->
        <dependency>
            <groupId>com.supcon.nebule</groupId>
            <artifactId>{moduleCode}-webapi</artifactId>
            <version>1.0.0</version>
        </dependency>
        
        <!-- 自定义模块 WebAPI -->
        <dependency>
            <groupId>com.supcon.nebule</groupId>
            <artifactId>{moduleCode}-custom-webapi</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <finalName>{appCode}</finalName>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## 使用指南

### 配置检查清单

- [ ] 启动类 `scanBasePackages` 包含所有必要的包路径
- [ ] 启动类 `@MapperScan` 包含平台生成 DAO 和自定义 DAO 包
- [ ] 启动类 `@EnableFeignClients` 声明所有需要调用的外部服务
- [ ] `bootstrap.properties` 中应用名称、模块编码配置正确
- [ ] Nacos 配置中心地址、用户名、密码配置正确
- [ ] MyBatis Mapper 路径配置正确
- [ ] 健康检查接口路径与 `bootstrap.properties` 中的忽略路径一致
- [ ] `pom.xml` 依赖模块版本正确

### 配置验证步骤

1. **编译检查**: 确保配置类能够正常编译
   ```bash
   mvn clean compile
   ```

2. **启动检查**: 应用启动时检查配置日志
   ```bash
   mvn spring-boot:run
   ```

3. **功能检查**: 验证模块功能是否正常工作
   - 访问健康检查接口：`http://localhost:8080/{appCode}/health`
   - 检查 Actuator 端点：`http://localhost:8080/actuator/health`

4. **日志检查**: 确认关键配置步骤都有日志记录
   - 检查 Nacos 配置拉取日志
   - 检查 Mapper 扫描日志
   - 检查 Feign 客户端注册日志

### 常见问题

**Q: 启动时提示 Mapper 找不到？**
A: 检查 `@MapperScan` 是否包含正确的 DAO 包路径，确保平台生成 DAO 和自定义 DAO 都被扫描到。

**Q: Feign 客户端调用失败？**
A: 检查 `@EnableFeignClients` 是否声明了正确的 API 包路径，确认 Nacos 服务注册中心配置正确。

**Q: 健康检查接口返回 401？**
A: 检查 `bootstrap.properties` 中是否配置了健康检查路径的权限忽略规则。
