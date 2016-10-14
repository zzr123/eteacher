package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.SchoolDAO;
import com.turing.eteacher.model.Major;
import com.turing.eteacher.model.School;
import com.turing.eteacher.service.ISchoolService;

@Service
public class SchoolServiceImpl extends BaseService<School> implements ISchoolService {
	
	@Autowired
	private SchoolDAO schoolDAO;
	@Override
	public BaseDAO<School> getDAO() {
		// TODO Auto-generated method stub
		return schoolDAO;
	}
	
	@Override
	public List<School> getListByParentId(String parentId) {
		List<School> list = null;
		String hql = "from School s where s.parentId = ? ";
		list = schoolDAO.find(hql, parentId);
		return list;
	}
	

}
