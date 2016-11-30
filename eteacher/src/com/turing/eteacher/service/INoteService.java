package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Note;

public interface INoteService extends IService<Note> {

	public void saveNote(Note note, List<MultipartFile> files,String savePath) throws Exception;
	
	public List getNoteDateList(String userId, String courseId);
	
	public List<Map> getListByDate(String userId, String date);
	
	public void deleteByDate(String userId, String date);
	/**
	 * 获取指定课程的笔记列表
	 * @param userId
	 * @param courseId
	 * @param page
	 * @return
	 */
	public List<Note> getNoteListByCourseId(String userId,String courseId,int flag,int page);
}
