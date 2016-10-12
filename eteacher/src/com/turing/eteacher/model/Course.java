package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COURSE")
public class Course implements Serializable {

	private static final long serialVersionUID = 2930264059229378022L;

	private String courseId;
	private String termId;
	private String courseName;
	private String introduction;
	private Integer classHours;
	private String majorId;
	private String teachingMethodId;
	private String courseTypeId;
	private String examinationModeId;
	private String formula;
	private String userId;
	private String remindTime;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "TERM_ID")
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	@Column(name = "COURSE_NAME")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Column(name = "INTRODUCTION")
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Column(name = "CLASS_HOURS")
	public Integer getClassHours() {
		return classHours;
	}
	public void setClassHours(Integer classHours) {
		this.classHours = classHours;
	}
	@Column(name = "MAJOR_ID")
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
	@Column(name = "TEACHING_METHOD_ID")
	public String getTeachingMethodId() {
		return teachingMethodId;
	}
	public void setTeachingMethodId(String teachingMethodId) {
		this.teachingMethodId = teachingMethodId;
	}
	@Column(name = "COURSE_TYPE_ID")
	public String getCourseTypeId() {
		return courseTypeId;
	}
	public void setCourseTypeId(String courseTypeId) {
		this.courseTypeId = courseTypeId;
	}
	@Column(name = "EXAMINATION_MODE_ID")
	public String getExaminationModeId() {
		return examinationModeId;
	}
	public void setExaminationModeId(String examinationModeId) {
		this.examinationModeId = examinationModeId;
	}
	@Column(name = "FORMULA")
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="REMIND_TIME")
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	
}
