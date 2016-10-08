package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_TEXTBOOK")
public class Textbook implements Serializable {

	private static final long serialVersionUID = 6738307725050217510L;
	
	private String textbookId;
	private String courseId;
	private String textbookName;
	private String author;
	private String publisher;
	private String edition;
	private String isbn;
	private String textbookType;
	
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "TEXTBOOK_ID")
	public String getTextbookId() {
		return textbookId;
	}
	public void setTextbookId(String textbookId) {
		this.textbookId = textbookId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "TEXTBOOK_NAME")
	public String getTextbookName() {
		return textbookName;
	}
	public void setTextbookName(String textbookName) {
		this.textbookName = textbookName;
	}
	@Column(name = "AUTHOR")
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(name = "PUBLISHER")
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Column(name = "EDITION")
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	@Column(name = "ISBN")
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	@Column(name = "TEXTBOOK_TYPE")
	public String getTextbookType() {
		return textbookType;
	}
	public void setTextbookType(String textbookType) {
		this.textbookType = textbookType;
	}

}
