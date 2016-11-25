package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.SchoolDAO;
import com.turing.eteacher.model.School;
import com.turing.eteacher.service.ISchoolService;

@Service
public class SchoolServiceImpl extends BaseService<School> implements ISchoolService {
	
	@Autowired
	private SchoolDAO schoolDAO;
	@Override
	public BaseDAO<School> getDAO() {
		// TODO Auto-generated method stub
		return schoolDAO;
	}
	
	@Override
	public List<Map> getListByParentType(int type, String id) {
		List<Map> list = null;
		System.out.println("IMPL层获取到的ID:"+id);
		if(id.matches("fistLoad")){//获取省份列表
			String hql = "select s.schoolId as id, s.type as type , s.code as code , s.value as value "
					+ " from School s where s.type = ? and s.status = 1";
			list = schoolDAO.findMap(hql, type);
			if(null != list && list.size()>0){
				//System.out.println("adfadf:"+list.get(0).toString());
				return list;
			}
		}else{//根据省份ID，查询所属市列表
			String hql = "select s.schoolId as id, s.type as type , s.code as code , s.value as value "
					+ " from School s where s.type = ? and s.schoolId = ? and s.status = 1";
			list = schoolDAO.findMap(hql, type , id);
			if(null != list && list.size()>0){
				return list;
			}
		}
		return null;
	}
	
	@Override
	public List<Map> getSchoolInfoById(String Id) {
		String hql = "select s.type as type , s.schoolId as id , s.value as school , s.parentCode as parentCode from School s "
				+ "where s.schoolId = ? and s.status = 1";
		
		List<Map> school = schoolDAO.findMap(hql, Id);
		if(null!=school && school.size()>0){
			//获取学校所在市的名称
			String hql2 = "select s.type as type , s.schoolId as id , s.value as city ,s.parentCode as cityCode from School s where s.code = ?";
			List<Map> city = schoolDAO.findMap(hql2, school.get(0).get("parentCode"));
			//获取学校所在省份的名称
			String hql3 = "select s.type as type , s.schoolId as id , s.value as province , s.code as provinceCode from School s where s.code = ?";
			List<Map> province = schoolDAO.findMap(hql3, city.get(0).get("cityCode"));
			List<Map> schoolInfo = school;
			schoolInfo.addAll(city);
			schoolInfo.addAll(province);
			/*			
  			for(int i=0;i<schoolInfo.size();i++){
				System.out.println(schoolInfo.get(i).toString());
			}*/
			return schoolInfo;
		}
		return null;
	}
	@Override
	public Map getSchoolByUserId(String userId){
		String hql = "select u.userType from User u where u.userId = ?";
		String type = (String) schoolDAO.find(hql, userId).get(0);
		Map school = null;
		System.out.println("EteacherConstants.USER_TYPE_TEACHER:"+EteacherConstants.USER_TYPE_TEACHER);
		if(EteacherConstants.USER_TYPE_TEACHER.equals(type)||EteacherConstants.USER_TYPE_ADMIN.equals(type)){//教师用户
			String hql2 = "select s.schoolId as schoolId, s.value as schoolName from School s, Teacher t where t.schoolId=s.schoolId and t.teacherId= ?";
			school = schoolDAO.findMap(hql2, userId).get(0);
			System.out.println(school.toString());
		}else if(EteacherConstants.USER_TYPE_STUDENT.equals(type)){
			String hql2 = "select s.schoolId as schoolId, s.value as schoolName from School s, Student t where t.schoolId=s.schoolId and t.stuId= ?";
			school = schoolDAO.findMap(hql2, userId).get(0);
			System.out.println(school.toString());
		}
		return school;
	}

	@Override
	public List<Map> getClassroomBySchooId(String schoolId) {
		String sql = "SELECT t.SCHOOL_ID AS buildingId, t.VALUE AS buildingName FROM t_school t WHERE t.TYPE = '4' AND t.PARENT_CODE = (SELECT t1.CODE FROM t_school t1 WHERE t1.SCHOOL_ID = ?)";
		List<Map> list = schoolDAO.findBySql(sql, schoolId);
		return list;
	}

}
