package com.turing.eteacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.service.IFileService;

@Controller
@RequestMapping("file")
public class FileController extends BaseController {
	
	@Autowired
	private IFileService fileServiceImpl;

	@RequestMapping("getFileDatas")
	@ResponseBody
	public Object getFileDatas(HttpServletRequest request){
		String dataId = request.getParameter("dataId");
		List<CustomFile> list = fileServiceImpl.getListByDataId(dataId);
		return list;
	}
	
	@RequestMapping("deleteFile")
	@ResponseBody
	public String deleteFile(HttpServletRequest request){
		String fileId = request.getParameter("fileId");
		fileServiceImpl.deleteById(fileId);
		return "success";
	}
}
