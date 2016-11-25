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
@Table(name="T_REGIST_CONFIG")
public class RegistConfig extends CreateTimeModel implements Serializable{
	private static final long serialVersionUID = -8247977880477828076L;
	
	private String configId;
	private int before;//课程开始前多长时间进行签到
	private int after;//课程开始后多长时间停止签到
	private int distance;//签到距离设置
	private int status;// 0:系统默认值    1：用户自定义设置
	private String userId;
	
	@Id
	@GeneratedValue(generator="customId")
	@GenericGenerator(name="customId", strategy="com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "CONFIG_ID")
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	@Column(name = "REGIST_BEFORE")
	public int getBefore() {
		return before;
	}
	public void setBefore(int before) {
		this.before = before;
	}
	@Column(name = "REGIST_AFTER")
	public int getAfter() {
		return after;
	}
	public void setAfter(int after) {
		this.after = after;
	}
	@Column(name = "REGIST_DISTANCE")
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Column(name = "STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "RegistConfig [configId=" + configId + ", before=" + before + ", after=" + after + ", distance="
				+ distance + ", status=" + status + "]";
	}
	
}
