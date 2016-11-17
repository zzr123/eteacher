package com.turing.eteacher.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.turing.eteacher.base.CreateTimeModel;

@Entity
@Table(name = "T_SCORE")
public class Score extends CreateTimeModel implements Serializable {

	private static final long serialVersionUID = -8791077874651954134L;
	
	private String scoreId;
	private String stuId;
	private String courseId;
	private String scoreType;//课程成绩组成主键
	private BigDecimal scoreNumber;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "SCORE_ID")
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	@Column(name = "STU_ID")
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "CS_ID")
	public String getScoreType() {
		return scoreType;
	}
	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	
	@Column(name = "SCORE")
	public BigDecimal getScoreNumber() {
		return scoreNumber;
	}
	
	public void setScoreNumber(BigDecimal scoreNumber) {
		this.scoreNumber = scoreNumber;
	}
	
}
