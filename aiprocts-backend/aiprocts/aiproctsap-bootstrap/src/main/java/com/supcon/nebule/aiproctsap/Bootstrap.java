package com.supcon.nebule.aiproctsap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.supcon.nebule.framework.common.utils.ApusicEnvUtil;
/**
 * 项目启动类
 *
 */
@SpringBootApplication(scanBasePackages={"com.supcon.nebule.aiproctsap","com.supcon.nebule.framework.starter","com.supcon.nebule.api.workflow","com.supcon.nebule.aiprocts"})
@MapperScan({"com.supcon.nebule.aiprocts.dao","com.supcon.nebule.aiprocts.custom.dao"})
@EnableScheduling
@EnableFeignClients({"com.supcon.supfusion.*.api","com.supcon.supfusion.file.server.api","com.supcon.supfusion.i18n.service.api","com.supcon.nebule.ec.api","com.supcon.nebule.api","com.supcon.nebule.npc.api","com.supcon.nebule.apisvr.api","com.supcon.nebule.app.stand.api","com.supcon.nebule.tenantmanager.api"})
public class Bootstrap {
    public static void main(String[] args) {
        ApusicEnvUtil.initBeforeStart();
        SpringApplication.run(Bootstrap.class, args);
    }
}
