package com.supcon.nebule.aiprocts.custom.controller;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.supcon.nebule.aiprocts.custom.bo.CustomAiproctsQjdstBO;
import com.supcon.nebule.aiprocts.custom.entity.CustomAiproctsQjdstEntity;
import com.supcon.nebule.aiprocts.custom.query.CustomAiproctsQjdstQuery;
import com.supcon.nebule.aiprocts.custom.service.CustomAiproctsQjdstService;
import com.supcon.nebule.aiprocts.custom.vo.CustomAiproctsQjdstVO;
import com.supcon.nebule.excel.bo.ExportTaskBO;
import com.supcon.nebule.excel.vo.ExportTaskVO;
import com.supcon.nebule.fr.bo.MenInfoBO;
import com.supcon.nebule.fr.bo.PrinterDataReq;
import com.supcon.nebule.fr.bo.WorkflowBatchRestartResultBO;
import com.supcon.nebule.fr.valid.ValidList;
import com.supcon.nebule.fr.vo.*;
import com.supcon.nebule.framework.common.annotation.audit.AuditLog;
import com.supcon.nebule.framework.common.annotation.nullupdate.ModifiedSerialization;
import com.supcon.nebule.framework.common.entity.Page;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.framework.common.utils.JsonUtils;
import com.supcon.nebule.framework.common.utils.PojoUtil;
import com.supcon.nebule.workflow.sdk.bo.WorkFlow;
import com.supcon.nebule.workflow.sdk.dto.GetUserTaskByProcessIdResp;
import com.supcon.supfusion.framework.cloud.annotation.InternalApi;
import com.supcon.supfusion.framework.cloud.common.constants.HttpConstants;
import com.supcon.supfusion.framework.cloud.common.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@InternalApi(path = HttpConstants.URL_SPLITER + "aiprocts" + HttpConstants.URL_SPLITER + "qjdst")
public class CustomAiproctsQjdstController {

	@Autowired
	private CustomAiproctsQjdstService customAiproctsQjdstService;

    @PostMapping("/save")
    @ApiOperation("根据VO对象保存请假单记录")
    @ModifiedSerialization
    @AuditLog
	public Result<Long> save( @RequestBody CustomAiproctsQjdstVO vo) {
        CustomAiproctsQjdstBO bo = PojoUtil.copy(vo, CustomAiproctsQjdstBO.class);
        Long id = customAiproctsQjdstService.saveBO(bo);
        return Result.success(id);
	}

    @PostMapping("/save-batch")
    @ApiOperation("根据VO列表批量保存请假单记录")
    @AuditLog
	public Result<Boolean> saveBatch( @RequestBody ValidList<CustomAiproctsQjdstVO> list) {
        List<CustomAiproctsQjdstBO> boList = PojoUtil.copyList(list, CustomAiproctsQjdstBO.class);
        Boolean rv  = customAiproctsQjdstService.saveBatchBO(boList);
        return Result.success(rv);
	}

    @PostMapping("/add")
    @ApiOperation("根据VO对象插入一条请假单记录")
    @AuditLog
	public Result<Long> add( @RequestBody CustomAiproctsQjdstVO vo) {
        CustomAiproctsQjdstBO bo = PojoUtil.copy(vo, CustomAiproctsQjdstBO.class);
        Long id = customAiproctsQjdstService.addBO(bo);
        return Result.success(id);
	}

    @PostMapping("/add-batch")
    @ApiOperation("根据VO列表批量插入请假单记录")
    @AuditLog
	public Result<Boolean> addBatch( @RequestBody ValidList<CustomAiproctsQjdstVO> list) {
        List<CustomAiproctsQjdstBO> boList = PojoUtil.copyList(list, CustomAiproctsQjdstBO.class);
        Boolean rv  = customAiproctsQjdstService.addBatchBO(boList);
        return Result.success(rv);
	}

    @PostMapping("/update")
    @ApiOperation("根据VO对象更新请假单记录")
    @ModifiedSerialization
    @AuditLog
	public Result<Boolean> update( @RequestBody CustomAiproctsQjdstVO entity) {
	    CustomAiproctsQjdstBO bo = PojoUtil.copy(entity, CustomAiproctsQjdstBO.class);
        Boolean rv = customAiproctsQjdstService.updateBO(bo);
        return Result.success(rv);
	}
    @PostMapping("/list")
    @ApiOperation("根据对象属性查询，返回请假单VO列表数据，不分页")
    public Result<List<CustomAiproctsQjdstVO>> list(@RequestBody CustomAiproctsQjdstVO vo) {
        CustomAiproctsQjdstBO bo = PojoUtil.copy(vo, CustomAiproctsQjdstBO.class);
        bo.setValid(1);
        List<CustomAiproctsQjdstBO> list = customAiproctsQjdstService.listBO(bo);
        return Result.success(PojoUtil.copyList(list, CustomAiproctsQjdstVO.class));
    }

    @PostMapping("/list-page")
    @ApiOperation("根据对象属性查询，返回请假单VO列表数据，分页查询使用")
    public Result<Page<CustomAiproctsQjdstVO>> listPage(@RequestBody PageQuery<CustomAiproctsQjdstVO> pageQuery) {
        PageQuery<CustomAiproctsQjdstBO> query = PojoUtil.copy(pageQuery, PageQuery.class);
        CustomAiproctsQjdstBO bo = PojoUtil.copy(pageQuery.getEntity(), CustomAiproctsQjdstBO.class);
        query.setEntity(bo);
        Page<CustomAiproctsQjdstBO> page = customAiproctsQjdstService.listPageBO(query);
        return Result.success(PojoUtil.copyPage(page, CustomAiproctsQjdstVO.class));
    }

    @PostMapping("/page")
    @ApiOperation("根据查询条件查询，返回请假单VO列表数据，分页查询使用")
    public Result<Page<CustomAiproctsQjdstVO>> pageQuery(@RequestBody PageQuery<CustomAiproctsQjdstQuery> pageQuery) {
        Page<CustomAiproctsQjdstBO> page = customAiproctsQjdstService.pageQueryBO(pageQuery);
        return Result.success(PojoUtil.copyPage(page, CustomAiproctsQjdstVO.class));
    }

    @PostMapping("/export")
    @ApiOperation("根据查询对象导出请假单列表数据")
    public Result<Long> export(@RequestBody ExportTaskVO<CustomAiproctsQjdstQuery> task) {
        ExportTaskBO exportTaskBO = PojoUtil.copy(task,ExportTaskBO.class);
        Long taskId =  customAiproctsQjdstService.export(exportTaskBO, task.getPageQuery());
        return Result.success(taskId);
    }
    @GetMapping("/info")
    @ApiOperation("根据ID获取请假单VO对象")
	public Result<CustomAiproctsQjdstVO> getById(@RequestParam("id") Long id) {
        CustomAiproctsQjdstBO bo = customAiproctsQjdstService.getByIdBO(id);
        return Result.success(PojoUtil.copy(bo, CustomAiproctsQjdstVO.class));
	}


    @GetMapping("/delete")
    @ApiOperation("根据ID逻辑删除请假单记录")
    @AuditLog
	public Result<Boolean> deleteById(@RequestParam("id") Long id) {
        Boolean rv  = customAiproctsQjdstService.delete(id);
        return Result.success(rv);
	}

    @PostMapping("/delete-batch")
    @ApiOperation("根据ID列表批量逻辑删除请假单记录")
    @AuditLog
    public Result<Boolean> deleteBatch(@RequestBody List<Long> idList) {
        Boolean rv = customAiproctsQjdstService.deleteBatch(idList);
        return Result.success(rv);
    }

    @GetMapping("/mne")
    @ApiOperation("根据助记码查询，返回请假单助记码选项列表")
	public Result<MenInfoBO<CustomAiproctsQjdstEntity>> mneList(@RequestParam("propotyCode") String propotyCode, @RequestParam("modelCode") String modelCode, @RequestParam("code") String code)  {
        MenInfoBO<CustomAiproctsQjdstEntity> menInfoBO = customAiproctsQjdstService.mneList(propotyCode,modelCode,code);
        return Result.success(menInfoBO);
	}
    @PostMapping("/printer")
    @ApiOperation("单表打印接口")
	public Result<String> printer(@RequestBody PrinterDataReq req) {
        String key = customAiproctsQjdstService.printer(req);
        return Result.success(key);
	}

    @PostMapping("/submit")
    @ModifiedSerialization
    @ApiOperation("提交请假单工作流")
    @AuditLog
	public Result<Long> submit( @RequestBody WorkFlow<CustomAiproctsQjdstVO> workFlowBO) {
	    WorkFlow<CustomAiproctsQjdstBO> wBO = PojoUtil.copy(workFlowBO, WorkFlow.class);
        CustomAiproctsQjdstBO bo = PojoUtil.copy(workFlowBO.getEntity(), CustomAiproctsQjdstBO.class);
        wBO.setEntity(bo);
        Long id = customAiproctsQjdstService.submit(wBO);
        return Result.success(id);
	}
	@PostMapping("/revocation-pending")
    @ApiOperation("撤回请假单工作流待办")
    public Result<Boolean> revocationPending(@RequestBody WorkFlow<CustomAiproctsQjdstVO> workFlowBO) {
        WorkFlow<CustomAiproctsQjdstBO> workFlowbo = PojoUtil.copy(workFlowBO, WorkFlow.class);
        CustomAiproctsQjdstBO bo = PojoUtil.copy(workFlowBO.getEntity(), CustomAiproctsQjdstBO.class);
        workFlowbo.setEntity(bo);
        Boolean result = customAiproctsQjdstService.revocationPending(workFlowbo);
        return Result.success(result);
    }
	@PostMapping("/cancel-process")
    @ApiOperation("作废请假单工作流")
    public Result<Boolean> cancelProcess(@RequestBody WorkFlow<CustomAiproctsQjdstVO> workFlowBO) {
        WorkFlow<CustomAiproctsQjdstBO> wBO = PojoUtil.copy(workFlowBO, WorkFlow.class);
        CustomAiproctsQjdstBO bo = PojoUtil.copy(workFlowBO.getEntity(), CustomAiproctsQjdstBO.class);
        wBO.setEntity(bo);
        Boolean result = customAiproctsQjdstService.cancelProcess(wBO);
        return Result.success(result);
    }
    @GetMapping("/get-by-pending-id")
    @ApiOperation("根据待办ID获取请假单数据")
	public Result<CustomAiproctsQjdstVO> getByPendingId(@RequestParam("pendingId") Long pendingId) {
        CustomAiproctsQjdstBO bo = customAiproctsQjdstService.getByPendingIdBO(pendingId);
        return Result.success(PojoUtil.copy(bo, CustomAiproctsQjdstVO.class));
	}


    @GetMapping("/get-task-name-list")
    @ApiOperation("获取单据状态列表")
	public Result<List<InternationalVO>> getTaskNameList() {
        List<InternationalVO> taskNameList = customAiproctsQjdstService.getTaskNameList();
        return Result.success(taskNameList);
	}


    @GetMapping("/get-pending-task")
    @ApiOperation("根据EntityID获取待办详情列表")
    public Result<GetUserTaskByProcessIdResp> getPendingTask(@RequestParam("entityId") Long entityId, @RequestParam("appId") String appId){
        GetUserTaskByProcessIdResp result = customAiproctsQjdstService.getPendingTask(entityId,appId);
        return Result.success(result);
    }


    @PostMapping("/save-process")
    @ApiOperation("工作流流程保存供业务编排使用")
    @AuditLog
    public Result<String> saveProcess(@RequestBody SaveProcessVO vo){
        String result =  customAiproctsQjdstService.saveProcess(vo.getFormData(),vo.getProcessKey(),vo.getComment(),vo.getAppId(),vo.getUserName());
        return Result.success(result);
    }


    @PostMapping("/generate-formdata")
    @ApiOperation("组装数据")
    public Result<String> generateFormData(@RequestBody GenerateFormDataVO vo){
        String result = customAiproctsQjdstService.generateFormData(vo.getEntityId(),vo.getProcessKey(),vo.getFormData());
        return Result.success(result);
    }

    @PostMapping("/submit-pending-batch")
    @ApiOperation("请假单工作流待办批量提交")
    @AuditLog
	public Result<WorkflowBatchSubmitResultVO> submitPendingBatch(@RequestBody WorkFlow<CustomAiproctsQjdstVO> workFlowBO) {
	    WorkFlow<CustomAiproctsQjdstBO> wBO = PojoUtil.copy(workFlowBO, WorkFlow.class);
        WorkflowBatchSubmitResultVO resultVO = PojoUtil.copy(customAiproctsQjdstService.submitPendingBatch(wBO), WorkflowBatchSubmitResultVO.class);
        return Result.success(resultVO);
	}
    @PostMapping("/restart-process")
    @ApiOperation("弃审请假单工作流")
    @AuditLog
	public Result<WorkflowBatchRestartResultVO> restartProcess(@RequestBody WorkFlow<CustomAiproctsQjdstVO> workFlowVO) {
        WorkFlow<CustomAiproctsQjdstBO> workFlowBO = JsonUtils.jsonToObj(JsonUtils.objToJson(workFlowVO), new TypeReference<WorkFlow<CustomAiproctsQjdstBO>>() {});
        WorkflowBatchRestartResultBO resultBO = customAiproctsQjdstService.restartProcess(workFlowBO);
        WorkflowBatchRestartResultVO resultVO = JSONUtil.toBean(JSONUtil.toJsonStr(resultBO), WorkflowBatchRestartResultVO.class);
        return Result.success(resultVO);
	}



}