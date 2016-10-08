package com.turing.eteacher.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_COURSE_WORKLOAD")
public class CourseWorkload implements Serializable {
	
	private static final long serialVersionUID = 2060765635914289632L;
	
	private String cwId;
	private String courseId;
	private String workloadName;
	private BigDecimal workloadPercent;
	private Integer cwOrder;
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "CW_ID")
	public String getCwId() {
		return cwId;
	}
	public void setCwId(String cwId) {
		this.cwId = cwId;
	}
	@Column(name = "COURSE_ID")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Column(name = "WORKLOAD_NAME")
	public String getWorkloadName() {
		return workloadName;
	}
	public void setWorkloadName(String workloadName) {
		this.workloadName = workloadName;
	}
	@Column(name = "WORKLOAD_PERCENT")
	public BigDecimal getWorkloadPercent() {
		return workloadPercent;
	}
	public void setWorkloadPercent(BigDecimal workloadPercent) {
		this.workloadPercent = workloadPercent;
	}
	@Column(name = "CW_ORDER")
	public Integer getCwOrder() {
		return cwOrder;
	}
	public void setCwOrder(Integer cwOrder) {
		this.cwOrder = cwOrder;
	}
	
}
