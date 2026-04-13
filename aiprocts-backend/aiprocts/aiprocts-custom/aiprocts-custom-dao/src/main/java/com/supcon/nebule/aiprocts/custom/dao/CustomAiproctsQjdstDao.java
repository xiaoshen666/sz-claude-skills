package com.supcon.nebule.aiprocts.custom.dao;

import com.supcon.nebule.aiprocts.custom.entity.CustomAiproctsQjdstEntity;
import com.supcon.nebule.aiprocts.custom.query.CustomAiproctsQjdstQuery;
import com.supcon.nebule.framework.common.constant.SysConstants;
import com.supcon.nebule.framework.common.dao.BaseDao;
import com.supcon.nebule.framework.common.entity.PageQuery;
import com.supcon.nebule.framework.common.entity.Tree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface CustomAiproctsQjdstDao extends BaseDao<CustomAiproctsQjdstEntity>{

    List<CustomAiproctsQjdstEntity> pageQuery(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstQuery> query);

    Long countPageQuery(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstQuery> query);

    Map sumNumberPageQuery(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstQuery> query);

    Map sumNumberListPageQuery(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstEntity> query);

    Map sumNumberListQuery(@Param(SysConstants.ENTITY) CustomAiproctsQjdstEntity entity);

    CustomAiproctsQjdstEntity getByPermission(@Param(SysConstants.ID) Long id, @Param(SysConstants.PERMISSION) String permission);

    Long countListPage(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstEntity> query);

    List<CustomAiproctsQjdstEntity> listPage(@Param(SysConstants.QUERY) PageQuery<CustomAiproctsQjdstEntity> query);

    List<CustomAiproctsQjdstEntity> listByEntity(@Param(SysConstants.ENTITY) CustomAiproctsQjdstEntity entity);

    Long deleteBatchByParent( @Param(SysConstants.PARENT_IDS) Collection<Long> parentIds,
                              @Param(SysConstants.PARENT_TABLE_NAME) String parentTableName,
                              @Param(SysConstants.DELETE_STAFF_CODE) String deleteStaffCode,
                              @Param(SysConstants.DELETE_STAFF_NAME) String deleteStaffName,
                              @Param(SysConstants.DELETE_TIME) Date deleteTime);

    List<CustomAiproctsQjdstEntity> treeSearchCriteria(@Param(SysConstants.QUERY) Tree<CustomAiproctsQjdstQuery> treequery,
                                                       @Param(SysConstants.PERMISSION) String permissionSql);


}