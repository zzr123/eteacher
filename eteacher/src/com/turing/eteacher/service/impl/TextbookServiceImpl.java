package com.turing.eteacher.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.base.BaseService;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.dao.TextbookDAO;
import com.turing.eteacher.model.Textbook;
import com.turing.eteacher.service.ITextbookService;

@Service
public class TextbookServiceImpl extends BaseService<Textbook> implements ITextbookService {

	@Autowired
	private TextbookDAO textbookDAO;
	
	@Override
	public BaseDAO<Textbook> getDAO() {
		return textbookDAO;
	}

	@Override
	public Textbook getMainTextbook(String courseId) {
		Textbook textbook = null;
		String hql = "from Textbook where courseId = ? and textbookType = ?";
		List<Textbook> list = textbookDAO.find(hql, courseId, EteacherConstants.BOOKTEXT_MAIN);
		if(list!=null&&list.size()>0){
			textbook = list.get(0);
		}
		return textbook;
	}

	@Override
	public List<Textbook> getTextbookList(String courseId) {
		String hql = "from Textbook where courseId = ? and textbookType = ?";
		List<Textbook> list = textbookDAO.find(hql, courseId, EteacherConstants.BOOKTEXT_OTHER);
		return list;
	}

	@Override
	public boolean delTextbook(String courseId,String type) {
		String sql = "DELETE FROM t_textbook t WHERE t.COURSE_ID = ? and t.TEXTBOOK_TYPE = ?";
		textbookDAO.executeBySql(sql, courseId,type);
		return true;
	}

	@Override
	public List<Map> getTextbook(String courseId, String type) {
		String hql = "select t.textbookId as textbookId,t.textbookName as textbookName," +
				"t.author as author,t.publisher as publisher ,t.edition as edition,t.isbn as isbn " +
				"from Textbook t where t.courseId=? and ";
		if ("1".equals(type)) {
			hql += "t.textbookType=01";
		} else {
			hql += "t.textbookType=02";
		}
		List<Map> list = textbookDAO.findMap(hql, courseId); 
		return list;
	}

}
