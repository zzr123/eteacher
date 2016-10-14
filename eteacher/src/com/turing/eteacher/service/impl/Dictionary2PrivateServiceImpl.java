package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.Dictionary2PrivateDAO;
import com.turing.eteacher.model.Dictionary2Private;
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
	public List<Map> getListByType(int type, String userId) {
		String hql = "SELECT t_dictionary2_public.DICTIONARY_ID AS id,"+
					 "t_dictionary2_public.VALUE AS content "+
					 "FROM t_dictionary2_public "+
					 "WHERE t_dictionary2_public.TYPE = "+type+" "+ 
					 "AND "+
					 "t_dictionary2_public.PARENT_CODE =("+
					 " SELECT t_dictionary2_public.CODE "+
					 " FROM t_dictionary2_public "+
					 " WHERE t_dictionary2_public.TYPE = "+type+" "+ 
					 " AND t_dictionary2_public.PARENT_CODE IS NULL) "+
					 "AND t_dictionary2_public.STATUS = 1 "+
					 "AND t_dictionary2_public.DICTIONARY_ID NOT IN ( "+
					 "SELECT t_dictionary2_private.DICTIONARY_ID "+
					 "FROM t_dictionary2_private "+
					 "WHERE t_dictionary2_private.USER_ID = '"+userId+"' "+ 
					 "AND t_dictionary2_private.type = "+type+" "+
					 "AND t_dictionary2_private.PARENT_CODE = ("+
					 " SELECT t_dictionary2_public.CODE "+
					 " FROM t_dictionary2_public "+
					 " WHERE t_dictionary2_public.TYPE = "+type+" "+ 
					 "AND t_dictionary2_public.PARENT_CODE IS NULL) "+
					 " AND t_dictionary2_private.STATUS = 2 "+
					 ") "+
					 "UNION "+ 
					 "SELECT t_dictionary2_private.DP_ID AS id, "+
					 "t_dictionary2_private.VALUE AS content "+ 
					 "FROM t_dictionary2_private "+ 
					 "WHERE t_dictionary2_private.TYPE = "+type+" "+ 
					 "AND t_dictionary2_private.USER_ID = '"+userId+"' "+ 
					 "AND t_dictionary2_private.STATUS = 1 "+ 
					 "AND t_dictionary2_private.PARENT_CODE =( "+
					 "SELECT t_dictionary2_public.CODE "+ 
					 "FROM t_dictionary2_public "+ 
					 "WHERE t_dictionary2_public.TYPE = "+type+" "+ 
					 "AND t_dictionary2_public.PARENT_CODE IS NULL "+
					 ")";
		List<Map> list = dictionary2PrivateDAO.findBySql(hql);
		return list;
	}

	@Override
	public boolean deleteItem(int type, String userId, String dId) {
		String hql = "from Dictionary2Private dp where dp.type = "+type+" and dp.dpId = '"+dId+"'";
		String sql2 = "";
		if (dictionary2PrivateDAO.find(hql).size() > 0) {
			sql2 = "DELETE FROM t_dictionary2_private WHERE t_dictionary2_private.DP_ID = '"+dId+"'";
		}else{
			sql2 = " INSERT INTO t_dictionary2_private (t_dictionary2_private.TYPE,t_dictionary2_private.PARENT_CODE,t_dictionary2_private.DICTIONARY_ID,t_dictionary2_private.USER_ID ) "+
					"SELECT t_dictionary2_public.TYPE,t_dictionary2_public.PARENT_CODE,t_dictionary2_public.DICTIONARY_ID,'"+userId+"' "+ 
					"FROM t_dictionary2_public WHERE "+
					"t_dictionary2_public.DICTIONARY_ID = '"+dId+"'" ;
		}
		int result = dictionary2PrivateDAO.executeBySql(sql2);
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addItem(int type, String userId) {
		String sql = "";
		return false;
	}
}
