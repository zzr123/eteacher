package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Post;

public interface IPostService extends IService<Post> {

	public List<Map> getListByPage(String courseId, int page, int pageSize);
	
	public Map getPostData(String postId);
	
	public void savePost(Post post, List<MultipartFile> files) throws Exception;
}
