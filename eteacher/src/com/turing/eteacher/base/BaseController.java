package com.turing.eteacher.base;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.Student;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.model.TermPrivate;
import com.turing.eteacher.model.User;

public class BaseController {

	/**
	 * <li>功能描述：设置request中绑定的参数。
	 * 
	 * @param binder
	 *            WebDataBinder
	 * @author caojian
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			public void setAsText(String value) {
				try {
					setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
				} catch (java.text.ParseException e) {
					try{
						setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
					} catch (java.text.ParseException e1) {
						setValue(null);
					}
				}
			}
		});
	}
	
	public User getCurrentUser(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(EteacherConstants.CURRENT_USER);
		return user;
	}
	
	public TermPrivate getCurrentTerm(HttpServletRequest request){
		TermPrivate tpId = (TermPrivate)request.getSession().getAttribute(EteacherConstants.CURRENT_TERM);
		return tpId;
	}
	
	public Student getCurrentStudent(HttpServletRequest request){
		Student student = (Student)request.getSession().getAttribute(EteacherConstants.CURRENT_STUDENT);
		return student;
	}
}
