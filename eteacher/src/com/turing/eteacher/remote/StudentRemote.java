package com.turing.eteacher.remote;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Student;
import com.turing.eteacher.service.IStudentService;
import com.turing.eteacher.util.BeanUtils;

@RestController
@RequestMapping("remote")
public class StudentRemote extends BaseController {
	
	@Autowired
	private IStudentService studentServiceImpl;

	/**
	 * 完善学生用户的基本信息
	 * @param request
	 * @return
	 */
	//--request--
//{
//	stuName : '姓名',
//	sex : '性别',
//	school : '学校',
//	faculty : '院系',
//	classId : '班级ID',
//	stuNo : '学号',
//	phone : '电话', //多个用英文逗号隔开
//	qq : 'QQ',
//	weixin : '微信',
//	email : '邮箱' //多个用英文逗号隔开
//}
	//--response--
//{
//	result : 'success',//成功success，失败failure
//	data : {},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "students/{stuId}", method = RequestMethod.PUT)
	public ReturnBody updateStudent(HttpServletRequest request, Student student, @PathVariable String stuId){
		try {
			Student serverStudent = studentServiceImpl.get(stuId);
			BeanUtils.copyToModel(student, serverStudent);
			studentServiceImpl.update(serverStudent);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 获取用户的信息
	 * @param request
	 * @param stuId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : {
//		stuName : '姓名',
//		sex : '性别',
//		school : '学校',
//		faculty : '院系',
//		classId : '班级ID',
//		stuNo : '学号',
//		phone : '电话', //多个用英文逗号隔开
//		qq : 'QQ',
//		weixin : '微信',
//		email : '邮箱' //多个用英文逗号隔开
//	},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "students/{stuId}", method = RequestMethod.GET)
	public ReturnBody getStudent(HttpServletRequest request, @PathVariable String stuId){
		try {
			Student student = studentServiceImpl.get(stuId);//TODO 缺数据
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, student);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 查询个人在校资料
	 * @param request
	 * @param stuId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : {
//		stuId : 'ID',
//		school : '学校',
//		faculty : '院系',
//		major : '专业',
//		className : '班级',
//		stuNo : '学号'
//	},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "students/{stuId}/school-info", method = RequestMethod.GET)
	public ReturnBody getStudentSchoolInfo(HttpServletRequest request, @PathVariable String stuId){
		try {
			Map data = studentServiceImpl.getStudentSchoolInfo(stuId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 查询个人资料
	 * @param request
	 * @param stuId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : {
//		stuId : 'ID',
//		picture : '头像', //图片服务访问地址
//		stuName : '姓名',
//		sex : '性别',
//		phone : '手机号',
//		email : '邮箱'
//	},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "students/{stuId}/user-info", method = RequestMethod.GET)
	public ReturnBody getStudentUserInfo(HttpServletRequest request, @PathVariable String stuId){
		try {
			Student data = studentServiceImpl.get(stuId);
			data.setPicture("/upload/" + data.getPicture());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
}
