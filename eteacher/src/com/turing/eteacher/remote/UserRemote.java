package com.turing.eteacher.remote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.constants.SystemConstants;
import com.turing.eteacher.model.Student;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IStudentService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.service.IUserService;
import com.turing.eteacher.util.DateUtil;
import com.turing.eteacher.util.Encryption;
import com.turing.eteacher.util.SmsUtil;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class UserRemote extends BaseController {

	@Autowired
	private IUserService userServiceImpl;

	@Autowired
	private ITeacherService teacherServiceImpl;

	@Autowired
	private IStudentService studentServiceImpl;

	@Autowired
	private ITermService termServiceImpl;

	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	// --request--
	// {
	// account : '账号（手机号）',
	// password : '密码'

	// }
	// --response--
	// {
	// result : 'success',//成功success，失败failure
	// data : 'userId',//用户数据主键
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ReturnBody login(HttpServletRequest request) {
		try {
			// 验证登录
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			User currentUser = userServiceImpl.getUserByAcct(account);
			if (currentUser != null) {
				if (currentUser.getPassword().equals(
						Encryption.encryption(password))) {
					if (EteacherConstants.USER_TYPE_TEACHER.equals(currentUser
							.getUserType())) {
						Teacher currentTeacher = teacherServiceImpl
								.get(currentUser.getUserId());
						request.getSession().setAttribute(
								EteacherConstants.CURRENT_TEACHER,
								currentTeacher);
						request.getSession().setAttribute(
								EteacherConstants.CURRENT_TERM,
								termServiceImpl.getCurrentTerm(currentUser
										.getUserId()));
					} else {
						Student currentStudent = studentServiceImpl
								.get(currentUser.getUserId());
						request.getSession().setAttribute(
								EteacherConstants.CURRENT_STUDENT,
								currentStudent);
					}
					request.getSession().setAttribute(
							EteacherConstants.CURRENT_USER, currentUser);
					return new ReturnBody(ReturnBody.RESULT_SUCCESS,
							currentUser.getUserId(), "登陆成功！");
				} else {
					return new ReturnBody(ReturnBody.RESULT_FAILURE,
							"登录失败，密码错误！");
				}
			} else {
				return new ReturnBody(ReturnBody.RESULT_FAILURE, "登录失败，用户不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, "登录失败，系统出现异常。");
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loginout", method = RequestMethod.POST)
	public ReturnBody loginout(HttpServletRequest request) {
		try {
			request.getSession().invalidate();
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 注册时获取手机验证码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "verifycode", method = RequestMethod.GET)
	public ReturnBody verifycode(HttpServletRequest request) {
		try {
			String mobile = request.getParameter("mobile");
			int type = Integer.parseInt(request.getParameter("type"));
			User user;
			if (StringUtil.isNotEmpty(mobile)) {
				switch (type) {
				case 0:// 注册
					user = userServiceImpl.getUserByAcct(mobile);
					if (null != user) {
						return new ReturnBody(ReturnBody.RESULT_FAILURE,
								"手机号已被注册");
					}
					break;
				case 1:// 忘记密码
					user = userServiceImpl.getUserByAcct(mobile);
					if (null == user) {
						return new ReturnBody(ReturnBody.RESULT_FAILURE,
								"手机号未注册");
					}
					break;
				default:
					return ReturnBody.getParamError();
				}
				int code = 100000 + new Random().nextInt(899999);
				request.getSession().setAttribute("timestamp",
						System.currentTimeMillis());
				request.getSession().setAttribute("verifyCode", code + "");
				// 使用聚合数据发送验证码
				Map result = SmsUtil.sendSms(mobile, "13909", "#code#=" + code);
				if ("0".equals(result.get("error_code") + "")) {
					return new ReturnBody(ReturnBody.RESULT_SUCCESS, code);
				} else {
					return new ReturnBody(ReturnBody.RESULT_FAILURE,
							"短信验证码获取失败");
				}
			} else {
				return ReturnBody.getParamError();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ReturnBody.getSystemError();
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	// --request--
	// {
	// account : '注册账号（手机号）',
	// password : '密码',
	// userType : '用户类别',//'01'教师 '02'学生
	// verifyCode : '验证码'
	// }
	// --response--
	// {
	// result : 'success',//成功success，失败failure
	// data : 'userId',//用户数据主键
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public ReturnBody register(HttpServletRequest request, User user) {
		try {
			String verifyCode = request.getParameter("verifyCode");
			if (StringUtil.isNotEmpty(verifyCode)) {
				if (!verifyCode.equals(request.getSession().getAttribute(
						"register_verifyCode"))) {
					return new ReturnBody(ReturnBody.RESULT_FAILURE, "验证码输入有误");
				}
			} else {
				return new ReturnBody(ReturnBody.RESULT_FAILURE, "验证码不能为空！");
			}
			User serverUser = userServiceImpl.getUserByAcct(user.getAccount());
			if (serverUser != null) {
				return new ReturnBody(ReturnBody.RESULT_FAILURE, "手机号已被注册");
			}
			user.setPassword(Encryption.encryption(user.getPassword()));
			user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));
			userServiceImpl.add(user);
			if (EteacherConstants.USER_TYPE_STUDENT.equals(user.getUserType())) {
				Student student = new Student();
				student.setStuId(user.getUserId());
				userServiceImpl.save(student);
			} else {
				Teacher teacher = new Teacher();
				teacher.setTeacherId(user.getUserId());
				userServiceImpl.save(teacher);
			}

			return new ReturnBody(ReturnBody.RESULT_SUCCESS, user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 更改用户密码
	 * 
	 * @param request
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : '短信验证码',
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public ReturnBody updatePassword(HttpServletRequest request) {
		try {
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newpassword");
			User user = getCurrentUser(request);
			if (StringUtil.isNotEmpty(password)
					&& StringUtil.isNotEmpty(newPassword)) {
				if (Encryption.encryption(password).equals(user.getPassword())) {
					user.setPassword(Encryption.encryption(newPassword));
					userServiceImpl.update(user);
					return new ReturnBody(ReturnBody.RESULT_SUCCESS, "密码修改成功！");
				} else {
					return new ReturnBody(ReturnBody.RESULT_FAILURE, "原密码输入有误！");
				}
			} else {
				return new ReturnBody(ReturnBody.RESULT_FAILURE, "密码不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 短信验证码的合法性验证
	 * 
	 * @param request
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : 'verifyresult',//验证结果
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ReturnBody validate(HttpServletRequest request){
		try{
			String verifycode=request.getParameter("verifyCode");
			if (null !=request.getSession().getAttribute("timestamp")) {
				long before = (long) request.getSession().getAttribute("timestamp");
				if (DateUtil.isAvailable(before, System.currentTimeMillis(), SystemConstants.MESSAGE_DISTANCE)) {
					if(!verifycode.equals(request.getSession().getAttribute("verifyCode"))){
						return new ReturnBody(ReturnBody.RESULT_FAILURE, "验证码输入有误");
					}
					else{
						request.getSession().setAttribute("timestamp", 0);
						return new ReturnBody(ReturnBody.RESULT_SUCCESS, "短信验证成功");
					}
				}else{
					return new ReturnBody(ReturnBody.RESULT_FAILURE,"验证码已失效");
				}

			}else{
				return new ReturnBody(ReturnBody.RESULT_FAILURE,"验证码已失效");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 找回密码操作
	 * 
	 * @param request
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : 'userId',
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "recovered", method = RequestMethod.POST)
	public ReturnBody recovered(HttpServletRequest request) {
		try {
			String mobile = request.getParameter("mobile");
			String verifycode = request.getParameter("verifycode");
			String password = request.getParameter("password");
			User user = getCurrentUser(request);

			if (verifycode.equals(request.getSession().getAttribute(
					"recovered_verifyCode"))) {
				user.setPassword(Encryption.encryption(user.getPassword()));
				userServiceImpl.save(password);
				return new ReturnBody(ReturnBody.RESULT_SUCCESS, "密码已修改，请登录");
			} else {
				return new ReturnBody(ReturnBody.RESULT_FAILURE, "验证码输入有误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}
}
