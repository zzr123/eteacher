package com.turing.eteacher.model;
/**
 * 任务对象
 * @author lifei
 *
 */
public class TaskModel {
	public static final int TYPE_COURSE_START_REMIND = 0;
	public static final int TYPE_NOTICE = 1;
	public static final int TYPE_SIGN_IN = 2;
	public static final int TYPE_HOMEWORK_PUBLISH = 3;
	public static final int UTYPE_STUDENT = 0;
	public static final int UTYPE_TEACHER = 1;
	public static final int UTYPE_ALL = 2;
	private int type;
	private String id;
	private String date;
	private int userType;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "TaskModel [type=" + type + ", id=" + id + ", date=" + date
				+ "]";
	}
	
}
