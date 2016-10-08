package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Dictionary2Private;
import com.turing.eteacher.model.Test;

public interface IDictionary2PrivateService extends IService<Dictionary2Private> {
	public List<Test> getListByType(int type, String userId);
}
