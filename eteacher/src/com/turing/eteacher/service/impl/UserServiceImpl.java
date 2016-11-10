package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.UserDAO;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IUserService;
import com.turing.eteacher.util.Encryption;

@Service
public class UserServiceImpl extends BaseService<User> implements IUserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public BaseDAO<User> getDAO() {
		return userDAO;
	}

	@Override
	public User getUserByAcctAndPwd(String account, String password) {
		String hql = "from User where account = ? and password = ?";
		List<User> list = userDAO.find(hql, account, Encryption.encryption(password));
		if (list != null && list.size() > 0) {
			System.out.println(list.get(0));
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getUserByAcct(String account) {
		String hql = "from User where account = ?";
		List<User> list = userDAO.find(hql, account);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getUserById(String id) {
		String hql = "from User user where user.userId = ?";
		List<User> list = userDAO.find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
