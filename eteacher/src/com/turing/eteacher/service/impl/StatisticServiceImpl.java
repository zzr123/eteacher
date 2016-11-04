package com.turing.eteacher.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.StatisticDAO;
import com.turing.eteacher.model.Statistic;
import com.turing.eteacher.service.IStatisticService;

@Service
public class StatisticServiceImpl extends BaseService<Statistic> implements IStatisticService  {
	
	@Autowired
	private StatisticDAO statisticDAO;
	
	@Override
	public BaseDAO<Statistic> getDAO() {
		return statisticDAO;
	}

	@Override
	public int getCountByTargetId(String id) {
		String hql = "select count(*) from Statistic c where c.targetId = ?";
		int count = ((Long)statisticDAO.getUniqueResult(hql, id)).intValue();
		return count;
	}


}
