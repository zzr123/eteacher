package com.turing.eteacher.service;

import java.util.List;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CourseCell;
/**
 * 
 * @author lifei
 *
 */
public interface ICourseCellService extends IService<CourseCell> {
	/**
	 * 通过ciId获取CourseCell
	 * @param ciId
	 * @return
	 */
	public List<CourseCell> getCells(String ciId);
}
