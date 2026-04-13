package com.supcon.nebule.aiprocts.custom.vo;

import com.supcon.nebule.framework.common.entity.AbstractNullField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
@Accessors(chain = true)
public class CustomAiproctsQjdstVO extends AbstractNullField{

    @ApiModelProperty("姓名")
    @Length(max = 255, message = "{\"propertyI18nKey\":\"aiprocts.model.Qjdst.field.name\",\"fillInfo\":\"长度不能超过255\"}")
    private String name;
    @ApiModelProperty("工号")
    @Length(max = 255, message = "{\"propertyI18nKey\":\"aiprocts.model.Qjdst.field.num\",\"fillInfo\":\"长度不能超过255\"}")
    private String num;
    @ApiModelProperty("入职时间")
    private Date optime;
    @ApiModelProperty("可用假期")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "{\"propertyI18nKey\":\"aiprocts.model.Qjdst.field.gsnum\",\"fillInfo\":\"小数位不能超过2\"}")
    private BigDecimal gsnum;
    @ApiModelProperty("请假开始时间")
    private Date starttime;
    @ApiModelProperty("请假结束时间")
    private Date endtime;
    @ApiModelProperty("公司编码")
    private String companyCode;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date modifyTime;
    @ApiModelProperty("删除时间")
    private Date deleteTime;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("流程key")
    private String processKey;
    @ApiModelProperty("流程实例id")
    private String processId;
    @ApiModelProperty("流程单据编号")
    private String formNo;
    @ApiModelProperty("流程单据状态")
    private String processTaskI18n;
    @ApiModelProperty("流程状态")
    private String processStatus;
    @ApiModelProperty("流程名称")
    private String processName;
    @ApiModelProperty("流程版本")
    private String processVersion;
    @ApiModelProperty("生效时间")
    private Date effectTime;
    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("版本号")
    private Integer version;
    @ApiModelProperty("是否有效")
    private Integer valid;
    @ApiModelProperty("岗位层级")
    private String posLayRec;
    @ApiModelProperty("所有者岗位编码")
    private String ownerPosCode;
    @ApiModelProperty("所有者岗位名称")
    private String ownerPosName;
    @ApiModelProperty("创建人岗位编码")
    private String createPosCode;
    @ApiModelProperty("创建人岗位名称")
    private String createPosName;
    @ApiModelProperty("所有者员工编码")
    private String ownerStaffCode;
    @ApiModelProperty("所有者员工名称")
    private String ownerStaffName;
    @ApiModelProperty("删除人编码")
    private String deleteStaffCode;
    @ApiModelProperty("删除人名称")
    private String deleteStaffName;
    @ApiModelProperty("修改人编码")
    private String modifyStaffCode;
    @ApiModelProperty("修改人名称")
    private String modifyStaffName;
    @ApiModelProperty("创建人编码")
    private String createStaffCode;
    @ApiModelProperty("创建人名称")
    private String createStaffName;
    @ApiModelProperty("所有者部门编码")
    private String ownerDeptCode;
    @ApiModelProperty("所有者部门名称")
    private String ownerDeptName;

}