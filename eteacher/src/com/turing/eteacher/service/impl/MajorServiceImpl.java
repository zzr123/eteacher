package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.MajorDAO;
import com.turing.eteacher.model.Major;
import com.turing.eteacher.service.IMajorService;

@Service
public class MajorServiceImpl extends BaseService<Major> implements IMajorService {

	@Autowired
	private MajorDAO majorDAO;
	
	@Override
	public BaseDAO<Major> getDAO() {
		return majorDAO;
	}

	@Override
	public List<Major> getListByParentId(String parentId) {
		List<Major> list = null;
		String hql = "from Major where parentId = ?";
		list = majorDAO.find(hql, parentId);
		return list;
	}
	
	@Override
	public List<Map> getMajorTree() {
		String sql = "SELECT tm.MAJOR_ID AS id, tm.MAJOR_NAME AS name FROM t_major tm WHERE tm.PARENT_ID = ?";
		List<Map> list = majorDAO.findBySql(sql,"0");
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				List<Map> list1 = majorDAO.findBySql(sql, list.get(i).get("id"));
				if(null != list1 && list1.size() > 0){
					for (int j = 0; j < list1.size(); j++) {
						List<Map> list2 = majorDAO.findBySql(sql, list1.get(j).get("id"));
						if (null != list2 && list2.size() > 0) {
							list1.get(j).put("sub", list2);
						}
					}
				}
				list.get(i).put("sub", list1);
			}
		}
		System.out.println("list:"+list.toString());
		return null;
	}
	
	

}
