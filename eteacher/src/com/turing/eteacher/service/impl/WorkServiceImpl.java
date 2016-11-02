package com.turing.eteacher.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.WorkCourseDAO;
import com.turing.eteacher.dao.WorkDAO;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.model.WorkCourse;
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
	/**
	 * 学生端获取作业列表
	 * 
	 */
	@Override
	public List<Map> getListByStuId(String stuId, String status,int page) {
//		String hql = "select distinct w.workId as workId,c.courseName as courseName," ;
		String hql ="";
		List<Map> list = null ;
		if("0".equals(status)){//未完成作业
			hql="select distinct w.WORK_ID as workId,c.COURSE_NAME as courseName, " +
				"w.CONTENT as content,w.PUBLISH_TIME as publishTime, w.END_TIME as endTime " +
				"from t_work w,t_course c,t_work_course wc,t_course_class cc,t_student s " +
				"where w.WORK_ID = wc.WORK_ID and wc.COURSE_ID = c.COURSE_ID " +
				"and c.COURSE_ID =cc.COURSE_ID and cc.CLASS_ID = s.CLASS_ID " +
				"and w.STATUS = 1 and w.PUBLISH_TIME <= now() " +
				"and s.STU_ID = ?  " +
				"and w.WORK_ID not in " +
				"(select w.WORK_ID from t_status ss,t_work w,t_student s where ss.WORK_ID = w.WORK_ID and ss.STU_ID = s.STU_ID) " +
				"order by w.PUBLISH_TIME desc";
			list = workDAO.findBySqlAndPage(hql,page*20, 20, stuId);
			System.out.println("list.size():"+list.size());
		}
		if("1".equals(status)){//已完成作业
			hql="select distinct w.WORK_ID as workId,c.COURSE_NAME as courseName, " +
				"w.CONTENT as content,w.PUBLISH_TIME as publishTime, w.END_TIME as endTime " +
				"from t_work w,t_course c,t_work_course wc,t_course_class cc,t_student s,t_status ss " +
				"where w.WORK_ID = wc.WORK_ID and wc.COURSE_ID = c.COURSE_ID " +
				"and c.COURSE_ID =cc.COURSE_ID and cc.CLASS_ID = s.CLASS_ID " +
				"and ss.WORK_ID = w.WORK_ID and ss.STU_ID = s.STU_ID" +
				"and w.STATUS = 1 and w.PUBLISH_TIME <= ? " +
				"and s.STU_ID = ? " ;
	//			"order by w.PUBLISH_TIME desc";
			list = workDAO.findBySqlAndPage(hql,page*20, 20, new Date(),stuId);
			System.out.println("0000list.size():"+list.size());
		}
		if("2".equals(status)){//所有作业
			hql="select distinct w.workId as workId,c.courseName as courseName," +
					"w.content as content,w.publishTime as publishTime, w.endTime as endTime " +
					"from Work w,Course c,WorkCourse wc,CourseClasses cc,Student s " +
					"where w.workId = wc.workId " +
					"and wc.courseId = c.courseId " +
					"and c.courseId =cc.courseId " +
					"and cc.classId = s.classId " +
					"and w.status = 1 " +
					"and s.stuId = ? " +
					"and w.publishTime <= now() order by w.publishTime desc";
			 list = workDAO.findMapByPage(hql,page*20, 20, stuId);
		}
		/*String hql = "select distinct w.workId as workId,c.courseName as courseName," +
				"w.content as content,w.publishTime as publishTime, w.endTime as endTime " +
				"from Work w,Course c,WorkCourse wc,CourseClasses cc,Student s " +
				"where w.workId = wc.workId " +
				"and wc.courseId = c.courseId " +
				"and c.courseId =cc.courseId " +
				"and cc.classId = s.classId " +
				"and w.status = 1 " +
				"and s.stuId = ? " +
				"and w.publishTime <= now() ";//已发布的作业
		if(StringUtil.isNotEmpty(status)){
			if("0".equals(status)){//待完成作业
				hql += "and not exists ";
			}
			else if("1".equals(status)){//已完成作业
				hql += "and exists ";
			}
			hql += "(select ws.wsId from WorkStatus ws,Work w where ws.workId = w.workId and ws.stuId = s.stuId) ";
		}
		hql += "order by w.publishTime desc";
		List<Map> list = workDAO.findMapByPage(hql,page*20, 20, stuId);
		if("0".equals(status)){
			for(Map record : list){
				Date publishTime = (Date)record.get("publishTime");
				int timeLength = (Integer)record.get("timeLength");
				int days = DateUtil.getDayBetween(new Date(), publishTime) + timeLength;
				record.put("days", days);
			}
		}*/
		
		/*List<Map> datas = null;
		List result = new ArrayList();
		Map record = null;
		for(Map data : datas){
			if(record==null||!data.get("workId").equals(record.get("workId"))){
				record = 
			}
		}*/
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
	public List<Map> getListWork(String userId,String status,String date,int page) {
		String hql = "select distinct w.workId as workId,c.courseName as courseName," ;
		List<Map> list = null ;
		if("0".equals(status)){//已过期作业
			hql+="w.publishTime as publishTime," +
				 "w.endTime as endTime," +
				 "w.status as status ,"+
				 "w.content as content " +
				 "from Work w,Course c,WorkCourse wc " +
				 "where w.workId=wc.workId and wc.courseId = c.courseId  " +
				 "and w.status=1 " +
				 "and c.userId = ? " +
				 "and w.endTime < now() order by w.endTime desc";
			list= workDAO.findMapByPage(hql, page*20, 20, userId);
			System.out.println("list.size():"+list.size());
		}
		if("1".equals(status)){//未过期作业（已发布但未到期）
			hql+="w.publishTime as publishTime," +
				 "w.endTime as endTime,"+
				 "w.status as status,"+
				 "w.content as content " +
				 "from Work w,Course c,WorkCourse wc "+
	             "where w.workId=wc.workId and wc.courseId = c.courseId and w.status=1 "+
				 "and c.userId=? "+
	             "and w.publishTime<now() and w.endTime > now() "+
				 "order by w.publishTime desc";
			list= workDAO.findMapByPage(hql, page*20, 20, userId);	
		}
		if("2".equals(status)){//获取待发布作业（草稿和待发布）
			hql+="w.publishTime as publishTime," +
				 "w.content as content," +
				 "w.status as status,"+
				 "w.endTime as endTime "+
				 "from Work w,Course c,WorkCourse wc " +
				 "where w.workId=wc.workId and wc.courseId = c.courseId and c.userId=? "+
		         "and (w.status=2 or (w.status=1 and w.publishTime>now())) "+
			     "order by w.publishTime asc";
			list=workDAO.findMapByPage(hql, page*20, 20, userId);
		}
		if("3".equals(status)){//获取指定截止日期的作业
			hql+="w.content as content "+
			    "from Work w,Course c,WorkCourse wc " +
			    "where w.workId=wc.workId and wc.courseId = c.courseId "+
		        "and c.userId=? and  substring(w.endTime,1,10)=? "+
			    "and w.status=1 and w.publishTime<now()";
			list=workDAO.findMapByPage(hql, page*20, 20,userId, date);
		}
		
		return list;
	}
	/**
	 * @author macong
	 *  查询作业详情
	 *  返回结果： 作业ID，作业所属课程名称[],作业内容，作业开始时间，作业结束时间，作业附件ID，作业附件名称，作业附件地址
	 */
	@Override
	public Map getWorkDetail(String workId) {
		/*String hql="select distinct w.workId as workId,w.publishTime as publishTime,"+
	               "w.endTime as endTime,w.content as content,w.remindTime as remindTime,f.fileId as fileId,"+
				   "f.fileName as fileName,c.courseName as courseName,cl.className as className "+
	               "from Work w,Course c,Classes cl,CustomFile f,CourseClasses cc "+
				   "where w.workId=f.dataId and w.courseId=c.courseId and "+
	               "c.courseId=cc.courseId and cc.classId=cl.classId and w.workId=? ";*/
		//第一步，根据作业ID查询该作业的内容，开始时间，结束时间（ＷＯＲＫ）
		String wi = "select w.workId as workId, w.publishTime as publishTime, "
				+ "w.endTime as endTime, w.content as content, w.remindTime as remindTime "
				+ "from Work w where w.workId = ?";
		List list = workDAO.findMap(wi, workId);
		Map data = null;
		if(null != list && list.size() > 0){
			data = workDAO.findMap(wi, workId).get(0);
			//第二步，通过作业-课程关联表，查询出该作业所属的课程ID（ＷＯＲＫCＯＵＲＳＥ，可能会有多个）
			//第三步，根据课程ＩＤ查询出课程名称（ＣＯＵＲＳＥ）
			String ci = "SELECT t_course.COURSE_ID AS courseId, t_course.COURSE_NAME AS courseName "
					+ "from t_course LEFT JOIN t_work_course "
					+ "ON t_course.COURSE_ID = t_work_course.COURSE_ID "
					+ "WHERE t_work_course.WORK_ID = ?";
			List<Map> clist = workDAO.findBySql(ci, workId);
			String courseName = "";
			String courseIds= "[";
			for(int i=0;i<clist.size();i++){
				courseIds += "\""+clist.get(i).get("courseId")+"\",";
			}
			String c = courseIds.substring(0, courseIds.length()-1);
			c = c+"]";
			System.out.println("courseIds==========="+c);
			if (null != clist) {
				for (int i = 0; i < clist.size(); i++) {
					String courName = (String) clist.get(i).get("courseName");
					String courseId = (String) clist.get(i).get("courseId");
					//第四步，根据课程-班级关联表，查询出该课程ID的班级ID（CLASSES,可能会有多个）
					//第五步，根据班级ID，查询出班级名称。
					String cli = "SELECT t_class.CLASS_NAME AS className FROM t_class LEFT JOIN t_course_class "
							+ "ON t_class.CLASS_ID = t_course_class.CLASS_ID "
							+ "WHERE t_course_class.COURSE_ID = ? ";
					List<Map> classList = workDAO.findBySql(cli, courseId);
					if(null != classList){
						courseName += courName+"(";
						for (int j = 0; j < classList.size(); j++) {
							courseName += classList.get(j).get("className")+",";
						}
						courseName = courseName.substring(0, courseName.length()-1);
						courseName += ")";
					}
					courseName = courseName += "||";
				}
				courseName = courseName.substring(0, courseName.length()-2);
			}
			data.put("courseName", courseName);
			data.put("courseIds", c);
		}
		//第六步，根据作业ID，查询出附件ID，附件name，附件URL。
		/*String fi = "select f.fileId as fileId ,f.fileName as fileName from CustomFile f where f.dataId = ?";
		List<Map> flist = workDAO.findMap(fi, workId);
		for(int i=0;i<flist.size();i++){
			System.out.println("----flist:"+flist.get(i).toString());
		}*/
		return data;
	}
    //改变作业状态
	@Override
	public void updateWorkStatus(String workId,String status) {
		String hql="update Work w ";
		if("0".equals(status)){//删除作业
			hql+="set w.status=0";
		}
		if("1".equals(status)){//（未发布作业->立即发布）
			hql+="set w.status=1,w.publishTime=now()";
		}
		if("2".equals(status)){//已发布作业撤回到草稿状态
			hql+="set w.status=2";
		}
		hql+=" where w.workId=?";
		workDAO.executeHql(hql, workId);
	}

}
