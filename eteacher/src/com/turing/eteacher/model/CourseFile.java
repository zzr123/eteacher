package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COURSE_FILE")
public class CourseFile implements Serializable {

	private static final long serialVersionUID = 3065006905845596448L;

	private String cfId;
	private String courseId;
	private String fileName;
	private String serverName;
	private String fileType;
	private String fileAuth;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "CF_ID")
	public String getCfId() {
		return cfId;
	}
	public void setCfId(String cfId) {
		this.cfId = cfId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name = "SERVER_NAME")
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	@Column(name = "FILE_TYPE")
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Column(name = "FILE_AUTH")
	public String getFileAuth() {
		return fileAuth;
	}
	public void setFileAuth(String fileAuth) {
		this.fileAuth = fileAuth;
	}
	
}
