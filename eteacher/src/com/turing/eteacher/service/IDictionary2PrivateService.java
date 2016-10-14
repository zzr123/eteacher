package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Dictionary2Private;

public interface IDictionary2PrivateService extends
		IService<Dictionary2Private> {
	/**
	 * 获取字典列表
	 * 
	 * @author lifei
	 * @param type
	 * @param userId
	 * @return
	 */
	public List<Map> getListByType(int type, String userId);

	/**
	 * 删除字典表的某一项
	 * 
	 * @author lifei
	 * @param type
	 * @param userId
	 * @param dId
	 *            删除项的ID
	 * @return
	 */
	public boolean deleteItem(int type, String userId, String dId);

	/**
	 * 添加一条数据
	 * 
	 * @author lifei
	 * @param type
	 * @param userId
	 * @return
	 */
	public boolean addItem(int type, String value, String userId);
}
