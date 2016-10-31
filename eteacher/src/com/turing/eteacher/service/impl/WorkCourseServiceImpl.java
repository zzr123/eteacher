package com.turing.eteacher.service.impl;

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
	
}
