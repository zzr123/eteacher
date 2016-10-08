package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COURSE_TABLE")
public class CourseTable implements Serializable {

	private static final long serialVersionUID = -3058915484281168305L;

	private String ctId;
	private String courseId;
	private Integer startWeek;
	private Integer endWeek;
	private String repeatType;
	private Integer repeatNumber;
	private String weekday;
	private String lessonNumber;
	private String location;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "CT_ID")
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "START_WEEK")
	public Integer getStartWeek() {
		return startWeek;
	}
	public void setStartWeek(Integer startWeek) {
		this.startWeek = startWeek;
	}
	@Column(name = "END_WEEK")
	public Integer getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(Integer endWeek) {
		this.endWeek = endWeek;
	}
	@Column(name = "REPEAT_TYPE")
	public String getRepeatType() {
		return repeatType;
	}
	public void setRepeatType(String repeatType) {
		this.repeatType = repeatType;
	}
	@Column(name = "REPEAT_NUMBER")
	public Integer getRepeatNumber() {
		return repeatNumber;
	}
	public void setRepeatNumber(Integer repeatNumber) {
		this.repeatNumber = repeatNumber;
	}
	@Column(name = "WEEKDAY")
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	@Column(name = "LESSON_NUMBER")
	public String getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(String lessonNumber) {
		this.lessonNumber = lessonNumber;
	}
	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
