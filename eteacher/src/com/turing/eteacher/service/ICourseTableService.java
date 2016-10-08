package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CourseTable;

public interface ICourseTableService extends IService<CourseTable> {

	public List<CourseTable> getListByCourseId(String courseId);
	
	public List<CourseTable> getListByUserId(String userId);
	
	public List<CourseTable> getListByClassId(String classId);
	
	public Map getCourseTableDatas(String id, String type);
}
