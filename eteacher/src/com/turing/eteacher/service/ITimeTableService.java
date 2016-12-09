package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.TimeTable;
/**
 * 
 * @author lifei
 *
 */
public interface ITimeTableService extends IService<TimeTable> {
	/**
	 * 通过schoolId 和 上课节数 获取具体上课时间
	 * @param schoolId
	 * @return
	 */
	public TimeTable getItemBySchoolId(String schoolId,String lessonNumer);
}
