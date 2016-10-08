package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.Note;

public interface INoteService extends IService<Note> {

	public void saveNote(Note note, List<MultipartFile> files) throws Exception;
	
	public List getNoteDateList(String userId, String courseId);
	
	public List<Map> getListByDate(String userId, String date);
	
	public void deleteByDate(String userId, String date);
}
