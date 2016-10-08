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
import com.turing.eteacher.model.Classes;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IClassService;
import com.turing.eteacher.service.IMajorService;

@Controller
@RequestMapping("class")
public class ClassController extends BaseController {
	
	@Autowired
	private IClassService classServiceImpl;
	
	@Autowired
	private IMajorService majorServiceImpl;
	
	@RequestMapping("viewListClass")
	public String viewListClass(HttpServletRequest request){
		return "class/listClass";
	}
	
	@RequestMapping("viewAddClass")
	public String viewAddClass(HttpServletRequest request){
		return "class/classForm";
	}
	
	@RequestMapping("viewEditClass")
	public String viewEditClass(HttpServletRequest request){
		String classId = request.getParameter("classId");
		request.setAttribute("editFlag", "true");
		request.setAttribute("classId", classId);
		return "class/classForm";
	}
	
	@RequestMapping("getClassListData")
	@ResponseBody
	public Object getClassListData(HttpServletRequest request){
		List list = classServiceImpl.getClassList();
		Map result = new HashMap();
		result.put("data", list);
		return result;
	}
	
	@RequestMapping("getClassData")
	@ResponseBody
	public Object getClassData(HttpServletRequest request){
		String classId = request.getParameter("classId");
		return classServiceImpl.get(classId);
	}
	
	@RequestMapping("getMajorSelectData")
	@ResponseBody
	public Object getMajorSelectData(HttpServletRequest request){
		String parentId = request.getParameter("parentId");
		List list = majorServiceImpl.getListByParentId(parentId);
		return list;
	}
	
	@RequestMapping("saveClass")
	@ResponseBody
	public String saveClass(HttpServletRequest request, Classes classes){
		if("".equals(classes.getClassId())){
			classes.setClassId(null);
		}
		classServiceImpl.saveOrUpdate(classes);
		return "success";
	}
	
	@RequestMapping("deleteClass")
	@ResponseBody
	public Object deleteClass(HttpServletRequest request){
		String classId = request.getParameter("classId");
		classServiceImpl.deleteById(classId);
		return "success";
	}
}
