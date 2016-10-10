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
@Table(name="T_LOG")
public class Log extends CreateTimeModel implements Serializable{
	private static final long serialVersionUID = -8247977880477828076L;
	
	private String logId;
	private String stuId;
	private String noticeId;
	private int type;//日志类型。 01：通知查看日志
	
	@Id
	@GeneratedValue(generator="customId")
	@GenericGenerator(name="customId", strategy="com.turing.eteacher.util.CustomIdGenerator")
	@Column(name="LOG_ID")
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	@Column(name="STU_ID")
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	@Column(name="NOTICE_ID")
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	@Column(name="TYPE")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
