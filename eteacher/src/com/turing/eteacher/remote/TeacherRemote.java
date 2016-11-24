package com.turing.eteacher.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseCell;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.TermPrivate;
import com.turing.eteacher.model.UserCommunication;
import com.turing.eteacher.service.ICourseCellService;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.service.ITermPrivateService;
import com.turing.eteacher.service.IUserCommunicationService;
import com.turing.eteacher.util.DateUtil;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class TeacherRemote extends BaseRemote {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	private ITeacherService teacherServiceImpl;

	@Autowired
	private IUserCommunicationService userCommunicationServiceImpl;
	
	@Autowired
	private ITermPrivateService termPrivateServiceImpl;
	
	@Autowired
	private ICourseCellService courseCellServiceImpl;
	/**
	 * 获取某门课程的授课教师的信息 学生端功能
	 * 
	 * @param courseId
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : {
	// teacherId : '教师ID'
	// name : '教师姓名',
	// title : '职称',
	// post : '职务',
	// phone : '联系电话',
	// qq : 'QQ',
	// weixin : '微信',
	// introduction : '教师简介'
	// },
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "student/courses/{courseId}/teacher", method = RequestMethod.GET)
	public ReturnBody courseTeacher(@PathVariable String courseId) {
		try {
			Course course = courseServiceImpl.get(courseId);
			Teacher teacher = teacherServiceImpl.get(course.getUserId());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, teacher);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 获取学生列表（签到学生列表、未签到学生列表、快速搜索）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacher/activity/getStuList", method = RequestMethod.GET)
	public ReturnBody getStuList(HttpServletRequest request) {
		String status = request.getParameter("status");
		String courseId = request.getParameter("course_id");
		if (StringUtil.isNotEmpty(status)) {
			if (status.equals("0")) {

			} else if (status.equals("1")) {

			} else if (status.equals(2)) {
				String target = request.getParameter("target");
				if (StringUtil.isNotEmpty(target)) {

				} else {
					return new ReturnBody(ReturnBody.RESULT_FAILURE, "搜索内容不能为空");
				}
			}
		}
		return null;
	}

	/**
	 * 获取（教师）用户的个人信息
	 * 
	 * @author macong
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : {
	// name : '姓名',
	// teacherNO : '教工号',
	// sex : '性别',
	// titleName : '职称',
	// postName : '职务',
	// schoolId : '学校Id',
	// schoolName : '学校名称',
	// department : '院系',
	// introduction : '教师简介'
	// },
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "teacher/personInfo", method = RequestMethod.POST)
	public ReturnBody getPersonInfo(HttpServletRequest request) {
		try {
			String userId = request.getParameter("userId");
			Map teacherInfo = teacherServiceImpl.getUserInfo(userId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, teacherInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * （教师）用户的个人信息的编辑操作
	 * 
	 * @author macong
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : {
	// name : '姓名',
	// teacherNO : '教工号',
	// sex : '性别',
	// titleName : '职称',
	// postName : '职务',
	// schoolId : '学校Id',
	// schoolName : '学校名称',
	// department : '院系',
	// introduction : '教师简介'
	// },
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "teacher/editPersonInfo", method = RequestMethod.POST)
	public ReturnBody editPersonInfo(HttpServletRequest request, Teacher teacher) {
		try {
			String userId = request.getParameter("userId");
			teacher.setTeacherId(userId);
			// Teacher t = teacherServiceImpl.get(userId);
			// if(!t.equals(teacher)){
			System.out.println(teacher);
			teacherServiceImpl.saveOrUpdate(teacher);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, "true");
			// }
			// return new ReturnBody(ReturnBody.RESULT_SUCCESS,"");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 获取课程列表
	 * 
	 * @author lifei
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacher/getCourseList", method = RequestMethod.POST)
	public ReturnBody getCourseList(HttpServletRequest request) {
		String userId = getCurrentUserId(request);
		String page = request.getParameter("page");
		if (StringUtil.checkParams(userId, page)) {
			List list = teacherServiceImpl.getCourseList(userId,
					Integer.parseInt(page));
			return new ReturnBody(list);
		} else {
			return ReturnBody.getParamError();
		}
	}

	/**
	 * 获取（教师）用户的联系方式（邮箱、电话、ＩＭ）
	 * 
	 * @author macong
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : {
	// type : 1, //1.电话 2.邮箱 3.IM
	// name : '移动电话',
	// value : '15631223549',
	// status : '0',//0：无意义 1：邮箱绑定
	// }
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "teacher/getCommunicationList", method = RequestMethod.POST)
	public ReturnBody getCommunicationList(HttpServletRequest request) {
		try {
			String userId = request.getParameter("userId");
			int type = Integer.parseInt(request.getParameter("type"));
			List<Map> list = userCommunicationServiceImpl.getComByUserId(
					userId, type);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 新增联系方式（邮箱、电话、ＩＭ）
	 * 
	 * @author macong
	 * @param type
	 * @param name
	 * @param value
	 * @param status
	 */
	@RequestMapping(value = "teacher/addCommunication", method = RequestMethod.POST)
	public ReturnBody addCommunication(HttpServletRequest request,
			UserCommunication userCommunication) {
		try {
			String userId = request.getParameter("userId");
			String name = request.getParameter("name");
			String value = request.getParameter("value");
			int status = Integer.parseInt(request.getParameter("status"));
			int type = Integer.parseInt(request.getParameter("type"));

			userCommunication.setUserId(userId);
			userCommunication.setName(name);
			userCommunication.setValue(value);
			userCommunication.setStatus(status);
			userCommunication.setType(type);
			userCommunicationServiceImpl.save(userCommunication);

			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 删除联系方式（邮箱、电话、ＩＭ）
	 * 
	 * @author macong
	 * @param type
	 * @param name
	 * @param value
	 * @param status
	 */
	@RequestMapping(value = "teacher/delCommunication", method = RequestMethod.POST)
	public ReturnBody delCommunication(HttpServletRequest request) {
		try {
			String itemId = request.getParameter("itemId");
			userCommunicationServiceImpl.deleteById(itemId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 1.2.16 获取指定月份有课程的日期
	 * 
	 * @author lifei
	 */
	@RequestMapping(value = "teacher/Course/getWorkday", method = RequestMethod.POST)
	public ReturnBody getWorkday(HttpServletRequest request) {
		String ym = request.getParameter("month");
		if (StringUtil.checkParams(ym)) {
			List<Map> result = new ArrayList<>(); 
			String cLastDay = DateUtil.getLastDayOfMonth(ym);
			String cFirstDay = ym + "-01";
			List<TermPrivate> list = termPrivateServiceImpl.getListByUserId(getCurrentUserId(request));
			// TODO 查看指定月是否在所创建的学期内
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					TermPrivate item = list.get(i);
					if (DateUtil.isOverlap(cFirstDay, cLastDay, item.getStartDate(), item.getEndDate())) {
						// TODO 如果在则查找本学学期内的课程
						List<Map> list2 = courseServiceImpl.getCourseByTermId(getCurrentUserId(request),item.getTpId());
						if (null != list2) {
							// TODO 查找本学期内天重复的的课程 List<Course> && 开始时间<本月月末 && 结束时间>本月月初
							for (int j = 0; j < list2.size(); j++) {
								Map map = list2.get(j);
								if (map.get("repeatType").equals("01")) {
									if (DateUtil.isOverlap(cFirstDay, cLastDay, (String)map.get("startDay"), (String)map.get("endDay"))) {
										// TODO 通过重复规律算出天重复的具体日期
										int repeatNumber = (int)map.get("repeatNumber");
										int distance = DateUtil.getDayBetween((String)map.get("startDay"), (String)map.get("endDay"));
										int repeat = distance / repeatNumber;
										for (int k = 0; k <= repeat; k++) {
											String date = DateUtil.addDays((String)map.get("startDay"), k*repeatNumber);
											if (DateUtil.isInRange(date, cFirstDay, cLastDay)) {
												Map<String, String> m = new HashMap<>(); 
												m.put("date", date);
												if (!result.contains(m)) {
													result.add(m);
												}
										   }
										}
									}
								}else{
									//周重复
									String start = DateUtil.addSpecialWeeks(item.getStartDate(), (int)map.get("startWeek")-1);
									String end = DateUtil.addSpecialWeeks(item.getStartDate(), ((int)map.get("endWeek"))-1);
									end = DateUtil.addDays(end, 6);
									if (DateUtil.isBefore(item.getEndDate(), end)) {
										end = item.getEndDate();
									}
									if (DateUtil.isOverlap(cFirstDay, cLastDay, start, end)) {
										List<CourseCell> list3 = courseCellServiceImpl.getCells((String)map.get("ciId"));
										if (null != list3) {
											for (int k = 0; k < list3.size(); k++) {
												CourseCell cell = list3.get(k);
												if (null != cell.getWeekDay()) {
													String[] week = cell.getWeekDay().split(",");
													for (int l = 0; l < week.length; l++) {
														int repeatNumber = (int)map.get("repeatNumber");
														int repeatCount = ((int)map.get("endWeek") - (int)map.get("startWeek"))/repeatNumber;
														for (int m = 0; m <= repeatCount; m++) {
															String dateStr = DateUtil.getWeek(start, m*repeatNumber, Integer.parseInt(week[l]));
															if (null != dateStr) {
																if (DateUtil.isBefore(dateStr,item.getEndDate()) && DateUtil.isInRange(dateStr, cFirstDay, cLastDay)) {
																	Map<String, String> n = new HashMap<>(); 
																	n.put("date", dateStr);
																	if (!result.contains(n)) {
																		result.add(n);
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
						}else {
							//没有相关课程
							return ReturnBody.getErrorBody("请先创建学期！");
						}
					}
				}
			}else {
				//没有创建学期
				return ReturnBody.getErrorBody("请先创建学期！");
			}
			return new ReturnBody(result);
		} else {
			return ReturnBody.getParamError();
		}
		// 1、筛选课程
		// TODO 算出本月第一天所在本学期的周数
		// TODO 算出本月最后一天所在本学期的周数
		// TODO 查找本学期内周重复的课程List<Course> && 开始周<本月结束周 && 结束周>本月开始周
		// TODO 根据周重复规律算出周重复的具体日期
		// 1、筛选作业
	}
}
