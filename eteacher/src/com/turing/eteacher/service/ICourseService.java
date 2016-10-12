package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Course;
import com.turing.eteacher.model.CourseFile;
import com.turing.eteacher.model.CourseScorePrivate;
import com.turing.eteacher.model.CourseWorkload;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.model.Textbook;
import com.turing.eteacher.model.User;

public interface ICourseService extends IService<Course> {
	
	public List<Map> getListByTermId2(String termId, String userId);
	
	public List<Course> getListByTermId(String termId, String userId);
	
	public List<CourseFile> getCourseFilesByCourseId(String courseId);
	
	public List<CourseFile> getPublicCourseFilesByCourseId(String courseId);

	public void addCourse(Course course, String[] classIds, List<CourseWorkload> courseWorkloads, List<CourseScorePrivate> courseScores, Textbook textbook, List<Textbook> textbookOthers, List<CourseFile> courseFiles);
	
	public void updateCourse(Course course, String[] classIds, List<CourseWorkload> courseWorkloads, List<CourseScorePrivate> courseScores, Textbook textbook, List<Textbook> textbookOthers, List<CourseFile> courseFiles);
	
	public void deleteCourseFile(String cfId);
	
	public List<CourseScorePrivate> getCoureScoreByCourseId(String courseId);
	
	public List<CourseWorkload> getCoureWorkloadByCourseId(String courseId);
	
	/**
	 * 获取当前时间某课程是否为课堂时间
	 * @return 
	 */
	public Map getCourseRecordNow(User user, String courseId);
	
	/**
	 * 获取今日课程列表信息
	 * @param currentTerm
	 * @param userId
	 * @return
	 */
	public List<Map> getCourseDatasOfToday(User user);
	
	/**
	 * 获取单个今日课程 上课时间信息
	 * @param currentTerm
	 * @param courseId
	 * @return
	 */
	public Map getCourseTimeData(String courseId);
	
	public List getListByTermAndStuId(String year, String term, String stuId);
	
	//教师接口
	//获取课程列表（1.根据学期 2.根据指定日期）
	public List<Map> getCourseList(String status,String data,String userId);
	//获取课程详细信息
	public List<Map> getCourseDetail(String courseId, String status);
	//修改教材教辅信息
	public void updateTextbook(Textbook text);
	//修改成绩组成项信息
	public void updateCoursescore(CourseScorePrivate cs);
	/**
	 * 通过type获取字典表的值
	 * @author lifei
	 * @param type
	 * @return
	 */
	public List<Map> getDictionaryByType(String type); 
}
