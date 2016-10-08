package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.StudentDAO;
import com.turing.eteacher.model.Student;
import com.turing.eteacher.service.IStudentService;

@Service
public class StudentServiceImpl extends BaseService<Student> implements IStudentService {

	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public BaseDAO<Student> getDAO() {
		return studentDAO;
	}

	@Override
	public List<Map> getListForTable(String courseId) {
		String hql = "select s.stuId as stuId," +
				"s.stuNo as stuNo," +
				"s.stuName as stuName," +
				"(select count(sc.scoreId) from Score sc where sc.courseId = cc.courseId and sc.stuId = s.stuId and sc.scoreType = ?) as normalScoreCount " + 
				"from Student s,CourseClasses cc where s.classId = cc.classId and cc.courseId = ?";
		List<Map> list = studentDAO.findMap(hql, EteacherConstants.SCORE_TYPE_COURSE, courseId);
		return list;
	}

	@Override
	public Student getByStuNo(String stuNo) {
		String hql = "from Student where stuNo = ?";
		List<Student> list = studentDAO.find(hql, stuNo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map getStudentSchoolInfo(String stuId) {
		String hql = "select s.stuId as stuId,s.school as school," +
				"s.faculty as faculty,m.majorName as major," +
				"c.className as className,s.stuNo as stuNo " +
				"from Student s,Classes c,Major m " +
				"where s.classId = c.classId " +
				"and c.majorId = m.majorId " +
				"and s.stuId = ?";
		List<Map> list = studentDAO.findMap(hql, stuId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
