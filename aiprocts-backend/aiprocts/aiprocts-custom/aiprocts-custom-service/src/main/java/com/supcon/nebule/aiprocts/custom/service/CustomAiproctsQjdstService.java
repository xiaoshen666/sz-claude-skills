package com.supcon.nebule.aiprocts.custom.service;

import com.supcon.nebule.aiprocts.custom.bo.CustomAiproctsQjdstBO;
import com.supcon.nebule.aiprocts.custom.entity.CustomAiproctsQjdstEntity;
import com.supcon.nebule.aiprocts.custom.query.CustomAiproctsQjdstQuery;
import com.supcon.nebule.excel.bo.ExportTaskBO;
import com.supcon.nebule.excel.pojo.ExportTask;
import com.supcon.nebule.fr.bo.MenInfoBO;
import com.supcon.nebule.fr.bo.PrinterDataReq;
import com.supcon.nebule.fr.bo.WorkflowBatchRestartResultBO;
import com.supcon.nebule.fr.bo.WorkflowBatchSubmitResultBO;
import com.supcon.nebule.fr.vo.InternationalVO;
import com.supcon.nebule.framework.common.entity.Page;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.framework.common.service.IECWorkflowBaseService;
import com.supcon.nebule.workflow.sdk.bo.WorkFlow;
import com.supcon.nebule.workflow.sdk.dto.GetUserTaskByProcessIdResp;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface CustomAiproctsQjdstService extends IECWorkflowBaseService<CustomAiproctsQjdstEntity> {

    Boolean delete(Long id);

    Boolean deleteBatch(List<Long> list);

	Page<CustomAiproctsQjdstBO> pageQueryBO(PageQuery<CustomAiproctsQjdstQuery> pageQuery);

	Long export(ExportTaskBO exportTaskBO,PageQuery<CustomAiproctsQjdstQuery> pageQuery);
	List<CustomAiproctsQjdstBO> loadExportData(ExportTask exportTask, PageQuery<CustomAiproctsQjdstQuery> pageQuery, MutableBoolean useAlternativeData, List<CustomAiproctsQjdstBO> alternativeData);


    Long saveBO(@Valid CustomAiproctsQjdstBO bo);


    Long addBO(@Valid CustomAiproctsQjdstBO bo);

    Boolean updateBO(@Valid CustomAiproctsQjdstBO bo);

    Boolean saveBatchBO(@Valid List<CustomAiproctsQjdstBO> boList);

    Boolean addBatchBO(@Valid List<CustomAiproctsQjdstBO> boList);

    Boolean updateBatchBO(List<CustomAiproctsQjdstBO> boList);

    List<CustomAiproctsQjdstBO> listBO(CustomAiproctsQjdstBO entity);
    Page<CustomAiproctsQjdstBO> listPageBO(PageQuery<CustomAiproctsQjdstBO> pageQuery);

    CustomAiproctsQjdstBO getByIdBO(Long id);

    CustomAiproctsQjdstBO getByIdBOFilterCompany(Long id);

    List<CustomAiproctsQjdstEntity> getEntityByIds(Collection<Long> ids, String permission);

    List<CustomAiproctsQjdstEntity> getEntityByIdsFilterCompany(Collection<Long> ids);

    List<CustomAiproctsQjdstEntity> getEntityByIds(Collection<Long> ids, String permission, boolean includeInvalid);

    List<CustomAiproctsQjdstEntity> getEntityByIdsFilterCompany(Collection<Long> ids, boolean includeInvalid);
   	MenInfoBO<CustomAiproctsQjdstEntity> mneList(String propotyCode, String modelCode, String code);
    String printer(PrinterDataReq req);
    Long deleteBatchByParent(List<Long> parentIdList,String parentTbName);

    Long submit(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO);
    /**
     *工作流待办撤回
     * @param workFlowBO
     * @return Long
     */
     Boolean revocationPending(WorkFlow<CustomAiproctsQjdstBO> workFlowBO);

    /**
     *工作流流程作废
     * @param workFlowBO
     * @return Boolean
     */
     Boolean cancelProcess(WorkFlow<CustomAiproctsQjdstBO> workFlowBO);

    CustomAiproctsQjdstBO getByPendingIdBO(Long pendingId);

    /**
     * 工作流流程发起(用于后台发起流程)
     * @param entity
     * @param processKey
     * @param comment
     * @return
     */
    public Boolean startProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String targetNode, String comment);

    /**
     * 工作流流程发起(用于后台发起流程,可以通过标签指定节点发起)
     * @param entity
     * @param processKey
     * @param customTag
     * @param comment
     * @return
     */
    public Boolean startProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String targetNode, String customTag, String comment);

    /**
     * 工作流流程保存(用于后台保存流程)
     * @param entity
     * @param processKey
     * @param comment
     * @return
     */
    public Boolean saveProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String comment);

    /**
     * 工作流流程保存(用于后台保存流程,可以通过标签指定节点发起)
     * @param entity
     * @param processKey
     * @param customTag
     * @param comment
     * @return
     */
    public Boolean saveProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String customTag, String comment);

    /**
     *工作流流程作废(用于后台流程作废)
     * @param processId
     * @return Boolean
     */
    Boolean cancelProcessBackend(String processId);


    List<InternationalVO> getTaskNameList();


    GetUserTaskByProcessIdResp getPendingTask(Long entityId, String appId);


    String saveProcess(String formData, String processKey, String comment,String appId,String userName);


    String generateFormData(Long entityId, String processKey, String formData);

    Boolean dealWorkflowVoInner(WorkFlow<CustomAiproctsQjdstBO> workFlowVO);

    Boolean dealWorkflowEntityInner(WorkFlow<CustomAiproctsQjdstEntity> workFlowEntity);

    WorkflowBatchSubmitResultBO submitPendingBatch(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO);
    WorkflowBatchRestartResultBO restartProcess(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO);
    
}
