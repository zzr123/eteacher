package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.TermPrivateDAO;
import com.turing.eteacher.model.TermPrivate;
import com.turing.eteacher.service.ITermPrivateService;

public class TermPrivateServiceImpl extends BaseService<TermPrivate> implements ITermPrivateService{
	
	@Autowired
	private TermPrivateDAO termPrivateDAO;
	
	@Override
	public BaseDAO<TermPrivate> getDAO() {
		// TODO Auto-generated method stub
		return termPrivateDAO;
	}

	@Override
	public List<TermPrivate> getListTermPrivatesName(String userId) {
		String hql = "from Term t where t.termId not in " +
				"(select tp.termId from TermPrivate tp where tp.userId = ? and tp.status = 2)";
		//String sql = "SELECT t_term_private.TP_ID  AS id,"
		//"t_term.TERM_NAME AS content FROM t_term_private LEFT JOIN t_term ON t_term_private.TREM_ID =  t_term.TERM_ID"
				 //"WHERE t_term_private.USER_ID = 'GevJsmPZP9'"
		List list = termPrivateDAO.find(hql, userId);
		return list;
	}

}
