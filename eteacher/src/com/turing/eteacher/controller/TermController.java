package com.turing.eteacher.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.ICourseService;
import com.turing.eteacher.service.ITermService;
import com.turing.eteacher.util.BeanUtils;

@Controller
@RequestMapping("term")
public class TermController extends BaseController {
	
	@Autowired
	private ITermService termServiceImpl;
	
	@Autowired
	private ICourseService courseServiceImpl;
	
	@RequestMapping("viewListTerm")
	public String viewListTerm(){
		return "term/listTerm";
	}
	
	@RequestMapping("viewAddTerm")
	public String viewAddTerm(){
		return "term/addTerm";
	}
	
	@RequestMapping("viewEditTerm")
	public String viewEditTerm(HttpServletRequest request){
		String termId = request.getParameter("termId");
		//Term term = termServiceImpl.get(termId);
		//request.setAttribute("term", term);
		return "term/editTerm";
	}
	
	@RequestMapping("getTermListData")
	@ResponseBody
	public Object getTermListData(HttpServletRequest request){
		User currentUser = getCurrentUser(request);
		List list = termServiceImpl.getTermList(currentUser.getUserId());
		Map result = new HashMap();
		result.put("data", list);
		return result;
	}
	
	@RequestMapping("addTerm")
	@ResponseBody
	public String addTerm(HttpServletRequest request, Term term){
		User currentUser = getCurrentUser(request);
		//term.setUserId(currentUser.getUserId());
		termServiceImpl.saveTerm(term);
		return "success";
	}
	
	@RequestMapping("updateTerm")
	@ResponseBody
	public String updateTerm(HttpServletRequest request, Term term){
		termServiceImpl.saveTerm(term);
		return "success";
	}
	
	@RequestMapping("deleteTerm1")
	@ResponseBody
	public Object deleteTerm(HttpServletRequest request){
		String termId = request.getParameter("termId");
		List list = courseServiceImpl.getListByTermId(termId, null);
		if(list!=null&&list.size()>0){
			return "该学期下有课程数据，无法删除。";
		}
		termServiceImpl.deleteById(termId);
		return "success";
	}
}
