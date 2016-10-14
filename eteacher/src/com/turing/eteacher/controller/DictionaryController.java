package com.turing.eteacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		int TYPE = Integer.valueOf(request.getParameter("type"));
		System.out.println("-------------------"+TYPE);
		List<Map> titleList = Dictionary2PrivateServiceImpl.getListByType(TYPE, currentUser.getUserId());
		System.out.println("list:"+titleList.size());
		request.setAttribute("titleList", titleList);
		return "dictionary";
	}

	@RequestMapping("viewDictionaryDel")
	public String viewDictionaryDel(HttpServletRequest request) {
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		String id = request.getParameter("id");
		String TYPE = request.getParameter("type");
		//List<Map> titleList = Dictionary2PublicServiceImpl.delListById(TYPE,id, currentUser.getUserId());
		//request.setAttribute("titleList", titleList);
		return null;
	}
}
