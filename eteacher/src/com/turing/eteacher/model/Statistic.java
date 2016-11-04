package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.turing.eteacher.base.BaseModel;

/**
 * 数据统计表
 * @author lifei
 * 
 */

@Entity
@Table(name = "T_STATISTIC")
public class Statistic extends BaseModel implements Serializable {

	private String statisticId;
	private String targetId;
	private String userId;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "STATISTIC_ID")
	public String getStatisticId() {
		return statisticId;
	}
	public void setStatisticId(String statisticId) {
		this.statisticId = statisticId;
	}
	@Column(name = "TARGET_ID")
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
