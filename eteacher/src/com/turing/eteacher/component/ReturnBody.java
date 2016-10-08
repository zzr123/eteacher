package com.turing.eteacher.component;

/**
 * remote响应对象
 * 
 * @author caojian
 * 
 */
public class ReturnBody {

	public static final String RESULT_SUCCESS = "success";
	public static final String RESULT_FAILURE = "failure";

	public static final String ERROR_MSG = "系统出现异常，请稍后再试。";

	public static final String NO_LOGIN = "未登陆系统，没有权限。";

	public static final String PARAMETER_ERROR = "参数错误！";

	private static ReturnBody paramErrorBody;
	
	private static ReturnBody systemErrorBody;
	
	public ReturnBody() {

	}

	public ReturnBody(String result, Object data) {
		this.result = result;
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
	 * @return
	 */
	public static ReturnBody getParamError() {
		if (null == paramErrorBody) {
			paramErrorBody = new ReturnBody(RESULT_FAILURE, PARAMETER_ERROR);
		}
		return paramErrorBody;
	}
	
	/**
	 * 获取一个系统错误的返回值
	 * 
	 * @return
	 */
	public static ReturnBody getSystemError(){
		if (null == systemErrorBody) {
			systemErrorBody= new ReturnBody(RESULT_FAILURE, ERROR_MSG);
		}
		return systemErrorBody;
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
