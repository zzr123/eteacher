package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_COURSE_ITEM")
public class CourseItem implements Serializable{
	
	private static final long serialVersionUID = -680512296036333383L;
	
	private String ciId;
	private String courseId;
	private Integer startWeek;
	private Integer endWeek;
	private String startDay;
	private String endday;
	private String repeatType;
	private Integer repeatNumber;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "CI_ID")
	public String getCiId() {
		return ciId;
	}
	public void setCiId(String ciId) {
		this.ciId = ciId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name="START_WEEK")
	public Integer getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}
	@Column(name="END_WEEK")
	public Integer getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}
	@Column(name="START_DAY")
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	@Column(name="END_DAY")
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	@Column(name="REPEAT_TYPE")
	public String getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}
	@Column(name="REPEAT_NUMBER")
	public Integer getRepeatNumber() {
		return repeatNumber;
	}
	public void setRepeatNumber(Integer repeatNumber) {
		this.repeatNumber = repeatNumber;
	}
	@Override
	public String toString() {
		return "CourseItem [ciId=" + ciId + ", courseId=" + courseId + ", startWeek=" + startWeek + ", endWeek="
				+ endWeek + ", startDay=" + startDay + ", endday=" + endday + ", repeatType=" + repeatType
				+ ", repeatNumber=" + repeatNumber + "]";
	}
}
