package com.turing.eteacher.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.CourseDAO;
import com.turing.eteacher.dao.CourseTableDAO;
import com.turing.eteacher.dao.UserDAO;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseTable;
import com.turing.eteacher.model.Teacher;
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
	
	@Override
	public BaseDAO<CourseTable> getDAO() {
		return courseTableDAO;
	}
	
	@Override
	public List<CourseTable> getListByCourseId(String courseId) {
		String hql = "from CourseTable where courseId = ?";
		List<CourseTable> list = courseTableDAO.find(hql, courseId);
		return list;
	}

	@Override
	public List<CourseTable> getListByUserId(String userId) {
		String hql = "select ct from CourseTable ct,Course c where ct.courseId = c.courseId and c.userId = ? order by ct.startWeek";
		List<CourseTable> list = courseTableDAO.find(hql, userId);
		return list;
	}
	
	@Override
	public List<CourseTable> getListByClassId(String classId) {
		String hql = "select ct from CourseTable ct,Course c,CourseClasses cc where ct.courseId = c.courseId and c.courseId = cc.courseId and cc.classId = ? order by c.courseId,ct.startWeek";
		List<CourseTable> list = courseTableDAO.find(hql, classId);
		return list;
	}

	@Override
	public Map getCourseTableDatas(String id, String type) {
		List<CourseTable> list = null;
		if("class".equals(type)){
			list = getListByClassId(id);
		}
		else{
			list = getListByUserId(id);
		}
		return genCourseTableData(list,type);
	}
	
	private Map genCourseTableData(List<CourseTable> list, String type){
		Map datas = new HashMap();
		for(CourseTable record : list){
			String desc = "<strong>" + courseDAO.get(record.getCourseId()).getCourseName() + "</strong><br/>" + record.getLocation();
			String repeat = "<span style='display:none'>每"+(record.getRepeatNumber()==null||record.getRepeatNumber()==1?"":record.getRepeatNumber())+"周重复</span>";
			String weekRepeat = "每";
			if(record.getRepeatNumber()>1){
				if(record.getStartWeek()%2==0){
					weekRepeat = "<span style='color:red'>双</span>";
				}
				else{
					weekRepeat = "<span style='color:red'>单</span>";
				}
			}
			String[] weekdays = {"1","2","3","4","5","6","7"};
			if(!EteacherConstants.COURSETABLE_REPEATTYPE_DAY.equals(record.getRepeatType())){
				weekdays = record.getWeekday().split(",");;
			}
			String user = "";
			if("class".equals(type)){
				Course course = courseDAO.get(record.getCourseId());
				String hql = "select name from Teacher where userId = ?";
				user = (String)courseDAO.getUniqueResult(hql, course.getUserId());
				if(!StringUtil.isNotEmpty(user)){
					user = userDAO.get(course.getUserId()).getAccount();
				}
			}
			else{
				String hql = "select c.className from CourseClasses cc,Classes c where cc.classId = c.classId and cc.courseId = ?";
				List<String> classNames = courseDAO.find(hql, record.getCourseId());
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
				String key = weekday+"_"+record.getLessonNumber();
				if(datas.get(key)==null){
					datas.put(key, desc  + "<br/>" + weekRepeat + "周(" + record.getStartWeek() + "-" + record.getEndWeek() + ")" + repeat + "<br/>" + user);
				}
				else if(datas.get(key).toString().contains(desc) && datas.get(key).toString().contains(repeat)){
					String orderDesc = datas.get(key).toString();
					int idx = orderDesc.indexOf(")", orderDesc.indexOf(desc+"<br/>周("));
					datas.put(key, orderDesc.substring(0,idx)+ "," + record.getStartWeek() + "-" + record.getEndWeek()+orderDesc.substring(idx));
				}
				else{
					datas.put(key, datas.get(key)+"<br/>" + desc + "<br/>" + weekRepeat + "周(" + record.getStartWeek() + "-" + record.getEndWeek() + ")" + repeat + "<br/>" + user);
				}
			}
		}
		return datas;
	}
}
