package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Dictionary2Public;

public interface IDictionary2PublicService extends IService<Dictionary2Public> {
	public List<Map> getListByType(String type, String userId);
}
