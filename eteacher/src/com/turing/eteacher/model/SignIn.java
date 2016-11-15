package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.turing.eteacher.base.CreateTimeModel;
@Entity
@Table(name = "T_SIGN_IN")
public class SignIn extends CreateTimeModel implements Serializable{
	private static final long serialVersionUID = 4943217375119808183L;
	
	private String signId;
	private String courseId;
	private String currentWeek;
	private String currentLessons;
	private String studentId;
	private int status;
	
	@Id
	@GeneratedValue(generator="customId")
	@GenericGenerator(name="customId", strategy="com.turing.eteacher.util.CustomIdGenerator")
	@Column(name="SIGN_ID")
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "CURRENT_WEEK")
	public String getCurrentWeek() {
		return currentWeek;
	}
	public void setCurrentWeek(String currentWeek) {
		this.currentWeek = currentWeek;
	}
	@Column(name = "CURRENT_CELL")
	public String getCurrentLessons() {
		return currentLessons;
	}
	public void setCurrentLessons(String currentLessons) {
		this.currentLessons = currentLessons;
	}
	@Column(name = "STUDENT_ID")
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	@Column(name = "STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
