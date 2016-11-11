package com.turing.eteacher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.CourseClassesDAO;
import com.turing.eteacher.model.CourseClasses;
import com.turing.eteacher.service.ICourseClassService;

@Service
public class CourseClassServiceImpl extends BaseService<CourseClasses> implements ICourseClassService {

	@Autowired
	private CourseClassesDAO courseClassesDao;
	
	@Override
	public BaseDAO<CourseClasses> getDAO() {
		return courseClassesDao;
	}

	@Override
	public boolean delByCourseId(String courseId) {
		String sql = "DELETE FROM t_course_class t WHERE t.COURSE_ID = ?";
		courseClassesDao.executeBySql(sql, courseId);
		return true;
	}

}
