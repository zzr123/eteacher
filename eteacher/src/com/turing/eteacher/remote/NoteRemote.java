package com.turing.eteacher.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Note;
import com.turing.eteacher.service.INoteService;
import com.turing.eteacher.util.FileUtil;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class NoteRemote extends BaseRemote {

	@Autowired
	private INoteService noteServiceImpl;

	/**
	 * 用户新增笔记
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "student/note/add", method = RequestMethod.POST)
	public ReturnBody addNote(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String createTime = request.getParameter("createTime");
		String isKey = request.getParameter("isKey");
		String courseId = request.getParameter("courseId");
		if (StringUtil.checkParams(isKey, createTime, courseId)) {
			Note note = new Note();
			note.setTitle(title);
			note.setContent(content);
			note.setCreateTime(createTime);
			note.setIsKey(isKey);
			note.setUserId(getCurrentUserId(request));
			note.setCourseId(courseId);
			try {
				List<MultipartFile> files = null;
				if (request instanceof MultipartRequest) {
					MultipartRequest multipartRequest = (MultipartRequest) request;
					files = multipartRequest.getFiles("myFiles");
				}
				noteServiceImpl.saveNote(note, files,FileUtil.getUploadPath(request));
				return new ReturnBody("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE,
						ReturnBody.ERROR_MSG);
			}
		} else {
			return ReturnBody.getParamError();
		}
		// try {
		// List<MultipartFile> files = null;
		// if(request instanceof MultipartRequest){
		// MultipartRequest multipartRequest = (MultipartRequest)request;
		// files = multipartRequest.getFiles("files");
		// }
		// note.setUserId(getCurrentUser(request).getUserId());
		// noteServiceImpl.saveNote(note, files);
		// return new ReturnBody(ReturnBody.RESULT_SUCCESS, note.getNoteId());
		// } catch (Exception e) {
		// e.printStackTrace();
		// return new ReturnBody(ReturnBody.RESULT_FAILURE,
		// ReturnBody.ERROR_MSG);
		// }
	}

	/**
	 * 获取笔记日期列表
	 * 
	 * @param request
	 * @param courseId
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : ['日期(yyyyMMdd)'],
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "course/{courseId}/note/dates", method = RequestMethod.GET)
	public ReturnBody noteList(HttpServletRequest request,
			@PathVariable String courseId) {
		try {
			String userId = getCurrentUser(request).getUserId();
			List list = noteServiceImpl.getNoteDateList(userId, courseId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 根据日期查看笔记
	 * 
	 * @param request
	 * @param noteId
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : [
	// {
	// noteId : '笔记ID',
	// content : '笔记内容',
	// files : [
	// {
	// fileId : '附件ID',
	// fileName : '附件名称',
	// serverName : 'upload/abc.jpg'
	// }
	// ]
	// },
	// ],
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "date/{date}/notes", method = RequestMethod.GET)
	public ReturnBody noteData(HttpServletRequest request,
			@PathVariable String date) {
		try {
			List list = noteServiceImpl.getListByDate(getCurrentUser(request)
					.getUserId(), date);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 删除笔记
	 * 
	 * @param request
	 * @param noteId
	 * @return
	 */
	/*
	 * { result : 'success',//成功success，失败failure data : {}, msg : '提示信息XXX' }
	 */
	@RequestMapping(value = "notes/{noteId}", method = RequestMethod.DELETE)
	public ReturnBody deleteNote(HttpServletRequest request,
			@PathVariable String noteId) {
		try {
			noteServiceImpl.deleteById(noteId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	/**
	 * 删除用户某天的笔记内容
	 * 
	 * @param request
	 * @param noteId
	 * @return
	 */
	// {
	// result : 'success',//成功success，失败failure
	// data : {},
	// msg : '提示信息XXX'
	// }
	@RequestMapping(value = "date/{date}/notes", method = RequestMethod.DELETE)
	public ReturnBody deleteNoteByDate(HttpServletRequest request,
			@PathVariable String date) {
		try {
			noteServiceImpl.deleteByDate(getCurrentUser(request).getUserId(),
					date);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}
	}

	@RequestMapping(value = "student/noticeList/{tokenid}/{courseid}/{type}", method = RequestMethod.GET)
	public ReturnBody noticeList(HttpServletRequest request,
			@PathVariable String tokenid, @PathVariable String courseid,
			@PathVariable int type) {

		return null;
	}
}
