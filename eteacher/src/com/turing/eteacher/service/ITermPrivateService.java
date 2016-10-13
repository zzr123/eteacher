package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.TermPrivate;

public interface ITermPrivateService  extends IService<TermPrivate>{
	public List<TermPrivate> getListTermPrivatesName(String userId);
}
