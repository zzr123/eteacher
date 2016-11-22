package com.turing.eteacher.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.service.ISchoolService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class SchoolRemote extends BaseRemote {
	
	@Autowired
	private ISchoolService schoolService;
	
	/**
	 * 1.2.1	获取学校教学楼列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacher/school/getTeachingBuildings", method = RequestMethod.POST)
	public ReturnBody posts(HttpServletRequest request){
		Teacher teacher = getCurrentTeacher(request);
		if (null != teacher) {
			String schoolId = teacher.getSchoolId();
			if(StringUtil.checkParams(schoolId)){
				List list = schoolService.getClassroomBySchooId(schoolId);
				return new ReturnBody(list);
			}else{
				return ReturnBody.getParamError();
			}
		}
		return ReturnBody.getParamError();
	}
}
