package com.turing.eteacher.component;

import freemarker.cache.StringTemplateLoader;

/**
 * remote响应对象
 * 
 * @author caojian
 * 
 */
public class ReturnBody {
	//登陆成功
	public static final String RESULT_SUCCESS = 		"200";
	//请求超时
	public static final String RESULT_TIMEOUT = 		"900";
	//非法请求
	public static final String RESULT_ILLEGAL_ACCESS = 	"406";
	//用户不存在
	public static final String RESULT_USER_NOT_EXIST = 	"401";
	//请求失败
	public static final String RESULT_FAILURE = 		"400";
	//token过期
	public static final String RESULT_TOKEN_TIMEOUT = 	"201";
	//身份信息待完善
	public static final String RESULT_IMPERFECT_INFORMATION = 	"402";

	public static final String ERROR_MSG = "系统出现异常，请稍后再试。";

	public static final String NO_LOGIN = "未登陆系统，没有权限。";

	public static final String PARAMETER_ERROR = "参数错误！";

	private static ReturnBody paramErrorBody;

	private static ReturnBody systemErrorBody;

	private static ReturnBody noLoginErrorBody;

	public ReturnBody() {

	}

	public ReturnBody(String result, Object data) {
		this.result = result;
		this.data = data;
	}
	
	public ReturnBody(Object data) {
		this.result = RESULT_SUCCESS;
		this.data = data;
	}

	public ReturnBody(String result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public ReturnBody(String result, String data, String msg) {
		this.result = result;
		this.data = data;
		this.msg = msg;
	}

	/**
	 * 获取一个参数错误的返回值
	 * 
	 * @author lifei
	 * @return
	 */
	public static ReturnBody getParamError() {
		if (null == paramErrorBody) {
			paramErrorBody = new ReturnBody(RESULT_FAILURE, PARAMETER_ERROR);
		}
		return paramErrorBody;
	}
	
	/**
	 * 返回一个带有错误信息的
	 * @param msg
	 * @return
	 */
	public static ReturnBody getErrorBody(String msg){
		return new ReturnBody(RESULT_FAILURE, msg);
	}

	/**
	 * 获取一个系统错误的返回值
	 * 
	 * @author lifei
	 * @return
	 */
	public static ReturnBody getSystemError() {
		if (null == systemErrorBody) {
			systemErrorBody = new ReturnBody(RESULT_FAILURE, ERROR_MSG);
		}
		return systemErrorBody;
	}

	/**
	 * 获取一个未登录的返回值
	 * 
	 * @author lifei
	 * @return
	 */
	public static ReturnBody getNoLoginError() {
		if (null == noLoginErrorBody) {
			noLoginErrorBody = new ReturnBody(RESULT_FAILURE, NO_LOGIN);
		}
		return noLoginErrorBody;
	}
	/**
	 * 返回一个用户信息不完善的错误
	 * @return
	 */
	public static ReturnBody getUserInfoError(){
		return new ReturnBody(RESULT_IMPERFECT_INFORMATION, "请您先完善个人信息！");
	}

	private String result;
	private String msg;
	private Object data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
