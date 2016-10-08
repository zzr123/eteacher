package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.Dictionary2PrivateDAO;
import com.turing.eteacher.model.Dictionary2Private;
import com.turing.eteacher.model.Test;
import com.turing.eteacher.service.IDictionary2PrivateService;

@Service
public class Dictionary2PrivateServiceImpl extends
		BaseService<Dictionary2Private> implements IDictionary2PrivateService {

	@Autowired
	private Dictionary2PrivateDAO dictionary2PrivateDAO;

	@Override
	public BaseDAO<Dictionary2Private> getDAO() {
		// TODO Auto-generated method stub
		return dictionary2PrivateDAO;
	}

	@Override
	public List<Test> getListByType(int type1, String userId) {
		String type = "01";
		userId = "CYXvQXi5Gv";
		String hql = "select public1.dictionaryId , public1.value from Dictionary2Public public1 where public1.status = '01' and public1.type = '"+type+"' and public1.parentCode is not null and "+
"public1.schoolId = (select teacher.schoolId from Teacher teacher where teacher.teacherId = '"+userId+"') and not exists "+
"(from Dictionary2Private private1 where private1.status = '02' and private1.userId = '"+userId+"' and private1.type = '"+type+"' and private1.parentCode is null) ";
		String hql2= "select private2.dpId,private2.value from Dictionary2Private private2 where private2.status = '01' and private2.userId = '"+userId+"' and private2.type = '"+type+"' and private2.parentCode is not null";
		List<Test> list1 = dictionary2PrivateDAO.find(hql);
		List<Test> list2 = dictionary2PrivateDAO.find(hql2);
		list1.addAll(list2);
		return list1;
	}
	

}
