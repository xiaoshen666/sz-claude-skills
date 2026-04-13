package com.supcon.nebule.aiprocts.service.rpc;

import cn.hutool.core.collection.CollUtil;
import com.supcon.nebule.aiprocts.dto.AiproctsLogicflowCommonParamDTO;
import com.supcon.nebule.aiprocts.api.AiproctsLogicflowApiService;
import com.supcon.nebule.fr.enums.AppErrorEnum;
import com.supcon.nebule.fr.exception.AppException;
import com.supcon.nebule.fr.vo.AppLogicCommonParam;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import com.supcon.nebule.logicflow.engine.core.LogicflowResponse;
import com.supcon.nebule.logicflow.engine.enums.CallMethodEnum;
import com.supcon.nebule.fr.service.LogicflowService;
import com.supcon.supfusion.framework.cloud.annotation.ServiceApiService;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@ServiceApiService
public class AiproctsLogicflowApiServiceImpl implements AiproctsLogicflowApiService{

    @Autowired
    private LogicflowService logicflowService;

    @Override
    public Result<?> executor(AiproctsLogicflowCommonParamDTO param) {
        if (Objects.isNull(param)) {
            throw new AppException(AppErrorEnum.APP_COMMON_ClASS_CONVERT_ERROR);
        }
        AppLogicCommonParam logic = new AppLogicCommonParam();

        List<AiproctsLogicflowCommonParamDTO.InputParamDTO> inputParamList = param.getInputParamList();
        if (CollUtil.isNotEmpty(inputParamList)) {
            List<AppLogicCommonParam.InputParam> paramList = PojoUtil.copyList(inputParamList, AppLogicCommonParam.InputParam.class);
            logic.setInputParamList(paramList);
        }

        logic.setLogicCode(param.getLogicCode());

        LogicflowResponse response = logicflowService.executorLogic(logic, CallMethodEnum.RPC);
        return Result.success(response.getOutput());
    }
}

