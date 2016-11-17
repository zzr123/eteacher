package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CourseTable;

public interface ICourseTableService extends IService<CourseTable> {

	public List<CourseTable> getListByCourseId(String courseId);
	//通过userId获取
	public List<Map> getListByUserId(String userId,Map tpId);
	//通过班级Id获取
	public List<Map> getListByClassId(String classId,Map tpId);
	
	public Map getCourseTableDatas(String id,Map tpId, String type);
	//选择班级，获取教师当前学期的授课班级
	public List<Map> getClassList(String userId, Map tpId);
}
