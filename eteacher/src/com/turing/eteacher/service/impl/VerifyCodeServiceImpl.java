package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.VerifyCodeDAO;
import com.turing.eteacher.model.User;
import com.turing.eteacher.model.VerifyCode;
import com.turing.eteacher.service.IVerifyCodeService;
import com.turing.eteacher.util.Encryption;

public class VerifyCodeServiceImpl extends BaseService<VerifyCode> implements IVerifyCodeService  {
	
	@Autowired
	VerifyCodeDAO verifyCodeDAO;
	
	@Override
	public BaseDAO<VerifyCode> getDAO() {
		return verifyCodeDAO;
	}

	@Override
	public VerifyCode getVerifyByMobile(String mobile) {
		String hql = "from VerifyCode where codeId = ?";
		List<VerifyCode> list = verifyCodeDAO.find(hql, mobile);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
