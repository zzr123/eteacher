package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.AppDAO;
import com.turing.eteacher.model.App;
import com.turing.eteacher.service.IAppService;

@Service
public class AppServiceImpl extends BaseService<App> implements IAppService  {
	
	@Autowired
	private AppDAO appDAO;
	
	@Override
	public App getAppByKey(String key) {
		String hql = "from App app where app.appKey = ?";
		List<App> list = appDAO.find(hql, key);
		if (list != null && list.size() > 0) {
			System.out.println(list.get(0));
			return list.get(0);
		}
		return null;
	}

	@Override
	public BaseDAO<App> getDAO() {
		return appDAO;
	}


}
