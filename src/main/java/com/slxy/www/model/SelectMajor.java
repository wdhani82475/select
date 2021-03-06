package com.slxy.www.model;

import com.baomidou.mybatisplus.enums.IdType;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengql
 * @since 2018-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("select_major")
public class SelectMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专业id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 专业名称
     */
    @TableField("maj_name")
    private String majName;
    /**
     * 所属系别
     */
    @TableField("dep_id")
    private Integer depId;
    /**
     * 专业班级数
     */
    @TableField("maj_class_num")
    private Integer majClassNum;
    /**
     * 专业状态 0禁用，1启用
     */
    @TableField("maj_status")
    private Integer majStatus;

    /**
     * 专业介绍
     */
    @TableField("maj_info")
    private String majInfo;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modify")
    private Date gmtModify;

}
