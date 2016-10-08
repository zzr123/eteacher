package com.turing.eteacher.remote;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.model.WorkStatus;
import com.turing.eteacher.service.IWorkService;

/**
 * @author Administrator
 *
 */
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

	/**
	 * 获取学生作业列表 
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
	@RequestMapping(value="teacher/work/getWorkList/{status}/{date}", method=RequestMethod.GET)
	public ReturnBody getListWork(HttpServletRequest request, @PathVariable String status, @PathVariable String date){
		String userId = getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
		try{
			List list = workServiceImpl.getListWork(userId,status,date);	
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}	
	/**
	 * 查看作业详情信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="teacher/work/getWorkDetail/{work_id}", method=RequestMethod.GET)
	public ReturnBody getWorkDetail(HttpServletRequest request,@PathVariable String work_id){
		try{
			List list = workServiceImpl.getWorkDetail(work_id);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 添加作业/修改作业信息
	 * @param request
	 * @param work
	 * @return
	 */	
	@RequestMapping(value = "teacher/work/addWork", method = RequestMethod.POST)
	public ReturnBody addWork(HttpServletRequest request, Work work){
		/*work.setCourseId("DDJHAT0SKb");
		work.setContent("22222222222222222222222");
		work.setEndTime(new Date());
		work.setPublishTime(new Date());
		work.setRemindTime("2");
		work.setTimeLength(2);*/
		try{
			String status=request.getParameter("status");
			String operator=request.getParameter("operator");
			if("0".equals(status)){
				work.setPublishType("01");
			}
			else{
				work.setPublishType("02");
			}
			work.setStatus(1);
			if("add".equals(operator)){
				workServiceImpl.add(work);
			}
			else if("update".equals(operator)){
				//work.setWorkId("SIf0nz0YHw");
				workServiceImpl.saveOrUpdate(work);
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
	public ReturnBody updateWorkStatus(HttpServletRequest request){
		try{
			String status=request.getParameter("status");
			String workId=request.getParameter("work_id");
			
			workServiceImpl.updateWorkStatus(workId,status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
}
