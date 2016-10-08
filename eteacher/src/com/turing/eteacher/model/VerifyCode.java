package com.turing.eteacher.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 验证码
 * @author lifei
 *
 */
@Entity
@Table(name = "T_VERIFY_CODE")
public class VerifyCode implements Serializable {
	private String codeId;
	private String verifyCode;
	private String imei;
	private String time;
	private int type;

	
	@Id
	@GeneratedValue(generator = "customId")
	@GenericGenerator(name = "customId", strategy = "assigned")
	@Column(name = "CODE_ID")
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	@Column(name = "VERIFY_CODE")
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	@Column(name = "IMEI")
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	@Column(name = "TIME")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Column(name = "TYPE")
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
