package com.turing.eteacher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.CustomFileDAO;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.service.ICustomFileService;

@Service
public class CustomFileServiceImpl extends BaseService<CustomFile> implements ICustomFileService  {
	
	@Autowired
	private CustomFileDAO customFileDAO;

	@Override
	public BaseDAO<CustomFile> getDAO() {
		return customFileDAO;
	}


}
