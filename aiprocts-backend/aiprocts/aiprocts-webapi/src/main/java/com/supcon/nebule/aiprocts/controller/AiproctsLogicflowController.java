package com.supcon.nebule.aiprocts.controller;

import com.supcon.nebule.fr.service.LogicflowService;
import com.supcon.nebule.fr.vo.AppLogicCommonParam;
import com.supcon.nebule.framework.i18n.manager.InternationalService;
import com.supcon.nebule.logicflow.engine.core.LogicflowResponse;
import com.supcon.nebule.logicflow.engine.enums.CallMethodEnum;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.exception.BizErrorEnum;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@InternalApi(path = HttpConstants.URL_SPLITER + "aiprocts" + HttpConstants.URL_SPLITER + "logic-common")
@Slf4j
@Api(value = "逻辑编排统一入口控制器", tags = {"逻辑编排统一入口控制器"})
public class AiproctsLogicflowController {

    @Autowired
    private LogicflowService logicflowService;
    @Autowired
    private InternationalService internationalService;

    @PostMapping("/executor/{logicCode}")
    @ApiOperation("统一入口接口")
    public Result<?> executor(@RequestBody @Valid AppLogicCommonParam logic) {
        LogicflowResponse response = logicflowService.executorLogic(logic, CallMethodEnum.WEB_API);
        Result<Object> success = Result.success(response.getOutput());
        String message = internationalService.getI18nValue(BizErrorEnum.SYSTEM_OK.getInfo());
        success.setMessage(message);
        return success;
    }
}

