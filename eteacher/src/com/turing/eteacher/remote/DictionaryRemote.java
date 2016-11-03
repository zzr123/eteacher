package com.turing.eteacher.remote;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Teacher;
import com.turing.eteacher.model.User;
import com.turing.eteacher.service.IDictionary2PrivateService;
import com.turing.eteacher.service.impl.Dictionary2PrivateServiceImpl;

@RestController
@RequestMapping("remote")
public class DictionaryRemote extends BaseRemote{
	
	@Autowired
	private IDictionary2PrivateService dictionary2PrivateServiceImpl;
	
	/**
	 * 1.2.1 获取特定类型的子项列表
	 * @author lifei
	 * @param request
	 * @param type
	 * @return
	 */
	/*@RequestMapping(value = "dictionary/{type}/getList", method = RequestMethod.POST)
	public ReturnBody getDicList(HttpServletRequest request,@PathVariable String type) {
		System.out.println("----------------------------");
	     Enumeration paramNames = request.getParameterNames();  
	        while (paramNames.hasMoreElements()) {  
	            String paramName = (String) paramNames.nextElement();  
	            String[] paramValues = request.getParameterValues(paramName);  
	            if (paramValues.length == 1) {  
	                String paramValue = paramValues[0];  
	                if (paramValue.length() != 0) {  
	                    System.out.println("参数："+paramName+"    值："+paramValue );
	                }  
	            }  
	        }
		User user = getCurrentUser(request);
		if (null != user) {
			System.out.println("user:"+user.toString());
			List<Map> list = dictionary2PrivateServiceImpl.getListByType(Integer.parseInt(type), user.getUserId());
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}else{
			return ReturnBody.getNoLoginError();
		}
	}*/
	/**
	 * 1.2.1 获取特定类型的子项列表
	 * @author macong
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "dictionary/getList", method = RequestMethod.POST)
	public ReturnBody getDicList(HttpServletRequest request) {
		try {
			int type = Integer.parseInt(request.getParameter("type"));
			String userId = request.getParameter("userId");
			List<Map> titleList = dictionary2PrivateServiceImpl.getListByType(type, userId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, titleList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}	
	}
	/**
	 * 用户添加自定义字典表的列表项
	 * @author macong
	 * @param request
	 * @param type
	 * @param content
	 */
	@RequestMapping(value = "dictionary/addItem", method = RequestMethod.POST)
	public ReturnBody addDicItem(HttpServletRequest request) {
		try {
			int type = Integer.parseInt(request.getParameter("type"));
			String userId = request.getParameter("userId");
			String content = request.getParameter("content");
			boolean result = dictionary2PrivateServiceImpl.addItem(type,content,userId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}	
	}
	/**
	 * 用户删除自定义字典表的列表项
	 * @author macong
	 * @param request
	 * @param type
	 * @param itemId
	 */
	@RequestMapping(value = "dictionary/delItem", method = RequestMethod.POST)
	public ReturnBody delDicItem(HttpServletRequest request) {
		try {
			int type = Integer.parseInt(request.getParameter("type"));
			String userId = request.getParameter("userId");
			String dId = request.getParameter("itemId");
			boolean result = dictionary2PrivateServiceImpl.deleteItem(type, userId, dId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,
					ReturnBody.ERROR_MSG);
		}	
	}
}
