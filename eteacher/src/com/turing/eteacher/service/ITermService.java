package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.model.TermPrivate;

public interface ITermService extends IService<TermPrivate> {
	
	public void saveTerm(Term term);


	
	
	
	public List<Map> getListByGrade(int grade);
	
	public Term getByYearAndTerm(int year, int term);
	
	//教师端
	//获取学期公有数据列表
	public List<Term> getListTerms();
	// 获取学期私有数据列表
	public List<Map> getListTermPrivates(String userId);
	//获取当前学期
	public TermPrivate getCurrentTerm(String userId);
	//获取最新的一个学期
	public List<TermPrivate> getTermList(String userId);
}
