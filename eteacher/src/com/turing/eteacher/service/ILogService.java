package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Log;
/**
 * 
 * @author lifei
 *
 */
public interface ILogService extends IService<Log> {
	/**
	 * 通过目标ID获取统计到的人数
	 * @param id
	 * @return
	 */
	public int getCountByTargetId(String id);
}
