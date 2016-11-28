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

	@Override
	public Student getById(String id) {
		String hql = "from Student where stuId = ?";
		List<Student> list = studentDAO.find(hql, id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 查看用户个人信息   stuName : '姓名', stuNO : '学号', sex : '性别', 
	 * schoolId : '学校Id', schoolName : '学校名称' faculty : '院系',
	 *  classId : '班级Id' , className : '班级名称',
	 *  email : '电子邮箱', phone : '联系电话' IM : 'IM',
	 */
	@Override
	public Map getUserInfo(String userId) {
		String hql="select s.stuName as stuName,s.stuNo as stuNo,s.sex as sex," +
				"s.classId as classId,c.className as className,s.schoolId as schoolId," +
				"sch.value as schoolName,s.faculty as faculty " +
				"from Student s , Classes c ,School sch " +
				"where s.classId = c.classId and s.schoolId = sch.schoolId and s.stuId = ?";
		Map<String,Map> map = studentDAO.findMap(hql, userId).get(0);
		//邮箱
		String el="select uc.value as email from UserCommunication uc where uc.userId =? and uc.type =2 ";
		List<Map> list1=studentDAO.findMap(el, userId);
		//联系电话
		String pl="select uc.value as phone from UserCommunication uc where uc.userId =? and uc.type =1 ";
		List<Map> list2=studentDAO.findMap(pl, userId);
		//IM
		String il="select uc.value as IM from UserCommunication uc where uc.userId =? and uc.type =3 ";
		List<Map> list3=studentDAO.findMap(il, userId);
		map.put("email", list1.get(0));
		map.put("phone", list2.get(0));
		map.put("IM", list3.get(0));
		return map;
	}

}
