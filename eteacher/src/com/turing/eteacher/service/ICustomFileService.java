package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CustomFile;
/**
 * 
 * @author lifei
 *
 */
public interface ICustomFileService extends IService<CustomFile> {
	/**
	 * 通过课程ID获取文件列表
	 * @author lifei
	 * @param courseId
	 * @param page
	 * @return
	 */
	public List<Map> getListByCourse(String courseId, int page,String path);
}
