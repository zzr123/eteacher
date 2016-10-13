package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Dictionary2Private;

public interface IDictionary2PrivateService extends IService<Dictionary2Private> {
	public List<Map> getListByType(int type, String userId);
}
