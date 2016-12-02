package com.turing.eteacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.ITeacherService;

@Controller
@RequestMapping("statistics")
public class WorkloadStatisticsController extends BaseController {

	@Autowired
	private ITeacherService teacherServiceImpl;
	
	@RequestMapping("viewWorkloadStatistics")
	public String viewWorkloadStatistics(HttpServletRequest request){
		
		return "statistics/workloadStatistics";
	}
	
	@RequestMapping("getWorkloadListData")
	@ResponseBody
	public Object getWorkloadListData(HttpServletRequest request){
		String year = request.getParameter("year");
		User currentUser = getCurrentUser(request);
		List list = teacherServiceImpl.getWorkloadData(currentUser.getUserId(), year);
		Map result = new HashMap();
		result.put("data", list);
		return result;
	}
}
