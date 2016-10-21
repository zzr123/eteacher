package com.turing.eteacher.service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.UserCommunicationDAO;
import com.turing.eteacher.model.UserCommunication;
import com.turing.eteacher.service.IUserCommunicationService;

@Service
public class UserCommunicationServiceImpl extends BaseService<UserCommunication> implements IUserCommunicationService {
	@Autowired
	private UserCommunicationDAO userCommunicationDAO;

	@Override
	public BaseDAO<UserCommunication> getDAO() {
		// TODO Auto-generated method stub
		return userCommunicationDAO;
	}

	@Override
	public List getConByUserId(String id,int type) {
		// TODO Auto-generated method stub
		String hql = "select uc.cId as id , uc.name as name , uc.value as value from UserCommunication uc where uc.userId = ? and uc.type = ?";
		List<Map> map = userCommunicationDAO.findMap(hql, id ,type);
		/*for(int i=0;i<map.size();i++){
			String name = map.get(i).get("name").toString();
			System.out.println(name);
		}*/
		return map;
	}
	


}
