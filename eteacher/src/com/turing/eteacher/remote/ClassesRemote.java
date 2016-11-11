package com.turing.eteacher.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.service.IClassService;

@RestController
@RequestMapping("remote")
public class ClassesRemote extends BaseRemote{

		@Autowired
		private IClassService classServiceImp;
		
		/**
		 * 获取用户当前学期所有课程对应的班级列表
		 * @param request
		 * @return
		 */
		@RequestMapping(value="teacher/classes/getClassList",method=RequestMethod.GET)
		public ReturnBody getClassListByUser(HttpServletRequest request){
			try{
				String userId=getCurrentUser(request)==null?null:getCurrentUser(request).getUserId();
				String tpId=getCurrentTerm(request)==null?null:(String)getCurrentTerm(request).get("termId");
				List list=classServiceImp.getClassListByUser(userId,tpId);
				return new ReturnBody(ReturnBody.RESULT_SUCCESS,list);
			}
			catch(Exception e){
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
			}
		}
		
}
