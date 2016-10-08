package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Message;

public interface IMessageService extends IService<Message>{

	/**
	 * 教师端
	 */
	public List<Map> getMessageList(String userId);
}
