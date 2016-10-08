package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.User;

public interface IUserService extends IService<User> {
	/**
	 * 通过用户名获取用户
	 * @param account
	 * @return
	 */
	public User getUserByAcct(String account);
	/**
	 * 通过用户名密码获取用户
	 * @param account
	 * @param password
	 * @return
	 */
	public User getUserByAcctAndPwd(String account, String password);
	
	/**
	 * 通过用户id获取用户
	 * @author lifei
	 * @param id
	 * @return
	 */
	public User getUserById(String id);
}
