package com.turing.eteacher.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.turing.eteacher.base.BaseRemote;import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.Student;
import com.turing.eteacher.model.Term;
import com.turing.eteacher.model.TermPrivate;
import com.turing.eteacher.service.IClassService;
import com.turing.eteacher.service.ITermPrivateService;
import com.turing.eteacher.service.ITermService;

@RestController
@RequestMapping("remote")
public class TermRemote extends BaseRemote {

	@Autowired
	private ITermService termServiceImpl;
	
	@Autowired
	private IClassService classServiceImpl;
	
	/**
	 * 获取用户学期列表
	 * @param request
	 * @return
	 */
	/*{
	 	result : 'success',//成功success，失败failure
		data : [
			{
				year : '年',//2015-2016
				term : '学期',//1或2
				termName : '学期名称'
			}
		],
		msg : '提示信息XXX'
	  }*/	
	@RequestMapping(value = "student/terms", method = RequestMethod.GET)
	public ReturnBody studentWorks(HttpServletRequest request){
		try{
			Student student = this.getCurrentStudent(request);
//			String grade = classServiceImpl.get(student.getClassId()).getGrade();
			String grade = classServiceImpl.get("1").getGrade();
			List list = termServiceImpl.getListByGrade(Integer.parseInt
(grade));
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, 
ReturnBody.ERROR_MSG);
		}
	}
	
	//教师端
	/**
	 * 获取学期列表信息（学期Id，学期名，学期开始时间，学期结束时间）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="teacher/term/getTermList", method=RequestMethod.GET)
	public ReturnBody getListTerms(HttpServletRequest request){
		try{
			String userId=getCurrentUser(request)==null?null:getCurrentUser
(request).getUserId();
			List<Map> list = termServiceImpl.getListTermPrivates(userId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
 		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, 
ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 删除指定的学期
	 * @param request
	 * @param termId
	 * @return
	 */
	@RequestMapping(value="teacher/term/delTerm/{tpId}", method=RequestMethod.GET)
	public ReturnBody delTermById(HttpServletRequest request, @PathVariable String 
tpId){
		try{
			termServiceImpl.deleteById(tpId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, new HashMap());
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, 
ReturnBody.ERROR_MSG);
		}
	}
	/**
	 * 添加新学期信息
	 * @param request
	 * @param term
	 * @return
	 */
	@RequestMapping(value="teacher/term/addTerm", method=RequestMethod.POST)
	public ReturnBody addTerm(HttpServletRequest request, TermPrivate tp){
		    /*tp.setStartDate("2016-9-1");
		    tp.setEndDate("2017-2-1");
		    tp.setWeekCount(13);*/
		try{
			String userId=getCurrentUser(request)==null?null:getCurrentUser
(request).getUserId();
			String[] termPrivate=request.getParameterValues("termPrivate");
			tp.setUserId(userId);
			String tpId=(String) termServiceImpl.save(tp);
			for(int i=0;i<termPrivate.length;i++){
				termServiceImpl.addTermPrivate(tpId, termPrivate[i]);
			}
			return new ReturnBody(ReturnBody.RESULT_SUCCESS,new HashMap());
			
		}
		catch(Exception e){
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, 
ReturnBody.ERROR_MSG);
		}
	}

	


}
