package com.supcon.nebule.aiprocts.custom.query;

import com.supcon.nebule.framework.common.entity.QueryElement;
import com.supcon.nebule.framework.common.entity.SortField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Accessors(chain = true)
public class CustomAiproctsQjdstQuery {

    @ApiModelProperty("姓名")
    private QueryElement<String> name;

    @ApiModelProperty("工号")
    private QueryElement<String> num;

    @ApiModelProperty("入职时间")
    private QueryElement<Date> _optime;

    @ApiModelProperty("入职时间")
    private QueryElement<Date> optime;

    @ApiModelProperty("可用假期")
    private QueryElement<BigDecimal> _gsnum;

    @ApiModelProperty("可用假期")
    private QueryElement<BigDecimal> gsnum;

    @ApiModelProperty("请假开始时间")
    private QueryElement<Date> _starttime;

    @ApiModelProperty("请假开始时间")
    private QueryElement<Date> starttime;

    @ApiModelProperty("请假结束时间")
    private QueryElement<Date> _endtime;

    @ApiModelProperty("请假结束时间")
    private QueryElement<Date> endtime;

    @ApiModelProperty("公司编码")
    private QueryElement<String> companyCode;

    @ApiModelProperty("创建时间")
    private QueryElement<Date> _createTime;

    @ApiModelProperty("创建时间")
    private QueryElement<Date> createTime;

    @ApiModelProperty("修改时间")
    private QueryElement<Date> _modifyTime;

    @ApiModelProperty("修改时间")
    private QueryElement<Date> modifyTime;

    @ApiModelProperty("删除时间")
    private QueryElement<Date> _deleteTime;

    @ApiModelProperty("删除时间")
    private QueryElement<Date> deleteTime;

    @ApiModelProperty("排序")
    private QueryElement<Integer> _sort;

    @ApiModelProperty("排序")
    private QueryElement<Integer> sort;

    @ApiModelProperty("流程key")
    private QueryElement<String> processKey;

    @ApiModelProperty("流程实例id")
    private QueryElement<String> processId;

    @ApiModelProperty("流程单据编号")
    private QueryElement<String> formNo;

    @ApiModelProperty("流程单据状态")
    private QueryElement<String> processTaskI18n;

    @ApiModelProperty("流程状态")
    private QueryElement<String> processStatus;

    @ApiModelProperty("流程名称")
    private QueryElement<String> processName;

    @ApiModelProperty("流程版本")
    private QueryElement<String> processVersion;

    @ApiModelProperty("生效时间")
    private QueryElement<Date> _effectTime;

    @ApiModelProperty("生效时间")
    private QueryElement<Date> effectTime;

    @ApiModelProperty("主键ID")
    private QueryElement<Long> _id;

    @ApiModelProperty("主键ID")
    private QueryElement<Long> id;

    @ApiModelProperty("版本号")
    private QueryElement<Integer> _version;

    @ApiModelProperty("版本号")
    private QueryElement<Integer> version;

    @ApiModelProperty("是否有效")
    private QueryElement<Integer> _valid;

    @ApiModelProperty("是否有效")
    private QueryElement<Integer> valid;

    @ApiModelProperty("岗位层级")
    private QueryElement<String> posLayRec;

    @ApiModelProperty("所有者岗位编码")
    private QueryElement<String> ownerPosCode;

    @ApiModelProperty("所有者岗位名称")
    private QueryElement<String> ownerPosName;

    @ApiModelProperty("创建人岗位编码")
    private QueryElement<String> createPosCode;

    @ApiModelProperty("创建人岗位名称")
    private QueryElement<String> createPosName;

    @ApiModelProperty("所有者员工编码")
    private QueryElement<String> ownerStaffCode;

    @ApiModelProperty("所有者员工名称")
    private QueryElement<String> ownerStaffName;

    @ApiModelProperty("删除人编码")
    private QueryElement<String> deleteStaffCode;

    @ApiModelProperty("删除人名称")
    private QueryElement<String> deleteStaffName;

    @ApiModelProperty("修改人编码")
    private QueryElement<String> modifyStaffCode;

    @ApiModelProperty("修改人名称")
    private QueryElement<String> modifyStaffName;

    @ApiModelProperty("创建人编码")
    private QueryElement<String> createStaffCode;

    @ApiModelProperty("创建人名称")
    private QueryElement<String> createStaffName;

    @ApiModelProperty("所有者部门编码")
    private QueryElement<String> ownerDeptCode;

    @ApiModelProperty("所有者部门名称")
    private QueryElement<String> ownerDeptName;

    @ApiModelProperty("排序字段列表")
    private List<SortField>  sortFieldList;

}