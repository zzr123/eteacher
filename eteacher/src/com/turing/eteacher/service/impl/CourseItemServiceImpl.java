package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.AppDAO;
import com.turing.eteacher.dao.CourseItemDAO;
import com.turing.eteacher.model.App;
import com.turing.eteacher.model.CourseItem;
import com.turing.eteacher.service.IAppService;
import com.turing.eteacher.service.ICourseItemService;

@Service
public class CourseItemServiceImpl extends BaseService<CourseItem> implements ICourseItemService  {
	
	@Autowired
	private CourseItemDAO courseItemDAO;

	@Override
	public BaseDAO<CourseItem> getDAO() {
		return courseItemDAO;
	}


}
