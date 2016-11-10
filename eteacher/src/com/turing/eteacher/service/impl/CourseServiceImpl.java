package com.turing.eteacher.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.fabric.xmlrpc.base.Data;
import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.ConfigContants;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.CourseDAO;
import com.turing.eteacher.dao.CourseItemDAO;
import com.turing.eteacher.dao.CourseScoreDAO;
import com.turing.eteacher.dao.CourseTableDAO;
import com.turing.eteacher.dao.MajorDAO;
import com.turing.eteacher.dao.TextbookDAO;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseClasses;
import com.turing.eteacher.model.CourseScore;
import com.turing.eteacher.model.CourseScorePrivate;
import com.turing.eteacher.model.CourseTable;
import com.turing.eteacher.model.CourseWorkload;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.model.Major;
import com.turing.eteacher.model.Textbook;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.util.BeanUtils;
import com.turing.eteacher.util.DateUtil;
import com.turing.eteacher.util.StringUtil;

@Service
public class CourseServiceImpl extends BaseService<Course> implements ICourseService {

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private CourseItemDAO courseItemDAO;

	@Autowired
	private TextbookDAO textbookDAO;

	@Autowired
	private CourseTableDAO courseTableDAO;

	@Autowired
	private CourseScoreDAO courseScoreDAO;

	@Autowired
	private MajorDAO majorDAO;

	@Autowired
	private ITermService termServiceImpl;

	@Override
	public BaseDAO<Course> getDAO() {
		return courseDAO;
	}

	// 获取学期下的课程数据，判断该学期下是否含有课程数据
	@Override
	@Transactional(readOnly = true)
	public List<Course> getListByTermId(String termId, String userId) {
		List args = new ArrayList();
		String hql = "from Course where 1=1 ";
		if (StringUtil.isNotEmpty(userId)) {
			hql += " and userId = ?";
			args.add(userId);
		}
		if (StringUtil.isNotEmpty(termId)) {
			hql += " and termId = ?";
			args.add(termId);
		}
		List<Course> list = courseDAO.find(hql, args.toArray());
		for (Course record : list) {
			if (StringUtil.isNotEmpty(record.getMajorId())) {
				Major major = majorDAO.get(record.getMajorId());
				if (major != null) {
					record.setMajorId(major.getMajorName());
				}
			}
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map> getListByTermId2(String termId, String userId) {
		List args = new ArrayList();
		String sql = "select t_course.COURSE_ID as courseId, " + "t_course.COURSE_NAME as courseName, "
				+ "t_course.COURSE_TYPE_ID as courseTypeId, "
				+ "t_major.MAJOR_NAME as specialty from t_course left join t_major on t_course.MAJOR_ID = t_major.MAJOR_ID where ";
		if (StringUtil.isNotEmpty(userId)) {
			sql += " t_course.USER_ID = ?";
			args.add(userId);
		}
		if (StringUtil.isNotEmpty(termId)) {
			sql += " and t_course.TERM_ID = ?";
			args.add(termId);
		}
		List<Map> list = courseDAO.findBySql(sql, args);
		for (int i = 0; i < list.size(); i++) {
			String typeId = (String) list.get(i).get("courseTypeId");
			String sql1 = "SELECT  VALUE as type FROM t_dictionary2_public WHERE  DICTIONARY_ID = ? " + "UNION "
					+ "SELECT  VALUE as type FROM t_dictionary2_private WHERE DP_ID =  ?";
			List<Map> list2 = courseDAO.findBySql(sql1, typeId, typeId);
			if (null != list2 && list2.size() >= 1) {
				list.get(i).put("courseType", list2.get(0).get("type"));
			}
		}
		return list;
	}

	@Override
	public List<CustomFile> getCourseFilesByCourseId(String courseId) {
		String hql = "from CustomFile cFile where cFile.dataId = ?";
		return courseDAO.find(hql, courseId);
	}

	@Override
	public List<CustomFile> getPublicCourseFilesByCourseId(String courseId) {
		String hql = "from customFile where courseId = ? and fileAuth = ?";
		return courseDAO.find(hql, courseId, EteacherConstants.COURSE_FILE_AUTH_PUBLIC);
	}

	@Override
	public void addCourse(Course course, String[] classIds, List<CourseWorkload> courseWorkloads,
			List<CourseScorePrivate> courseScores, Textbook textbook, List<Textbook> textbookOthers,
			List<CustomFile> customFile) {
		String courseId = (String) courseDAO.save(course);
		// 授课班级
		if (classIds != null) {
			for (String classId : classIds) {
				CourseClasses record = new CourseClasses();
				record.setCourseId(courseId);
				record.setClassId(classId);
				courseDAO.save(record);
			}
		}
		// 工作量组成
		if (courseWorkloads != null) {
			for (int i = 0; i < courseWorkloads.size(); i++) {
				CourseWorkload record = courseWorkloads.get(i);
				record.setCourseId(courseId);
				record.setCwOrder(i);
				courseDAO.save(record);
			}
		}
		// 成绩组成
		if (courseScores != null) {
			for (int i = 0; i < courseScores.size(); i++) {
				CourseScorePrivate record = courseScores.get(i);
				record.setCourseId(courseId);
				record.setCsOrder(i);
				courseDAO.save(record);
			}
		}
		// 教材
		if (textbook != null) {
			textbook.setCourseId(courseId);
			textbook.setTextbookType(EteacherConstants.BOOKTEXT_MAIN);
			courseDAO.save(textbook);
		}
		// 教辅
		if (textbookOthers != null) {
			for (Textbook record : textbookOthers) {
				record.setCourseId(courseId);
				record.setTextbookType(EteacherConstants.BOOKTEXT_OTHER);
				courseDAO.save(record);
			}
		}
		// 资源
		for (CustomFile record : customFile) {
			record.setDataId(courseId);
			courseDAO.save(record);
		}
	}

	@Override
	public void updateCourse(Course course, String[] classIds, List<CourseWorkload> courseWorkloads,
			List<CourseScorePrivate> courseScores, Textbook textbook, List<Textbook> textbookOthers,
			List<CustomFile> customFile) {
		Course serverCourse = courseDAO.get(course.getCourseId());
		BeanUtils.copyToModel(course, serverCourse);
		courseDAO.update(serverCourse);
		// 授课班级
		String hql = "delete from CourseClasses where courseId = ?";
		courseDAO.executeHql(hql, course.getCourseId());
		if (classIds != null) {
			for (String classId : classIds) {
				CourseClasses record = new CourseClasses();
				record.setCourseId(course.getCourseId());
				record.setClassId(classId);
				courseDAO.save(record);
			}
		}
		// 工作量组成
		List<String> cwIds = new ArrayList();
		if (courseWorkloads != null) {
			for (int i = 0; i < courseWorkloads.size(); i++) {
				CourseWorkload record = courseWorkloads.get(i);
				record.setCourseId(course.getCourseId());
				record.setCwOrder(i);
				if ("".equals(record.getCwId())) {
					record.setCwId(null);
				}
				courseDAO.saveOrUpdate(record);
				cwIds.add(record.getCwId());
			}
		}
		hql = "delete from CourseWorkload where courseId = :courseId and cwId not in (:cwIds)";
		Map paramsMap = new HashMap();
		paramsMap.put("courseId", course.getCourseId());
		paramsMap.put("cwIds", cwIds);
		courseDAO.executeHqlByParams(hql, paramsMap);
		// 成绩组成
		List<String> csIds = new ArrayList();
		if (courseScores != null) {
			for (int i = 0; i < courseScores.size(); i++) {
				CourseScorePrivate record = courseScores.get(i);
				record.setCourseId(course.getCourseId());
				record.setCsOrder(i);
				if ("".equals(record.getCsId())) {
					record.setCsId(null);
				}
				courseDAO.saveOrUpdate(record);
				csIds.add(record.getCsId());
			}
		}
		hql = "delete from CourseScore where courseId = :courseId and csId not in (:csIds)";
		paramsMap = new HashMap();
		paramsMap.put("courseId", course.getCourseId());
		paramsMap.put("csIds", csIds);
		courseDAO.executeHqlByParams(hql, paramsMap);
		// 教材
		if (textbook != null) {
			if ("".equals(textbook.getTextbookId())) {
				textbook.setTextbookId(null);
			}
			textbook.setCourseId(course.getCourseId());
			textbook.setTextbookType(EteacherConstants.BOOKTEXT_MAIN);
			courseDAO.saveOrUpdate(textbook);
		}
		// 教辅
		// 先删后加
		textbookDAO.deleteOthersByCourseId(course.getCourseId());
		if (textbookOthers != null) {
			for (Textbook record : textbookOthers) {
				record.setCourseId(course.getCourseId());
				record.setTextbookType(EteacherConstants.BOOKTEXT_OTHER);
				courseDAO.save(record);
			}
		}
		// 资源
		for (CustomFile record : customFile) {
			record.setDataId(course.getCourseId());
			courseDAO.save(record);
		}
	}

	@Override
	public void deleteCourseFile(String cfId) {
		String hql = "delete from CustomFile where cfId = ?";
		courseDAO.executeHql(hql, cfId);
	}

	@Override
	public List<CourseWorkload> getCoureWorkloadByCourseId(String courseId) {
		String hql = "from CourseWorkload where courseId = ? order by cwOrder";
		List<CourseWorkload> list = courseDAO.find(hql, courseId);
		return list;
	}

	@Override
	public List<CourseScorePrivate> getCoureScoreByCourseId(String courseId) {
		String hql = "from CourseScorePrivate where courseId = ? order by csOrder";
		List<CourseScorePrivate> list = courseDAO.find(hql, courseId);
		return list;
	}

	/**
	 * 获取当前时间,是否为某门课程的上课时间
	 * 
	 * @param user
	 * @param courseId
	 */
	@Override
	public Map getCourseRecordNow(User user, String courseId) {
		Map result = null;
		String startTimeStr = null;
		boolean boo = false;
		Calendar now = Calendar.getInstance();
		CourseTable currentCourseTable = null;
		List<CourseTable> courseTables = getTodayCourseTables(user, courseId);
		// 根据课表循环类型、第几节、以及设置中的上课时间计算当前时间是否为这门课程的课堂时间
		for (CourseTable courseTable : courseTables) {
			// 判断当前时间是否为上课时间
			String lessonNumber = courseTable.getLessonNumber();
			if (StringUtil.isNotEmpty(lessonNumber)) {
				for (String ln : lessonNumber.split(",")) {
					// 每节课挨个判断
					String startTime = ConfigContants.configMap.get(ConfigContants.CLASS_TIME[Integer.parseInt(ln)])
							.split("-")[0];
					String endTime = ConfigContants.configMap.get(ConfigContants.CLASS_TIME[Integer.parseInt(ln)])
							.split("-")[1];
					Calendar lessonStart = DateUtil.getCalendarByTime(startTime + ":00");
					Calendar lessonEnd = DateUtil.getCalendarByTime(endTime + ":59");
					if (now.after(lessonStart) && now.before(lessonEnd)) {
						result = new HashMap();
						startTimeStr = startTime;
						boo = true;
						currentCourseTable = courseTable;
						result.put("startTime", startTimeStr);
						result.put("currentCourseTable", currentCourseTable);
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取用户所有课程的今天课表数据或者指定课程的今天课表数据
	 * 
	 * @param currentTerm
	 * @param userId
	 * @param courseId
	 * @return
	 */
	private List<CourseTable> getTodayCourseTables(User user, String courseId) {
		List<CourseTable> result = new ArrayList();
		String startTimeStr = null;
		boolean boo = false;

		String hql = null;
		List args = new ArrayList();
		if (courseId != null) {// 根据课程ID获取指定课程的课表信息
			hql = "select ct from CourseTable ct where ct.courseId = ?";
			args.add(courseId);
		} else {
			if (EteacherConstants.USER_TYPE_TEACHER.equals(user.getUserType())) {// 获取某个教师的课程的课表信息
				hql = "select ct from CourseTable ct,Course c " + "where ct.courseId = c.courseId and c.userId = ?";
			} else {// 获取某个学生的课程的课表信息
				hql = "select ct from CourseTable ct,Course c " + "where ct.courseId = c.courseId "
						+ "and exists (select cc.courseId from CourseClasses cc,Student s "
						+ "where cc.classId = s.classId and cc.courseId = ct.courseId and s.stuId = ?) ";
			}
			args.add(user.getUserId());
		}
		List<CourseTable> courseTables = courseTableDAO.find(hql, args.toArray());
		// 遍历筛选后的课表数据
		for (CourseTable courseTable : courseTables) {
			// 获取当前时间是本学期的第几周
			Course course = courseDAO.get(courseTable.getCourseId());
			Map currentTerm = termServiceImpl.getCurrentTerm(course.getUserId());
			Calendar termStart = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar now = Calendar.getInstance();
			try {
				termStart.setTime(dateFormat.parse((String) currentTerm.get("startDate")));
				termStart.add(Calendar.DATE, -(DateUtil.getDayOfWeek(termStart) - 1));
				now.setTime(dateFormat.parse(dateFormat.format(new Date())));
			} catch (Exception e) {
				e.printStackTrace();
			}
			int weekNo = (int) ((now.getTimeInMillis() - termStart.getTimeInMillis()) / (1000 * 60 * 60 * 24 * 7) + 1);
			// 获取这门课程的课表数据，筛选条件为包含本周。
			if (weekNo >= courseTable.getStartWeek() && weekNo <= courseTable.getEndWeek()) {
				// 判断今天是否有课
				now.setTime(new Date());
				if (EteacherConstants.COURSETABLE_REPEATTYPE_DAY.equals(courseTable.getRepeatType())
						|| (((weekNo - courseTable.getStartWeek()) % courseTable.getRepeatNumber() == 0)
								&& courseTable.getWeekday().contains(DateUtil.getDayOfWeek(now) + ""))) {
					result.add(courseTable);
				}
			}
		}
		return result;
	}

	@Override
	public Map getCourseTimeData(String courseId) {
		// 起止周
		String startWeek = "";
		String endWeek = "";
		// 获取上课时间
		String startTime = "";
		String endTime = "";
		List<CourseTable> courseTables = getTodayCourseTables(null, courseId);
		for (CourseTable courseTable : courseTables) {
			String lessonNumber = courseTable.getLessonNumber();
			if (StringUtil.isNotEmpty(lessonNumber)) {
				startWeek = courseTable.getStartWeek() + "";
				endWeek = courseTable.getEndWeek() + "";
				String[] lessonNumberArr = lessonNumber.split(",");
				startTime = ConfigContants.configMap
						.get(ConfigContants.CLASS_TIME[Integer.parseInt(lessonNumberArr[0])]).split("-")[0];
				endTime = ConfigContants.configMap
						.get(ConfigContants.CLASS_TIME[Integer.parseInt(lessonNumberArr[lessonNumberArr.length - 1])])
						.split("-")[1];
				break;
			}
		}
		Map result = new HashMap();
		result.put("startWeek", startWeek);
		result.put("endWeek", endWeek);
		result.put("startTime", startTime);
		result.put("endTime", endTime);
		return result;
	}

	@Override
	public List<Map> getCourseDatasOfToday(User user) {
		List<Map> list = new ArrayList();
		List<CourseTable> courseTables = getTodayCourseTables(user, null);
		Map record = null;
		for (CourseTable courseTable : courseTables) {
			Course course = courseDAO.get(courseTable.getCourseId());
			record = new HashMap();
			record.put("courseId", course.getCourseId());
			record.put("courseName", course.getCourseName());
			// 获取上课时间
			String courseTime = "";
			String startTime = "";
			String endTime = "";
			String lessonNumber = courseTable.getLessonNumber();
			if (StringUtil.isNotEmpty(lessonNumber)) {
				String[] lessonNumberArr = lessonNumber.split(",");
				startTime = ConfigContants.configMap
						.get(ConfigContants.CLASS_TIME[Integer.parseInt(lessonNumberArr[0])]).split("-")[0];
				endTime = ConfigContants.configMap
						.get(ConfigContants.CLASS_TIME[Integer.parseInt(lessonNumberArr[lessonNumberArr.length - 1])])
						.split("-")[1];
				courseTime = startTime + "-" + endTime;
			}
			record.put("courseTime", courseTime);
			record.put("location", courseTable.getLocation());
			list.add(record);
		}
		return list;
	}

	@Override
	public List getListByTermAndStuId(String year, String term, String stuId) {
		String hql = "select c from Course c,Term t where c.termId = t.termId and t.year = ? and t.term = ? "
				+ "and exists (select cc.courseId from CourseClasses cc,Student s "
				+ "where cc.classId = s.classId and s.stuId = ? and cc.courseId = c.courseId) ";
		hql = "select c from Course c,CourseClasses cc,Term t where c.courseId = cc.courseId "
				+ "and c.termId = t.termId and t.year = ? and t.term = ? "
				+ "and cc.classId = (select s.classId from Student s where s.stuId = ?) ";
		return courseDAO.find(hql, year, term, stuId);
	}

	/**
	 * 教师接口
	 */
	// 获取课程列表（1.根据学期 2.根据指定日期）
	@Override
	public List<Map> getCourseList(String termId, String data, String userId) {
		String hql = "select c.courseId as courseId,c.courseName as courseName,cl.className as className";
		List<Map> list = null;

		if (termId != null && data == null) {// 根据学期获取课程列表
			hql += " from Course c,Classes cl,CourseClasses cc where c.courseId=cc.courseId and cc.classId=cl.classId and c.termId=? and c.userId=?";
			list = courseDAO.findMap(hql, termId, userId);
		}

		if (data != null && termId == null) {// 根据指定日期获取课程列表
			/**
			 * 1. 判断日期参数所属的学期ID，并计算date属于本学期的第几周，及data是周几。 2.
			 * 根据userId,查询出该用户本学期内的课程。 3. 根据data是周几，筛选出当天的课程。 4.
			 * 根据课程ID，查询课程的开始日期和结束日期，筛选出课程ID。 5.
			 * 根据课程ID，查询课程的重复周期。判断当前日期是否在重复周期内，筛选出符合条件的课程。 6.
			 * 查询出课程的开始时间，结束时间，上课教室。
			 */
			String startDate = (String) termServiceImpl.getCurrentTerm(userId).get("startDate");// 获取当前学期的开始日期时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				// 计算指定日期与开始日期相隔天数
				Date beginDate = sdf.parse(startDate);
				Date endDate = sdf.parse(data);
				long dateNum = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24) + 1;
				System.out.println("相隔天数：------------" + dateNum);
				// 计算指定日期是星期几
				Calendar endWeek = Calendar.getInstance();
				endWeek.setTime(sdf.parse(data));
				int endDay = 0;
				if (endWeek.get(Calendar.DAY_OF_WEEK) == 1) {
					endDay = 7;
				} else {
					endDay = endWeek.get(Calendar.DAY_OF_WEEK) - 1;
				}
				System.out.println("星期几：--------------" + endDay);
				// 计算指定日期属于第几周
				int weekNum = 0;
				if (dateNum % 7 == 0) {
					if (endDay != 1) {
						weekNum = (int) ((dateNum / 7) + 1);
					} else {
						weekNum = (int) (dateNum / 7);
					}
				} else {
					if (dateNum % 7 > (8 - endDay)) {
						weekNum = (int) ((dateNum / 7) + 2);
					} else {
						weekNum = (int) ((dateNum / 7) + 1);
					}
				}
				System.out.println("第几周：--------------" + weekNum);
				// 判断指定日期是否在上课周内
				// String hql1="select c.courseId from Course c,CourseItem ci
				// where "+
				// "c.courseId=ci.courseId and ci.startWeek <= ? and ci.endWeek
				// >= ? and c.userId= ?";
				// List<Map> l1=courseDAO.findMap(hql1, weekNum,weekNum,userId);
				// System.out.println("length:-----------------"+l1.get(0));
				// if(l1!=null && l1.size()>0){
				// hql+=",ccell.weekDay as weekDay,ccell.lessonNumber as
				// lessonNumber,ccell.location as location,ccell.classRoom as
				// classRoom from Course c,Classes cl,CourseClasses
				// cc,CourseItem ci,CourseCell ccell where "+
				// "c.courseId=cc.courseId and cc.classId=cl.classId and
				// ci.courseId=c.courseId and ci.ciId=ccell.ciId and c.userId=?
				// and ccell.weekDay=?";
				// list=courseDAO.findMap(hql, userId, endDay+"");
				// }
				// 根据用户ID，筛选出符合当前条件的课程ID
				String cql = "select ci.courseId as courseId, ci.repeatType as repeatType, cc.weekDay as weekDay, "
						+ "ci.startWeek as startWeek, ci.endWeek as endWeek, ci.startDay as startDay, "
						+ "ci.endDay as endDay, ci.repeatNumber as repeatNumber, cc.lessonNumber as lessonNumber "
						+ "from CourseCell cc, Course c, CourseItem ci where "
						+ "cc.ciId=ci.ciId and ci.courseId=c.courseId " + "and c.userId = ? ";
				List<Map> courseList = courseItemDAO.findMap(cql, userId);
				System.out.println("根据用户ID，筛选出符合当前条件的课程:" + courseList.size() + courseList.get(0).toString());
				// 根据课程的重复周期，重复类型筛选符合条件的课程
				ArrayList<String> cIdList = new ArrayList<>();// 定义数组，存放符合条件的课程ＩＤ
				for (int i = 0; i < courseList.size(); i++) {
					// 1.“天”重复：根据开始日期、结束日期进行判断
					if (EteacherConstants.COURSETABLE_REPEATTYPE_DAY.equals(courseList.get(i).get("repeatType"))) {
						String lastDay = (String) courseList.get(i).get("endDay");
						String startDay = (String) courseList.get(i).get("startDay");

						int repeatNum = (int) courseList.get(i).get("repeatNumber");
						SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
						Date d = sim.parse(data);
						Date sd = sim.parse(startDay);
						Date ld = sim.parse(lastDay);
						if (sd.getTime() <= d.getTime() && ld.getTime() >= d.getTime()) {
							// 根据课程的重复数字，对课程进行筛选
							// 获取当前时间与课程开始时间相差的天数
							int days = (int) Math.abs((d.getTime() - sd.getTime()) / (24 * 60 * 60 * 1000));
							System.out.println("days:" + days);
							if (days % repeatNum == 0) {
								System.out.println("符合条件");
								cIdList.add((String) courseList.get(i).get("courseId"));
							}
						}
					}
					// 2.“周”重复：根据开始周，结束周，周几进行判断
					else if (EteacherConstants.COURSETABLE_REPEATTYPE_WEEK
							.equals(courseList.get(i).get("repeatType"))) {
						int start = (int) courseList.get(i).get("startWeek");
						int end = (int) courseList.get(i).get("endWeek");
						String week = (String) courseList.get(i).get("weekDay");
						if (start <= weekNum && end >= weekNum && week.contains(Integer.toString(endDay))) {
							// 根据 当前日期为课程开始后的第几周，判断当前日期是否为课程的上课周
							int startWeek = (int) courseList.get(i).get("startWeek");
							int repeatNum = (int) courseList.get(i).get("repeatNumber");
							if ((weekNum - startWeek) % repeatNum == 0) {
								cIdList.add((String) courseList.get(i).get("courseId"));
							}
						}

					}
				}
				System.out.println("有效数据：" + cIdList.size());
				// 返回用户需要的数据：课程名称，上课时间，上课地点
				if (cIdList.size() > 0 && cIdList != null) {
					List<Map> courses = new ArrayList<>();
					String hql2 = "select c.courseId as courseId, c.courseName as courseName, cc.location as location, "
							+ "cc.classRoom as classRoom, cc.lessonNumber as lessonNumber "
							+ "from Course c, CourseCell cc, CourseItem ci "
							+ "where c.courseId = ci.courseId and cc.ciId=ci.ciId and c.courseId = ? ";
					for (int i = 0; i < cIdList.size(); i++) {
						System.out.println("....." + cIdList.get(i));
						Map m = courseDAO.findMap(hql2, cIdList.get(i)).get(0);
						System.out.println("今日课程" + i + ":" + m.toString());
						courses.add(m);
					}
					return courses;
				} else {
					return null;
				}

				/*
				 * //查询用户的所有课程 String clql =
				 * "select c.courseId as from Course c where c.userId = ?";
				 * List<Map> clist = courseDAO.findMap(clql, userId);
				 * //根据课程ID查询出当天要上的课程 String tc =
				 * "select c.courseId as courseId, c.courseName as courseName, cc.location as location, "
				 * +
				 * "cc.classRoom as classRoom, cc.lessonNumber as lessonNumber "
				 * + "from CourseItem ci, CourseCell cc, Course c " +
				 * "where cc.ciId = ci.ciId and ci.courseId = c.courseId" +
				 * "and ci.repeatType = "
				 * +EteacherConstants.COURSETABLE_REPEATTYPE_DAY+
				 * " and ci.startDay <= ? " +
				 * "and ci.endDay >= ? and ? % repeatNum == 0 and courseId = ?";
				 * if(clist.size()>0 && clist!=null){ for(int i =
				 * 0;i<clist.size();i++){
				 * courseDAO.findMap(tc,data,data,days,clist.get(i).get(
				 * "courseId")); } }
				 */

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	/**
	 * 根据第几节课，获取上课时间（上课时间：3,4节课-->10:00-12:00）
	 * 
	 * @author macong
	 * @param lessonNumber
	 * @return
	 */
	/*
	 * private Map lessonConvert(String lessonNumber){ String[] lesson =
	 * lessonNumber.split(","); List<Map> m=null; String hql =
	 * "select tt.startTime as startTime, tt.endTime as endTime " +
	 * "form TimeTable tt where tt.lessonNumber = ?"; for(int
	 * i=0;i<lesson.length;i++){ List<Map> m1 = courseDAO.findMap(hql,
	 * lesson[i]);
	 * 
	 * } System.out.println("转换结果："+m); return null; }
	 */
	// 获取课程的详细信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getCourseDetail(String courseId, String status) {
		List<Map> list = null;
		String hql = "";
		if ("0".equals(status)) {
			// 获取基本信息
			hql = "select c.courseId as courseId,c.courseName as courseName,c.introduction as introduction,c.classHours as classHours,c.studentNumber as studentNumber,"
					+ "c.specialty as specialty,c.teachingMethodId as teachingMethodId,c.courseTypeId as courseTypeId,c.examinationModeId as examinationModeId,c.formula as formula,"
					+ "c.remindTime as remindTime from Course c where c.courseId=?";
			list = courseDAO.findMap(hql, courseId);
			// 获取班级信息
			String hql1 = "select cl.className from Classes cl,CourseClasses cc where cc.classId=cl.classId and cc.courseId=?";
			List<Object> list1 = courseDAO.find(hql1, courseId);
			List<Map> list2;
			if (list1 == null || list1.size() == 0) {
				list.get(0).put("className", null);
			} else {
				list.get(0).put("className", list1);
			}
			// 获取课程组成信息
			hql1 = "select cs.scoreName as scoreName,cs.scorePercent as scorePercent from CourseScore cs where cs.courseId=?";
			list2 = courseDAO.findMap(hql1, courseId);
			if (list2 == null || list2.size() == 0) {
				list.get(0).put("courseScore", null);
			} else {
				list.get(0).put("courseScore", list2);
			}
			// 获取主教材信息
			hql1 = "select t.textbookName as textbookName from Textbook t where t.courseId=? and t.textbookType=01";
			list1 = courseDAO.find(hql1, courseId);
			if (list1 == null || list1.size() == 0) {
				list.get(0).put("mainTextbook", null);
			} else {
				list.get(0).put("mainTextbook", list1.get(0));
			}
			// 获取辅助教材信息
			hql1 = "select t.textbookName as textbookName from Textbook t where t.courseId=? and t.textbookType=02";
			list1 = courseDAO.find(hql1, courseId);
			if (list1 == null || list1.size() == 0) {
				list.get(0).put("fuzhuTextBook", null);
			} else {
				list.get(0).put("fuzhuTextBook", list1);
			}
			// 获取资源信息
			hql1 = "select fileName as fileName,f.vocabularyId as vocabularyId from CustomFile f where f.dataId=?";
			list2 = courseDAO.findMap(hql1, courseId);
			if (list2 == null || list2.size() == 0) {
				list.get(0).put("customFile", null);
			} else {
				list.get(0).put("customFile", list2);
			}
			// 获取上课信息
			hql1 = "select ct.ctId as ctId,ct.weekDay as weekDay,ct.lessonNumber as lessonNumber,ct.location as location,ct.classRoom as classRoom,c.courseName "
					+ "from Course c,CourseItem ci,CourseCell ct where c.courseId=? and c.courseId=ci.courseId and ci.ciId=ct.ciId";
			list2 = courseDAO.findMap(hql1, courseId);
			if (list2 == null || list2.size() == 0) {
				list.get(0).put("courseTable", null);
			} else {
				list.get(0).put("courseTable", list2);
			}
			return list;
		}
		if ("1".equals(status)) {
			hql = "select cs.csId as csId,cs.scoreName as scoreName,cs.scorePercent as scorePercent,cs.scorePointId as scorePointId "
					+ "from CourseScore cs where cs.courseId=?";
		}
		if ("2".equals(status) || "3".equals(status)) {
			hql = "select t.textbookId as textbookId,t.textbookName as textbookName,t.author as author,t.publisher as publisher,"
					+ "t.edition as edition,t.isbn as isbn from Textbook t where t.courseId=? and ";
			if ("2".equals(status)) {
				hql += "t.textbookType=01";
			} else {
				hql += "t.textbookType=02";
			}
		}
		if ("4".equals(status)) {
			hql = "select f.fileId as fileId,fileName as fileName,f.vocabularyId as vocabularyId,f.fileAuth as fileAuth from CustomFile f "
					+ "where f.dataId=? and f.isCourseFile=1";
		}
		if ("5".equals(status)) {// 获取上课时间信息，其中courseId为ctId
			String hql2 = "select ci.repeatType as repeatType from CourseCell ct,CourseItem ci where ct.ctId=? and ct.ciId=ci.ciId";
			String type = (String) courseDAO.find(hql2, courseId).get(0);
			hql = "select ct.ctId as ctId,ct.weekDay as weekDay,ct.lessonNumber as lessonNumber,ct.location as location,ct.classRoom as classRoom,"
					+ "ci.repeatType as repeatType,ci.repeatNumber as repeatNumber,";
			if ("1".equals(type)) {
				hql += "ci.startDay as startTime,ci.endDay as endTime ";
			} else {
				hql += "ci.startWeek as startTime,ci.endWeek as endTime ";
			}
			hql += "from CourseCell ct,CourseItem ci where ct.ctId=? and ct.ciId=ci.ciId";
		}
		list = courseDAO.findMap(hql, courseId);
		return list;
	}

	// 修改教材教辅信息
	@Override
	public void updateTextbook(Textbook text) {
		textbookDAO.saveOrUpdate(text);

	}

	// 修改课程成绩组成项信息
	@Override
	public void updateCoursescore(CourseScorePrivate cs) {
		CourseScore score = courseScoreDAO.get(cs.getCsId());
		cs.setCourseId(score.getCourseId());
		cs.setCsOrder(score.getCsOrder());
		courseScoreDAO.saveOrUpdate(cs);
	}

	@Override
	public List<Map> getDictionaryByType(String type) {
		// String sql = ""
		return null;
	}

	@Override
	public int getStudentCountById(String CourseId) {
		// TODO Auto-generated method stub
		return 0;
	}
	//获取班级课表
	@Override
	public List<Map> getClassCourseTable(String classId,int page) {
		// TODO Auto-generated method stub
		String sql="SELECT c.COURSE_NAME as courseName,ce.WEEKDAY as weekDay, " +
				"ce.LESSON_NUMBER as lessonNumber,ce.LOCATION as location, " +
				"ce.CLASSROOM as classroom "+ 
				"FROM t_course_cell ce "+
				"INNER JOIN t_course_item ci ON ce.CI_ID=ci.CI_ID "+
				"INNER JOIN t_course c ON ci.COURSE_ID = c.COURSE_ID "+
				"INNER JOIN t_course_class cl ON c.COURSE_ID =cl.COURSE_ID "+
				"WHERE cl.CLASS_ID = ?";
		List<Map> list=courseDAO.findBySqlAndPage(sql,page*20, 20,classId);
		for(int i = 0;i< list.size();i++){
			System.out.println("map"+i+":"+list.get(i).toString());
		}
		return list;
	}
	

	/**
	 * 获取当前时间正在进行的课程（判断当前时间是否为教师的授课时间）
	 * 
	 * @author macong
	 * @param userId
	 * @param time
	 *            "2016-11-13 10:21:51"
	 */
	public Map getCurrentCourse(String userId, String times, Map school) {
		// 1.时间数据的处理 {times："2016-11-13
		// 10:21:51"-->date:2016-11-13,time:10:21:51}
		System.out.println("11111time:" + times);
		String[] t = times.split(" ");
		String date = t[0];
		String time = t[1];
		// 2.查询当前time对应的是第几节课
		String hql = "select tt.timetableId as timetableId, tt.lessonNumber as lessonNumber "
				+ "from TimeTable tt where tt.endTime>=? and tt.startTime<=? " + "and tt.schoolId=?";
		List<Map> timeTable = courseScoreDAO.findMap(hql, time, time, school.get("schoolId"));
		if (timeTable.size() > 0 && timeTable.get(0) != null) {
			// 3.查询出今天要上的课程
			List<Map> courseList = getCourseList(null, date, userId);
			// 4. 筛选出当前正在进行的课程
			String todayCourse = null;
			String nowLessonNumber = (String) timeTable.get(0).get("lessonNumber");
			if (null != courseList && courseList.size() > 0) {
				for (int i = 0; i < courseList.size(); i++) {
					todayCourse = (String) courseList.get(i).get("lessonNumber");
					if (todayCourse.indexOf(nowLessonNumber) >= 0) {
						return courseList.get(i);
					} else {
						return null;// 不在上课时间内
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}

