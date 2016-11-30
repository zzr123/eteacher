package com.turing.eteacher.remote;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.service.ISignInService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class SignInRemote extends BaseRemote{
	@Autowired
	private ISignInService signInServiceImpl;
	/**
	 * 学生端功能：根据courseID，获取某课程的位置信息（所在市，学校，教学楼）
	 * @author macong
	 * 时间：2016年11月29日11:40:16
	 */
//	{
//	    "loaction": "逸夫楼"，
//	    "schoolName": "国防科技大学",
//	    "cityName": "石家庄市"
//	}
	@RequestMapping(value="signIn/getAddressInfo",method=RequestMethod.POST)
	public ReturnBody getAddressInfo(HttpServletRequest request){
		try{
	 		String courseId = request.getParameter("courseId");
	 		if(StringUtil.checkParams(courseId)){
	 			Map map = signInServiceImpl.getAddressInfo(courseId);
				return new ReturnBody(ReturnBody.RESULT_SUCCESS, map);	
	 		}
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
		return null;
	}
	
	/**
	 * 学生端接口：获取学生本学期内各课程的签到情况
	 * @author macong
	 * 时间：2016年11月30日09:12:02
	 */
	@RequestMapping(value="signIn/courseSignIn",method=RequestMethod.POST)
	public ReturnBody getSignInfo(HttpServletRequest request){
		try{
	 		String studentId = getCurrentUserId(request);
	 		String courseId = request.getParameter("courseId");
	 		String lon = request.getParameter("lon");
	 		String lat = request.getParameter("lat");
	 		if(StringUtil.checkParams(studentId,courseId,lon,lat)){
	 			signInServiceImpl.courseSignIn(studentId,courseId,lon,lat);
	 			return new ReturnBody(ReturnBody.RESULT_SUCCESS);
	 		}
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
		return null;
	}
}
