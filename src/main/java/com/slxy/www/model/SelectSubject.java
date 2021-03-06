package com.slxy.www.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhengql123
 * @since 2018-01-25
 */

@TableName("select_subject")
public class SelectSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 题目名称
     */
    @TableField("sub_name")
    private String subName;
    /**
     * 发布教师id
     */
    @TableField("tea_id")
    private Integer teaId;
    /**
     * 题目类型，1应用型，2理论性
     */
    @TableField("sub_type")
    private Integer subType;
    /**
     * 题目内容
     */
    @TableField("sub_content")
    private String subContent;
    /**
     * 题目附件
     */
    @TableField("sub_file")
    private String subFile;
    /**
     * 审核状态 0未处理，1审核不通过，2审核通过
     */
    @TableField("adm_audit_state")
    private Integer admAuditState;
    /**
     * 审核意见
     */
    @TableField("adm_audit_content")
    private String admAuditContent;
    /**
     * 审核人id
     */
    @TableField("adm_audit_id")
    private Integer admAuditId;
    /**
     * 指导老师评分
     */
    @TableField("tutor_score")
    private Double tutorScore;
    /**
     * 评阅老师评分
     */
    @TableField("judge_score")
    private Double judgeScore;
    /**
     * 答辩得分
     */
    @TableField("defence_score")
    private Double defenceScore;
    /**
     * 最终总得分
     */
    @TableField("final_total_score")
    private Double finalTotalScore;
    /**
     * 题目面向系别id
     */
    @TableField("for_dep_id")
    private String forDepId;

    /**
     * 题目选题状态 0未选 1审核中 2已选
     */
    @TableField("sub_select_status")
    private Integer subSelectStatus;
    /**
     * 题目年份
     */
    @TableField("sub_year")
    private String subYear;
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
    public SelectSubject() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getSubName() {
        return this.subName;
    }

    public Integer getTeaId() {
        return this.teaId;
    }

    public Integer getSubType() {
        return this.subType;
    }

    public String getSubContent() {
        return this.subContent;
    }

    public Integer getAdmAuditState() {
        return this.admAuditState;
    }

    public String getAdmAuditContent() {
        return this.admAuditContent;
    }

    public Integer getAdmAuditId() {
        return this.admAuditId;
    }

    public Double getTutorScore() {
        return this.tutorScore;
    }

    public Double getJudgeScore() {
        return this.judgeScore;
    }

    public Double getDefenceScore() {
        return this.defenceScore;
    }

    public Double getFinalTotalScore() {
        return this.finalTotalScore;
    }

    public String getSubYear() {
        return this.subYear;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public Date getGmtModify() {
        return this.gmtModify;
    }

    public String getSubFile() {
        return subFile;
    }

    public SelectSubject setSubFile(String subFile) {
        this.subFile = subFile;
        return this;
    }

    public SelectSubject setId(Integer id) {
        this.id = id;
        return this;
    }

    public SelectSubject setSubName(String subName) {
        this.subName = subName;
        return this;
    }

    public SelectSubject setTeaId(Integer teaId) {
        this.teaId = teaId;
        return this;
    }

    public SelectSubject setSubType(Integer subType) {
        this.subType = subType;
        return this;
    }

    public SelectSubject setSubContent(String subContent) {
        this.subContent = subContent;
        return this;
    }

    public SelectSubject setAdmAuditState(Integer admAuditState) {
        this.admAuditState = admAuditState;
        return this;
    }

    public SelectSubject setAdmAuditContent(String admAuditContent) {
        this.admAuditContent = admAuditContent;
        return this;
    }

    public SelectSubject setAdmAuditId(Integer admAuditId) {
        this.admAuditId = admAuditId;
        return this;
    }

    public SelectSubject setTutorScore(Double tutorScore) {
        this.tutorScore = tutorScore;
        return this;
    }

    public SelectSubject setJudgeScore(Double judgeScore) {
        this.judgeScore = judgeScore;
        return this;
    }

    public SelectSubject setDefenceScore(Double defenceScore) {
        this.defenceScore = defenceScore;
        return this;
    }

    public SelectSubject setFinalTotalScore(Double finalTotalScore) {
        this.finalTotalScore = finalTotalScore;
        return this;
    }

    public SelectSubject setSubYear(String subYear) {
        this.subYear = subYear;
        return this;
    }

    public SelectSubject setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public SelectSubject setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
        return this;
    }

    public String getForDepId() {
        return forDepId;
    }

    public SelectSubject setForDepId(String forDepId) {
        this.forDepId = forDepId;
        return this;
    }

    public Integer getSubSelectStatus() {
        return subSelectStatus;
    }

    public SelectSubject setSubSelectStatus(Integer subSelectStatus) {
        this.subSelectStatus = subSelectStatus;
        return this;
    }
}
