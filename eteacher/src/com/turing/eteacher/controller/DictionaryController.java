package com.turing.eteacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IDictionary2PrivateService;
import com.turing.eteacher.service.IDictionary2PublicService;
import com.turing.eteacher.service.impl.Dictionary2PrivateServiceImpl;
import com.turing.eteacher.service.impl.Dictionary2PublicServiceImpl;

@Controller
@RequestMapping("dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private IDictionary2PrivateService Dictionary2PrivateServiceImpl;

	@RequestMapping("viewDictionaryModal")
	public String viewDictionaryModal(HttpServletRequest request) {
		// 获取字典表的列表项
		User currentUser = getCurrentUser(request);
		int TYPE = Integer.valueOf(request.getParameter("type"));
		String parentId = request.getParameter("pid");
		List<Map> titleList = Dictionary2PrivateServiceImpl.getListByType(TYPE, currentUser.getUserId());
		request.setAttribute("titleList", titleList);
		request.setAttribute("type", TYPE);
		request.setAttribute("pid", parentId);
		return "dictionary";
	}

	@RequestMapping("viewDictionaryDel")
	@ResponseBody
	public boolean viewDictionaryDel(HttpServletRequest request) {
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		String id = request.getParameter("id");
		int TYPE = Integer.valueOf(request.getParameter("type"));
		boolean result = Dictionary2PrivateServiceImpl.deleteItem(TYPE,currentUser.getUserId(),id);
		return result;
	}
	//增加用户自定义字典项
	@RequestMapping("viewDictionaryAdd")
	@ResponseBody
	public boolean viewDictionaryAdd(HttpServletRequest request) {
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		String value = request.getParameter("value");
		int TYPE = Integer.valueOf(request.getParameter("type"));
		boolean result = Dictionary2PrivateServiceImpl.addItem(TYPE, value, currentUser.getUserId());
		System.out.println("增加结果为："+result);
		return result;
	}
}

