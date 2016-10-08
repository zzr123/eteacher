package com.turing.eteacher.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.FileDAO;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.service.IFileService;

@Service
public class FileServiceImpl extends BaseService<CustomFile> implements IFileService {

	@Autowired
	private FileDAO fileDAO;
	
	@Override
	public BaseDAO<CustomFile> getDAO() {
		return fileDAO;
	}

	@Override
	public List<CustomFile> getListByDataId(String dataId) {
		String hql = "from CustomFile where dataId = ? order by createTime";
		List<CustomFile> list = fileDAO.find(hql, dataId);
		return list;
	}

}
