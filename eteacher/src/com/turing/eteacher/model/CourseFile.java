package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_FILE")
public class CourseFile implements Serializable {

	private static final long serialVersionUID = 3065006905845596448L;

	private String fileId;
	private String dataId;
	private String fileName;
	private String serverName;
	private String isCourseFile;
	private String vocabularyId;
	private String fileAuth;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "FILE_ID")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	@Column(name = "DATA_ID")
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
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
	@Column(name = "IS_COURSE_FILE")
	public String getIsCourseFile() {
		return isCourseFile;
	}
	public void setIsCourseFile(String isCourseFile) {
		this.isCourseFile = isCourseFile;
	}
	@Column(name = "FILE_AUTH")
	public String getFileAuth() {
		return fileAuth;
	}
	public void setFileAuth(String fileAuth) {
		this.fileAuth = fileAuth;
	}
	@Column(name = "VOCABULARY_ID")
	public String getVocabularyId() {
		return vocabularyId;
	}
	public void setVocabularyId(String vocabularyId) {
		this.vocabularyId = vocabularyId;
	}
	
}
