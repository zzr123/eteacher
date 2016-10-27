package com.turing.eteacher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.NoticeDAO;
import com.turing.eteacher.model.Notice;
import com.turing.eteacher.service.INoticeService;
import com.turing.eteacher.util.StringUtil;

@Service
public class NoticeServiceImpl extends BaseService<Notice> implements INoticeService {

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Override
	public BaseDAO<Notice> getDAO() {
		return noticeDAO;
	}

	@Override
	public List<Map> getListForTable(String userId, boolean ckb1, boolean ckb2) {
		List params = new ArrayList();
		if(ckb1||ckb2){
			String hql = "select n.noticeId as noticeId,n.courseId as courseId,n.title as title,n.content as content,n.publishTime as publishTime," +
					"(case when n.publishTime > ? then '未发布' else '已发布' end) as status," +
					"(case when n.courseId = 'all' then '全部' else ((select c.courseName from Course c where c.courseId = n.courseId)||'') end) as noticeObject " +
					"from Notice n " +
					"where 1=1 ";
			params.add(new Date());
			if(StringUtil.isNotEmpty(userId)){
				hql += "and n.userId = ? ";
				params.add(userId);
			}
			if(ckb1&&ckb2){
				//none
			}
			else if(ckb1){
				hql += " and n.publishTime <= ?";
				params.add(new Date());
			}
			else{
				hql += " and n.publishTime > ?";
				params.add(new Date());
			}
			hql += " order by n.publishTime desc";
			List<Map> list = noticeDAO.findMap(hql, params);
			for(Map record : list){
				String courseId = (String)record.get("courseId");
				hql = "select c.className from Classes c,CourseClasses cc where c.classId = cc.classId and cc.courseId = ?";
				List<String> classNames = noticeDAO.find(hql, courseId);
				if(classNames!=null&&classNames.size()>0){
					record.put("noticeObject", record.get("noticeObject") + "(" + StringUtil.joinByList(classNames, "，") + ")");
				}
			}
			return list;
		}
		else{
			return new ArrayList();
		}
	}

	@Override
	public void publishNotice(String noticeId) {
		Notice notice = noticeDAO.get(noticeId);
		//notice.setPublishType(EteacherConstants.WORK_STAUTS_NOW);
		notice.setPublishTime(new Date());
		noticeDAO.update(notice);
	}
	/**
	 * 教师端接口
	 */
	//获取通知列表（已发布、待发布）
	@Override
	public List<Map> getListNotice(String userId,String status) {
		List<Map> list=null,list1=null;
		String hql="select n.noticeId as noticeId,n.title as titile,substring(n.publishTime,1,10) as publishTime,SUBSTRING(n.content,1,20) as content ";
		if("0".equals(status)){//待发布通知
			hql+="from Notice n where n.userId=? and n.publishTime>now() and n.status=0 order by n.publishTime asc";
			list=noticeDAO.findMap(hql, userId);
		}
		if("1".equals(status)){//已发布通知
			hql+=",c.studentNumber as allstudentNum from Notice n,Course c "+
		         "where n.courseId=c.courseId and n.userId=? and n.publishTime<now() and n.status=1 order by n.publishTime desc";
			list=noticeDAO.findMap(hql, userId);
			String hql1="select n.noticeId as noticeId,count(l.noticeId) as readstudentNum from Log l,Notice n where n.noticeId=l.noticeId and n.userId=? "+
			            "and n.status=1 group by n.noticeId";
			list1=noticeDAO.findMap(hql1, userId);
			for(int i=0;i<list.size();i++){
				boolean flag=false;
				String noticeId=(String) list.get(i).get("noticeId");
				for(int j=0;j<list1.size();j++){
					if(noticeId.equals(list1.get(j).get("noticeId"))){
						list.get(i).put("readstudentNum", list1.get(j).get("readstudentNum"));
						list1.remove(j);
						flag=true;
						break;
					}
				}
				if(flag==false){
					list.get(i).put("readstudentNum", 0);
				}
			}
		}
		
		return list;
	}
	//通知状态的修改
	@Override
	public void ChangeNoticeState(String noticeId,String status) {
		String hql = "update Notice n " ;
		if("0".equals(status)){//待发布通知->立即通知
			hql+="set n.publishTime=now() where n.noticeId=?";			
		}
		if("1".equals(status)){//删除通知，不可见
			hql+="set n.status=0 where n.noticeId=?";
		}
		noticeDAO.executeHql(hql, noticeId);
	}
	//查看通知详情
	@Override
	public List<Map> getNoticeDetail(String noticeId) {
		String hql="select n.noticeId as noticeId,n.title as title,n.content as content,substring(n.publishTime,1,10) as publishTime,"+
	               "f.fileId as fileId,f.fileName as fileName,c.courseName,cl.className from Notice n,CustomFile f,Course c,Classes cl,CourseClasses cc "+
				   "where n.noticeId=f.dataId and n.courseId=c.courseId and c.courseId=cc.courseId and cc.classId=cl.classId "+
	               "and n.noticeId=?";
		List<Map> list=noticeDAO.findMap(hql, noticeId);
		return list;
	}
	//查看通知未读人员列表
	@Override
	public List<Map> getNoticeLog(String noticeId) {
		String hql="select s.stuId as stuId,s.stuName as stuName from Student s where "+
	               "s.stuId not in(select l.stuId from Log l where l.targetId=?) and "+
				   "s.classId in (select cc.classId from Notice n,CourseClasses cc where n.noticeId=? and n.courseId=cc.courseId)";
		List<Map> list=noticeDAO.findMap(hql, noticeId,noticeId);
		return list;
	}

}
