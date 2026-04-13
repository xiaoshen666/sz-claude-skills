# 后端配置管理工具指南

## 概述

本指南详细说明了 CDS 后端项目的启动类如何使用和配置。

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

## 使用指南

### 配置检查清单


### 配置验证步骤

1. **编译检查**: 确保配置类能够正常编译
2. **启动检查**: 应用启动时检查配置日志
3. **功能检查**: 验证模块功能是否正常工作
4. **日志检查**: 确认关键配置步骤都有日志记录