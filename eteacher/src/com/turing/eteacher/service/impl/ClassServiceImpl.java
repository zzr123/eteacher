package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.ClassDAO;
import com.turing.eteacher.model.Classes;
import com.turing.eteacher.service.IClassService;

@Service
public class ClassServiceImpl extends  BaseService<Classes> implements IClassService {

	@Autowired
	private ClassDAO classDAO;
	
	@Override
	public BaseDAO<Classes> getDAO() {
		return classDAO;
	}

	@Override
	public List<Map> getClassList() {
		String hql = "select c.classId as classId,c.className as className," +
				"c.grade as grade,m.majorName as majorName " +
				"from Classes c,Major m where c.majorId = m.majorId " +
				"order by c.grade,c.className";
		List<Map> list = classDAO.findMap(hql);
		return list;
	}

	@Override
	public List<Map> getClassByCourseId(String courseId) {
		String hql = "SELECT "+
				"t_class.CLASS_ID AS classId ,"+
				"t_class.CLASS_NAME AS className "+ 
				"FROM t_class WHERE "+
				"t_class.CLASS_ID IN ( "+
				"SELECT t_course_class.CLASS_ID "+
				"FROM t_course_class WHERE "+
				"t_course_class.COURSE_ID = ?)";
		List<Map> classIds = classDAO.findBySql(hql, courseId); 
		return classIds;
	}

	// 获取用户当前学期所有课程对应的班级列表
	@Override
	public List<Map> getClassListByUser(String userId, String tpId) {
		String hql="select cl.classId as classId,cl.className as className,SUBSTRING(cl.grade,3,4) as grade "+
	               "from Classes cl,Course c,CourseClasses cc where c.userId=? and c.termId=? "+
				   "and c.courseId=cc.courseId and cc.classId=cl.classId";
		List<Map> list=classDAO.findMap(hql, userId,tpId);
		for(int i = 0;i< list.size();i++){
			System.out.println("mappp"+i+":"+list.get(i).toString());
		}
		return list;
	}

	//删除指定课程的班级
	@Override
	public void deleteClassByCourseId(String courseId) {
		String hql="delete from CourseClasses where courseId=?";
		classDAO.executeHql(hql, courseId);
	}

	//增加班级课程对应信息
	@Override
	public void addCourseClasses(String courseId, String classId) {
		String hql="insert into CourseClasses(courseId,classId) values(?,?)";
		classDAO.executeHql(hql, courseId,classId);
	}

	@Override
	public List<Map> getClassByMajor(String majorId, String schoolId) {
		String sql = "SELECT t_class.CLASS_ID AS classId, "+
					 "t_class.CLASS_NAME AS className "+
					 "FROM t_class WHERE "+
					 "t_class.MAJOR_ID = ? "+
					 "AND t_class.SCHOOL_ID = ?";
		List<Map> list = classDAO.findBySql(sql, majorId,schoolId);
		return list;
	}

	@Override
	public List<Map> getClassByKey(String key, String schoolId) {
		String sql = "SELECT t_class.CLASS_ID AS classId, "+
				 	 "t_class.CLASS_NAME AS className "+
				 	 "FROM t_class WHERE t_class.SCHOOL_ID = ? " +
				 	 "AND t_class.CLASS_NAME LIKE '%"+key+"%' " +
				 	 "ORDER BY t_class.CLASS_NAME";
		List<Map> list = classDAO.findBySql(sql,schoolId);
		return list;
	}



}
