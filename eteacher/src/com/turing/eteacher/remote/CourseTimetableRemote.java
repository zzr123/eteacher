package com.turing.eteacher.remote;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.service.IClassService;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITermService;

@RestController
@RequestMapping("remote")
public class CourseTimetableRemote extends BaseRemote {
	
	@Autowired
	private IClassService classServiceImple;
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private ITermService termServiceImpl;
	
	/**
	 * 获取课程列表（1.根据学期 2.根据指定日期）
	 * @return
	 */
//{
//	result : '200',
//	data : [
//		{
//			courseName : '课程名称',
//			weekDay : '星期几',
//			lessonNumber : '第几节课',
//			location : '地点'
//			classRoom : '教室',	
//		}
//	],
//	msg : '提示信息XXX'
//}
	@RequestMapping(value="teacher/course/classCourseTable", method=RequestMethod.POST)
	public ReturnBody getClassCourseTable(HttpServletRequest request){
		try{
			String classId = request.getParameter("classId");
			String page =  (String)request.getParameter("page");
			List<Map> list = courseServiceImpl.getClassCourseTable(classId,Integer.parseInt(page));
//			System.out.println("结果："+list.get(0).toString());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
}
