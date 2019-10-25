package com.yyl.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yyl.modal.PagerInfo;
import com.yyl.service.ResourceService;
import com.yyl.service.UsersService;

@RequestMapping("/Users")
@Controller
public class UsersController extends BaseController{
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	UsersService usersService;

	@RequestMapping("/UserList")
	public ModelAndView UserList(HttpServletRequest request)
	{
		ModelAndView mv = getAuthedMV("user", request);
		if(mv == null) return lgoinPage();
		
		mv.setViewName("/Users/userlist");
		
		mv.addObject("AreaList", resourceService.LoadAresList());
		mv.addObject("JobList", resourceService.LoadJobList());
		mv.addObject("SalaryList", resourceService.LoadSalaryList());
		
		return mv;
	}
	
	@RequestMapping("/loaduserlist")
	public ModelAndView Loaduserlist(HttpServletRequest request,
			String phoneNumber,
			String nickName,
			String Gender,
			String AreaID,
			String JobID,
			String SalaryID,
			String method,
			int page)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
		try {
				nickName 	= URLDecoder.decode(nickName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		if(method != null && !method.equals(""))
		{
			if(method.equals("sub"))
			{
				page--;
				page = page<=0?1:page;
			}
			
			if(method.equals("add"))
			{
				page++;
			}
		}
		
		PagerInfo pi = usersService.SearchUsers2(phoneNumber, nickName, Gender, AreaID, JobID,SalaryID,page);
		mv.addObject("list", pi);
		mv.setViewName("/Users/_userlist");
		
		return mv;
	}
	
}
