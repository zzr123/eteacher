package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.Dictionary2PrivateDAO;
import com.turing.eteacher.dao.Dictionary2PublicDAO;
import com.turing.eteacher.model.Dictionary2Private;
import com.turing.eteacher.model.Dictionary2Public;
import com.turing.eteacher.service.IDictionary2PrivateService;
import com.turing.eteacher.service.IDictionary2PublicService;

@Service
public class Dictionary2PublicServiceImpl extends
		BaseService<Dictionary2Public> implements IDictionary2PublicService {

	@Autowired
	private Dictionary2PublicDAO dictionary2PubliceDAO;

	@Override
	public BaseDAO<Dictionary2Public> getDAO() {
		// TODO Auto-generated method stub
		return dictionary2PubliceDAO;
	}

	@Override
	public List<Dictionary2Public> getListByType(int type, String userId) {
		// TODO Auto-generated method stub
		return null;
	}


}
