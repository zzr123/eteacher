package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.UserCommunication;

public interface IUserCommunicationService extends IService<UserCommunication> {
	/**
	 * 通过USerID获取其联系电话，电子邮箱，IM 的值
	 * @author macong
	 * 2016年10月19日13:37:15
	 */
	public List getComByUserId(String id,int type);
}
