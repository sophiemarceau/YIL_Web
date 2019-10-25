package com.yyl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yyl.common.WebUtil;
import com.yyl.modal.AdminUser;
import com.yyl.modal.ApiResult;
import com.yyl.service.AdminService;

@Controller
@RequestMapping("/Admin")
public class AdminUserController extends BaseController{
	
	@Autowired
	AdminService adminService;
	
	/**
	 * 新增管理员
	 * @return
	 */
	@RequestMapping("/addAdminUser")
	public ModelAndView addAdminUser(HttpServletRequest request) 
	{
		ModelAndView mv = getAuthedMV("admin1", request);
		if(mv == null) return lgoinPage();
		
		mv.setViewName("/AdminUser/adminuserform");
		
		return mv;
	}
	
	/**
	 * 修改管理员密码
	 * @return
	 */
	@RequestMapping("/ChangeUserPass")
	public ModelAndView ChangeUserPass(HttpServletRequest request) 
	{
		ModelAndView mv = getAuthedMV("admin1", request);
		if(mv == null) return lgoinPage();
		
		mv.setViewName("/AdminUser/adminpass");
		
		return mv;
	}
	
	/**
	 * 修改管理员密码操作
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doChangeUserPass")
	public int DoChangeUserPass(HttpServletRequest request,String oldPass,String newPass) 
	{
		AdminUser u = getLogonUser(request);
		if(u == null) return -1;
		
		int ret = adminService.ChangeUserPass(u.UID, oldPass, newPass);
		
		return ret;
	}
	
	@RequestMapping("/adminUserList")
	public ModelAndView adminUserList(HttpServletRequest request) 
	{
		ModelAndView mv = getAuthedMV("admin2", request);
		if(mv == null) return lgoinPage();
				
		mv.setViewName("/AdminUser/adminuserlist");
		
		List list = adminService.LoadAdminList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/doAddUser", method=RequestMethod.POST)
	public ApiResult DoAddUser(HttpServletRequest request,String username,String nickname,String pass)
	{
		ApiResult ar = new ApiResult();
		
		try
		{
			username 	= WebUtil.SafeRequestString(username);
			nickname 	= WebUtil.SafeRequestString(nickname);
			pass 		= WebUtil.SafeRequestString(pass);
			
			ar.Infomation = adminService.CreateAdminUser(username, nickname, pass, 1);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = "创建账户失败";
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/doDeleteAdminUser", method=RequestMethod.POST)
	public ApiResult DoDeleteAdminUser(int uid)
	{
		ApiResult ar = new ApiResult();
		
		try
		{	
			adminService.DeleteAdminUser(uid);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = "删除账户失败";
		}
		
		return ar;
	}
}
