package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.MessageDAO;
import com.turing.eteacher.dao.WorkDAO;
import com.turing.eteacher.model.Message;
import com.turing.eteacher.service.IMessageService;

@Service
public class MessageServiceImpl extends BaseService<Message> implements IMessageService{

	@Autowired
	private MessageDAO messageDAO;
	
	@Override
	public BaseDAO<Message> getDAO() {
		return messageDAO;
	}
	
	/**
	 * 教师端
	 */
	//获取消息列表
	@Override
	public List<Map> getMessageList(String userId) {
		String hql="select m.msgId as msgId,m.content as content,m.createTime as createTime "+
	               "from Message m where m.toUserId=?";
		List<Map> list=messageDAO.findMap(hql, userId);
		return list;
	}

}
