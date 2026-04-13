package com.supcon.nebule.aiprocts.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
        "com.supcon.supfusion.module.registry",
        "com.supcon.nebule.ecentity"
    }
)
@EnableFeignClients("com.supcon.supfusion.module.registry.api")
@MapperScan({"com.supcon.nebule.aiprocts.custom.dao"})
public class AiproctsPubServiceConfiguration {
}
