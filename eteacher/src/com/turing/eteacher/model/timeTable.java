package com.turing.eteacher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_TIMETABLE")
public class timeTable {
	private String timetableId;
	private String schoolId;
	private String endTime;
	private String startTime;
	private String lessonNumber;
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "TIMETABLE_ID")
	public String getTimetableId() {
		return timetableId;
	}
	public void setTimetableId(String timetableId) {
		this.timetableId = timetableId;
	}
	@Column(name = "SCHOOL_ID")
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	@Column(name = "END_TIME")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name = "START_TIME")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "LESSON_NUMBER")
	public String getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(String lessonNumber) {
		this.lessonNumber = lessonNumber;
	}
	
	
}
