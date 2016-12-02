package com.turing.eteacher.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.ClassDAO;
import com.turing.eteacher.dao.CourseDAO;
import com.turing.eteacher.dao.CourseTableDAO;
import com.turing.eteacher.dao.UserDAO;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseTable;
import com.turing.eteacher.service.ICourseTableService;
import com.turing.eteacher.util.StringUtil;

@Service
public class CourseTableServiceImpl extends BaseService<CourseTable> implements ICourseTableService {

	@Autowired
	private CourseTableDAO courseTableDAO;
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ClassDAO classDAO;
	
	@Override
	public BaseDAO<CourseTable> getDAO() {
		return courseTableDAO;
	}
	
	@Override
	public List<Map> getListByCourseId(String courseId) {
//		String hql = "from CourseTable where courseId = ?";
//		List<CourseTable> list = courseTableDAO.find(hql, courseId);
//		return list;
		String sql="SELECT distinct c.COURSE_ID AS courseId,c.COURSE_NAME as courseName," +
				"ce.WEEKDAY as weekDay,ce.LESSON_NUMBER as lessonNumber," +
				"ce.LOCATION as location, ce.CLASSROOM as classroom " +
				"FROM t_course_cell ce " +
				"INNER JOIN t_course_item ci ON ce.CI_ID = ci.CI_ID " +
				"INNER JOIN t_course c ON ci.COURSE_ID = c.COURSE_ID " +
				"INNER JOIN t_course_class cc ON c.COURSE_ID = cc.COURSE_ID " +
				"INNER JOIN t_class cl ON cc.CLASS_ID = cl.CLASS_ID " +
				"WHERE c.COURSE_ID = ?";
		List<Map> list=courseDAO.findBySql(sql,courseId);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String sql2 = "SELECT c.CLASS_NAME AS className  FROM t_class c WHERE c.CLASS_ID IN (SELECT cc.CLASS_ID FROM t_course_class cc WHERE cc.COURSE_ID = ?)";
				List<Map> list2 = courseDAO.findBySql(sql2,list.get(i).get("courseId"));
				if (null != list2 && list2.size() > 0) {
					String className = "(";
					for (int j = 0; j < list2.size(); j++) {
						className += list2.get(j).get("className") + ",";
					}
					className = className.substring(0, className.length() - 1);
					className += ")";
					list.get(i).put("courseName", list.get(i).get("courseName")+className);
				}
			}
		}
		
		for(int i = 0;i< list.size();i++){
			System.out.println("map"+i+":"+list.get(i).toString());
		}
		return list;
	}

	@Override
	public List<Map> getListByUserId(String userId,Map tpId) {
		String sql="SELECT distinct c.COURSE_ID AS courseId,c.COURSE_NAME as courseName,ce.WEEKDAY as weekDay," +
				"ce.LESSON_NUMBER as lessonNumber,ce.LOCATION as location," +
				" ce.CLASSROOM as classroom " +
				"FROM t_course_cell ce " +
				"INNER JOIN t_course_item ci ON ce.CI_ID = ci.CI_ID " +
				"INNER JOIN t_course c ON ci.COURSE_ID = c.COURSE_ID " +
				"INNER JOIN t_course_class cc ON c.COURSE_ID = cc.COURSE_ID " +
				"INNER JOIN t_class cl ON cc.CLASS_ID = cl.CLASS_ID " +
				"WHERE c.USER_ID = ? and c.TERM_ID = ? ";
		List<Map> list=courseDAO.findBySql(sql,userId,tpId);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String sql2 = "SELECT c.CLASS_NAME AS className  FROM t_class c WHERE c.CLASS_ID IN (SELECT cc.CLASS_ID FROM t_course_class cc WHERE cc.COURSE_ID = ?)";
				List<Map> list2 = courseDAO.findBySql(sql2,list.get(i).get("courseId"));
				if (null != list2 && list2.size() > 0) {
					String className = "(";
					for (int j = 0; j < list2.size(); j++) {
						className += list2.get(j).get("className") + ",";
					}
					className = className.substring(0, className.length() - 1);
					className += ")";
					list.get(i).put("courseName", list.get(i).get("courseName")+className);
				}
			}
		}
		
		for(int i = 0;i< list.size();i++){
			System.out.println("map"+i+":"+list.get(i).toString());
		}
		return list;
	}
	
	@Override
	public List<Map> getListByClassId(String classId,Map tpId) {
//		String hql = "select ct from CourseTable ct,Course c,CourseClasses cc where ct.courseId = c.courseId and c.courseId = cc.courseId and cc.classId = ? order by c.courseId,ct.startWeek";
//		List<Map> list = courseTableDAO.find(hql, classId);
//		return list;
		String sql="SELECT c.COURSE_NAME as courseName, " +
				"ce.WEEKDAY as weekDay, ce.LESSON_NUMBER as lessonNumber, " +
				"ce.LOCATION as location, ce.CLASSROOM as classroom "+ 
				"FROM t_course_cell ce "+
				"INNER JOIN t_course_item ci ON ce.CI_ID=ci.CI_ID "+
				"INNER JOIN t_course c ON ci.COURSE_ID = c.COURSE_ID "+
				"INNER JOIN t_course_class cl ON c.COURSE_ID =cl.COURSE_ID "+
				"WHERE cl.CLASS_ID = ? and c.TERM_ID = ?";
		List<Map> list=courseDAO.findBySql(sql,classId,tpId);
		for(int i = 0;i< list.size();i++){
			System.out.println("map"+i+":"+list.get(i).toString());
		}
		return list;
	}

	@Override
	public Map getCourseTableDatas(String id,Map tpId, String type) {
		List<Map> list = null;
		if("class".equals(type)){
			list = getListByClassId(id,tpId);
		}
		else{
			list = getListByUserId(id,tpId);
		}
		return genCourseTableData(list,type);
	}
	
	private Map genCourseTableData(List<Map> list, String type){
		Map datas = new HashMap();
		for(Map record : list){
			String desc = "<strong>" + courseDAO.get(((Course) record).getCourseId()).getCourseName() + "</strong><br/>" + ((CourseTable) record).getLocation();
			String repeat = "<span style='display:none'>每"+(((CourseTable) record).getRepeatNumber()==null||((CourseTable) record).getRepeatNumber()==1?"":((CourseTable) record).getRepeatNumber())+"周重复</span>";
			String weekRepeat = "每";
			if(((CourseTable) record).getRepeatNumber()>1){
				if(((CourseTable) record).getStartWeek()%2==0){
					weekRepeat = "<span style='color:red'>双</span>";
				}
				else{
					weekRepeat = "<span style='color:red'>单</span>";
				}
			}
			String[] weekdays = {"1","2","3","4","5","6","7"};
			if(!EteacherConstants.COURSETABLE_REPEATTYPE_DAY.equals(((CourseTable) record).getRepeatType())){
				weekdays = ((CourseTable) record).getWeekday().split(",");;
			}
			String user = "";
			if("class".equals(type)){
				Course course = courseDAO.get(((Course) record).getCourseId());
				String hql = "select name as name from Teacher where userId = ?";
				user = (String)courseDAO.getUniqueResult(hql, course.getUserId());
				if(!StringUtil.isNotEmpty(user)){
					user = userDAO.get(course.getUserId()).getAccount();
				}
			}
			else{
				String hql = "select c.className as className from CourseClasses cc,Classes c where cc.classId = c.classId and cc.courseId = ?";
				List<String> classNames = courseDAO.find(hql, ((Course) record).getCourseId());
				if(classNames!=null&&classNames.size()>0){
					for(String className : classNames){
						user += className + ",";
					}
					if(user.endsWith(",")){
						user = user.substring(0,user.length()-1);
					}
				}
			}
			for(String weekday : weekdays){
				String key = weekday+"_"+((CourseTable) record).getLessonNumber();
				if(datas.get(key)==null){
					datas.put(key, desc  + "<br/>" + weekRepeat + "周(" + ((CourseTable) record).getStartWeek() + "-" + ((CourseTable) record).getEndWeek() + ")" + repeat + "<br/>" + user);
				}
				else if(datas.get(key).toString().contains(desc) && datas.get(key).toString().contains(repeat)){
					String orderDesc = datas.get(key).toString();
					int idx = orderDesc.indexOf(")", orderDesc.indexOf(desc+"<br/>周("));
					datas.put(key, orderDesc.substring(0,idx)+ "," + ((CourseTable) record).getStartWeek() + "-" + ((CourseTable) record).getEndWeek()+orderDesc.substring(idx));
				}
				else{
					datas.put(key, datas.get(key)+"<br/>" + desc + "<br/>" + weekRepeat + "周(" + ((CourseTable) record).getStartWeek() + "-" + ((CourseTable) record).getEndWeek() + ")" + repeat + "<br/>" + user);
				}
			}
		}
		return datas;
	}
	//选择班级，获取教师当前学期的授课班级
	@Override
	public List<Map> getClassList(String userId, Map tpId) {
		String sql = "select distinct c.CLASS_NAME AS className,c.CLASS_ID AS classId " +
				"from t_class c " +
				"INNER JOIN t_course_class cl ON c.CLASS_ID =cl.CLASS_ID " +
				"INNER JOIN t_course co ON cl.COURSE_ID = co.COURSE_ID " +
				"WHERE co.USER_ID=? and co.TERM_ID=?";
	List<Map> list = classDAO.findBySql(sql,userId,tpId);
	for(int i = 0;i< list.size();i++){
		System.out.println("map"+i+":"+list.get(i).toString());
	}
	return list;
	}
	
	
}
