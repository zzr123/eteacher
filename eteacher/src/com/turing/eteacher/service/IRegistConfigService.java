package com.turing.eteacher.service;

import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.RegistConfig;

public interface IRegistConfigService extends IService<RegistConfig> {
	/**
	 * 更改用户的签到设置
	 * @author macong
	 * @param configId
	 * @param before
	 * @param after
	 * @param distance
	 * 时间： 2016年12月9日18:32:18
	 */
	public void changeSetting(String configId, String before, String after, String distance);
	
	/**
	 * 通过CourseId 获取该课程的上课签到时间
	 */
	public Map getRegistTimeByCourseId(String courseId);
}
