package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.WorkCourse;

public interface IWorkCourseService extends IService<WorkCourse> {
	public void deleteData(String wId);
	
}
