package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Major;

public interface IMajorService extends IService<Major> {

	public List<Major> getListByParentId(String parentId);
}
