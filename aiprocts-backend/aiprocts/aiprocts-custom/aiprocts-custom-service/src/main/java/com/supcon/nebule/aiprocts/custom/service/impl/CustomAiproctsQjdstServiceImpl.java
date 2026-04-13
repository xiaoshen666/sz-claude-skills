package com.supcon.nebule.aiprocts.custom.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.supcon.nebule.aiprocts.custom.bo.CustomAiproctsQjdstBO;
import com.supcon.nebule.aiprocts.custom.dao.CustomAiproctsQjdstDao;
import com.supcon.nebule.aiprocts.custom.entity.CustomAiproctsQjdstEntity;
import com.supcon.nebule.aiprocts.custom.query.CustomAiproctsQjdstQuery;
import com.supcon.nebule.aiprocts.custom.service.CustomAiproctsQjdstService;
import com.supcon.nebule.excel.bo.ExportTaskBO;
import com.supcon.nebule.excel.pojo.ExportTask;
import com.supcon.nebule.excel.service.ExcelExportService;
import com.supcon.nebule.fr.bo.*;
import com.supcon.nebule.fr.constant.RuntimeConst;
import com.supcon.nebule.fr.counter.CounterManager;
import com.supcon.nebule.fr.counter.support.SupCodeGeneratorProperty;
import com.supcon.nebule.fr.enums.FRErrorEnum;
import com.supcon.nebule.fr.enums.ProcessTypeEnum;
import com.supcon.nebule.fr.exception.FRException;
import com.supcon.nebule.fr.pubservice.IVerifyService;
import com.supcon.nebule.fr.pubservice.MenCodeGenerator;
import com.supcon.nebule.fr.pubservice.PropertyFillService;
import com.supcon.nebule.fr.pubservice.WorkFlowRunTimeService;
import com.supcon.nebule.fr.service.*;
import com.supcon.nebule.fr.utils.*;
import com.supcon.nebule.fr.vo.InternationalVO;
import com.supcon.nebule.framework.common.utils.*;
import com.supcon.nebule.framework.common.constant.TreeOperateConstants;
import com.supcon.nebule.framework.common.entity.AbstractConfigurationWorkflowEntity;
import com.supcon.nebule.framework.common.entity.Page;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.framework.common.enums.GroupEnum;
import com.supcon.nebule.framework.common.service.impl.ECWorkflowBaseService;
import com.supcon.nebule.framework.i18n.manager.InternationalService;
import com.supcon.nebule.workflow.sdk.api.SuposFlowRuntimeApi;
import com.supcon.nebule.workflow.sdk.bo.WorkFlow;
import com.supcon.nebule.workflow.sdk.bo.WorkFlowParam;
import com.supcon.nebule.workflow.sdk.dto.GetPendingListResp;
import com.supcon.nebule.workflow.sdk.dto.GetUserTaskByProcessIdResp;
import com.supcon.nebule.workflow.sdk.dto.SaveProcessReq;
import com.supcon.nebule.workflow.sdk.dto.StartProcessReq;
import com.supcon.nebule.workflow.sdk.dto.support.CustomTagFilter;
import com.supcon.nebule.workflow.sdk.dto.support.PendingTaskInfo;
import com.supcon.nebule.workflow.sdk.dto.support.TargetNodeFilter;
import com.supcon.nebule.workflow.sdk.enums.OperatorTypeEnum;
import com.supcon.nebule.workflow.sdk.util.WorkflowUtil;
import com.supcon.supfusion.framework.cloud.common.context.UserContext;
import com.supcon.supfusion.framework.cloud.common.tools.IDGenerator;
import com.supcon.supfusion.framework.scaffold.dbp.util.DataId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
@Slf4j
public class CustomAiproctsQjdstServiceImpl extends ECWorkflowBaseService<CustomAiproctsQjdstDao, CustomAiproctsQjdstEntity> implements CustomAiproctsQjdstService {

    private static final long MAX_NUMBER = 999;
    private static final String FORM_DATA_ENTITY_CLASS = "ENTITY_CLASS";
    private static final String FORM_DATA_ENTITY_ID = "ENTITY_ID";
    private static final String FORM_DATA_MODULE_CODE = "MODULE_CODE";
    private static final String FORM_DATA_MODEL_CODE = "MODEL_CODE";
    private static final ThreadLocal<SupCodeGeneratorProperty> generatorProperty = new ThreadLocal<>();
    private static final String SERVICE_SOURCE = "com.supcon.icds";
    @Autowired
    private PropertyFillService propertyFillService;
    @Autowired
    private IDataPermissionService dataPermissionService;
    @Autowired
    private IKeywordService keywordService;
    @Autowired
    private CustomPropertyConfigService customPropertyConfigService;
    @Autowired
    private WorkFlowRunTimeService workFlowRunTimeService;
    @Autowired
    private MenCodeGenerator menCodeGenerator;
    @Autowired
    private IVerifyService verifyService;
    @Autowired
    private InternationalService internationalService;
    @Autowired
    private TaskNameListService taskNameListService;
    @Autowired
    private CounterManager counterManager;
    @Autowired
    private SuposFlowRuntimeApi runtimeApi;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private PrinterDataService printerDataService;
    @Autowired
    private RtViewFilterCriteriaService viewFilterCriteriaService;
    @Autowired
    DatabaseTypeService databaseTypeService;
    @Autowired
    private DataId dataId;
    @Autowired
    @Lazy
    private CustomAiproctsQjdstService _selfService;

    @Override
    @Transactional
    public Boolean delete(Long id) {
       return this.deleteBatch(Collections.singletonList(id));
    }

    @Override
    @Transactional
    public Boolean deleteBatch(List<Long> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Boolean.FALSE;
        }
        menCodeGenerator.deleteMnemonicCode(idList, CustomAiproctsQjdstEntity.class);
        verifyService.deleteBizPrimary("aipro", idList);
        return super.deleteBatch(idList);
    }

    @Override
    public Page<CustomAiproctsQjdstBO> pageQueryBO(PageQuery<CustomAiproctsQjdstQuery> pageQuery) {
        CustomAiproctsQjdstQuery query = pageQuery.getEntity();
        if (Objects.nonNull(query)) {
            QueryUtil.formatQuery(query);
        }
        if (pageQuery.getWorkFlowParam() != null) {
            pageQuery.getWorkFlowParam().setPendingUserName(UserContext.getUserContext().getUserName());
            pageQuery.getWorkFlowParam().setModelCode("aiproctsQjdst");
        }
        if (BooleanUtil.isFalse(pageQuery.getPaging())){
            pageQuery.setPageSize(RuntimeConst.PAGE_MAX_SIZE);
        }
        pageQuery.setPermission(dataPermissionService.getPermissionSql(""));
        pageQuery.setKeyword(null);
        pageQuery.setDataCategory(DataCategoryQueryUtils.formatQuery(pageQuery.getDataCategoryParamList()));
        pageQuery.setFilterCriteria(FilterCriteriaQueryUtils.formatQuery(pageQuery.getFilterCriteria()));
        if (Objects.nonNull(pageQuery.getFilterCriteriaConfig()) && CollUtil.isNotEmpty(pageQuery.getFilterCriteriaConfig())) {
            pageQuery.setFilterCriteria(FilterCriteriaQueryUtils.formatQuery(viewFilterCriteriaService.generateSql(GroupEnum.AND, pageQuery.getFilterCriteriaConfig())));
        }
        pageQuery.setDynamicPropertyConfig(customPropertyConfigService.getDynamicPropertyConfig());
        Long count = super.baseDao.countPageQuery(pageQuery);
        List<CustomAiproctsQjdstEntity> dataList = super.baseDao.pageQuery(pageQuery);
        workFlowRunTimeService.transProcessTaskName(dataList);
        List<CustomAiproctsQjdstBO> list = PojoUtil.copyList(dataList, CustomAiproctsQjdstBO.class);
        if (Objects.nonNull(pageQuery.getIsTotalSum()) && Boolean.TRUE.equals(pageQuery.getIsTotalSum())) {
            try {
                Map sumMap = super.baseDao.sumNumberPageQuery(pageQuery);
                return PageHelper.buildPageWithSum(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count, sumMap);
            } catch (Exception e) {
                e.printStackTrace();
                return PageHelper.buildPage(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count);
            }
        } else {
            return PageHelper.buildPage(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count);
        }
    }


    @Override
    public Long export(ExportTaskBO exportTaskBO,PageQuery<CustomAiproctsQjdstQuery> pageQuery) {
         ExportTask exportTask = excelExportService.buildExportTask(exportTaskBO,pageQuery);
         AsyncTaskUtils.submitTask(() -> {
             CustomAiproctsQjdstService self = SpringApplicationContextHelper.getBean(CustomAiproctsQjdstService.class);
             List<CustomAiproctsQjdstBO> sourceData = self.loadExportData(exportTask,pageQuery,new MutableBoolean(false),new ArrayList<>());
             excelExportService.export(exportTask, sourceData);
         });
         return exportTask.getTaskId();
    }
    @Override
    public List<CustomAiproctsQjdstBO> loadExportData(ExportTask exportTask, PageQuery<CustomAiproctsQjdstQuery> pageQuery, MutableBoolean useAlternativeData, List<CustomAiproctsQjdstBO> alternativeData) {
        if(Boolean.TRUE.equals(useAlternativeData.getValue())){
            return alternativeData;
        }
        List<CustomAiproctsQjdstBO> sourceData = new ArrayList<>();
        Integer pagesToQuery = pageQuery.getPagesToQuery();
        for (int i = 0; i < pagesToQuery; i++) {
            Page<CustomAiproctsQjdstBO> queryData = pageQueryBO(pageQuery);
            sourceData.addAll(queryData.getRecords());
            if (queryData.getRecords().size() < pageQuery.getPageSize() || pagesToQuery == 1) {
                break;
            }
            pageQuery.setPageNo(pageQuery.getPageNo() + 1);
        }
        return sourceData;
    }
    @Override
    @Transactional
    public Long saveBO(@Valid CustomAiproctsQjdstBO bo) {
        if (Objects.isNull(bo.getId())){
            return addBO(bo);
        }
        updateBO(bo);
        return bo.getId();
    }

    @Override
    @Transactional
    public Long addBO(@Valid CustomAiproctsQjdstBO bo) {
        CustomAiproctsQjdstEntity entity = PojoUtil.copy(bo, CustomAiproctsQjdstEntity.class);
        propertyFillService.process(entity, ProcessTypeEnum.SAVE);
        //忽略大小写
        IgnoreCaseUtils.ignoreCase(entity);
        entity.setValid(1);
        entity.setVersion(1);
        Long id = super.add(entity);
        propertyFillService.afterAdd(entity, ProcessTypeEnum.AFTER_SAVE);
        super.update(entity);
        //业务主键校验
        verifyService.checkBizPrimary("aipro", entity, Boolean.FALSE);
        menCodeGenerator.addMnemonicCode(entity);
        bo.setId(id);
        bo.setCompanyCode(entity.getCompanyCode());
        bo.setCreatePosCode(entity.getCreatePosCode());
        bo.setCreatePosName(entity.getCreatePosName());
        bo.setCreateStaffCode(entity.getCreateStaffCode());
        bo.setCreateStaffName(entity.getCreateStaffName());
        bo.setCreateTime(entity.getCreateTime());
        bo.setOwnerPosCode(entity.getOwnerPosCode());
        bo.setOwnerPosName(entity.getOwnerPosName());
        bo.setOwnerDeptCode(entity.getOwnerDeptCode());
        bo.setOwnerDeptName(entity.getOwnerDeptName());
        return id;
    }

    @Override
    @Transactional
    public Boolean updateBO(@Valid CustomAiproctsQjdstBO bo){
        if (Objects.isNull(bo.getId())){
            return Boolean.FALSE;
        }
        CustomAiproctsQjdstEntity entity = PojoUtil.copy(bo, CustomAiproctsQjdstEntity.class);
        propertyFillService.process(entity, ProcessTypeEnum.UPDATE);
        menCodeGenerator.updateMnemonicCode(entity);
        //忽略大小写
        IgnoreCaseUtils.ignoreCase(entity);
        //业务主键校验
        verifyService.checkBizPrimary("aipro", entity, Boolean.TRUE);
        Boolean rev = super.update(entity);
        if(Boolean.FALSE.equals(rev)){
             throw new FRException(FRErrorEnum.NOT_FOUND_VERSION);
        }
        bo.setModifyStaffCode(entity.getModifyStaffCode());
        bo.setModifyTime(entity.getModifyTime());
        bo.setModifyStaffName(entity.getModifyStaffName());
        return rev;
    }

    @Override
    @Transactional
    public Boolean saveBatchBO(@Valid List<CustomAiproctsQjdstBO> boList) {
        List<CustomAiproctsQjdstBO> addBatch = new ArrayList<>(boList.size());
        List<CustomAiproctsQjdstBO> updateBatch = new ArrayList<>(boList.size());
        String mainDisplay = super.getMainDisplay(CustomAiproctsQjdstEntity.class);
        if(ClassTypeUtils.isTreeClass(CustomAiproctsQjdstBO.class)){
            treeToList(boList, null, addBatch, updateBatch, mainDisplay);
        }else {
            for (CustomAiproctsQjdstBO bo : boList){
                if (Objects.isNull(bo.getId())){
                    addBatch.add(bo);
                    continue;
                }
                updateBatch.add(bo);
            }
        }
        if (CollUtil.isNotEmpty(addBatch)){
            addBatchBO(addBatch);
        }
        if (CollUtil.isNotEmpty(updateBatch)){
            updateBatchBO(updateBatch);
        }
        return Boolean.TRUE;
    }

    private void treeToList(List<CustomAiproctsQjdstBO> entityList, CustomAiproctsQjdstBO parentNode, List<CustomAiproctsQjdstBO> addList, List<CustomAiproctsQjdstBO> updateList, String mainDisplay) {
        if(CollectionUtils.isEmpty(entityList) || !ClassTypeUtils.isTreeClass(CustomAiproctsQjdstBO.class)){
            return;
        }
        Integer sortNum = 1;
        for (CustomAiproctsQjdstBO t : entityList) {
            if(Objects.isNull(t)){
                continue;
            }
            boolean isAdd = false;
            if(Objects.isNull(t.getId())){
                isAdd = true;
                t.setId(IDGenerator.newInstance().generate().longValue());
            }
            try {
                Field sort = CustomAiproctsQjdstBO.class.getDeclaredField("sort");
                sort.setAccessible(true);
                sort.set(t, sortNum);
                Field layNo = CustomAiproctsQjdstBO.class.getDeclaredField("layNo");
                layNo.setAccessible(true);
                layNo.set(t, Objects.isNull(parentNode) ? 1 : (Integer) layNo.get(parentNode) + 1);
                Field parentId = CustomAiproctsQjdstBO.class.getDeclaredField("parentId");
                parentId.setAccessible(true);
                parentId.set(t, Objects.isNull(parentNode) ? -1 : parentNode.getId());
                Field childList = CustomAiproctsQjdstBO.class.getDeclaredField("childList");
                childList.setAccessible(true);
                Field leaf = CustomAiproctsQjdstBO.class.getDeclaredField("leaf");
                leaf.setAccessible(true);
                leaf.set(t, CollectionUtils.isEmpty((List)childList.get(t)) ? 1 : 0);
                Field fullPathName = CustomAiproctsQjdstBO.class.getDeclaredField("fullPathName");
                fullPathName.setAccessible(true);
                if(!StringUtils.isEmpty(mainDisplay) && null != CustomAiproctsQjdstBO.class.getDeclaredField(mainDisplay)){
                    Field displayField = CustomAiproctsQjdstBO.class.getDeclaredField(mainDisplay);
                    displayField.setAccessible(true);
                    String tDisplay = null == displayField.get(t) ? "" : displayField.get(t).toString();
                    fullPathName.set(t, Objects.isNull(parentNode) ? tDisplay : fullPathName.get(parentNode) + TreeOperateConstants.PATH_SEPARATOR + tDisplay);
                }else {
                    fullPathName.set(t, Objects.isNull(parentNode) ? t.getId().toString() : fullPathName.get(parentNode) + TreeOperateConstants.PATH_SEPARATOR + t.getId().toString());
                }
                Field layRec = CustomAiproctsQjdstBO.class.getDeclaredField("layRec");
                layRec.setAccessible(true);
                layRec.set(t, Objects.isNull(parentNode) ? t.getId().toString() : layRec.get(parentNode) + "-" + t.getId().toString());
                if(isAdd){
                    addList.add(t);
                }else {
                    updateList.add(t);
                }
                if(!CollectionUtils.isEmpty((List) childList.get(t))){
                    treeToList((List<CustomAiproctsQjdstBO>) childList.get(t), t, addList, updateList, mainDisplay);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            sortNum++;
        }
    }
    @Override
    @Transactional
    public Boolean addBatchBO(@Valid List<CustomAiproctsQjdstBO> boList) {
        List<CustomAiproctsQjdstEntity> entityList = new ArrayList<>();
        if(ClassTypeUtils.isTreeClass(CustomAiproctsQjdstBO.class)){
            treeToList(boList, null, entityList, getMainDisplay(CustomAiproctsQjdstEntity.class));
        }else {
            for(CustomAiproctsQjdstBO bo : boList){
                if(bo.getId() == null){
                    bo.setId(IDGenerator.newInstance().generate().longValue());
                }
                CustomAiproctsQjdstEntity entity = PojoUtil.copy(bo, CustomAiproctsQjdstEntity.class);
                propertyFillService.process(entity, ProcessTypeEnum.SAVE);
                entity.setValid(1);
                entity.setVersion(1);
                entityList.add(entity);
            }
        }
        //忽略大小写
        IgnoreCaseUtils.ignoreCase(entityList);
        Boolean rev = Boolean.FALSE;
        //sql server时,分批插入,防止sql过大出现超2100 个参数的错误
        final int length = entityList.size();
        final int BATCH_INSERT_SIZE = 50;
        if(length > BATCH_INSERT_SIZE) {
            int num = (length + BATCH_INSERT_SIZE - 1) / BATCH_INSERT_SIZE;
            for (int i = 0; i < num; i++) {
                int fromIndex = i * BATCH_INSERT_SIZE;
                int toIndex = Math.min((i + 1) * BATCH_INSERT_SIZE, length);
                super.addBatch(entityList.subList(fromIndex, toIndex));
            }
        }else{
            rev = super.addBatch(entityList);
        }
        //业务主键校验
        propertyFillService.afterAddBatch(entityList,ProcessTypeEnum.AFTER_SAVE);
        entityList.forEach(entity->{
                  super.update(entity);
                });
        verifyService.checkBizPrimary("aipro", entityList, Boolean.FALSE);
        menCodeGenerator.addBatchMnemonicCode(entityList);
        return rev;
    }

    private void treeToList(List<CustomAiproctsQjdstBO> entityList, CustomAiproctsQjdstBO parentNode, List<CustomAiproctsQjdstEntity> addList, String mainDisplay) {
        if(CollectionUtils.isEmpty(entityList) || !ClassTypeUtils.isTreeClass(CustomAiproctsQjdstBO.class)){
            return;
        }
        Integer sortNum = 1;
        for (CustomAiproctsQjdstBO t : entityList) {
            if(Objects.isNull(t)){
                continue;
            }
            t.setValid(1);
            t.setVersion(1);
            if(t.getId() == null){
               t.setId(IDGenerator.newInstance().generate().longValue());
            }else{
               CustomAiproctsQjdstEntity entity = PojoUtil.copy(t, CustomAiproctsQjdstEntity.class);
               propertyFillService.process(entity, ProcessTypeEnum.SAVE);
               addList.add(entity);
               continue;
            }
            try {
                Field sort = CustomAiproctsQjdstBO.class.getDeclaredField("sort");
                sort.setAccessible(true);
                sort.set(t, sortNum);
                Field layNo = CustomAiproctsQjdstBO.class.getDeclaredField("layNo");
                layNo.setAccessible(true);
                layNo.set(t, Objects.isNull(parentNode) ? 1 : (Integer) layNo.get(parentNode) + 1);
                Field parentId = CustomAiproctsQjdstBO.class.getDeclaredField("parentId");
                parentId.setAccessible(true);
                parentId.set(t, Objects.isNull(parentNode) ? -1 : parentNode.getId());
                Field childList = CustomAiproctsQjdstBO.class.getDeclaredField("childList");
                childList.setAccessible(true);
                Field leaf = CustomAiproctsQjdstBO.class.getDeclaredField("leaf");
                leaf.setAccessible(true);
                leaf.set(t, CollectionUtils.isEmpty((List)childList.get(t)) ? 1 : 0);
                Field fullPathName = CustomAiproctsQjdstBO.class.getDeclaredField("fullPathName");
                fullPathName.setAccessible(true);
                if(!StringUtils.isEmpty(mainDisplay) && null != CustomAiproctsQjdstBO.class.getDeclaredField(mainDisplay)){
                    Field displayField = CustomAiproctsQjdstBO.class.getDeclaredField(mainDisplay);
                    displayField.setAccessible(true);
                    String tDisplay = null == displayField.get(t) ? "" : displayField.get(t).toString();
                    fullPathName.set(t, Objects.isNull(parentNode) ? tDisplay : fullPathName.get(parentNode) + TreeOperateConstants.PATH_SEPARATOR + tDisplay);
                }else {
                    fullPathName.set(t, Objects.isNull(parentNode) ? t.getId().toString() : fullPathName.get(parentNode) + TreeOperateConstants.PATH_SEPARATOR + t.getId().toString());
                }
                Field layRec = CustomAiproctsQjdstBO.class.getDeclaredField("layRec");
                layRec.setAccessible(true);
                layRec.set(t, Objects.isNull(parentNode) ? t.getId().toString() : layRec.get(parentNode) + "-" + t.getId().toString());
                CustomAiproctsQjdstEntity entity = PojoUtil.copy(t, CustomAiproctsQjdstEntity.class);
                propertyFillService.process(entity, ProcessTypeEnum.SAVE);
                addList.add(entity);
                if(!CollectionUtils.isEmpty((List) childList.get(t))){
                    treeToList((List<CustomAiproctsQjdstBO>) childList.get(t), t, addList, mainDisplay);
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            sortNum++;
        }
    }

    @Override
    @Transactional
    public Boolean updateBatchBO(List<CustomAiproctsQjdstBO> boList) {
        if(CollUtil.isEmpty(boList)){
          return Boolean.FALSE;
        }
        boList.forEach(item->{
          this.updateBO(item);
        });
        return Boolean.TRUE;
    }

    @Override
    public List<CustomAiproctsQjdstBO> listBO(CustomAiproctsQjdstBO bo) {
        CustomAiproctsQjdstEntity entity = PojoUtil.copy(bo, CustomAiproctsQjdstEntity.class);
        List<CustomAiproctsQjdstEntity> entityList = super.list(entity);
        List<CustomAiproctsQjdstBO> boList = PojoUtil.copyList(entityList, CustomAiproctsQjdstBO.class);
        return boList;
    }
    @Override
    public Page<CustomAiproctsQjdstBO> listPageBO(PageQuery<CustomAiproctsQjdstBO> pageQuery) {
        CustomAiproctsQjdstEntity entity = PojoUtil.copy(pageQuery.getEntity(), CustomAiproctsQjdstEntity.class);
        pageQuery.setFilterCriteria(FilterCriteriaQueryUtils.formatQuery(pageQuery.getFilterCriteria()));
        if (Objects.nonNull(pageQuery.getFilterCriteriaConfig()) && CollUtil.isNotEmpty(pageQuery.getFilterCriteriaConfig())) {
            pageQuery.setFilterCriteria(FilterCriteriaQueryUtils.formatQuery(viewFilterCriteriaService.generateSql(GroupEnum.AND, pageQuery.getFilterCriteriaConfig())));
        }
        PageQuery<CustomAiproctsQjdstEntity> query = PojoUtil.copy(pageQuery, PageQuery.class);
        entity.setValid(1);
        List<CustomAiproctsQjdstEntity> entityList;
        Long count;
        if (BooleanUtil.isTrue(pageQuery.getPaging())) {

            entity.setValid(1);
            query.setEntity(entity);
            count = super.baseDao.countListPage(query);
            entityList = super.baseDao.listPage(query);
        } else {
            entity.setFilterCriteria(pageQuery.getFilterCriteria());
            entity.setValid(1);
            entityList = super.baseDao.listByEntity(entity);
            count = (long) entityList.size();
        }
        List<CustomAiproctsQjdstBO> list = PojoUtil.copyList(entityList, CustomAiproctsQjdstBO.class);
        if (Objects.nonNull(pageQuery.getIsTotalSum()) && Boolean.TRUE.equals(pageQuery.getIsTotalSum())) {
            try {
                if (BooleanUtil.isTrue(pageQuery.getPaging())) {
                    Map sumMap = super.baseDao.sumNumberListPageQuery(query);
                    return PageHelper.buildPageWithSum(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count, sumMap);
                } else {
                    Map sumMap = super.baseDao.sumNumberListQuery(entity);
                    return PageHelper.buildPageWithSum(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count, sumMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return PageHelper.buildPage(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count);
            }
        } else {
            return PageHelper.buildPage(pageQuery.getPageSize(), pageQuery.getPageNo(), list, count);
        }
    }
    @Override
    public CustomAiproctsQjdstBO getByIdBO(Long id) {
        CustomAiproctsQjdstEntity entity = this.get(id);
        if(ObjectUtil.isNull(entity)){
            return null;
        }
        String permissionSql = dataPermissionService.getPermissionSql("");
        CustomAiproctsQjdstEntity entityPermission = super.baseDao.getByPermission(id, permissionSql);
        if(ObjectUtil.isNull(entityPermission)){
            throw new FRException(FRErrorEnum.NO_DATA_PERMISSION);
        }
        propertyFillService.process(entityPermission, ProcessTypeEnum.QUERY);
        if (AbstractConfigurationWorkflowEntity.class.isAssignableFrom(entityPermission.getClass())) {
            workFlowRunTimeService.transProcessTaskName(entityPermission);
        }
        CustomAiproctsQjdstBO bo = PojoUtil.copy(entityPermission, CustomAiproctsQjdstBO.class);
        return bo;
    }

    @Override
    public CustomAiproctsQjdstBO getByIdBOFilterCompany(Long id) {
        CustomAiproctsQjdstEntity entity = this.get(id);
        if(ObjectUtil.isNull(entity)){
            return null;
        }
        String permissionSql = "";
        CustomAiproctsQjdstEntity entityPermission = super.baseDao.getByPermission(id, permissionSql);
        if(ObjectUtil.isNull(entityPermission)){
            throw new FRException(FRErrorEnum.NO_DATA_PERMISSION);
        }
        propertyFillService.process(entityPermission, ProcessTypeEnum.QUERY);
        if (AbstractConfigurationWorkflowEntity.class.isAssignableFrom(entityPermission.getClass())) {
            workFlowRunTimeService.transProcessTaskName(entityPermission);
        }
        CustomAiproctsQjdstBO bo = PojoUtil.copy(entityPermission, CustomAiproctsQjdstBO.class);
        return bo;
    }

    @Override
    public List<CustomAiproctsQjdstEntity> getEntityByIds(Collection<Long> ids, String permission) {
        return this.baseDao.getByIds(ids,permission);
    }
    @Override
    public List<CustomAiproctsQjdstEntity> getEntityByIdsFilterCompany(Collection<Long> ids) {
        return this.baseDao.getByIds(ids,"");
    }


    @Override
    public List<CustomAiproctsQjdstEntity> getEntityByIds(Collection<Long> ids, String permission, boolean includeInvalid) {
        if(includeInvalid){
            return this.baseDao.getByIdsIncludeInvalid(ids,permission);
        }
        return this.baseDao.getByIds(ids,permission);
    }

    @Override
    public List<CustomAiproctsQjdstEntity> getEntityByIdsFilterCompany(Collection<Long> ids, boolean includeInvalid) {
        if(includeInvalid){
            return this.baseDao.getByIdsIncludeInvalid(ids,"");
        }
        return this.baseDao.getByIds(ids,"");
    }


    @Override
    @Transactional
    public MenInfoBO<CustomAiproctsQjdstEntity> mneList(String propotyCode, String modelCode, String code){
        String joinString = "";
        List<String> mneShowList = joinString.isEmpty() ? new ArrayList<>() : Arrays.asList(joinString.split(","));

        String joinTime = "";
        String joinDateTime = "aiprocts_Qjdst_optime,aiprocts_Qjdst_starttime,aiprocts_Qjdst_endtime,aiprocts_Qjdst_createTime,aiprocts_Qjdst_modifyTime,aiprocts_Qjdst_deleteTime,aiprocts_Qjdst_effectTime";
        String joinDate = "";
        List<String> timeList = joinTime.isEmpty() ? new ArrayList<>() : Arrays.asList(joinTime.split(","));
        List<String> dateTimeList = joinDateTime.isEmpty() ? new ArrayList<>() : Arrays.asList(joinDateTime.split(","));
        List<String> dateList = joinDate.isEmpty() ? new ArrayList<>() : Arrays.asList(joinDate.split(","));

        String permissionSql = dataPermissionService.getPermissionSql("");

        MenInfoBO<CustomAiproctsQjdstEntity> menInfoBO = menCodeGenerator.mneList(code, CustomAiproctsQjdstEntity.class);
        if (CollUtil.isNotEmpty(menInfoBO.getIdList())){
            List<CustomAiproctsQjdstEntity> list = this.getEntityByIds(menInfoBO.getIdList(),permissionSql);
            List<CustomAiproctsQjdstEntity> resultList = new ArrayList<>(list.size());
            if (!CollectionUtils.isEmpty(mneShowList)){
                menInfoBO.setEntityList(list);
            }else {
                for (CustomAiproctsQjdstEntity temp:list){
                    CustomAiproctsQjdstEntity entity = new CustomAiproctsQjdstEntity();
                    entity.setId(temp.getId());
                    resultList.add(entity);
                }
                menInfoBO.setEntityList(resultList);
            }
        }

        if (!CollectionUtils.isEmpty(mneShowList)){
            Set<String> resultSet = parsePropertyShowCode(mneShowList);
            menInfoBO.setDisplayFieldSet(resultSet);
        }
        if (!CollectionUtils.isEmpty(timeList)){
            Set<String> resultSet = parsePropertyShowCode(timeList);
            menInfoBO.setTimeSet(resultSet);
        }
        if (!CollectionUtils.isEmpty(dateTimeList)){
            Set<String> resultSet = parsePropertyShowCode(dateTimeList);
            menInfoBO.setDateTimeSet(resultSet);
        }
        if (!CollectionUtils.isEmpty(dateList)){
            Set<String> resultSet = parsePropertyShowCode(dateList);
            menInfoBO.setDateSet(resultSet);
        }
        return menInfoBO;
    }

    
    private static Set<String> parsePropertyShowCode(List<String> propertyCodeList){
        Set<String> resultSet = propertyCodeList.stream()
                .map(s -> {
                    if (s.contains("_")) {
                        return s.substring(s.lastIndexOf("_") + 1);
                    } else {
                        return s;
                    }
                })
                .collect(Collectors.toSet());
        return resultSet;
    }

    @Override
    public String printer(PrinterDataReq req) {
        if (Objects.isNull(req.getCondition())) {
            return "";
        }
        long id;
        try {
            id = Long.parseLong(req.getCondition().getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return "";
        }
        Map<String, Object> map = PojoUtil.toMap(getByIdBO(id));
        return printerDataService.cachePrinterData(map, req);
    }


    @Override
    @Transactional
    public Long deleteBatchByParent(List<Long> parentIdList,String parentTbName) {
        if (CollUtil.isEmpty(parentIdList)) {
            return 0L;
        }
        return super.baseDao.deleteBatchByParent(parentIdList, parentTbName,
                UserContext.getUserContext().getStaffCode(), UserContext.getUserContext().getStaffName(), new Date());
    }



    @Override
    @Transactional
    public Long submit(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        CustomAiproctsQjdstBO bo = workFlowBO.getEntity();
        bo.setCheckCompany(false);
        Long id = bo.getId();
        if(id == null){
            id = _selfService.addBO(bo);
            bo.setId(id);
        }else{
            _selfService.updateBO(bo);
        }
        //放入模块缩略码,继承模型同时放入源模块
        workFlowBO.setAcronym("aipro");
        workFlowBO.setSrcAcronym("");
        //处理工作流
        this.dealWorkflowVoInner(workFlowBO);
        return id;
    }

    @Override
    @Transactional
    public Boolean revocationPending(WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        //工作流待办撤回
        return workFlowRunTimeService.revocationPending(workFlowBO);
    }

    @Override
    @Transactional
    public Boolean cancelProcess(WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        //工作流流程作废
        return workFlowRunTimeService.cancelProcess(workFlowBO);
    }

    @Override
    public CustomAiproctsQjdstBO getByPendingIdBO(Long pendingId) {
        Long entityId = workFlowRunTimeService.getEntityIdByPendingId(pendingId, UserContext.getUserContext().getUserName());
        CustomAiproctsQjdstEntity entity = this.get(entityId);
        if(ObjectUtil.isNull(entity)){
            return null;
        }
        propertyFillService.process(entity, ProcessTypeEnum.QUERY);
        return PojoUtil.copy(entity, CustomAiproctsQjdstBO.class);
    }



    @Override
    @Transactional
    public Boolean startProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String targetNode, String comment) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String srcAcronym = "";
        String userName = UserContext.getUserContext().getUserName();
        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = new WorkFlow<>();

        StartProcessReq startProcessReq = new StartProcessReq();
        startProcessReq.setProcessKey(processKey);
        if(!StringUtils.isEmpty(targetNode)){
            startProcessReq.setTargetNode(targetNode);
        }
        startProcessReq.setUsername(userName);
        startProcessReq.setComment(comment);
        startProcessReq.setAppId(LayerModuleUtil.generateWorkFlowSvcCode(moduleCode));

        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.START_PROCESS);
        workFlowParam.setStartProcessParam(startProcessReq);

        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setSrcAcronym(srcAcronym);
        entityWorkFlow.setEntity(entity);
        entityWorkFlow.setWorkFlowParam(workFlowParam);

        return this.dealWorkflowEntityInner(entityWorkFlow);
    }

    @Override
    @Transactional
    public Boolean startProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String targetNode, String customTag, String comment) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String userName = UserContext.getUserContext().getUserName();
        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = new WorkFlow<>();
        StartProcessReq startProcessReq = new StartProcessReq();
        startProcessReq.setProcessKey(processKey);
        if(!StringUtils.isEmpty(customTag)){
            CustomTagFilter customTagFilter = new CustomTagFilter();
            customTagFilter.setCustomTag(customTag);
            TargetNodeFilter targetNodeFilter = new TargetNodeFilter();
            targetNodeFilter.setType("customTag");
            targetNodeFilter.setCustomTagFilter(customTagFilter);
            startProcessReq.setTargetNodeFilter(targetNodeFilter);
        }else if(!StringUtils.isEmpty(targetNode)){
            startProcessReq.setTargetNode(targetNode);
        }
        startProcessReq.setUsername(userName);
        startProcessReq.setComment(comment);
        startProcessReq.setAppId(WorkflowUtil.getSvcCode(moduleCode));
        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.START_PROCESS);
        workFlowParam.setStartProcessParam(startProcessReq);
        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setEntity(entity);
        entityWorkFlow.setWorkFlowParam(workFlowParam);

        return this.dealWorkflowEntityInner(entityWorkFlow);
    }




    @Override
    @Transactional
    public Boolean saveProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String comment) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String srcAcronym = "";
        String userName = UserContext.getUserContext().getUserName();
        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = new WorkFlow<>();

        SaveProcessReq saveProcessReq = new SaveProcessReq();
        saveProcessReq.setProcessKey(processKey);
        saveProcessReq.setUsername(userName);
        saveProcessReq.setComment(comment);
        saveProcessReq.setAppId(LayerModuleUtil.generateWorkFlowSvcCode(moduleCode));

        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.SAVE_PROCESS);
        workFlowParam.setSaveProcessParam(saveProcessReq);

        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setSrcAcronym(srcAcronym);
        entityWorkFlow.setEntity(entity);
        entityWorkFlow.setWorkFlowParam(workFlowParam);

        return this.dealWorkflowEntityInner(entityWorkFlow);
    }

    @Override
    @Transactional
    public Boolean saveProcessBackend(CustomAiproctsQjdstEntity entity, String processKey, String customTag, String comment) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String userName = UserContext.getUserContext().getUserName();
        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = new WorkFlow<>();

        SaveProcessReq saveProcessReq = new SaveProcessReq();
        saveProcessReq.setProcessKey(processKey);
        saveProcessReq.setUsername(userName);
        saveProcessReq.setComment(comment);
        saveProcessReq.setAppId(WorkflowUtil.getSvcCode(moduleCode));
        if(!StringUtils.isEmpty(customTag)){
            CustomTagFilter customTagFilter = new CustomTagFilter();
            customTagFilter.setCustomTag(customTag);
            TargetNodeFilter targetNodeFilter = new TargetNodeFilter();
            targetNodeFilter.setType("customTag");
            targetNodeFilter.setCustomTagFilter(customTagFilter);
            saveProcessReq.setTargetNodeFilter(targetNodeFilter);
        }
        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.SAVE_PROCESS);
        workFlowParam.setSaveProcessParam(saveProcessReq);
        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setEntity(entity);
        entityWorkFlow.setWorkFlowParam(workFlowParam);
        return this.dealWorkflowEntityInner(entityWorkFlow);
    }


    @Override
    public Boolean cancelProcessBackend(String processId) {
        WorkFlow<CustomAiproctsQjdstBO> entityWorkFlow = new WorkFlow<>();
        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.CANCEL_PROCESS);
        workFlowParam.setCancelProcessId(processId);
        entityWorkFlow.setWorkFlowParam(workFlowParam);
        return this.cancelProcess(entityWorkFlow);
    }



    @Override
    public List<InternationalVO> getTaskNameList() {
        List<InternationalVO> resultList = new ArrayList<>();
        List<String> taskNameList = taskNameListService.getTaskNameList("aipro_qjdst");
        if (CollUtil.isNotEmpty(taskNameList)){
            HashSet<String> nameSet = new HashSet<>();
            //去重
            for (String name : taskNameList){
                String[] nameSplit = name.split(",");
                nameSet.addAll(Arrays.asList(nameSplit));
            }
            //获取国际化
            for (String name:nameSet){
                if(StringUtils.isEmpty(name)){
                    continue;
                }
                InternationalVO vo = new InternationalVO();
                String i18nValue = internationalService.getI18nValue(name);
                if (StringUtils.isEmpty(i18nValue)){
                    i18nValue = name;
                }
                vo.setKey(name);
                vo.setValue(i18nValue);
                resultList.add(vo);
            }
        }
        return resultList;
            //TODO
        }


    @Override
    public GetUserTaskByProcessIdResp getPendingTask(Long entityId, String appId) {
        GetUserTaskByProcessIdResp result = new GetUserTaskByProcessIdResp();
        CustomAiproctsQjdstEntity entity = this.get(entityId);
        if (Objects.isNull(entity)){
            return result;
        }
        String processId = entity.getProcessId();
        //根据entityId查询对应数据
        String userName = UserContext.getUserContext().getUserName();

        GetPendingListResp resp = runtimeApi.getPendingList(userName, processId, SERVICE_SOURCE, 1, 1, appId);
        if(CollectionUtils.isNotEmpty(resp.getList())){
            PendingTaskInfo pendingInfo = resp.getList().get(0);
            result.setPendingTask(pendingInfo);
        }

        return result;
    }


    @Override
    public String saveProcess(String formData, String processKey, String comment,String appId,String userName) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String srcAcronym = "";

        JSONObject formDataJson = new JSONObject();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(formData)){
            formDataJson = JSON.parseObject(formData);
        }
        //获取entity
        Object entityId = formDataJson.get(FORM_DATA_ENTITY_ID);
        CustomAiproctsQjdstEntity entity = this.get((Long)entityId);
        if(ObjectUtil.isNull(entity)){
            return null;
        }

        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = new WorkFlow<>();

        SaveProcessReq saveProcessReq = new SaveProcessReq();
        saveProcessReq.setProcessKey(processKey);
        saveProcessReq.setUsername(userName);
        saveProcessReq.setComment(comment);
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(appId)){
            saveProcessReq.setAppId(appId);
        }

        WorkFlowParam workFlowParam = new WorkFlowParam();
        workFlowParam.setOperatorType(OperatorTypeEnum.SAVE_PROCESS);
        workFlowParam.setSaveProcessParam(saveProcessReq);

        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setSrcAcronym(srcAcronym);
        entityWorkFlow.setEntity(entity);
        entityWorkFlow.setWorkFlowParam(workFlowParam);

        Boolean saveFlag = this.dealWorkflowEntityInner(entityWorkFlow);
        if (saveFlag){
            return entity.getProcessId();
        }else {
            return null;
        }
    }



    @Override
    public String generateFormData(Long entityId, String processKey, String formData) {
        String moduleCode = "aiprocts";
        String modelCode = "aiproctsQjdst";
        Class<? extends AbstractConfigurationWorkflowEntity> entityClass = CustomAiproctsQjdstEntity.class;

        WorkflowFormDataGenBO formDataGenBO = new WorkflowFormDataGenBO();
        formDataGenBO.setEntityId(entityId);
        formDataGenBO.setEntityClass(entityClass);
        formDataGenBO.setProcessKey(processKey);
        formDataGenBO.setFormData(formData);
        formDataGenBO.setModuleCode(moduleCode);
        formDataGenBO.setModelCode(modelCode);
        return workFlowRunTimeService.generateFormData(formDataGenBO);
    }



    @Override
    public Boolean dealWorkflowVoInner(WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        WorkFlow<CustomAiproctsQjdstEntity> workFlowEntity = PojoUtil.copy(workFlowBO, WorkFlow.class);
        CustomAiproctsQjdstEntity entity = PojoUtil.copy(workFlowBO.getEntity(), CustomAiproctsQjdstEntity.class);
        workFlowEntity.setEntity(entity);
        if(workFlowEntity.getAcronym() == null){
            workFlowEntity.setAcronym("aipro");
            workFlowEntity.setSrcAcronym("");
        }
        return this.dealWorkflowEntityInner(workFlowEntity);
    }

    @Override
    public Boolean dealWorkflowEntityInner(WorkFlow<CustomAiproctsQjdstEntity> workFlowEntity) {
        if(workFlowEntity.getAcronym() == null){
            workFlowEntity.setAcronym("aipro");
            workFlowEntity.setSrcAcronym("");
        }
        workFlowEntity.setModelCode("aiproctsQjdst");
        //启动工作流、处理工作流
        workFlowRunTimeService.dealWorkFlow(workFlowEntity);
        //记录流程信息
        workFlowRunTimeService.recordProcessInfo(workFlowEntity);
        return Boolean.TRUE;
    }


    @Override
    @Transactional
    public WorkflowBatchSubmitResultBO submitPendingBatch(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        //放入模块缩略码,继承模型同时放入源模块
        workFlowBO.setAcronym("aipro");
        workFlowBO.setSrcAcronym("");
        //处理工作流
        return PojoUtil.copy(workFlowRunTimeService.dealWorkFlow(workFlowBO), WorkflowBatchSubmitResultBO.class);
    }



    @Override
    @Transactional
    public WorkflowBatchRestartResultBO restartProcess(@Valid WorkFlow<CustomAiproctsQjdstBO> workFlowBO) {
        String moduleCode = "aiprocts";
        String acronym = "aipro";
        String srcAcronym = "";

        WorkFlow<CustomAiproctsQjdstEntity> entityWorkFlow = JsonUtils.jsonToObj(JsonUtils.objToJson(workFlowBO), new TypeReference<WorkFlow<CustomAiproctsQjdstEntity>>() {});
        entityWorkFlow.setModuleCode(moduleCode);
        entityWorkFlow.setAcronym(acronym);
        entityWorkFlow.setSrcAcronym(srcAcronym);
        entityWorkFlow.setModelCode("aiproctsQjdst");
        return workFlowRunTimeService.restartProcess(entityWorkFlow, CustomAiproctsQjdstEntity.class);
    }

    }