package com.turing.eteacher.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.WorkDAO;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.service.IWorkService;
import com.turing.eteacher.util.DateUtil;
import com.turing.eteacher.util.StringUtil;

@Service
public class WorkServiceImpl extends BaseService<Work> implements IWorkService {

	@Autowired
	private WorkDAO workDAO;
	
	@Override
	public BaseDAO<Work> getDAO() {
		return workDAO;
	}

	@Override
	public List<Map> getListForTable(String termId, String courseId) {
		List args = new ArrayList();
		String hql = "select w.workId as workId," +
				"w.courseId as courseId," +
				"w.content as content," +
				"w.publishType as publishType," +
				"c.courseName as courseName " +
				"from Work w,Course c " +
				"where w.courseId = c.courseId";
//		if(StringUtil.isNotEmpty(termId)){
			hql += " and c.termId = ?";
			args.add(termId);
//		}
		if(StringUtil.isNotEmpty(courseId)){
			hql += " and w.courseId = ?";
			args.add(courseId);
		}
		List<Map> list = workDAO.findMap(hql, args.toArray());
		return list;
	}



	@Override
	public List<Map> getListByStuId(String stuId, String status) {
		String hql = "select distinct w.workId as workId,c.courseName as courseName," +
				"w.content as content,w.publishTime as publishTime, w.timeLength as timeLength " +
				"from Work w,Course c,CourseClasses cc,Student s " +
				"where w.courseId = c.courseId " +
				"and w.courseId = cc.courseId " +
				"and cc.classId = s.classId " +
				"and s.stuId = ? " +
				"and w.publishTime <= ? and w.publishType <> ? ";//已发布的作业
		if(StringUtil.isNotEmpty(status)){
			if("0".equals(status)){
				hql += "and not exists ";
			}
			else if("1".equals(status)){
				hql += "and exists ";
			}
			hql += "(select ws.wsId from WorkStatus ws where ws.workId = w.workId and ws.stuId = s.stuId) ";
		}
		hql += "order by w.publishTime desc";
		List<Map> list = workDAO.findMap(hql, stuId, new Date(), EteacherConstants.WORK_STAUTS_DRAFT);
		if("0".equals(status)){
			for(Map record : list){
				Date publishTime = (Date)record.get("publishTime");
				int timeLength = (Integer)record.get("timeLength");
				int days = DateUtil.getDayBetween(new Date(), publishTime) + timeLength;
				record.put("days", days);
			}
		}
		return list;
	}

	/**
	 * 删除作业
	 */
	@Override
	public void deleteWork(String workId) {
		workDAO.deleteById(workId);
	}
	/**
	 * 教师相关接口
	 */
	//获取作业列表（已过期、未过期、待发布、指定截止日期）
	@Override
	public List<Map> getListWork(String userId,String status,String date) {
		String hql = "select distinct w.workId as workId,c.courseName as courseName," ;
		List<Map> list = null ;
		
		userId="Qsq73xbQDS";
		if("0".equals(status)){//已过期作业
			hql+="w.endTime as endTime,SUBSTRING(w.content,1,20) as content " +
				 "from Work w,Course c " +
				 "where w.courseId = c.courseId and w.status=1 " +
				 "and c.userId = ? and w.publishType=02" +
				 "and w.endTime < ? order by w.endTime desc";
			list= workDAO.findMap(hql,userId ,new Date());
		}
		if("1".equals(status)){//未过期作业（已发布但未到期）
			hql+="w.publishTime as publishTime,w.endTime as endTime,"+
				 "SUBSTRING(w.content,1,20) as content from Work w,Course c "+
	             "where w.courseId=c.courseId and w.status=1 "+
				 "and c.userId=? and w.publishType=02 "+
	             "and w.publishTime<? and w.endTime > ? "+
				 "order by w.publishTime desc";
			list= workDAO.findMap(hql,userId ,new Date() ,new Date());	
		}
		if("2".equals(status)){//获取待发布作业（草稿和待发布）
			hql+="w.publishTime as publishTime,SUBSTRING(w.content,1,20) as content,w.publishType as publishType "+
				 "from Work w,Course c where w.courseId=c.courseId and c.userId=? and w.status=1 "+
		         "and (w.publishType=01 or (w.publishType=02 and w.publishTime>now())) "+
			     "order by w.publishTime asc";
			list=workDAO.findMap(hql, userId);
		}
		if("3".equals(status)){//获取指定截止日期的作业
			hql+="w.content as content "+
			    "from Work w,Course c where w.courseId=c.courseId and w.status=1 "+
		        "and c.userId=? and  substring(w.endTime,1,10)=? "+
			    "and w.publishType=02 and w.publishTime<now()";
			list=workDAO.findMap(hql, userId, date);
		}
		return list;
	}
	//查看作业详情
	@Override
	public List<Map> getWorkDetail(String workId) {
		String hql="select distinct w.workId as workId,w.publishTime as publishTime,"+
	               "w.endTime as endTime,w.content as content,w.remindTime as remindTime,f.fileId as fileId,"+
				   "f.fileName as fileName,c.courseName as courseName,cl.className as className "+
	               "from Work w,Course c,Classes cl,CustomFile f,CourseClasses cc "+
				   "where w.workId=f.dataId and w.courseId=c.courseId and "+
	               "c.courseId=cc.courseId and cc.classId=cl.classId and w.workId=? ";
		List<Map> list = workDAO.findMap(hql, workId) ;
		return list;
	}
    //改变作业状态
	@Override
	public void updateWorkStatus(String workId,String status) {
		String hql="update Work w ";
		if("0".equals(status)){//已发布作业撤回到草稿状态
			hql+="set w.publishType='01'";
		}
		if("1".equals(status)){//（未发布作业->立即发布）
			hql+="set w.publishType='02',w.publishTime=now()";
		}
		if("2".equals(status)){//删除作业
			hql+="set w.status=0";
		}
		hql+=" where w.workId=?";
		workDAO.executeHql(hql, workId);
	}

}
