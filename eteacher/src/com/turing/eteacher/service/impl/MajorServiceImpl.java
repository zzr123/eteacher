package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.MajorDAO;
import com.turing.eteacher.model.Major;
import com.turing.eteacher.service.IMajorService;

@Service
public class MajorServiceImpl extends BaseService<Major> implements IMajorService {

	@Autowired
	private MajorDAO majorDAO;
	
	@Override
	public BaseDAO<Major> getDAO() {
		return majorDAO;
	}

	@Override
	public List<Major> getListByParentId(String parentId) {
		List<Major> list = null;
		String hql = "from Major where parentId = ?";
		list = majorDAO.find(hql, parentId);
		return list;
	}
	
	

}
