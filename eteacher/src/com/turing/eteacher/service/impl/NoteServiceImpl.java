package com.turing.eteacher.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.NoteDAO;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.model.Note;
import com.turing.eteacher.service.IFileService;
import com.turing.eteacher.service.INoteService;
import com.turing.eteacher.util.CustomIdGenerator;
import com.turing.eteacher.util.FileUtil;

@Service
public class NoteServiceImpl extends BaseService<Note> implements INoteService {

	@Autowired
	private NoteDAO noteDAO;
	
	@Autowired
	private IFileService fileServiceImpl;
	
	@Override
	public BaseDAO<Note> getDAO() {
		return noteDAO;
	}

	@Override
	public void saveNote(Note note, List<MultipartFile> files) throws Exception {
		noteDAO.save(note);
		String pathRoot = FileUtil.getRootPath();
		if(files!=null){
			for(MultipartFile file : files){
				if(!file.isEmpty()){
					String serverName = CustomIdGenerator.generateShortUuid() + FileUtil.getSuffixes(file.getOriginalFilename());
	    	        String path="/upload/"+serverName;
	    	        file.transferTo(new File(pathRoot+path));
	    	        CustomFile customFile = new CustomFile();
	    	        customFile.setDataId(note.getNoteId());
	    	        customFile.setFileName(file.getOriginalFilename());
	    	        customFile.setServerName(serverName);
	    	        noteDAO.save(customFile);
				}
			}
		}
	}

	@Override
	public List getNoteDateList(String userId, String courseId) {
		String hql = "select distinct(date_format(n.createTime,'%Y%m%d')) from Note n where n.userId = ? and n.courseId = ? order by n.createTime desc";
		List<String> list = noteDAO.find(hql, userId, courseId);
		return list;
	}

	@Override
	public List<Map> getListByDate(String userId, String date) {
		String hql = "select n.noteId as noteId, n.content as content from Note n where n.userId = ? and date_format(n.createTime,'%Y-%m-%d') = ?";
		List<Map> list = noteDAO.find(hql, userId, date);
		//附件
		for(Map record : list){
			String noteId = (String) record.get("noteId");
			List<CustomFile> files = fileServiceImpl.getListByDataId(noteId);
 			for(CustomFile file : files){
 				file.setServerName("/upload/" + file.getServerName());
			}
 			record.put("files", files);
		}
		return list;
	}

	@Override
	public void deleteByDate(String userId, String date) {
		String hql = "delete from Note n where n.userId = ? and date_format(n.createTime,'%Y-%m-%d') = ?";
		noteDAO.executeHql(hql, userId, date);
	}
}
