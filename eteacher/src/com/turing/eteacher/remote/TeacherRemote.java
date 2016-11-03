package com.turing.eteacher.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class TeacherRemote extends BaseRemote {

	@Autowired
	private ICourseService courseServiceImpl;

	@Autowired
	private ITeacherService teacherServiceImpl;

	/**
	 * 获取某门课程的授课教师的信息
	 * 学生端功能
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
					
				}else {
					return new ReturnBody(ReturnBody.RESULT_FAILURE,"搜索内容不能为空");
				}
			}
		}
		return null;
	}
	/**
	 * 获取（教师）用户的个人信息
	 * @author macong
	 * @return
	 */
	// {
		// result : 'success',//成功success，失败failure
		// data : {
		// name : '姓名',
		// teacherNO : '教工号',
		// sex : '性别',
		// title : '职称',
		// post : '职务',
		// school : '学校',
		// department : '院系',
		// phone[] : '[{'移动电话'},{'15631223549'}]',
		// IM[] : '[{'QQ','214535086'},{'陌陌'},'521785648']',
		// email[] : [{'mxconleone@163.com'},0]
		// introduction : '教师简介'
		// },
		// msg : '提示信息XXX'
		// }
	@RequestMapping(value = "teacher/personInfo", method = RequestMethod.POST)
	public ReturnBody getPersonInfo(HttpServletRequest request) {
		try {
			String userId = request.getParameter("userId");
			Teacher teacher = teacherServiceImpl.get(userId);
			
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, teacher);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 获取课程列表
	 * @author lifei
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacher/getCourseList", method = RequestMethod.POST)
	public ReturnBody getCourseList(HttpServletRequest request) {
		String userId = getCurrentUserId(request);
		String page = request.getParameter("page");
		if(StringUtil.checkParams(userId,page)){
			List list = teacherServiceImpl.getCourseList(userId, Integer.parseInt(page));
			return new ReturnBody(list);
		}else {
			return ReturnBody.getParamError();
		}
	}
}
