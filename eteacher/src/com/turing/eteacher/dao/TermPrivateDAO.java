package com.turing.eteacher.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.model.TermPrivate;
@Repository
public class TermPrivateDAO extends BaseDAO<TermPrivate> {
	public List<Map> getListTermPrivates(String hql){
		return this.findMap(hql);

	}
}
