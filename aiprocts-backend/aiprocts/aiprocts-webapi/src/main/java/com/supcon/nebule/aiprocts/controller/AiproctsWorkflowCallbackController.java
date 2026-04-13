package com.supcon.nebule.aiprocts.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.supcon.nebule.fr.bo.GroupMemberQueryParamBO;
import com.supcon.nebule.fr.enums.FRErrorEnum;
import com.supcon.nebule.fr.exception.FRException;
import com.supcon.nebule.framework.common.utils.JsonUtils;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import com.supcon.nebule.fr.enums.AppErrorEnum;
import com.supcon.nebule.fr.enums.BasicDataTypeEnum;
import com.supcon.nebule.fr.exception.AppException;
import com.supcon.nebule.fr.vo.AppLogicCommonParam;
import com.supcon.nebule.fr.pubservice.WorkFlowRunTimeService;
import com.supcon.nebule.workflow.sdk.dto.TaskChangeCbDto;
import com.supcon.supfusion.framework.cloud.annotation.OpenApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.ListResult;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@OpenApi(path = "/public/" + "aiprocts" + HttpConstants.URL_SPLITER + "workflow-callback")
@Slf4j
@Api(value = "工作流回调入口", tags = {"工作流回调入口"})
public class AiproctsWorkflowCallbackController {

    @Autowired
    private WorkFlowRunTimeService workFlowRunTimeService;

    @PostMapping("/task-change")
    @ApiOperation("流程任务变化")
    public Result<Boolean> taskChange(@RequestBody String body) {
        log.info("通过http回调传回流程节点变化内容:\n" + body);
        workFlowRunTimeService.taskChangeCallback(body,"aipro");
        return Result.success(true);
    }

    @GetMapping("/group-member-list")
    @ApiOperation("组成员列表")
    @SuppressWarnings("unchecked")
    public ListResult<String> groupMemberList(@RequestParam(required = false,name = "GID") Long gid,
        @RequestParam(required = false,name = "processId")String processId,
        @RequestParam(required = false,name = "processKey")String processKey) {
        log.debug("\n组成员列表回调,gid:{},processId:{},processKey:{}",gid,processId,processKey);
        GroupMemberQueryParamBO queryParamBO = new GroupMemberQueryParamBO();
        queryParamBO.setModuleCode("aiprocts");
        queryParamBO.setGid(gid);
        queryParamBO.setProcessId(processId);
        queryParamBO.setProcessKey(processKey);
        List<String> memberList = workFlowRunTimeService.queryGroupMember(queryParamBO);
        log.debug("\n返回给OS工作流组成员,gid:{},人员编码:{}",gid,memberList);
        return ListResult.success(memberList);
    }

}

