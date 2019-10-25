package com.yyl.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yyl.modal.AdminUser;
import com.yyl.service.AdminService;
import com.yyl.service.UsersService;

@Controller
public class HomeController extends BaseController{

	@Autowired
	UsersService userService;
	@Autowired
	AdminService adminService;
	
	@RequestMapping("/index")
	public ModelAndView Index()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("name", "melp");
		
		List list = userService.LoadUsersList();
		mv.addObject("list",list);
		
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView Login(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		
		AdminUser user = getLogonUser(request);
		if(user != null)
		{
			mv.setViewName("redirect:/mainPage");
		}
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/doLogin")
	public String DoLogin(String userName,String password,HttpServletResponse response)
	{
		String retString = "0";
		AdminUser adminuser = adminService.UserLogonCheck(userName, password);
		if(adminuser != null)
		{
			retString = "1";
			Cookie c = new Cookie("token", createAuthToken(adminuser));
			response.addCookie(c);
		}
		
		return retString;
	}
	
	@RequestMapping("/mainPage")
	public ModelAndView MainPage(HttpServletRequest request)
	{
		ModelAndView mv = getAuthedMV("home", request);
		if(mv == null)
		{
			return lgoinPage();
		}
		
		mv.setViewName("mainpage");
		
		return mv;
	}
	
	@RequestMapping("/logoff")
	public ModelAndView LogoffAdmin(HttpServletResponse response,HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/login");
		
		removeCookieByName(response, request, "token");
		
		return mv;
	}
	
}
