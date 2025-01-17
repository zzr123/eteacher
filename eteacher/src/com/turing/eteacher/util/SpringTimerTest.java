package com.turing.eteacher.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseCell;
import com.turing.eteacher.model.Notice;
import com.turing.eteacher.model.PushMessage;
import com.turing.eteacher.model.TaskModel;
import com.turing.eteacher.model.TimeTable;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.service.ICourseCellService;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.INoticeService;
import com.turing.eteacher.service.IRegistConfigService;
import com.turing.eteacher.service.ISignInService;
import com.turing.eteacher.service.ITermPrivateService;
import com.turing.eteacher.service.ITimeTableService;
import com.turing.eteacher.service.IWorkService;
import com.turing.eteacher.service.impl.SignInServiceImpl;

/**
 * Spring定时器
 */
@Component
// 将本类完成bean创建和自动依赖注入
public class SpringTimerTest {

	@Autowired
	private INoticeService noticeServiceImpl;
	
	@Autowired
	private ITermPrivateService termPrivateServiceImpl;
	
	@Autowired
	private ICourseCellService courseCellServiceImpl;
	
	@Autowired
	private ITimeTableService timeTableServiceImpl;
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private IWorkService workServiceImpl;
	
	@Autowired
	private IRegistConfigService registConfigServiceImpl;
	
	@Autowired
	private ISignInService signInServiceImpl;
	
	private List<TaskModel> allList = new ArrayList<>();

	/**
	 * Spring定时器测试方法
	 * 
	 * @author lifei
	 */
	@Scheduled(cron = "0 0/20 22,23 * * ?")
	// 每天凌晨触发//0 0 0 * * ?
	public void test() {
		System.out.println(new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒").format(new Date()));
		allList.clear();
		allList.addAll(getCurrentDayCourseStartTime());
		allList.addAll(getNoticeList());
		allList.addAll(getHomeWorkList());
	    Collections.sort(allList, new MyComparator());
	    for (int i = 0; i < allList.size(); i++) {
			System.out.println("第"+i+"个通知："+allList.get(i).toString());
		}
		timer();
	}
	
	/**
	 * 通知排序
	 * @author lifei
	 *
	 */
    class MyComparator implements Comparator<TaskModel>
    {
        public int compare(TaskModel m1, TaskModel m2)
        {
        	if (DateUtil.isBefore(m1.getDate(), m2.getDate(), DateUtil.YYYYMMDDHHMM)) {
				return -1;
			}else{
				return 1;
			}
        }
    }

	/**
	 * 获取当天待通知的列表
	 */
	private List<TaskModel> getNoticeList() {
		List<TaskModel> tempList = new ArrayList<>();
		List<Map> list = noticeServiceImpl.getDateLimitNotice(DateUtil
				.getCurrentDateStr("yyyy-MM-dd HH:mm"), DateUtil.addDays(
				DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm"), 1));
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				TaskModel temp = new TaskModel();
				temp.setDate((String) list.get(i).get("time"));
				temp.setId((String) list.get(i).get("id"));
				temp.setType(TaskModel.TYPE_NOTICE);
				temp.setUserType(TaskModel.UTYPE_STUDENT);
				tempList.add(temp);
			}
		}
		return tempList;
	}
	
	/**
	 * 获取当天要发布的作业提醒
	 * @return
	 */
	private List<TaskModel> getHomeWorkList() {
		List<TaskModel> tempList = new ArrayList<>();
		List<Map> list = workServiceImpl.getDateLimitHomeWork(DateUtil
				.getCurrentDateStr("yyyy-MM-dd HH:mm"), DateUtil.addDays(
						DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm"), 1));
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				TaskModel temp = new TaskModel();
				temp.setDate((String) list.get(i).get("time"));
				temp.setId((String) list.get(i).get("id"));
				temp.setType(TaskModel.TYPE_HOMEWORK_PUBLISH);
				temp.setUserType(TaskModel.UTYPE_STUDENT);
				tempList.add(temp);
			}
		}
		System.out.println("作业提醒有："+tempList.size()+"条");
		return tempList;
	}
	
	/**
	 * 获取当天所有课程的提醒时间
	 * 
	 * @return
	 */
	private List<TaskModel> getCurrentDayCourseStartTime() {
		String now = DateUtil.getCurrentDateStr(DateUtil.YYYYMMDDHHMM);
		List<Map> list = termPrivateServiceImpl.getContainDateList(now, now);
		//System.out.println("*与今天有交集的学期："+list.size());
		List<TaskModel> result = new ArrayList<>(); 
		for (int i = 0; i < list.size(); i++) {
			Map term = list.get(i); 
			//System.out.println("*有交集的学期Id:"+term.get("tpId"));
			List<Map> list2 = courseServiceImpl.getCourseByTermId((String)term.get("tpId"));
			if (null != list2) {
				//System.out.println("*该学期下的课程数："+list2.size());
				for (int j = 0; j < list2.size(); j++) {
					Map map = list2.get(j);
					//System.out.println("*课程类型："+map.get("repeatType"));
					//天循环的课程
					if (map.get("repeatType").equals("01")) {
						//System.out.println("*有天循环的课程");
						//判断课程的开始结束时间是否与今天有交集
						//System.out.println("*课程的开始结束时间："+map.get("startDay")+ "   "+map.get("endDay"));
						if (DateUtil.isOverlap2(now, now, (String)map.get("startDay"), (String)map.get("endDay"))) {
							//System.out.println("*课程有重复因子");
							//课程重复天数
							int repeatNumber = (int)map.get("repeatNumber");
							//该课程一共有多少天
							int distance = DateUtil.getDayBetween((String)map.get("startDay"), (String)map.get("endDay"));
							//一共上几次课
							int repeat = distance / repeatNumber;
							for (int k = 0; k <= repeat; k++) {
								//每次上课的具体日期
								String date = DateUtil.addDays((String)map.get("startDay"), k*repeatNumber);
								//判断是否上课时间在指定月份里
								//System.out.println("*上课时间："+date);
								if (date.equals(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD))) {
									//System.out.println("*今天有要上的课："+map.get("courseId"));
									//获取课程的重复规律
									List<CourseCell> list3 = courseCellServiceImpl.getCells((String)map.get("ciId"));
									if (null != list3 && list3.size() > 0) {
										//System.out.println("*courseCell的个数："+list3.size());
										for (int l = 0; l < list3.size(); l++) {
											//获取上课时间集合
											String[] lessions = list3.get(l).getLessonNumber().split(",");
											for (int m = 0; m < lessions.length; m++) {
												TimeTable timeTable = timeTableServiceImpl.getItemBySchoolId((String)term.get("schoolId"), lessions[m]);
												Map registMap = registConfigServiceImpl.getRegistTimeByCourseId((String)map.get("courseId"));
												if (null != timeTable) {
													int remind = Integer.parseInt(((String)map.get("remindTime")).trim());
													String remindTime = DateUtil.deleteMinutes(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD)+" "+timeTable.getStartTime(),remind);
													//System.out.println("*课程的提醒时间："+remindTime);
													int regist = 10;
													if (null != registMap) {
														regist = (int)registMap.get("registTime");
													}
													String registTime = DateUtil.deleteMinutes(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD)+" "+timeTable.getStartTime(),regist);
													//System.out.println("*课程的签到时间："+registTime);
													if (DateUtil.isBefore(now,remindTime, DateUtil.YYYYMMDDHHMM)) {
														TaskModel model = new TaskModel();
														model.setDate(remindTime);
														model.setId((String)map.get("courseId"));
														model.setType(TaskModel.TYPE_COURSE_START_REMIND);
														model.setUserType(TaskModel.UTYPE_TEACHER);
														result.add(model);
													}
													if (DateUtil.isBefore(now,registTime, DateUtil.YYYYMMDDHHMM)) {
														TaskModel model1 = new TaskModel();
														model1.setDate(registTime);
														model1.setId((String)map.get("courseId"));
														model1.setType(TaskModel.TYPE_SIGN_IN);
														model1.setUserType(TaskModel.UTYPE_TEACHER);
														result.add(model1);
													}
												}
											}
										}
									}
							   }
							}
						}
					}else{
						//获取周重复课程的开始时间
						String start = DateUtil.addSpecialWeeks((String)term.get("startDate"), (int)map.get("startWeek")-1);
						//获取周重复课程结束周的周一
						String end = DateUtil.addSpecialWeeks((String)term.get("startDate"), ((int)map.get("endWeek"))-1);
						//获取周重复课程结束周的周日
						//System.out.println("*周重复的开始时间和结束时间："+start+"   "+end);
						end = DateUtil.addDays(end, 6);
						//是否如果学期在周日前结束 则课程结束日期为学期最后一天
						if (DateUtil.isBefore((String)term.get("endDate"), end,DateUtil.YYYYMMDD)) {
							end = (String)term.get("endDate");
						}
						//查看课程是否与指定的月份有交集
						if (DateUtil.isOverlap2(now, now, start, end)) {
							//System.out.println("*周重复有交集的");
							//获取课程的重复规律
							List<CourseCell> list3 = courseCellServiceImpl.getCells((String)map.get("ciId"));
							if (null != list3) {
								for (int k = 0; k < list3.size(); k++) {
									CourseCell cell = list3.get(k);
									if (null != cell.getWeekDay()) {
										//查看具体课程在周几上课
										String[] week = cell.getWeekDay().split(",");
										for (int l = 0; l < week.length; l++) {
											//课程的间隔周期
											int repeatNumber = (int)map.get("repeatNumber");
											//课程一共上几周
											int repeatCount = ((int)map.get("endWeek") - (int)map.get("startWeek"))/repeatNumber;
											for (int m = 0; m <= repeatCount; m++) {
												//获取课程具体在指定星期的上课时间
												String dateStr = DateUtil.getWeek(start, m*repeatNumber, Integer.parseInt(week[l]));
												if (null != dateStr) {
													//如果上课时间在学期内&&在所指定的月份内
													if (DateUtil.isBefore(dateStr,(String)term.get("endDate"),DateUtil.YYYYMMDD) && dateStr.equals(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD))) {
														//获取上课时间集合
														String[] lessions = cell.getLessonNumber().split(",");
														if (null != lessions) {
															for (int n = 0; n < lessions.length; n++) {
																TimeTable timeTable = timeTableServiceImpl.getItemBySchoolId((String)term.get("schoolId"), lessions[n]);
																Map registMap = registConfigServiceImpl.getRegistTimeByCourseId((String)map.get("courseId"));
																if (null != timeTable) {
																	int remind = Integer.parseInt(((String)map.get("remindTime")).trim());
																	String remindTime = DateUtil.deleteMinutes(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD)+" "+timeTable.getStartTime(),remind);
																	int regist = 10;
																	if (null != registMap) {
																		regist = (int)registMap.get("registTime");
																	}
																	String registTime = DateUtil.deleteMinutes(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDD)+" "+timeTable.getStartTime(),regist);
																	if (null != remindTime) {
																		if (DateUtil.isBefore(now,remindTime, DateUtil.YYYYMMDDHHMM)) {
																			TaskModel model = new TaskModel();
																			model.setDate(remindTime);
																			model.setId((String)map.get("courseId"));
																			model.setType(TaskModel.TYPE_COURSE_START_REMIND);
																			model.setUserType(TaskModel.UTYPE_TEACHER);
																			result.add(model);
																		}
																		if (DateUtil.isBefore(now,registTime, DateUtil.YYYYMMDDHHMM)) {
																			TaskModel model1 = new TaskModel();
																			model1.setDate(registTime);
																			model1.setId((String)map.get("courseId"));
																			model1.setType(TaskModel.TYPE_SIGN_IN);
																			model1.setUserType(TaskModel.UTYPE_TEACHER);
																			result.add(model1);
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("上课提醒有："+result.size()+"条");
		return result;
	}

	private void timer() {
		if (allList.size() == 0) {
			return;
		}
		final TaskModel model = allList.get(0);
		allList.remove(0);
		if (DateUtil.isBefore(model.getDate(),
				DateUtil.getCurrentDateStr(DateUtil.YYYYMMDDHHMM),
				DateUtil.YYYYMMDDHHMM)) {
				doPush(model);
		} else {
			long i = DateUtil.getTimeBetween(DateUtil.getCurrentDateStr(DateUtil.YYYYMMDDHHMM), model.getDate());
			System.out.println("i:"+i);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					doPush(model);
					timer();
				}
			}, i);
		}
	}
	/**
	 * 发起推送
	 */
	private void doPush(TaskModel model){
		switch (model.getType()) {
		case TaskModel.TYPE_NOTICE:
			pushNotice(model);
			break;
		case TaskModel.TYPE_COURSE_START_REMIND:
			pushCourseStartRemind(model);
			break;
		case TaskModel.TYPE_HOMEWORK_PUBLISH:
			pushHomeWorkPublish(model);
			break;
		case TaskModel.TYPE_SIGN_IN:
			pushSignIn(model);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 发送通知推送
	 * @param model
	 */
	private void pushNotice(TaskModel model){
		Notice notice = noticeServiceImpl.get(model.getId());
		String schoolId = noticeServiceImpl.getSchoolIdbyNoticeId(model.getId()); 
		List<Map> list = noticeServiceImpl.getClassIdByNoticeId(model.getId());
		String classIds = ""; 
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				classIds += list.get(i).get("classId")+",";
			}
			classIds = classIds.substring(0, classIds.length()-1);
		}
		PushMessage message = new PushMessage();
		message.setAction(JPushUtil.ACTION_NOTICE_DETAIL);
		message.setTitle(notice.getTitle());
		message.setContent(notice.getContent());
		message.setShow(JPushUtil.SHOW_ON);
		message.setUserType(model.getUserType());
		message.setSchoolId(schoolId);
		message.setClassId(classIds);
		Map<String, String> map = new HashMap();
		map.put("noticeId", model.getId());
		map.put("flag", "1");
		message.setExtra(map);
		JPushUtil.pushMessage(message);
		System.out.println("message:"+message.toString());
		System.out.println("执行推送啦");
	}
	/**
	 * 发送作业发布推送
	 * @param model
	 */
	private void pushHomeWorkPublish(TaskModel model){
		Work work = workServiceImpl.get(model.getId());
		String schoolId = workServiceImpl.getSchoolIdbyWorkId(model.getId()); 
		List<Map> list = workServiceImpl.getClassIdByWorkId(model.getId());
		String classIds = ""; 
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				classIds += list.get(i).get("classId")+",";
			}
			classIds = classIds.substring(0, classIds.length()-1);
		}
		PushMessage message = new PushMessage();
		message.setAction(JPushUtil.ACTION_HOMEWORK_DETAIL);
		message.setTitle("有新作业啦！");
		message.setContent(work.getContent());
		message.setShow(JPushUtil.SHOW_ON);
		message.setUserType(model.getUserType());
		message.setSchoolId(schoolId);
		message.setClassId(classIds);
		Map<String, String> map = new HashMap();
		map.put("workId", model.getId());
		message.setExtra(map);
		JPushUtil.pushMessage(message);
		System.out.println("message:"+message.toString());
		System.out.println("执行推送啦");
	}
	
	/**
	 * 发送课程上课提醒推送（教师端）
	 * @param model
	 */
	private void pushSignIn(TaskModel model){
		Course course = courseServiceImpl.get(model.getId());
		Map map = courseServiceImpl.getSchoolIdbyCourseId(course.getCourseId());
		List<Map> list = noticeServiceImpl.getClassIdByNoticeId(model.getId());
		String classIds = ""; 
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				classIds += list.get(i).get("classId")+",";
			}
			classIds = classIds.substring(0, classIds.length()-1);
		}
		PushMessage message = new PushMessage();
		message.setAction(JPushUtil.ACTION_ALERT);
		message.setTitle(course.getCourseName()+" 快要上课啦！");
		message.setContent("提前"+course.getCourseId()+"进行课程提醒！");
		message.setShow(JPushUtil.SHOW_ON);
		if (null != map) {
			message.setSchoolId((String)map.get("schoolId"));
		}
		message.setClassId(classIds);
		message.setUserType(model.getUserType());
		JPushUtil.pushMessage(message);
		System.out.println("message:"+message.toString());
		System.out.println("执行推送啦");
	}
	/**
	 * 发送课程上课提醒推送（教师端）
	 * @param model
	 */
	private void pushCourseStartRemind(TaskModel model){
		Course course = courseServiceImpl.get(model.getId());
		Map map = courseServiceImpl.getSchoolIdbyCourseId(course.getCourseId());
		List<Map> list = noticeServiceImpl.getClassIdByNoticeId(model.getId());
		String classIds = ""; 
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				classIds += list.get(i).get("classId")+",";
			}
			classIds = classIds.substring(0, classIds.length()-1);
		}
		PushMessage message = new PushMessage();
		message.setAction(JPushUtil.ACTION_ALERT);
		message.setTitle(course.getCourseName()+" 快要上课啦！");
		message.setContent("提前"+course.getCourseId()+"进行课程提醒！");
		message.setUserId(course.getUserId());
		message.setShow(JPushUtil.SHOW_ON);
		if (null != map) {
			message.setSchoolId((String)map.get("schoolId"));
		}
		message.setClassId(classIds);
		message.setUserType(model.getUserType());
		JPushUtil.pushMessage(message);
		System.out.println("message:"+message.toString());
		System.out.println("执行推送啦");
		//课程上课次数+1
		signInServiceImpl.updateCourseNum(course.getCourseId());
	}
}