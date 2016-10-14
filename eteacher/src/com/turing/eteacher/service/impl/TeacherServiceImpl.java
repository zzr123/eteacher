package com.turing.eteacher.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.TeacherDAO;
import com.turing.eteacher.model.CourseWorkload;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.util.StringUtil;

@Service
public class TeacherServiceImpl extends BaseService<Teacher> implements ITeacherService {

	@Autowired
	private TeacherDAO teacherDAO;
	
	@Override
	public BaseDAO<Teacher> getDAO() {
		return teacherDAO;
	}

	@Override
	public List<Map> getWorkloadData(String userId, String year) {
		String hql = "select c.courseId as courseId,c.courseName as workloadName,c.classHours as classHours,c.studentNumber as studentNumber,t.termName as termName from Course c,Term t where c.termId = t.termId and c.userId = ? and t.startDate not like ? and t.endDate like ?";
		List<Map> list = new ArrayList();
		List<Map> list1 = teacherDAO.findMap(hql, userId, year+"%", year+"%");
		hql = "select c.courseId as courseId,c.courseName as workloadName,c.classHours as classHours,c.studentNumber as studentNumber,t.termName as termName from Course c,Term t where c.termId = t.termId and c.userId = ? and t.startDate like ? and t.endDate like ?";
		List<Map> list2 = teacherDAO.findMap(hql, userId, year+"%", year+"%");
		List<Map> temp = new ArrayList();
		temp.addAll(list1);
		temp.addAll(list2);
		for(Map record : temp){
			hql = "from CourseWorkload where courseId = ?";
			List<CourseWorkload> courseWorkloads = this.teacherDAO.find(hql, record.get("courseId"));
			BigDecimal hoursPercent = new BigDecimal(0);
			BigDecimal studentPercent = new BigDecimal(0);
			String desc = "";
			for(CourseWorkload workload : courseWorkloads){
				if(workload.getWorkloadPercent()!=null){
					desc += workload.getWorkloadName();
					if("学时".equals(workload.getWorkloadName())){
						hoursPercent = workload.getWorkloadPercent();
					}else if("学生数".equals(workload.getWorkloadName())){
						hoursPercent = workload.getWorkloadPercent();
					}
					desc += "*" + StringUtil.getBigDecimalStr(workload.getWorkloadPercent()) + "% + ";
				}
			}
			if(desc.endsWith(" + ")){
				desc = "（公式：" + desc.substring(0,desc.length()-3) + "）";
			}
			record.put("workloadName", record.get("workloadName"));
			record.put("workload", desc);
			BigDecimal hoursWorklod = hoursPercent.multiply(new BigDecimal((Integer)record.get("classHours"))).divide(new BigDecimal(100));
			BigDecimal studnetWorklod = studentPercent.multiply(new BigDecimal((Integer)record.get("studentNumber"))).divide(new BigDecimal(100));
			record.put("workloadNumber", hoursWorklod.add(studnetWorklod));
			
		}
		BigDecimal workloadTerm1 = new BigDecimal(0);
		BigDecimal workloadTerm2 = new BigDecimal(0);
		Map total = null;
		String termName = null;
		if(list1!=null&&list1.size()>0){
			list.addAll(list1);
			for(Map record : list1){
				termName = (String)record.get("termName");
				workloadTerm1.add((BigDecimal)record.get("workloadNumber"));
			}
			total = new HashMap();
			total.put("workloadName", "<b>"+(termName==null?"":termName)+"</b>");
			total.put("workloadNumber", workloadTerm1);
			list.add(total);
		}
		if(list2!=null&&list2.size()>0){
			list.addAll(list2);
			for(Map record : list2){
				termName = (String)record.get("termName");
				workloadTerm2.add((BigDecimal)record.get("workloadNumber"));
			}
			total = new HashMap();
			total.put("workloadName", "<b>"+(termName==null?"":termName)+"</b>");
			total.put("workloadNumber", workloadTerm2);
			list.add(total);
		}
		total = new HashMap();
		total.put("workloadName", "<b>"+(Integer.parseInt(year)-1)+"-"+year+"学年</b>");
		total.put("workloadNumber", workloadTerm1.add(workloadTerm2));
		list.add(total);
		return list;
	}
	//获取用户的详细信息。
	@Override
	public Map getTeacherDetail(String userId){
//		String hql = "select * from Teacher where teacherId =?";
//		Teacher teacher = (Teacher) teacherDAO.find(hql);
//		String titleId = teacher.getTitleId();
//		String postId = teacher.getPostId();
//		String schoolId = teacher.getSchoolId();
		
		String hql2 = "select  from Dictionary2Private pri where pri.dpId = ? union "
				+ "select * from Dictionary2Public p where p.dictionaryId = ?";
		
		
		return null;
	}
}
