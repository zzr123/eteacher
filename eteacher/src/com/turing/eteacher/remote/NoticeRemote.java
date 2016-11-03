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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Notice;
import com.turing.eteacher.model.User;
import com.turing.eteacher.model.WorkCourse;
import com.turing.eteacher.service.INoticeService;
import com.turing.eteacher.service.IWorkCourseService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class NoticeRemote extends BaseRemote {

	@Autowired
	private INoticeService noticeServiceImpl;
	
	@Autowired
	private IWorkCourseService workCourseServiceImpl;

	/**
	 * 教师端通知展示列表
	 * 
	 * @param request
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : [
	// {
	// noticeId : '通知ID'
	// title : '通知标题',
	// content : '通知内容',
	// status : '状态',
	// publishTime : '发布时间',
	// noticeObject : '通知对象'
	// }
	// ],
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "teacher/notices", method = RequestMethod.GET)
	public ReturnBody teacherNotices(HttpServletRequest request) {
		try {
			User currentUser = getCurrentUser(request);
			String userId = currentUser != null ? currentUser.getUserId()
					: null;
			List list = noticeServiceImpl.getListForTable(userId, true, true);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	// 教师端接口的实现
	/**
	 * 获取通知列表(已发布、待发布)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacher/notice/getNoticeList", method = RequestMethod.POST)
	public ReturnBody getListEndNotice(HttpServletRequest request) {
		String status = (String) request.getParameter("status");
		String page = (String) request.getParameter("page");
		String userId = getCurrentUser(request).getUserId();
		System.out.println("****status:" + status + "  page:" + page
				+ "   userID:" + userId);
		if (StringUtil.checkParams(status, page, userId)) {
			try {
				List list = noticeServiceImpl.getListNotice(userId, status,
						null, Integer.parseInt(page));
				return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
			} catch (Exception e) {
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE,
						ReturnBody.ERROR_MSG);
			}
		} else {
			return ReturnBody.getParamError();
		}
	}

	/**
	 * 通知状态的修改（待发布通知->立即通知，删除通知）
	 * 
	 * @param request
	 * @param notice_id
	 * @return
	 */
	@RequestMapping(value = "teacher/notice/updateNotice", method = RequestMethod.POST)
	public ReturnBody ChangeNoticeState_Publish(HttpServletRequest request) {
		try {
			String noticeId = request.getParameter("noticeId");
			String status = request.getParameter("status");
			noticeServiceImpl.ChangeNoticeState(noticeId, status);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 查看通知详情
	 * 
	 * @param request
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "teacher/notice/getNoticeDetail/{noticeId}", method = RequestMethod.GET)
	public ReturnBody getNoticeDetail(HttpServletRequest request,
			@PathVariable String noticeId) {
		try {
			List list = noticeServiceImpl.getNoticeDetail(noticeId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 查看通知未读人员列表
	 * 
	 * @param request
	 * @param notice_id
	 * @return
	 */
	@RequestMapping(value = "teacher/notice/getNoticeLog/{notice_id}", method = RequestMethod.GET)
	public ReturnBody getNoticeLog(HttpServletRequest request,
			@PathVariable String notice_id) {
		try {
			List list = noticeServiceImpl.getNoticeLog(notice_id);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 添加通知/修改通知信息
	 * 
	 * @param request
	 * @param notice
	 * @return
	 */
	@RequestMapping(value = "teacher/notice/addNotice", method = RequestMethod.POST)
	public ReturnBody addNotice(HttpServletRequest request) {
		try {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String noticeId = request.getParameter("noticeId");
			String publishTime = request.getParameter("publishTime");
			String course = request.getParameter("course");
			if (StringUtil.checkParams(title, content, publishTime, course)) {
				List<Map> list = (List<Map>) JSONUtils.parse(course);
				if (StringUtil.isNotEmpty(noticeId)) {
					// TODO 更新通知内容
					Notice notice = noticeServiceImpl.get(noticeId);
					if (null != notice) {
						notice.setTitle(title);
						notice.setContent(content);
						notice.setPublishTime(publishTime);
						notice.setUserId(getCurrentUserId(request));
						noticeServiceImpl.update(notice);
					}
					// TODO 删除关联表中的数据
					workCourseServiceImpl.deleteData(noticeId);
					// TODO 重新加入关联表
					for (int i = 0; i < list.size(); i++) {
						String courseId = (String) list.get(i).get("id");
						WorkCourse workCourse =new WorkCourse();
						workCourse.setWorkId(noticeId);
						workCourse.setCourseId(courseId);
						workCourseServiceImpl.add(workCourse);
					}
				} else {
					// TODO 增加通知
					Notice notice = new Notice();
					notice.setTitle(title);
					notice.setContent(content);
					notice.setPublishTime(publishTime);
					noticeServiceImpl.save(notice);
					noticeId = notice.getNoticeId();
					// TODO 增加关联表数据
					for (int i = 0; i < list.size(); i++) {
						String courseId = (String) list.get(i).get("id");
						WorkCourse workCourse =new WorkCourse();
						workCourse.setWorkId(noticeId);
						workCourse.setCourseId(courseId);
						workCourseServiceImpl.add(workCourse);
					}
				}
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}
}
