package com.turing.eteacher.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.constants.EteacherConstants;
import com.turing.eteacher.model.User;

/**
 * 
 *系统拦截Controller的AOP类 
 * @author caojian
 * @version 1.0
 */
@Component
public class EteacherInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(EteacherInterceptor.class.getName());
    

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(request.getSession().getAttribute("context")==null){
			request.getSession().setAttribute("context", request.getContextPath());
		}
		boolean remote = false;
		if(request.getRequestURI().contains("/remote/")){
			remote = true;
		}
		HttpSession session = request.getSession();
		User currentUser = (User)session.getAttribute(EteacherConstants.CURRENT_USER);
		if(currentUser == null){
			if(remote){
				response.setContentType("text/html");
				PrintWriter out;
				try {
					out = response.getWriter();
					out.print(new ObjectMapper().writeValueAsString(new ReturnBody(ReturnBody.RESULT_FAILURE,ReturnBody.NO_LOGIN)));
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
				response.sendRedirect(request.getContextPath() + "/login");
			}
			return false;
		}
		return super.preHandle(request, response, handler);
	}
    
}
