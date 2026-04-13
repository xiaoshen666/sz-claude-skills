package com.supcon.nebule.aiprocts.custom.service.impl;

import com.supcon.nebule.fr.bo.LicenseInfoAdapterBO;
import com.supcon.nebule.fr.service.LicenseApiAdapterService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
* nacos注册模块授权信息
**/
@Service
public class AiproctsRegisterNacosLicense implements InitializingBean{

    private String artifact = "aiprocts";

    //自定义代码区，对licenseKey，applicationName，applicationType，applicationNameJson进行赋值
    /* CUSTOM CODE START*/

    /**
    * 软件狗key
    */
    private String licenseKey = "";

    /**
    * 应用模块名称（授权中文）
    */
    private String applicationName = "";

    /**
    * 应用模块型号（授权英文）
    */
    private String applicationType = "";

    /**
    * 应用模块名称(国际化),样例:{'zh_CN':'supPlant应用模块开发软件','en_US':'supPlant application develop software','zh_HK':'supPlant應用模塊開發軟件'}
    */
    String applicationNameJson = "";

    /* CUSTOM CODE END */

    @Autowired
    private LicenseApiAdapterService licenseApiAdapterService;

    @Override
    public void afterPropertiesSet() {
        // 向nacos注册模块信息  软件狗key不允许为空
        if (!ObjectUtils.isEmpty(licenseKey)) {
            LicenseInfoAdapterBO licenseInfoBO = new LicenseInfoAdapterBO();
            licenseInfoBO.setModuleCode(artifact);
            licenseInfoBO.setLicenseKey(licenseKey);
            licenseInfoBO.setApplicationName(applicationName);
            licenseInfoBO.setApplicationType(applicationType);
            licenseInfoBO.setApplicationNameJSON(applicationNameJson);
            licenseApiAdapterService.registerLicenseInfo(licenseInfoBO);
        }
    }

    public String getArtifact() {
    return artifact;
    }

    public String getLicenseKey() {
    return licenseKey;
    }

    public String getApplicationName() {
    return applicationName;
    }

    public String getApplicationType() {
    return applicationType;
    }

    public String getApplicationNameJson() {
    return applicationNameJson;
    }

}