package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Statistic;
/**
 * 
 * @author lifei
 *
 */
public interface IStatisticService extends IService<Statistic> {
	/**
	 * 通过目标ID获取统计到的人数
	 * @param id
	 * @return
	 */
	public int getCountByTargetId(String id);
}
