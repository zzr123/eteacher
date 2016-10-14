package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Major;
import com.turing.eteacher.model.School;

public interface ISchoolService extends IService<School>  {

	public List<School> getListByParentId(String parentId);

}
