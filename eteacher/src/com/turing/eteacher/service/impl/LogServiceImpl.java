package com.turing.eteacher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.LogDAO;
import com.turing.eteacher.model.Log;
import com.turing.eteacher.service.ILogService;

@Service
public class LogServiceImpl extends BaseService<Log> implements ILogService  {
	
	@Autowired
	private LogDAO logDAO;

	@Override
	public BaseDAO<Log> getDAO() {
		return logDAO;
	}

	@Override
	public int getCountByTargetId(String id) {
		String hql = "select count(*) from Log c where c.noticeId = ? and c.type = 1";
		int count = ((Long)logDAO.getUniqueResult(hql, id)).intValue();
		return count;
	}


}
