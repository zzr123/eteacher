package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.VerifyCode;

public interface IVerifyCodeService extends IService<VerifyCode>{
	
	public VerifyCode getVerifyByMobile(String mobile,int type);
	
}
