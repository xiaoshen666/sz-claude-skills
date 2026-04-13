package com.supcon.nebule.aiprocts.api;

import com.supcon.nebule.aiprocts.dto.AiproctsLogicflowCommonParamDTO;
import com.supcon.supfusion.framework.cloud.annotation.ServiceApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "aiprocts", contextId = "AiproctsLogicflowApiService")
@ServiceApi(path = HttpConstants.URL_SERVICEAPI + HttpConstants.URL_SPLITER
        + "aiprocts" + HttpConstants.URL_SPLITER + "logic-common-rpc")
public interface AiproctsLogicflowApiService {

    @PostMapping("/executor/{logicCode}")
    @ApiOperation("统一入口接口")
    Result<?> executor(@RequestBody @Valid AiproctsLogicflowCommonParamDTO param);
}

