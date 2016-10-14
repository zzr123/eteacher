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
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		int TYPE = Integer.valueOf(request.getParameter("type"));
		List<Map> titleList = Dictionary2PrivateServiceImpl.getListByType(TYPE, currentUser.getUserId());
		System.out.println("list:"+titleList.toString());
		request.setAttribute("titleList", titleList);
		request.setAttribute("type", TYPE);
		return "dictionary";
	}

	@RequestMapping("viewDictionaryDel")
	@ResponseBody
	public String viewDictionaryDel(HttpServletRequest request) {
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		String id = request.getParameter("id");
		int TYPE = Integer.valueOf(request.getParameter("type"));
		System.out.println("-------------------"+TYPE);
		boolean result = Dictionary2PrivateServiceImpl.deleteItem(TYPE,currentUser.getUserId(),id);
		String data = null;
		if(result == true){
			data = "success";
		}else{
			data = "error";
		}
		return data;
	}
}
