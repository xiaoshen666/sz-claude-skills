package com.supcon.nebule.aiprocts.custom;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supcon.nebule.apisvr.api.NebuleMenuInfoApiService;
import com.supcon.nebule.apisvr.api.NebulePermissionApiService;
import com.supcon.nebule.apisvr.api.NebuleSystemCodeApiService;
import com.supcon.nebule.apisvr.api.dto.NebuleSystemCodeInfoDTO;
import com.supcon.nebule.apisvr.api.dto.NebuleSystemEntityInfoDTO;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 初始化注册 nebule为模块 国际化等地方使用
 */
@Component
@Slf4j
@Order(value = 1)
public class ModuleRegisterInitialize implements CommandLineRunner {
    @Autowired
    private ModuleRegistryApi moduleRegistryApi;
    @Autowired
    private NebuleMenuInfoApiService menuInfoApiService;
    @Autowired
    private NebulePermissionApiService permissionApiService;
    @Autowired
    private NebuleSystemCodeApiService nebuleSystemCodeApiService;
    @Autowired
    private SuposFlowCfgApi suposFlowCfgApi;
    @Override
    public void run(String... args) throws Exception {
        try {
            RpcContext.getContext().setTenantId("dt");
            AddModuleDTO moduleDTO = new AddModuleDTO();
            moduleDTO.setModuleId("aiprocts");
            moduleDTO.setModuleCode("aiprocts");
            moduleDTO.setNameOfI18nCode("aiprocts");
            //log.info("调用ModuleRegistry注册模块: {}",moduleDTO);
            moduleRegistryApi.addModule(moduleDTO);
            log.info("ModuleRegistry注册成功");
            JSONHelper jsonHelperApollo = new JSONHelper();
            String jsonApollo = jsonHelperApollo.resolveJsonFileToString("initmenuaiprocts.json");
            menuInfoApiService.initModuleMenu(jsonApollo);
            log.info("菜单注册成功");
            initGanttPlantSystemCode();
            initCustomViewPageToWorkflow();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initCustomViewPageToWorkflow() throws IOException {
        //注册模块工作流服务
        RegSvcReq reqq = new RegSvcReq();
        reqq.setServiceCode("nb_flow_aiprocts");
        reqq.setServiceName("AI演示模块"+ "_工作流服务");
        reqq.setServiceDesc(reqq.getServiceName() + "(nb_flow_aiprocts)");
        reqq.setServiceSource(WorkflowConst.SERVICE_SOURCE);
        suposFlowCfgApi.regSvc(reqq);
        log.info("模块注册到工作流服务成功");
        //注册模块页面到工作流
        JSONHelper jsonHelper = new JSONHelper();
        String viewJson = jsonHelper.resolveJsonFileToString("view.json");
        JSONObject jsonObject = JSON.parseObject(viewJson);
        JSONArray viewArray = jsonObject.getJSONArray("view");
        List<View> viewList = new ArrayList<>();
        if (viewArray != null) {
            for (int i = 0; i < viewArray.size(); i++) {
                JSONObject viewObj = viewArray.getJSONObject(i);
                View view = new View();
                view.setCode(viewObj.getString("code"));
                view.setViewCode(viewObj.getString("viewCode"));
                view.setViewName(viewObj.getString("viewName"));
                view.setUrl(viewObj.getString("viewUrl"));
                viewList.add(view);
            }
        }
        List<RegPageReq> reqList = new ArrayList<>();
        viewList.forEach(v -> {
            RegPageReq req = new RegPageReq();
            req.setServiceCode("nb_flow_aiprocts");
            req.setServiceSource(WorkflowConst.SERVICE_SOURCE);
            req.setPageCode(v.getCode());
            req.setPageName(v.getViewName());
            req.setAccessUrl(v.getUrl());
            req.setEmbedded(0);
            reqList.add(req);
        });
        suposFlowCfgApi.regPage(reqList);
        log.info("页面注册到工作流服务成功");
        try {
            viewList.forEach(v -> {
                log.info("注册页面: code={}, name={}", v.getCode(), v.getViewName());
            });
        } catch (Exception e) {
            log.warn("打印注册页面日志失败: {}", e.getMessage());
        }
    }

    /**
     * 甘特图状态编码初始化
     */
    private void initGanttPlantSystemCode(){

//        NebuleSystemEntityInfoDTO nebuleSystemEntityInfo = new NebuleSystemEntityInfoDTO();
//        nebuleSystemEntityInfo.setCode("nebuleConfig_ganttPlanState");
//        nebuleSystemEntityInfo.setModuleId("nebuleConfig");
//        nebuleSystemEntityInfo.setName("nbConfig.cdsApp.sysEntity.ganttPlant");
//        nebuleSystemEntityInfo.setDisplayName("甘特图计划状态");
//        nebuleSystemEntityInfo.setCid(1000L);
//        nebuleSystemEntityInfo.setType("list");
//
//        List<NebuleSystemCodeInfoDTO> elements = new ArrayList<>();
//        elements.add(createSystemCode("plan","nbConfig.cdsApp.sysEntity.ganttPlant.plan","计划",1d));
//        elements.add(createSystemCode("processing","nbConfig.cdsApp.sysEntity.ganttPlant.processing","进行中",2d));
//        elements.add(createSystemCode("delayed","nbConfig.cdsApp.sysEntity.ganttPlant.delayed","延期",3d));
//        elements.add(createSystemCode("done","nbConfig.cdsApp.sysEntity.ganttPlant.done","完成",4d));
//        nebuleSystemEntityInfo.setSystemCodeInfoDTOList(elements);
//        nebuleSystemCodeApiService.batchAddSystemCode(Collections.singletonList(nebuleSystemEntityInfo));
        log.info("initGanttPlantSystemCode -- SUCCESS!!!");
    }

    private NebuleSystemCodeInfoDTO createSystemCode(String code, String name,String displayName,double sort){
        NebuleSystemCodeInfoDTO systemCode = new NebuleSystemCodeInfoDTO();
        systemCode.setCode(code);
        systemCode.setName(name);
        systemCode.setDisplayName(displayName);
        systemCode.setSort(sort);
        systemCode.setCid(1000L);
        return systemCode;
    }
}
