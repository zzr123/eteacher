package com.turing.eteacher.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.model.WorkCourse;
import com.turing.eteacher.service.IWorkCourseService;

@Service
public class WorkCourseServiceImpl extends BaseService<WorkCourse> implements IWorkCourseService {
	
	@Autowired
	private BaseDAO<WorkCourse> workCourseDAO; 
	
	@Override
	public BaseDAO<WorkCourse> getDAO() {
		// TODO Auto-generated method stub
		return workCourseDAO;
	}

	@Override
	public void deleteData(String wId){
		// TODO Auto-generated method stub
		String hql = "delete from WorkCourse wc where wc.workId = ?";
		workCourseDAO.executeHql(hql, wId);
	}

	@Override
	public int getStudentCountByWId(String wId) {
		String sql = "SELECT COUNT(*) FROM t_student WHERE t_student.CLASS_ID IN "+
				 "(SELECT t_course_class.CLASS_ID FROM t_course_class WHERE t_course_class.COURSE_ID IN ( "+
			 	 "SELECT t_work_course.COURSE_ID FROM t_work_course WHERE t_work_course.WORK_ID = ?))";
		return ((BigInteger)workCourseDAO.getUniqueResultBySql(sql, wId)).intValue();
	}

	@Override
	public List<Map> getCoursesByWId(String wId) {
		String sql = "SELECT t_course.COURSE_ID AS courseId, "+
				 "t_course.COURSE_NAME AS courseName FROM "+
			 	 "t_work_course LEFT JOIN t_course ON t_work_course.COURSE_ID = t_course.COURSE_ID "+ 
			 	 "WHERE t_work_course.WORK_ID = ?";
		List<Map> list = workCourseDAO.findBySql(sql, wId);
		return list;
	}
	
}
