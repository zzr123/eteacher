package com.turing.eteacher.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.Dictionary2PrivateDAO;
import com.turing.eteacher.dao.Dictionary2PublicDAO;
import com.turing.eteacher.model.Dictionary2Private;
import com.turing.eteacher.model.Dictionary2Public;
import com.turing.eteacher.service.IDictionary2PrivateService;
import com.turing.eteacher.service.IDictionary2PublicService;

@Service
public class Dictionary2PublicServiceImpl extends
		BaseService<Dictionary2Public> implements IDictionary2PublicService {

	@Autowired
	private Dictionary2PublicDAO dictionary2PubliceDAO;

	@Override
	public BaseDAO<Dictionary2Public> getDAO() {
		// TODO Auto-generated method stub
		return dictionary2PubliceDAO;
	}
	
	/**
	 * @author macong
	 * 功能：获取某一类型的字典表列表项
	 * 2016年10月11日 13:07:20
	 * 
	 */
	@Override
	public List<Map> getListByType(String type, String userId) {
		// TODO Auto-generated method stub
		String hql = "select dp.dictionaryId as id,"
				+ "dp.value as value, dp.type as type from Dictionary2Public dp where dp.type = "+type+" "
				+ "and dp.parentCode!=null and dp.dictionaryId not in ("
				+ "select dpri.dictionaryId from Dictionary2Private dpri where dpri.userId = '"+userId+"' "
				+ "and dpri.parentCode!=null and dpri.status = 2 and dpri.type = "+type+")";//公共表中存在且未被用户删除的项。
		
		String hql2 = "select d.dpId as id, d.value as value, d.type as type from Dictionary2Private d where"
				+ " d.userId = '"+userId+"' "
				+ "and d.type = "+type+" "
				+ "and d.status = 1"
				+ " and d.parentCode=(select dp.code from Dictionary2Private dp where dp.type = "+type+" and dp.parentCode is null)";//用户自定义的项。
		
		List<Map> map = dictionary2PubliceDAO.findMap(hql);
		List<Map> map2 = dictionary2PubliceDAO.findMap(hql2);
		map.addAll(map2);
		/*for(int i = 0;i< map2.size();i++){
			System.out.println("map"+i+":"+map2.get(i).toString());
		}*/
		return map;
	}

	
}
