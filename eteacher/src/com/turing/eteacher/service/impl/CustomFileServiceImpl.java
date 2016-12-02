package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.dao.CustomFileDAO;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.service.ICustomFileService;
import com.turing.eteacher.service.IDictionary2PrivateService;

@Service
public class CustomFileServiceImpl extends BaseService<CustomFile> implements ICustomFileService  {
	
	@Autowired
	private CustomFileDAO customFileDAO;

	@Autowired
	private IDictionary2PrivateService dictionary2PrivateServiceImpl;
	
	@Override
	public BaseDAO<CustomFile> getDAO() {
		return customFileDAO;
	}

	@Override
	public List<Map> getListByCourse(String courseId, int page,String path) {
		String sql = "SELECT tf.File_ID AS fileId, "+
				"tf.DATA_ID AS dataId, "+
				"tf.FILE_NAME AS fileName, "+
				"tf.SERVER_NAME AS serverName, "+
				"tf.VOCABULARY_ID AS vocabularyId "+
				"FROM t_file tf WHERE tf.IS_COURSE_FILE = 1 "+
				"AND tf.FILE_AUTH = '01' AND tf.DATA_ID = ? ";
		List<Map> list = customFileDAO.findBySqlAndPage(sql, page*20, 20, courseId);
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				Map map = dictionary2PrivateServiceImpl.getValueById((String)list.get(i).get("vocabularyId"));
				if (null != map) {
					list.get(i).put("vocabulary",(String)map.get("value"));
				}
				list.get(i).put("path", path+"/"+list.get(i).get("serverName"));
			}
		}
		return list;
	}


}
