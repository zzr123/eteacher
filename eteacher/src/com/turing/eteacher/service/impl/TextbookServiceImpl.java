package com.turing.eteacher.service.impl;

import java.util.List;

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

}
