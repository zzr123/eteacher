package com.turing.eteacher.remote.model;
/**
 * 登录返回参数
 * @author lifei
 *
 */
public class LoginReturn {
	private String userId;
	private String token;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
