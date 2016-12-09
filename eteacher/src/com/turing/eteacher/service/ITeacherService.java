package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Teacher;

public interface ITeacherService extends IService<Teacher> {

	public List<Map> getWorkloadData(String userId, String year);
	
	/**
	 * 获取教师的课程列表
	 * @param userId
	 * @return
	 */
	public List<Map> getCourseList(String userId, int page);
	/**
	 * @author macong
	 * 获取教师的个人信息
	 * @param userId
	 * @return
	 */
	public Map getUserInfo(String userId);
	/**
	 * @author zjx
	 * 学生端获取教师的个人信息
	 * @param teacherId
	 * @return
	 */
//	public Map getTeacherInfo(String teacherId);

}
