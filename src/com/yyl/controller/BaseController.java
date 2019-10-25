package com.yyl.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.yyl.common.EncryptionHelper;
import com.yyl.common.UtiltyHelper;
import com.yyl.modal.AdminUser;

@Controller
public class BaseController {

	/**
	 * 获取Cookie的值
	 * @param request
	 * @param name
	 * @return
	 */
	protected String getCookieByName(HttpServletRequest request, String name)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return "";
		
		for(int i=0;i<cookies.length;i++)
		{  
			if(cookies[i].getName().equals(name))
			{
				return cookies[i].getValue();
			}
		} 
		
		return "";
	}
	
	/**
	 * 移除Cookie
	 * @param response
	 * @param request
	 * @param name
	 */
	protected void removeCookieByName(HttpServletResponse response,HttpServletRequest request,String name)
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return;
		
		for(int i=0;i<cookies.length;i++)
		{  
			if(cookies[i].getName().equals(name))
			{
				cookies[i].setMaxAge(0);  
				response.addCookie(cookies[i]);  
				return;
			}
		} 
	}
	
	protected String createAuthToken(AdminUser adminuser) 
	{
		String token = adminuser.UID + "," + adminuser.UserName + "," + adminuser.nickName + "," + adminuser.UserLevel + "," + UtiltyHelper.GetNowDate();
		token = EncryptionHelper.getBase64(token);
		
		return token;
	}
	
	protected AdminUser getLogonUserFromToken(String token) 
	{
		AdminUser user = null;
		
		try
		{
			String userString = EncryptionHelper.getFromBase64(token);
			user = new AdminUser();
			String[] data = userString.split(",");
			user.UID 		= Integer.parseInt(data[0]);
			user.UserName 	= data[1];
			user.nickName 	= data[2];
			user.UserLevel 	= Integer.parseInt(data[3]);
		}
		catch(Exception ex)
		{
			user = null;
		}
		
		return user;
	}
	
	protected AdminUser getLogonUser(HttpServletRequest request)
	{
		String tokenString = getCookieByName(request, "token");
		if(tokenString.equals(""))
		{
			return null;
		}
		
		AdminUser user = getLogonUserFromToken(tokenString);
		return user;
	}

	protected ModelAndView getAuthedMV(String menu,HttpServletRequest request) 
	{
		ModelAndView mv = new ModelAndView();
		
		AdminUser u = getLogonUser(request);
		if(u == null) return null;
		
		mv.addObject("User", u);
		mv.addObject("menu", menu);
		
		return mv;
	}
	
	protected ModelAndView lgoinPage()
	{
		return new ModelAndView("redirect:/login");
	}
	
}
