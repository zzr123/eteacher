package com.turing.eteacher.service;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.App;
/**
 * 
 * @author lifei
 *
 */
public interface IAppService extends IService<App> {
	/**
	 * 通过APPKey获取App
	 * @author lifei
	 * @param key
	 * @return
	 */
	public App getAppByKey(String key);
}
