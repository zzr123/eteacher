package com.turing.eteacher.remote;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.service.IScoreService;
import com.turing.eteacher.util.StringUtil;

@RestController
@RequestMapping("remote")
public class ScoreRemote extends BaseRemote {

		@Autowired
		private IScoreService scoreServiceImpl;

	/**
	 * 课程活动-单人单次成绩录入
	 * @author macong
	 * @param request
	 * @return
	 */
		@RequestMapping(value="score/addAverageScore", method=RequestMethod.POST)
		public ReturnBody getClassList(HttpServletRequest request){
			try{
				String score = request.getParameter("score");
				String studentId = request.getParameter("studentId");
				String courseId = request.getParameter("courseId");
				if(StringUtil.checkParams(score,studentId,courseId)){
					String result = scoreServiceImpl.addAverageScore(courseId, studentId, Integer.parseInt(score));
					return new ReturnBody(result);
				}
				return new ReturnBody(ReturnBody.RESULT_SUCCESS,null);
			}
			catch(Exception e){
				e.printStackTrace();
				return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
			}
		}
	
}
