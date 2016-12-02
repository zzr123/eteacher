package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_TERM")
public class Term implements Serializable {

	private static final long serialVersionUID = -5182819582167560669L;
	
	private String termId;
	private String termName;
	private String startDate;
	private String endDate;
	private Integer weekCount;
	private String schoolId;
	
	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "com.turing.eteacher.util.CustomIdGenerator")
	@Column(name = "TERM_ID")
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	@Column(name = "TERM_NAME")
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	@Column(name="START_DATE")
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="END_DATE")
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="WEEK_COUNT")
	public Integer getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(Integer weekCount) {
		this.weekCount = weekCount;
	}
	@Column(name="SCHOOL_ID")
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
}
