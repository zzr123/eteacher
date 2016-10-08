package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Dictionary2Private;
import com.turing.eteacher.model.Dictionary2Public;

public interface IDictionary2PublicService extends IService<Dictionary2Public> {
	public List<Dictionary2Public> getListByType(int type, String userId);
}
