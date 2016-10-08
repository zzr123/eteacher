package com.turing.eteacher.remote;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Post;
import com.turing.eteacher.model.PostReply;
import com.turing.eteacher.service.IPostService;

@RestController
@RequestMapping("remote")
public class PostRemote extends BaseController {
	
	@Autowired
	private IPostService postServiceImpl;

	/**
	 * 查询同学帮列表
	 * @param request
	 * @return
	 */
	//--request--
//{
//	courseId : '课程ID',//不传该参数代表查询所有课程
//	page : '页码',
//	pageSize : '每页数据条数'
//}
	//--response--
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			postId : '同学帮ID'
//			stuName : '作者姓名',
//			createTime : '发布时间',
//			content : '内容（前25个字）'
//		}
//	],
//	msg : '提示信息XXX'
//}	
	@RequestMapping(value = "posts", method = RequestMethod.GET)
	public ReturnBody posts(HttpServletRequest request){
		try{
			int page = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			String courseId = request.getParameter("courseId");
			List list = postServiceImpl.getListByPage(courseId, page, pageSize);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 查询同学帮详情
	 * @param request
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : {
//		postId : '同学帮ID'
//		stuName : '作者姓名',
//		createTime : '发布时间',
//		content : '内容',
//		likeCounts : '点赞数',
//		files : [
//			{
//				serverName : '图片http访问地址'
//			}
//		],
//		replys : [
//			{
//				prId : '回复ID',
//				stuName : '回复人',
//				content : '回复内容'
//			}
//		]
//	},
//	msg : '提示信息XXX'
//}
	@RequestMapping(value = "posts/{postId}", method = RequestMethod.GET)
	public ReturnBody getPostData(HttpServletRequest request, @PathVariable String postId){
		try{
			Map data = postServiceImpl.getPostData(postId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, data);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 添加同学帮
	 * @param request
	 * @param post
	 * @return
	 */
	//--request--
//{
//	courseId : '课程ID',
//	content : '内容',
//	files : ['图片文件流']
//}
	//--response--
//{
//	result : 'success',//成功success，失败failure
//	data : '同学帮ID',
//	msg : '提示信息XXX'
//}
	@RequestMapping(value = "posts", method = RequestMethod.POST)
	public ReturnBody addPost(HttpServletRequest request, Post post){
		try{
			List<MultipartFile> files = null;
			if(request instanceof MultipartRequest){
				MultipartRequest multipartRequest = (MultipartRequest)request;
				files = multipartRequest.getFiles("files");
			}
			post.setUserId(getCurrentUser(request).getUserId());
			postServiceImpl.savePost(post, files);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, post.getPostId());
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	/**
	 * 添加同学帮回复
	 * @param request
	 * @param post
	 * @return
	 */
	//--request--
//{
//	postId : '同学帮ID',
//	parentPrId : '回复同学帮回复的ID（对同学帮的回复不传该参数）',	
//	content : '内容'
//}
	//--response--
//{
//	result : 'success',//成功success，失败failure
//	data : '同学帮ID',
//	msg : '提示信息XXX'
//}
	@RequestMapping(value = "post/replays", method = RequestMethod.POST)
	public ReturnBody addPostReply(HttpServletRequest request, PostReply reply){
		try{
			reply.setUserId(getCurrentUser(request).getUserId());
			postServiceImpl.save(reply);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, reply.getPrId());
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
}
