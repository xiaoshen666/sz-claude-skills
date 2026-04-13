package com.supcon.nebule.aiprocts.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.supcon.nebule.fr.annotation.EnCodeField;
import com.supcon.nebule.framework.common.annotation.audit.AuditModel;
import com.supcon.nebule.framework.common.entity.AbstractConfigurationWorkflowEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("aipro_qjdst")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@EnCodeField(module = "aiprocts",model = "aiproctsQjdst",acronym="aipro", srcAcronym="")
@AuditModel(module = "aiprocts",model = "aiproctsQjdst")


public class CustomAiproctsQjdstEntity extends AbstractConfigurationWorkflowEntity{

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("工号")
    @TableField("num")
    private String num;

    @ApiModelProperty("入职时间")
    @TableField("optime")
    private Date optime;

    @ApiModelProperty("可用假期")
    @TableField("gsnum")
    private BigDecimal gsnum;

    @ApiModelProperty("请假开始时间")
    @TableField("starttime")
    private Date starttime;

    @ApiModelProperty("请假结束时间")
    @TableField("endtime")
    private Date endtime;


}