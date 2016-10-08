package com.turing.eteacher.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.turing.eteacher.base.BaseController;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.service.IMessageService;

@RestController
@RequestMapping("remote")
public class MessageRemote extends BaseController {
	@Autowired
	private IMessageService messageServiceImp;
	
	@RequestMapping(value="teacher/message/getMessageList",method=RequestMethod.GET)
	public ReturnBody getMessageList(HttpServletRequest request){
		try{
			String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
			List list = messageServiceImp.getMessageList("Qsq73xbQDS");
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.ERROR_MSG);
		}
	}
}
