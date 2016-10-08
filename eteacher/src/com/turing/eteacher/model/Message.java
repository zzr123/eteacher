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
@Table(name = "T_MESSAGE")
public class Message extends CreateTimeModel implements Serializable {

	private static final long serialVersionUID = -7834919944583938790L;

	private String msgId;
	private String fromUserId;
	private String toUserId;
	private String content;
	private Integer isHaveFile;
	private String parentMsgId;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "MSG_ID")
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	@Column(name = "FROM_USER_ID")
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	@Column(name = "TO_USER_ID")
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="IS_HAVE_FILE")
	public Integer getIsHaveFile() {
		return isHaveFile;
	}
	public void setIsHaveFile(Integer isHaveFile) {
		this.isHaveFile = isHaveFile;
	}
	@Column(name="PARENT_MSG_ID")
	public String getParentMsgId() {
		return parentMsgId;
	}
	public void setParentMsgId(String parentMsgId) {
		this.parentMsgId = parentMsgId;
	}
}
