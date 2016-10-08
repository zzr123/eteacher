package com.turing.eteacher.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COURSE_SCORE")
public class CourseScore implements Serializable {

	private static final long serialVersionUID = 7924545853360472248L;

	private String csId;
	private String courseId;
	private String scoreName;
	private BigDecimal scorePercent;
	private Integer csOrder;
    private String scorePointId;
    private Integer status;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "SIGN_ID")
	public String getCsId() {
		return csId;
	}
	public void setCsId(String csId) {
		this.csId = csId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "SCORE_NAME")
	public String getScoreName() {
		return scoreName;
	}
	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}
	@Column(name = "SCORE_PERCENT")
	public BigDecimal getScorePercent() {
		return scorePercent;
	}
	public void setScorePercent(BigDecimal scorePercent) {
		this.scorePercent = scorePercent;
	}
	@Column(name = "CS_ORDER")
	public Integer getCsOrder() {
		return csOrder;
	}
	public void setCsOrder(Integer csOrder) {
		this.csOrder = csOrder;
	}
	@Column(name="SCORE_POINT_ID")
	public String getScorePointId() {
		return scorePointId;
	}
	public void setScorePointId(String scorePointId) {
		this.scorePointId = scorePointId;
	}
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
