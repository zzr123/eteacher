package com.turing.eteacher.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.turing.eteacher.base.CreateTimeModel;

@Entity
@Table(name = "T_WORK")
public class Work implements Serializable {

	private static final long serialVersionUID = 8523575661624181680L;
	
	private String workId;
	private String courseId;
	private String content;
	private Date publishTime;
	private Date endTime;
	private Date createTime;
	private String remindTime;
	private Integer status;  //0：发布/待发布作业； 1：草稿
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "WORK_ID")
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	};
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "PUBLISH_TIME")
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name="REMIND_TIME")
	public String getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}
	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
