package com.turing.eteacher.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.PostDAO;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.model.Post;
import com.turing.eteacher.service.IFileService;
import com.turing.eteacher.service.IPostService;
import com.turing.eteacher.util.FileUtil;
import com.turing.eteacher.util.StringUtil;

@Service
public class PostServiceImpl extends BaseService<Post> implements IPostService {

	@Autowired
	private IFileService fileServiceImpl;
	
	@Autowired
	private PostDAO postDAO;
	
	@Override
	public BaseDAO<Post> getDAO() {
		return postDAO;
	}

	@Override
	public List<Map> getListByPage(String courseId, int page, int pageSize) {
		List args = new ArrayList();
		String hql = "select p.postId as postId,s.stuName as stuName," +
				"substring(p.content,1,25) as content,p.createTime as createTime " +
				"from Post p,Student s where p.userId = s.stuId ";
		if(StringUtil.isNotEmpty(courseId)){
			hql += "and p.courseId = ? ";
			args.add(courseId);
		}
		hql += "order by p.createTime desc";
		int first = (page-1)*pageSize;
		int max = first + pageSize -1;
		return postDAO.findMapByPage(hql, first, max, args);
	}

	@Override
	public Map getPostData(String postId) {
		Map data = null;
		String hql = "select p.postId as postId,s.stuName as stuName," +
				"p.createTime as createTime,p.content as content," +
				"(select count(pl.plId) from PostLike pl where pl.postId = p.postId) as likeCounts," +//点赞数
//				"(select count(pr.prId) from PostReply pr where pr.postId = p.postId) as replyCounts " +//评论数
				"from Post p,Student s where p.userId = s.stuId and p.postId = ?";
		List<Map> list = postDAO.findMap(hql, postId);
		if(list!=null&&list.size()>0){
			data = list.get(0);
			//图片
			List<CustomFile> files = fileServiceImpl.getListByDataId(postId);
			for(CustomFile file : files){
				file.setServerName(FileUtil.getFileStorePath() + file.getServerName());
			}
			data.put("files", files);
			//评论列表
			hql = "select pr.prId as prId,pr.content as content,s.stuName as stuName " +
					"from PostReply pr,Student s where pr.userId = s.stuId and pr.postId = ? " +
					"order by pr.createTime desc";
			List<Map> replays = postDAO.find(hql, postId);
			data.put("replays", replays);
		}
		return data;
	}

	@Override
	public void savePost(Post post, List<MultipartFile> files) throws Exception {
		postDAO.save(post);
		if(files!=null){
			for(MultipartFile file : files){
				CustomFile customFile = FileUtil.genCustomFile(file, post.getPostId());
				if(customFile != null){
					postDAO.save(customFile);
				}
			}
		}
	}

}
