package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Work;

public interface IWorkService extends IService<Work> {

	public List<Map> getListForTable(String termId, String courseId);
	//学生端获取作业列表
	public List<Map> getListByStuId(String stuId, String status,int page);
	//学生端查看作业详情
	public Map getSWorkDetail(String workId);
	
	/**
	 * 教师相关接口
	 */
	//获取作业列表（已过期、未过期、待发布、指定截止日期）
	public List<Map> getListWork(String userId, String state, String date,int page);
	//查看作业详情
	public Map getWorkDetail(String workId);
	//更改作业状态
	public void updateWorkStatus(String workId,String status);
	//删除作业
	public void deleteWork(String workId);
}
