package com.supcon.nebule.aiprocts.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("逻辑编排统一入口请求参数DTO")
public class AiproctsLogicflowCommonParamDTO {

    @ApiModelProperty("逻辑编排Code")
    @NotBlank(message = "逻辑编排Code不能为空")
    private String logicCode;

    @ApiModelProperty("逻辑编排统一入口方法入参")
    private List<InputParamDTO> inputParamList;

    @Data
    @ApiModel("逻辑编排统一入口方法入参")
    public static class InputParamDTO {

        @ApiModelProperty("方法参数名称")
        private String paramName;

        @ApiModelProperty("方法参数类名")
        private String paramClassName;

        @ApiModelProperty("值")
        private String value;

    }
}

