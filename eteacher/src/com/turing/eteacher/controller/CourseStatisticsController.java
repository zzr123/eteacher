package com.turing.eteacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseScorePrivate;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.IScoreService;

@Controller
@RequestMapping("statistics")
public class CourseStatisticsController extends BaseController {

	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private IScoreService scoreServiceImpl;
	
	@RequestMapping("viewCourseStatistics")
	public String viewCourseStatistics(HttpServletRequest request){
		//该课程成绩组成
		String courseId = request.getParameter("courseId");
		List<CourseScorePrivate> CourseScoreList = courseServiceImpl.getCoureScoreByCourseId(courseId);
		Course course = courseServiceImpl.get(courseId);
		request.setAttribute("CourseScoreList", CourseScoreList);
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseName", course==null?"":course.getCourseName());
		return "statistics/courseStatistics";
	}
	
	@RequestMapping("getScoreStatisticsData")
	@ResponseBody
	public Object getScoreStatisticsData(HttpServletRequest request){
		String courseId = request.getParameter("courseId");
		String scoreType = request.getParameter("scoreType");
		int[] data = scoreServiceImpl.getScoreStatisticsData(courseId, scoreType);
		return data;
	}
}
