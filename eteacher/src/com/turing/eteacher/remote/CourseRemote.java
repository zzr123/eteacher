package com.turing.eteacher.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseScorePrivate;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.Textbook;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IClassService;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITeacherService;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.service.ITextbookService;

@RestController
@RequestMapping("remote")
public class CourseRemote extends BaseRemote {
	
	private static final Log log = LogFactory.getLog(CourseRemote.class);
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	@Autowired
	private ITermService termServiceImpl;
	
	@Autowired
	private ITextbookService textbookServiceImpl;
	
	@Autowired
	private ITeacherService teacherServiceImpl;
	
	@Autowired
	private IClassService classServiceImple;

//	{
//	result : 'success',//成功success，失败failure
//	data : '[{courseId:"课程ID",courseName:"课程名称",courseTime:"课程时间",location:"上课地点"}]',//课程列表数据
//	msg : '提示信息XXX'
//}
	@RequestMapping(value = "student/courses/today", method = RequestMethod.GET)
	public ReturnBody getStudentCourseOfTaday(HttpServletRequest request){
		try{
			List<Map> data = courseServiceImpl.getCourseDatasOfToday(getCurrentUser(request));
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		}
		catch (Exception e) {
			log.error(this, e);
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
//{
//	result : 'success',//成功success，失败failure
//	data : {
//		courseId : '课程ID'
//		courseName : '课程名称',
//		examinationMode : '考核方式',
//		teacherName : '授课教师',
//		courseWeek : '起止周',
//		courseTime : '授课时间',
//		introduction : '课程简介',
//		textbook : {textbookName : '教材名称'}, //教材
//		textbookOthers : [{textbookName : '教辅名称'}] //教辅
//	},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "student/courses/{courseId}", method = RequestMethod.GET)
	public ReturnBody getCourseData(HttpServletRequest request, @PathVariable String courseId){
		try{
			Map data = new HashMap();
			Map courseTime = courseServiceImpl.getCourseTimeData(courseId);
			Course course = courseServiceImpl.get(courseId);
			Teacher teacher = teacherServiceImpl.get(course.getUserId());
			Textbook textbook = textbookServiceImpl.getMainTextbook(courseId);
			List<Textbook> textbookOthers = textbookServiceImpl.getTextbookList(courseId);
//			课程名称，考核方式，授课教师，起止周，授课时间，课程简介，教材教辅
			data.put("courseId", courseId);
			data.put("courseName", course.getCourseName());
			data.put("examinationMode", course.getExaminationModeId());
			data.put("teacherName", teacher.getName());
			data.put("courseWeek", courseTime.get("startWeek") + "-" + courseTime.get("endWeek"));
			data.put("courseTime", courseTime.get("startTime") + "-" + courseTime.get("endTime"));
			data.put("introduction", course.getIntroduction());
			data.put("textbook", textbook==null?new HashMap():textbook);
			data.put("textbookOthers", textbookOthers);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		}
		catch (Exception e) {
			log.error(this, e);
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 获取用户某学期下的课程列表
	 * @param request
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			courseId : '课程ID',
//			courseName : '课程名称'
//		}
//	],
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "student/{year}/{term}/courses", method = RequestMethod.GET)
	public ReturnBody studentCourses(HttpServletRequest request, @PathVariable String year, @PathVariable String term){
		try{
			User currentUser = getCurrentUser(request);
			List<Course> list = courseServiceImpl.getListByTermAndStuId(year, term, currentUser.getUserId());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			log.error(this, e);
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 获取资料库内容列表
	 * @param request
	 * @param courseId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			cfId : '课程资源ID',
//			fileName : '资源名称',
//			serverName : '资源服务器名称',   t6fd
//			fileType : '资源类型'//（01大纲 02日历 03教案 04课件）
//		}
//	],
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "course/{courseId}/files", method = RequestMethod.GET)
	public ReturnBody courseFiles(HttpServletRequest request, @PathVariable String courseId){
		try{
			List<CustomFile> courseFiles = courseServiceImpl.getPublicCourseFilesByCourseId(courseId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, courseFiles);
		}
		catch (Exception e) {
			log.error(this, e);
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 课程资源下载
	 * @param request
	 * @param response
	 * @param cfId
	 * @return
	 */
	@RequestMapping(value = "course-files/{cfId}", method = RequestMethod.GET)
	public ReturnBody downloadFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String cfId){
		InputStream is = null;
        OutputStream os = null;
		try{
			CustomFile courseFile = (CustomFile)courseServiceImpl.get(CustomFile.class, cfId);
			String pathRoot = request.getSession().getServletContext().getRealPath("");
			System.out.println("----"+pathRoot);
			File file = new File(pathRoot + "/upload/" + courseFile.getServerName());
			response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                + new String(courseFile.getFileName().getBytes(), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            is = new BufferedInputStream(new FileInputStream(file));
            os = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = is.read(b)) > 0) {
                os.write(b, 0, i);
            }
			return null;
		}
		catch (Exception e) {
			log.error(this, e);
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
		finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
	}
	
	//教师端操作
	/**
	 * 获取课程列表（1.根据学期 2.根据指定日期）
	 * @param request
	 * @param status
	 * @param data
	 * @return
	 */
	@RequestMapping(value="teacher/course/courseList/{status}/{data}", method=RequestMethod.GET)
	public ReturnBody getCourseList(HttpServletRequest request, @PathVariable String status, @PathVariable String data){
		try{
			String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			List list = courseServiceImpl.getCourseList(status, data,"Qsq73xbQDS");
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 获取课程详细信息
	 * @param request
	 * @param courseId
	 * @param status
	 * @return
	 */
	@RequestMapping(value="teacher/course/getCourseDetail/{courseId}/{status}", method=RequestMethod.GET)
	public ReturnBody getCourseDetail(HttpServletRequest request,@PathVariable String courseId, @PathVariable String status){
		try{
			List list = courseServiceImpl.getCourseDetail(courseId, status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 添加/修改课程信息
	 * @param request
	 * @param c
	 * @return
	 */
	@RequestMapping(value="teacher/course/updateCourse", method=RequestMethod.POST)
	public ReturnBody updateCourse(HttpServletRequest request,Course c){
		try{
			String status=request.getParameter("status");
			String[] classes=request.getParameterValues("classes");
			System.out.println("----------"+status);
			if("0".equals(status)){ //添加新课程
				String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
				c.setUserId(userId);
				System.out.println("-----------------"+c.getCourseId());
				String cId=(String) courseServiceImpl.save(c);
				for(int i=0;i<classes.length;i++){
					classServiceImple.addCourseClasses(cId, classes[i]);
				}
			}
			else{//修改课程信息
				Course course = courseServiceImpl.get(c.getCourseId());
				System.out.println("------------"+course.getCourseId()+":"+course.getCourseName());
				c.setTermId(course.getTermId());
				c.setUserId(course.getUserId());
				courseServiceImpl.update(c);
				classServiceImple.deleteClassByCourseId(c.getCourseId());
				for(int i=0;i<classes.length;i++){
					classServiceImple.addCourseClasses(c.getCourseId(), classes[i]);
				}
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 增加或修改课程组成项信息
	 * @param request
	 * @param cs
	 * @return
	 */
	@RequestMapping(value="teacher/course/addOrUpdateType",method=RequestMethod.GET)
	public ReturnBody addOrUpdateType(HttpServletRequest request,CourseScorePrivate cs){
		try{
			String status=request.getParameter("status");
			if("0".equals(status)){//增加课程组成项信息
				courseServiceImpl.save(cs);
			}
			else{//修改课程组成项信息
				courseServiceImpl.updateCoursescore(cs);
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 删除特定的成绩组成项
	 * @param request
	 * @param course_scoreid
	 * @return
	 */
	@RequestMapping(value="teacher/course/delType/{course_scoreid}", method=RequestMethod.GET)
	public ReturnBody deleteType(HttpServletRequest request,@PathVariable String course_scoreid){
		try{
			courseServiceImpl.deleteById(course_scoreid);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}	
	}
	/**
	 * 增加或修改课程的教材教辅信息
	 * @param request
	 * @param cs
	 * @return
	 */
	@RequestMapping(value="teacher/course/addOrUpdateTextbook",method=RequestMethod.GET)
	public ReturnBody addOrUpdateTextbook(HttpServletRequest request,Textbook book){
		try{
			String status=request.getParameter("status");
			String type=request.getParameter("type");//1代表教材 2代表教辅
			if("1".equals(type)){
				book.setTextbookType("01");
			}
			else{
				book.setTextbookType("02");
			}
			if("0".equals(status)){//增加教材教辅信息
				
				courseServiceImpl.save(book);
			}
			else{//修改教材教辅信息
				courseServiceImpl.updateTextbook(book);
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 删除特定课程的教材或教辅
	 * @param request
	 * @param textbookId
	 * @return
	 */
	@RequestMapping(value="teacher/course/delTextBook/{textbookId}", method=RequestMethod.GET)
	public ReturnBody deleteTextBook(HttpServletRequest request,@PathVariable String textbookId){
		try{
			courseServiceImpl.deleteById(textbookId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}	
	}
}
