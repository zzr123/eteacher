package com.turing.eteacher.remote;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.model.WorkCourse;
import com.turing.eteacher.model.WorkStatus;
import com.turing.eteacher.service.IWorkCourseService;
import com.turing.eteacher.service.IWorkService;
import com.turing.eteacher.util.CustomIdGenerator;
import com.turing.eteacher.util.StringUtil;

/**
 * @author Administrator
 *
 */
/**
 * <p>Title:WorkRemote </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author Administrator
 * @date 2016-8-8下午1:55:48
 */
@RestController
@RequestMapping("remote")
public class WorkRemote extends BaseRemote {
	
	@Autowired
	private IWorkService workServiceImpl;
	
	@Autowired
	private IWorkCourseService workCourseServiceImpl;
	/**
	 * 获取作业列表 
	 * @param request
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			workId : '作业ID',
//			courseName : '课程名称',
//			content : '作业内容',
//			days : '距离作业提交的时间（天）'
//		}
//	],
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "student/works", method = RequestMethod.GET)
	public ReturnBody studentWorks(HttpServletRequest request){
		try{
			String stuId = getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			String status = request.getParameter("status");
			List list = workServiceImpl.getListByStuId(stuId, status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 获取作业详情
	 * @param request
	 * @param workId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : 
//	{
//		workId : '作业ID',
//		content : '作业内容',
//		publishTime : '开始时间',
//		endTime : '结束时间'	
//	},
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "works/{workId}", method = RequestMethod.GET)
	public ReturnBody workDetail(HttpServletRequest request,@PathVariable String workId){
		try{
			Work work = workServiceImpl.get(workId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, work);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 添加作业完成标识
	 * @param request
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : 'wsId',//作业完成标识数据主键
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "student/work-status", method = RequestMethod.POST)
	public ReturnBody workstatus(HttpServletRequest request){
		try{
			String stuId = getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			String workId = request.getParameter("workId");
			WorkStatus record = new WorkStatus();
			record.setStuId(stuId);
			record.setWorkId(workId);
			String wsId = (String)workServiceImpl.save(record);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, wsId);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	
	//教师操作
	/**
	 * 获取作业列表（已到期、未到期、待发布、指定截止日期）
	 * @param request  
	 * @return
	 */
	@RequestMapping(value="teacher/work/getWorkList", method=RequestMethod.POST)
	public ReturnBody getListWork(HttpServletRequest request){
		String status = (String) request.getParameter("status");
		String page = (String) request.getParameter("page");
		String userId = getCurrentUser(request).getUserId();
		System.out.println("status:"+status+"  page:"+page+"   userID:"+userId);
		if(StringUtil.checkParams(status,page,userId)){
			try{
				List list = workServiceImpl.getListWork(userId,status,null,Integer.parseInt(page));
				return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
			}
		}else{
			return ReturnBody.getParamError();
		}
	}	
	/**
	 * 查看作业详情信息
	 * @param request
	 * @return
	 */
	int i = 0;
	@RequestMapping(value="teacher/work/detail", method=RequestMethod.POST)
	public ReturnBody getWorkDetail(HttpServletRequest request, String workId){
		try{
			i++;
			System.out.println("workId :"+workId);
			Map data = workServiceImpl.getWorkDetail(workId);
			System.out.println(data.size()+"------i:"+i+"-------data:"+data.toString());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * @author macong
	 * 添加作业/修改作业信息
	 * @param request
	 * @param work
	 * @return
	 */	
	@RequestMapping(value = "teacher/work/addWork", method = RequestMethod.POST)
	public ReturnBody addWork(HttpServletRequest request, Work work ,WorkCourse workCourse){
		try{
			String file = request.getParameter("fileURL");
			System.out.println("file:"+file);
			String endTime = request.getParameter("endTime");
			String publishTime = request.getParameter("publishTime");
			//Date endDate = Date.valueOf(endTime);
			//Date publistDate = Date.valueOf(publishTime); 
			//赋值
			work.setContent(request.getParameter("content"));
			work.setEndTime(endTime);
			work.setPublishTime(publishTime);
			work.setRemindTime(request.getParameter("remindTime"));
			work.setStatus(Integer.parseInt(request.getParameter("status")));
			//作业（除附件之外）的操作
			if(null!=request.getParameter("workId")){
				//编辑作业
				work.setWorkId(request.getParameter("workId"));
				workServiceImpl.saveOrUpdate(work);
			}else if(null == request.getParameter("workId")){
				//新增作业
				/*//生成作业表主键（uuid）
				String wId = CustomIdGenerator.generateShortUuid();
				work.setWorkId(wId);
				System.out.println("wId:"+wId);*/
				workServiceImpl.add(work);
				String wId = work.getWorkId();
				//获取该作业作用的班级列表
				String list = request.getParameter("courseIds");
				//System.out.println(list);
				String lists = list.replace("[", "").replace("]", "").replace("\"", "");
				String [] cIds = lists.split(",");
				//数据库插入数据，每个courseId生成一条数据。
				
				for(int n=0;n<cIds.length;n++){
					//生成作业表主键（uuid）
					String wcId = CustomIdGenerator.generateShortUuid();
					workCourse.setWcId(wcId);
					System.out.println("wId2:"+wId);
					workCourse.setWorkId(wId);
					workCourse.setCourseId(cIds[n]);
					workCourseServiceImpl.add(workCourse);
				}
			}
			//对作业附件的处理
			if(null!= request.getParameter("file")){
				//..
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 更新作业状态信息
	 * @param request
	 * @param WORK_ID
	 * @return
	 */	
	@RequestMapping(value="teacher/work/updateWorkStatus", method=RequestMethod.POST)
	public ReturnBody updateWorkStatus(HttpServletRequest request,Work work,WorkCourse workCourse){
		try{
			int status=Integer.parseInt(request.getParameter("status"));
			String wId = request.getParameter("workId");
			//作业的作用对象ID
			work.setStatus(status);
			//获取该作业作用的班级列表
			String list = request.getParameter("courseIds");
			//System.out.println(list);
			if(list!=null){//作业的接受对象发生变化，更新"作业-课程"关联表。
				String lists = list.replace("[", "").replace("]", "").replace("\"", "");
				String [] cIds = lists.split(",");
				//更新“作业-课程”关联表
				workCourseServiceImpl.deleteData(wId);//删除原有数据
				for(int n=0;n<cIds.length;n++){
					//生成作业表主键（uuid）
					String wcId = CustomIdGenerator.generateShortUuid();
					workCourse.setWcId(wcId);
					workCourse.setWorkId(wId);
					workCourse.setCourseId(cIds[n]);
					workCourseServiceImpl.add(workCourse);
				}
			}
			workServiceImpl.saveOrUpdate(work);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
}
