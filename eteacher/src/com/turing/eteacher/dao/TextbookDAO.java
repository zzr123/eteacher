package com.turing.eteacher.dao;

import org.springframework.stereotype.Repository;

import com.turing.eteacher.base.BaseDAO;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.Textbook;

@Repository
public class TextbookDAO extends BaseDAO<Textbook> {

	public int deleteOthersByCourseId(String courseId){
		String hql = "delete from Textbook where courseId = ? and textbookType =?";
		return executeHql(hql, courseId, EteacherConstants.BOOKTEXT_OTHER);
	}
}
