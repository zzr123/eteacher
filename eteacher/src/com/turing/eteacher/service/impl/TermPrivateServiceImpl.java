package com.turing.eteacher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.TermPrivateDAO;
import com.turing.eteacher.model.TermPrivate;
import com.turing.eteacher.service.ITermPrivateService;

public class TermPrivateServiceImpl extends BaseService<TermPrivate> implements ITermPrivateService{
	
	@Autowired
	private TermPrivateDAO termPrivateDAO;
	
	@Override
	public BaseDAO<TermPrivate> getDAO() {
		// TODO Auto-generated method stub
		return termPrivateDAO;
	}

}
