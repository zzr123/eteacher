package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Teacher;

public interface ITeacherService extends IService<Teacher> {

	public List<Map> getWorkloadData(String userId, String year);

	public Map getTeacherDetail(String userId);

}
