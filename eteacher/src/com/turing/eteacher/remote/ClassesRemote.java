package com.turing.eteacher.remote;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.service.IClassService;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class ClassesRemote extends BaseRemote {

		@Autowired
		private IClassService classServiceImpl;
		
		@Autowired
		private ITermService termServiceImpl;
		/**
		 * 获取用户当前学期所有课程对应的班级列表
		 * @param request
		 * @return
		 */
		@RequestMapping(value="teacher/classes/getClassList",method=RequestMethod.POST)
		public ReturnBody getClassList(HttpServletRequest request){
			try{
		 		String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
		    	String tpId=getCurrentTerm(request)==null?null:(String)getCurrentTerm(request).get("termId");
		    	String page=request.getParameter("page");
			  	System.out.println("0000  " +tpId);
				List list=classServiceImpl.getClassList(userId,tpId,Integer.parseInt(page));
				return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
			}
			catch(Exception e){
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
			}
		}
	/**
	 * 获取用户当前学期所有课程对应的班级列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="teacher/classes/getClassListByMajor",method=RequestMethod.POST)
		public ReturnBody getClassListByMajor(HttpServletRequest request){
				String majorId = request.getParameter("major");
				String page = request.getParameter("page");
				Teacher teacher = getCurrentTeacher(request);
				if (null != teacher && StringUtil.checkParams(page, teacher.getSchoolId())) {
					int endTime = Calendar.getInstance().get(Calendar.YEAR);
					List list = classServiceImpl.getClassListByUser(
							teacher.getSchoolId(), endTime, majorId,
							Integer.parseInt(page));
					System.out.println(list.toString());
					return new ReturnBody(list);
				} else {
					return ReturnBody.getParamError();
				}
		}
}
