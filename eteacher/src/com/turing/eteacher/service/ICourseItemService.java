package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CourseItem;
/**
 * 
 * @author lifei
 *
 */
public interface ICourseItemService extends IService<CourseItem> {
	/**
	 * 通过课程Id获取CourseItem
	 * @author lifei
	 * @param courseId
	 * @return
	 */
	public CourseItem getItemByCourseId(String courseId);
}
