package com.yyl.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SqlInjectIntercepter implements HandlerInterceptor {

	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}


	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception 
	{
		String methodString = request.getMethod();
		if(methodString.equals("POST")) return true;
		
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) 
		{
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			
			
			for (int i = 0; i < values.length; i++) 
			{
				if(WebUtil.isSqlIn(values[i]))
				{
			//		response.getWriter().println("<span style=\"color:red\">SQL injection detected!</span>");
					return false;
				}
				
				if(WebUtil.isXSS(values[i]))
				{
			//		response.getWriter().println("<span style=\"color:red\">XSS detected!</span>");
					return false;
				}
				
				request.setAttribute("qid", "1");
			}
		}
		
		return true;
	}

}
