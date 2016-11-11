package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CourseClasses;

public interface ICourseClassService extends IService<CourseClasses> {
	/**
	 * 通过CourseId删除对应的课程班级关联数据
	 */
	public boolean delByCourseId(String courseId);
}
