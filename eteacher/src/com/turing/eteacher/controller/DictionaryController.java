package com.turing.eteacher.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IDictionary2PublicService;
import com.turing.eteacher.service.impl.Dictionary2PublicServiceImpl;

@Controller
@RequestMapping("dictionary")
public class DictionaryController extends BaseController {

	@Autowired
	private IDictionary2PublicService Dictionary2PublicServiceImpl;

	@RequestMapping("viewDictionaryModal")
	public String viewDictionaryModal(HttpServletRequest request) {
		// 获取职称下拉列表
		User currentUser = getCurrentUser(request);
		String TYPE = "04";
		List<Map> titleList = Dictionary2PublicServiceImpl.getListByType(TYPE, currentUser.getUserId());
		request.setAttribute("titleList", titleList);
		return "dictionary";
	}
}
