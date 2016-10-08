package com.turing.eteacher.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Notice;
import com.turing.eteacher.model.User;
import com.turing.eteacher.model.Work;
import com.turing.eteacher.service.INoticeService;

@RestController
@RequestMapping("remote")
public class NoticeRemote extends BaseController {

	@Autowired
	private INoticeService noticeServiceImpl;
	
	/**
	 * 教师端通知展示列表
	 * @param request
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			noticeId : '通知ID'
//			title : '通知标题',
//			content : '通知内容',
//			status : '状态',
//			publishTime : '发布时间',
//			noticeObject : '通知对象'
//		}
//	],
//	msg : '提示信息XXX'
//}
	@RequestMapping(value = "teacher/notices", method = RequestMethod.GET)
	public ReturnBody teacherNotices(HttpServletRequest request){
		try{
			User currentUser = getCurrentUser(request);
			String userId = currentUser!=null?currentUser.getUserId():null;
			List list = noticeServiceImpl.getListForTable(userId,true,true);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	//教师端接口的实现
	/**
	 * 获取通知列表(已发布、待发布)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="teacher/notice/getNoticeList/{status}", method=RequestMethod.GET)
	public ReturnBody getListEndNotice(HttpServletRequest request, @PathVariable String status){
		try{
			String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			List list = noticeServiceImpl.getListNotice(userId,status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 通知状态的修改（待发布通知->立即通知，删除通知）
	 * @param request
	 * @param notice_id
	 * @return
	 */
	@RequestMapping(value="teacher/notice/updateNotice", method=RequestMethod.POST)
	public ReturnBody ChangeNoticeState_Publish(HttpServletRequest request){
		try{
			String noticeId=request.getParameter("noticeId");
			String status=request.getParameter("status");
			noticeServiceImpl.ChangeNoticeState(noticeId,status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 查看通知详情
	 * @param request
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="teacher/notice/getNoticeDetail/{noticeId}", method=RequestMethod.GET)
	public ReturnBody getNoticeDetail(HttpServletRequest request,@PathVariable String noticeId){
		try{
			List list = noticeServiceImpl.getNoticeDetail(noticeId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 查看通知未读人员列表
	 * @param request
	 * @param notice_id
	 * @return
	 */
	@RequestMapping(value="teacher/notice/getNoticeLog/{notice_id}", method=RequestMethod.GET)
	public ReturnBody getNoticeLog(HttpServletRequest request,@PathVariable String notice_id){
		try{
			List list = noticeServiceImpl.getNoticeLog(notice_id);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 添加通知/修改通知信息
	 * @param request
	 * @param notice
	 * @return
	 */	
	@RequestMapping(value = "teacher/notice/addNotice", method = RequestMethod.POST)
	public ReturnBody addNotice(HttpServletRequest request, Notice notice){
		/*notice.setCourseId("DDJHAT0SKb");
		notice.setTitle("wwwwww");
		notice.setContent("yyyyyyyyyyyyyyyyyyyyyyyyyyy");
		notice.setPublishTime(new Date());*/
		
		try{
			
			String status=request.getParameter("status");
			
			String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			notice.setUserId(userId);
			notice.setStatus(1);
			if("0".equals(status)){
				noticeServiceImpl.add(notice);
			}
			else{	
				//notice.setNoticeId("peWmBjMez7");
				noticeServiceImpl.saveOrUpdate(notice);
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
}
