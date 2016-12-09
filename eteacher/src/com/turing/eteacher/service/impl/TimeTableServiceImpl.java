package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.TimeTableDAO;
import com.turing.eteacher.model.TimeTable;
import com.turing.eteacher.service.ITimeTableService;

@Service
public class TimeTableServiceImpl extends BaseService<TimeTable> implements
		ITimeTableService {

	@Autowired
	private TimeTableDAO timeTableDAO;

	@Override
	public BaseDAO<TimeTable> getDAO() {
		return timeTableDAO;
	}

	@Override
	public TimeTable getItemBySchoolId(String schoolId,
			String lessonNumber) {
		String hql = "from TimeTable t where t.schoolId = ? and t.lessonNumber = ?";
		List<TimeTable> list = timeTableDAO.find(hql, schoolId, lessonNumber);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
