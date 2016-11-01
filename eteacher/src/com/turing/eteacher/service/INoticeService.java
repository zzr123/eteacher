package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Notice;

public interface INoticeService extends IService<Notice> {

	public List<Map> getListForTable(String userId, boolean ckb1, boolean ckb2);
	
	public void publishNotice(String noticeId);
	
	//获取已发布的通知列表
	public List<Map> getListNotice(String userId,String status,String date,int page);
	//通知状态的修改
	public void ChangeNoticeState(String noticeId,String status);
	//查看通知详情
	public List<Map> getNoticeDetail(String noticeId);
	//查看通知未读人员列表
	public List<Map> getNoticeLog(String noticeId);
}
