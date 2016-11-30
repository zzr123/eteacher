package com.turing.eteacher.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.SignInDAO;
import com.turing.eteacher.model.SignIn;
import com.turing.eteacher.service.ISignInService;
import com.turing.eteacher.util.DateUtil;
import com.turing.eteacher.util.StringUtil;

@Service
public class SignInServiceImpl extends BaseService<SignIn> implements ISignInService {
	
	@Autowired
	private SignInDAO signInDAO;
	
	@Override
	public BaseDAO<SignIn> getDAO() {
		return null;
	}
	/**
	 * 获取某课程的签到位置信息（所在市，学校，教学楼）
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Override
	public Map getAddressInfo(String courseId) {
		Map m = new HashMap<>();
		String hql = "select ci.courseId as courseId, cc.location as locationId, "
				+ "s.value as location, s.parentCode as schoolCode "
				+ "from CourseCell cc, CourseItem ci, School s "
				+ "where cc.ciId = ci.ciId and cc.location = s.schoolId and "
				+ "ci.courseId = ?";
		List<Map> lmp = signInDAO.findMap(hql, courseId);
		m.put("location", lmp.get(0).get("location"));
		//获取签到课程的市、学校信息
		String cs = "select s1.value as schoolName, s2.value as city "
				+ "from School s1, School s2 "
				+ "where s1.code = ? and s1.parentCode = s2.code ";
		Map li = signInDAO.findMap(cs, lmp.get(0).get("schoolCode")).get(0);
		m.put("schoolName", li.get("schoolName"));
		m.put("cityName", li.get("city"));
		if(null != m){
			return m;
		}
		return null;
	}
	
	/**
	 * 学生用户的课程签到。
	 * @author macong
	 * 时间： 2016年11月30日13:43:27
	 * @param studentId
	 * @param courseId
	 * @param lon
	 * @param lat
	 * @return
	 */
	@Override
	public void courseSignIn(String studentId, String courseId, String lon, String lat) {
		//获取当前系统时间，并计算出当前时间距离学期开始后的第多少周，当前时间为第几节课。
//		1.根据课程ID，获取课程所在学期的开始时间
		String tql = "select tp.tpId as termId, tp.startDate as startDate, "
				+ "c.userId as teacherId, t.schoolId as schoolId "
				+ "from TermPrivate tp, Course c, Teacher t "
				+ "where c.termId = tp.tpId and c.courseId = ? "
				+ "and tp.status = 1 and c.userId = t.teacherId ";
		Map term = signInDAO.findMap(tql, courseId).get(0);
		
//		2.计算出当前时间距离学期开始后的第多少周，当前时间为第几节课
		String startDate = (String) term.get("startDate");
		String cd = DateUtil.getCurrentDateStr("yyyy-MM-dd");
		String weekNum = Integer.toString(DateUtil.getWeekCount(startDate, cd));
		//当前时间是第几节课的签到时间
		String lessonNum = (String)getCurrentLessonNum((String)term.get("schoolId"),(String)term.get("teacherId"));
		//数据存储
		SignIn sign = new SignIn();
		if(StringUtil.checkParams(lessonNum,weekNum)){
			sign.setCourseId(courseId);
			sign.setCurrentLessons(lessonNum);
			sign.setCurrentWeek(weekNum);
			sign.setStudentId(studentId);
			sign.setLat(lat);
			sign.setLon(lon);
			sign.setStatus(1);
			signInDAO.save(sign);
		}
	}

	/**
	 * 辅助方法：根据学校ID，判断当前时间是该学校的第几节课的签到时间。
	 * 
	 * @return
	 */
	private String getCurrentLessonNum(String schoolId, String teacherId) {
		// 获取当前时间
		String time = DateUtil.getCurrentDateStr("HH:mm");
		// 获取该课程的签到时间设置
		String hql = "select r.before as before, r.after as after, r.distance as distance "
				+ "from RegistConfig r where r.userId = ? and r.status = 1";
		String hql2 = "select r.before as before, r.after as after, r.distance as distance "
				+ "from RegistConfig r where r.status = 0 ";
		//查询用户的签到设置
		List<Map> c = null;
		c = signInDAO.findMap(hql, teacherId);
		if(c == null || c.size() == 0){
			c = signInDAO.findMap(hql2);
		}
		//时间处理： （当前时间-after）< 课程开始时间  < （当前时间+before）,符合签到条件
		String time1 = DateUtil.timeSubtraction(time, "-", (Integer)c.get(0).get("before"));
		String time2 = DateUtil.timeSubtraction(time, "+", (Integer)c.get(0).get("after"));
		
		String hql3 = "select tt.lessonNumber as lessonNumber " + "from TimeTable tt where "
				+ "tt.schoolId = ? and tt.startTime >= ? and tt.startTime <= ?";
		String ln = (String) signInDAO.findMap(hql3, schoolId, time1, time2).get(0).get("lessonNumber");
		return ln;
	}
}
