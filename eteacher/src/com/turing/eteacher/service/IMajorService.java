package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Major;

public interface IMajorService extends IService<Major> {

	public List<Major> getListByParentId(String parentId);
	
	/**
	 * 获取学科专业json数据格式（工具接口）
	 * @author lifei
	 * @return
	 */
	public List<Map> getMajorTree();
}
