package com.turing.eteacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.Dictionary2Public;
import com.turing.eteacher.model.School;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IDictionary2PrivateService;
import com.turing.eteacher.service.IDictionary2PublicService;
import com.turing.eteacher.service.ISchoolService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.service.IUserCommunicationService;
import com.turing.eteacher.service.IUserService;
import com.turing.eteacher.service.impl.Dictionary2PublicServiceImpl;
import com.turing.eteacher.service.impl.SchoolServiceImpl;
import com.turing.eteacher.service.impl.UserCommunicationServiceImpl;
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
	private IDictionary2PrivateService Dictionary2PrivateServiceImpl;
	
	@Autowired
	private ISchoolService SchoolServiceImpl;
	
	@Autowired
	private IUserCommunicationService UserCommunicationServiceImpl;
	
	@RequestMapping("viewEidtUserInfo")
	public String viewEidtUserInfo(HttpServletRequest request) throws JsonProcessingException{
		Teacher teacher = (Teacher)request.getSession().getAttribute(EteacherConstants.CURRENT_TEACHER);
		request.setAttribute("teacher", teacher);
		
		//获取职、职务称信息
		String titleId = teacher.getTitleId();
		System.out.println("============="+titleId);
		if(null != titleId){
			Map title = Dictionary2PrivateServiceImpl.getValueById(titleId);
			request.setAttribute("title", title);
		}
		String postId = teacher.getPostId();
		if(null != postId){
			Map post = Dictionary2PrivateServiceImpl.getValueById(postId);
			request.setAttribute("post", post);
		}
		
		//获取学校信息
		String schoolId = teacher.getSchoolId();
		if(null!=schoolId){
			List<Map> schoolInfo = SchoolServiceImpl.getSchoolInfoById(schoolId);
			request.setAttribute("school", schoolInfo);
			//System.out.println(schoolInfo.get(0).toString());
		}
		//获取手机、邮箱、IM信息
		String userId = teacher.getTeacherId();
		List<Map> phones =UserCommunicationServiceImpl.getConByUserId(userId, 1);//电话
		if(null != phones && phones.size()>0){
		 	request.setAttribute("phones", phones);
		 }
		List<Map> emails =UserCommunicationServiceImpl.getConByUserId(userId, 2);//邮箱
		if(null != emails && emails.size()>0){
			request.setAttribute("emails", emails);
		}
		List<Map> IMs =UserCommunicationServiceImpl.getConByUserId(userId, 3);//IM
		if(null != IMs && IMs.size()>0){
			request.setAttribute("IMs", IMs);
		}
		
		return "user/eidtUserInfo";
	}
	
	@RequestMapping(value = "getCurrentTeacherJson")
	@ResponseBody
	public Teacher getCurrentTeacherJson(HttpServletRequest request){
		Teacher teacher = (Teacher) request.getSession().getAttribute(EteacherConstants.CURRENT_TEACHER);
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
	
	//学校的级联查询
	@RequestMapping("getSchoolSelectData")
	@ResponseBody
	public List<Map> getSchoolSelectData(HttpServletRequest request){
		int type = Integer.parseInt(request.getParameter("type"));
		String id = request.getParameter("id");
		System.out.println("type:"+type+"  ID:"+id);
		List<Map> list = SchoolServiceImpl.getListByParentType(type,id);
		for(int i=0;i<list.size();i++){
			System.out.println("结果："+list.get(i).toString());
		}
		return list;
	}

}
