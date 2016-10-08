package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_COURSE_CELL")
public class CourseCell implements Serializable{
	
	private static final long serialVersionUID = -2940963477296088798L;
	
	private String ctId;
	private String ciId;
	private String weekDay;
	private String lessonNumber;
	private String location;
	private String classRoom;
	
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
	@Column(name="CI_ID")
	public String getCiId() {
		return ciId;
	}
	public void setCiId(String ciId) {
		this.ciId = ciId;
	}
	@Column(name="WEEKDAY")
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	@Column(name="LESSON_NUMBER")
	public String getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(String lessonNumber) {
		this.lessonNumber = lessonNumber;
	}
	@Column(name="LOCATION")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Column(name="CLASSROOM")
	public String getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	
}
