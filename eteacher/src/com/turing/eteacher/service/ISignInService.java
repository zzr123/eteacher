package com.turing.eteacher.service;

import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.SignIn;
/**
 * @author macong
 *
 */
public interface ISignInService extends IService<SignIn> {
	/**
	 * 获取某课程的签到位置信息（所在市，学校，教学楼）
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public Map getAddressInfo(String courseId);
	/**
	 * 学生用户的课程签到。
	 * @author macong
	 * 时间： 2016年11月30日13:43:27
	 * @param studentId
	 * @param courseId
	 * @param lon
	 * @param lat
	 * @return
	 */
	public void courseSignIn(String studentId, String courseId, String lon, String lat);

}
