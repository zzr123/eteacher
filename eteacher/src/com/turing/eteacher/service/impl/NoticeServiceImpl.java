package com.turing.eteacher.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.NoticeDAO;
import com.turing.eteacher.model.Log;
import com.turing.eteacher.model.Notice;
import com.turing.eteacher.service.INoticeService;
import com.turing.eteacher.service.IStatisticService;
import com.turing.eteacher.service.IWorkCourseService;
import com.turing.eteacher.util.StringUtil;

@Service
public class NoticeServiceImpl extends BaseService<Notice> implements INoticeService {

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private IWorkCourseService workCourseServiceImpl;
	
	@Autowired
	private IStatisticService statisticServiceImpl;
	
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
		//notice.setPublishTime(new Date().getTime());
		noticeDAO.update(notice);
	}
	/**
	 * 教师端接口
	 */
	//获取通知列表（已发布、待发布）
	@Override
	public List<Map> getListNotice(String userId,String status,String date,int page) {
		List<Map> list=null;
		String hql="select n.noticeId as noticeId,n.title as titile,n.publishTime as publishTime,SUBSTRING(n.content,1,20) as content ";
		if("0".equals(status)){//待发布通知
			hql+="from Notice n where n.userId=? and n.publishTime > now() and n.status=1 order by n.publishTime asc";
			list=noticeDAO.findMapByPage(hql, page*20, 20, userId);
		}else if("1".equals(status)){//已发布通知
			hql+="from Notice n where n.userId=? and n.publishTime < now() and n.status=1 order by n.publishTime asc";
			list=noticeDAO.findMapByPage(hql, page*20, 20, userId);
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					int all = workCourseServiceImpl.getStudentCountByWId((String)list.get(i).get("noticeId"));
					list.get(i).put("all", all);
					int statistics = statisticServiceImpl.getCountByTargetId((String)list.get(i).get("noticeId"));
					list.get(i).put("statistics", statistics);
				}
			}
		}
		System.out.println("list.size():"+list.size());
		return list;
	}
	//通知状态的修改
	@Override
	public void ChangeNoticeState(String noticeId,String status) {
		Notice notice = noticeDAO.get(noticeId);
		if (null != notice) {
			if("1".equals(status)){//待发布通知->立即通知
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
				notice.setPublishTime(df.format(new Date(new Date().getTime() - 1000*10*60)));
			}else if("0".equals(status)){//删除通知，不可见
				notice.setStatus(0);
			}
			noticeDAO.update(notice);
		}
	}
	//查看通知详情
	@Override
	public Map getNoticeDetail(String noticeId) {
		Map detail = null;
		String hql="select n.noticeId as noticeId ," +
				   "n.title as title," +
				   "n.content as content," +
				   "n.publishTime as publishTime "+
	               "from Notice n "+
				   "where n.noticeId=?";
		List<Map> list=noticeDAO.findMap(hql, noticeId);
		if (null != list && list.size() > 0) {
			detail = list.get(0);
			detail.put("statistics", statisticServiceImpl.getCountByTargetId(noticeId));
			detail.put("all", workCourseServiceImpl.getStudentCountByWId(noticeId));
			detail.put("courses", workCourseServiceImpl.getCoursesByWId(noticeId));
		}
		return detail;
	}
	//查看通知未读人员列表
	@Override
	public List<Map> getNoticeReadList(String noticeId,int type, int page) {
		String sql = "SELECT DISTINCT stu.STU_ID AS studentId, stu.STU_NAME AS studentName ";
		List<String> params = new ArrayList<>();
		switch (type) {
		case 1:
			sql += "FROM t_student stu WHERE stu.STU_ID IN ( "+
					"SELECT sta.USER_ID FROM t_statistic sta WHERE sta.TARGET_ID = ?)";
			params.add(noticeId);
			break;
		default:
			sql += "FROM t_student stu WHERE stu.CLASS_ID IN ( "+
					"SELECT DISTINCT  cc.CLASS_ID FROM t_course_class cc WHERE cc.COURSE_ID IN ( "+
					"SELECT DISTINCT wc.COURSE_ID FROM t_work_course wc WHERE wc.WORK_ID = ?)) "+
					"AND "+
					"stu.STU_ID NOT IN (SELECT sta.USER_ID FROM t_statistic sta WHERE sta.TARGET_ID = ?)";
			params.add(noticeId);
			params.add(noticeId);
			break;
		}
		List<Map> list=noticeDAO.findBySqlAndPage(sql, page*20, 20, params);
		return list;
	}
	/**
	 * 学生端接口：获取通知列表（已读通知和未读通知）
	 * @author macong
	 * @param userId
	 * @param status
	 * @param parseInt
	 * @param termId
	 * @return
	 */
	@Override
	public List<Map> getNoticeList_student(String userId, String status, int page) {
		//1. 查出该学生所属的班级信息，根据班级信息，查询出该学生本学期的课程ID
		//2. 根据课程ID，查询出对应的通知ID。
		//3. 筛选出最近15天的通知
		//获取当前时间及十五天前的时间（String类型）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date();
        String currentDay = sdf.format(d);
        //获取之前15天的日期
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DATE, - 15);  
        Date monday = c.getTime();
        String preDays = sdf.format(monday);
		String hql = "select distinct n.noticeId as noticeId, n.title as title, "
				+ "substring(n.content,1,30) as content, n.publishTime as publishTime, te.name as author "
				+ "from Notice n, CourseClasses cc, WorkCourse wc, "
				+ "Student s, Teacher te ";
		List<Map> list = null;
		if(status.equals("01")){//未读通知
			String hql1 = hql + "where s.classId = cc.classId and cc.courseId = wc.courseId "
					+ "and n.noticeId = wc.workId and n.userId = te.teacherId "
					+ "and s.stuId = ? and n.publishTime > ? and n.publishTime < ? and n.status=1 "
					+" and n.noticeId NOT IN(select log.noticeId from Log log "
					+ "where log.stuId = ? and log.type = 1 )";
			System.out.println("hql1:"+hql1);
			list = noticeDAO.findMapByPage(hql1, page*10, 10,userId,preDays,currentDay,userId); 
			
		}else if(status.equals("02")){//已读通知
			String hql2 = hql + ", Log l where s.classId = cc.classId and cc.courseId = wc.courseId "
					+ "and n.noticeId = wc.workId and n.userId = te.teacherId "
					+ "and s.stuId = ? and n.publishTime > ? and n.publishTime < ? and n.status=1 "
					+ "and l.noticeId = n.noticeId and l.stuId = ? and l.type=1 ";
			System.out.println("hql2:"+hql2);
			list = noticeDAO.findMapByPage(hql2, page*10, 10,userId,preDays,currentDay,userId);
		}
		if(null != list && list.size() > 0){
			return list;
		}
		return null;
	}
	/**
	 * 学生端接口：查看通知详情
	 * @author macong
	 * @param noticeId
	 * @return
	 */
	@Override
	public Map getNoticeDetail_student(String noticeId,int flag) {
		// TODO Auto-generated method stub
		String hql = "select n.noticeId as noticeId, n.title as title, "
				+ "n.content as content,n.publishTime as publishTime, t.name as author "
				+ "from Notice n,Teacher t where n.userId = t.teacherId and n.noticeId = ? ";
		Map m = noticeDAO.findMap(hql, noticeId).get(0);
		if(null != m){
			return m;
		}
		return null;
	}
	/**
	 * 学生端接口：将未读通知置为已读状态
	 * @author macong
	 * @param noticeId
	 * @param userId
	 */
	@Override
	public void addReadFlag(String noticeId, String userId) {
		// TODO Auto-generated method stub
		Log l = new Log();
		l.setNoticeId(noticeId);
		l.setStuId(userId);
		l.setType(1);
		noticeDAO.save(l);
	}

}
