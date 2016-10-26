package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.VerifyCodeDAO;
import com.turing.eteacher.model.VerifyCode;
import com.turing.eteacher.service.IVerifyCodeService;

@Service
public class VerifyCodeServiceImpl extends BaseService<VerifyCode> implements
		IVerifyCodeService {

	@Autowired
	VerifyCodeDAO verifyCodeDAO;

	@Override
	public BaseDAO<VerifyCode> getDAO() {
		return verifyCodeDAO;
	}

	@Override
	public VerifyCode getVerifyByMobile(String mobile, int type) {
		String hql = "from VerifyCode where codeId = ? and type = ?";
		List<VerifyCode> list = verifyCodeDAO.find(hql, mobile, type);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
