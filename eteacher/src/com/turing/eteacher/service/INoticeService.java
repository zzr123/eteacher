package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Notice;

public interface INoticeService extends IService<Notice> {

	public List<Map> getListForTable(String userId, boolean ckb1, boolean ckb2);
	
	public void publishNotice(String noticeId);
	
	//获取已发布的通知列表
	public List<Map> getListNotice(String userId,String status,String date,int page);
	//通知状态的修改
	public void ChangeNoticeState(String noticeId,String status);
	//查看通知详情
	public Map getNoticeDetail(String noticeId);
	/**
	 * 查看通知已读读人员列表
	 * @author lifei
	 * @param noticeId
	 * @return
	 */
	public List<Map> getNoticeReadList(String noticeId,int type, int page);
	/**
	 * 学生端接口：获取通知列表（已读通知和未读通知）
	 * @author macong
	 * @param userId
	 * @param status
	 * @param parseInt
	 * @param termId
	 * @return
	 */
	public List<Map> getNoticeList_student(String userId, String status, int page);
	/**
	 * 学生端接口：查看通知详情
	 * @author macong
	 * @param noticeId
	 * @return
	 */
	public Map getNoticeDetail_student(String noticeId,int flag);
	/**
	 * 学生端接口：将未读通知置为已读状态
	 * @author macong
	 * @param noticeId
	 * @param userId
	 */
	public void addReadFlag(String noticeId, String userId);
}
