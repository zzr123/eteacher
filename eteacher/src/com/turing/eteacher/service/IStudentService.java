package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Student;

public interface IStudentService extends IService<Student> {

	public List<Map> getListForTable(String courseId);
	
	public Student getByStuNo(String stuNo);
	
	public Map getStudentSchoolInfo(String stuId);
	
	public Student getById(String id);
	/**
	 * @author zjx
	 * 获取学生用户的个人信息
	 * @param
	 * @return
	 * */
	public Map getUserInfo(String userId,String url);
}
