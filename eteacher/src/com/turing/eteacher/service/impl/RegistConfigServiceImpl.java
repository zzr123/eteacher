package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.RegistConfigDAO;
import com.turing.eteacher.model.RegistConfig;
import com.turing.eteacher.service.IRegistConfigService;

@Service
public class RegistConfigServiceImpl extends BaseService<RegistConfig>  implements IRegistConfigService {
	
	@Autowired
	private RegistConfigDAO registConfigDAO;
	
	@Override
	public BaseDAO<RegistConfig> getDAO() {
		return registConfigDAO;
	}
	/**
	 * 更改用户的签到设置
	 * @author macong
	 * @param configId
	 * @param before
	 * @param after
	 * @param distance
	 * 时间： 2016年12月9日18:32:18
	 */
	@Override
	public void changeSetting(String configId, String before, String after, String distance) {
		String hql = "UPDATE t_regist_config rc SET rc.REGIST_AFTER = ?, "
				+ "rc.REGIST_BEFORE = ?, rc.REGIST_DISTANCE = ? "
				+ "WHERE rc.CONFIG_ID = ? ";
		registConfigDAO.executeBySql(hql, before,after,distance,configId);
	}
	@Override
	public Map getRegistTimeByCourseId(String courseId) {
		String sql = "SELECT trc.REGIST_BEFORE AS registTime FROM t_regist_config trc ,t_course tc WHERE trc.USER_ID = tc.USER_ID AND tc.COURSE_ID = ?";
		List<Map> list = registConfigDAO.findBySql(sql, courseId);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public void deleteByUserId(String userId) {
		String hql = "delete from RegistConfig rc where rc.userId = ?";
		registConfigDAO.executeHql(hql, userId);
	}

}
