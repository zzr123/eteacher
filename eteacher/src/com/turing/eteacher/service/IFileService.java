package com.turing.eteacher.service;

import java.util.List;
import java.util.Map;

import com.turing.eteacher.base.IService;
import com.turing.eteacher.model.CustomFile;

public interface IFileService extends IService<CustomFile> {

	public List<CustomFile> getListByDataId(String dataId);
	
	/**
	 * 获取笔记附件
	 * @author lifei
	 * @param noteId
	 * @return
	 */
	public List<Map> getNoteFileList(String  noteId);
	/**
	 * 删除笔记
	 * @author lifei
	 * @param noteId
	 */
	public void deletebyDataId(String noteId,String path);
}
