package com.turing.eteacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.Dictionary2Public;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IDictionary2PublicService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.service.IUserService;
import com.turing.eteacher.service.impl.Dictionary2PublicServiceImpl;
import com.turing.eteacher.util.BeanUtils;
import com.turing.eteacher.util.StringUtil;
/**
 * 用户controller
 * @author caojian
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	private IUserService userServiceImpl;
	
	@Autowired
	private ITeacherService teacherServiceImpl;
	
	@Autowired
	private IDictionary2PublicService Dictionary2PublicServiceImpl;
	
	private String TYPE = null;
	
	@RequestMapping("viewEidtUserInfo")
	public String viewEidtUserInfo(HttpServletRequest request){
		//获取职称下拉列表
		User currentUser = getCurrentUser(request);
		TYPE="04";
		List<Map> titleList =Dictionary2PublicServiceImpl.getListByType(TYPE ,currentUser.getUserId());
		request.setAttribute("titleList", titleList);
		
		//邮箱手机之类的
		Teacher teacher = (Teacher)request.getSession().getAttribute(EteacherConstants.CURRENT_TEACHER);
		String[] emails = {};
		String email = teacher.getEmailId();
		if(StringUtil.isNotEmpty(email)){
			email.split("||");
		}
		request.setAttribute("emails", emails);
		return "user/eidtUserInfo";
	}
	
	@RequestMapping(value = "getCurrentTeacherJson")
	@ResponseBody
	public Teacher getCurrentTeacherJson(HttpServletRequest request){
		Teacher teacher = (Teacher)request.getSession().getAttribute(EteacherConstants.CURRENT_TEACHER);
		return teacher;
	}
	
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request, Teacher teacher){
		Teacher currentTeacher = (Teacher)request.getSession().getAttribute(EteacherConstants.CURRENT_TEACHER);
		BeanUtils.copyToModel(teacher, currentTeacher);
		teacherServiceImpl.update(currentTeacher);
		return "success";
	}
}
