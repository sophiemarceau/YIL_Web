package com.yyl.apicontroller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easemob.server.example.httpclient.apidemo.EasemobChatGroups;
import com.easemob.server.example.jersey.apidemo.EasemobIMUsers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;
import com.yyl.service.ContactService;
import com.yyl.service.UsersService;

@RequestMapping("/Api/Contact")
@Controller
public class ContactApiController extends BaseApiController{

	@Autowired
	ContactService contactService;
	
	@Autowired
	UsersService userService;
	
	/**
	 * 查询是否是本系统的用户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/SyncContactUsers")
	public ApiResult SyncContactUsers(HttpServletRequest request,String phoneNumbers)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();

		try {
			List userList = contactService.SyncContactUsers(phoneNumbers);
			for(int i=0;i<userList.size();i++)
			{
				Map userInfo = (Map)userList.get(i);
				int buddyId = Integer.parseInt(userInfo.get("UID").toString());
				int isPending = userService.IsPendingBuddy(ti.uid, buddyId);
				userInfo.put("BuddyStatus", isPending);
			}
			
			ar.Data = userList;
			ar.Result = true;
		} catch (Exception ex) {

		}

		return ar;
	}
	
	/**
	 * 获取好友列表
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/BuddyList")
	public ApiResult BuddyList(HttpServletRequest request)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();

		try {
			ar.Data = contactService.getBuddyList(ti.uid);
			ar.Result = true;
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 加好友
	 * @param request
	 * @param buddyId
	 * @param buddyHxId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AddBuddy",method=RequestMethod.POST)
	public ApiResult AddBuddy(HttpServletRequest request,int buddyId,String buddyHxId)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();
		
		try 
		{
		//	int ret = contactService.addBuddy(buddyId,buddyHxId, 1,"x");
			int ret = contactService.addBuddy(buddyId,buddyHxId, ti.uid,ti.hxUserName);
			if(ret == 1) //需等得验证
			{
				ar.Data = ret;
				ar.Result = true;
				ar.Infomation = "已发送请求，等待对方验证";
			}
			else if(ret == 0) //加为好友
			{
				ar.Data = ret;
				ar.Result = true;
				ar.Infomation = "加好友成功";
			}
			else
			{
				ar.Infomation = "加好友失败";
			}
		} 
		catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 好友请求通过
	 * @param request
	 * @param BR_ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/AllowAddBuddy",method=RequestMethod.POST)
	public ApiResult AllowAddBuddy(HttpServletRequest request,int BR_ID)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();
		
		try 
		{
			int ret = contactService.BuddyRequestAllow(BR_ID);
			if(ret == 0)
			{
				ar.Result = true;
				ar.Infomation = "加好友成功";
			}
			else
			{
				ar.Infomation = "加好友失败";
			}
		} 
		catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 决绝加为好友
	 * @param request
	 * @param BR_ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/RejectAddBuddy",method=RequestMethod.POST)
	public ApiResult RejectAddBuddy(HttpServletRequest request,int BR_ID)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();
		
		try 
		{
			int ret = contactService.BuddyRequestReject(BR_ID);
			if(ret == 0)
			{
				ar.Result = true;
				ar.Infomation = "拒绝加为好友";
			}
			else
			{
				ar.Infomation = "拒绝加好友失败";
			}
		} 
		catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 好友请求列表
	 * @param request
	 * @param BR_ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/BuddyRequestList",method=RequestMethod.GET)
	public ApiResult BuddyRequestList(HttpServletRequest request,int page)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();
		
		try 
		{
			ar.Data = contactService.BuddyRequestList(ti.uid, page);
			ar.Result = true;
		} 
		catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 好友请求的数量
	 * @param request
	 * @param BR_ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/BuddyRequestListCount",method=RequestMethod.GET)
	public ApiResult BuddyRequestListCount(HttpServletRequest request)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();
		
		try 
		{
			ar.Data = contactService.BuddyRequestListCount(ti.uid);
			ar.Result = true;
		} 
		catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 移除好友
	 * @param request
	 * @param buddyId
	 * @param buddyHxId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/RemoveBuddy",method=RequestMethod.POST)
	public ApiResult RemoveBuddy(HttpServletRequest request,int buddyId,String buddyHxId)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();

		try {
			int ret = contactService.removeBuddy(buddyId, buddyHxId, ti.uid,ti.hxUserName);
			if (ret == 0) {
				ar.Result = true;
				ar.Infomation = "移除好友成功";
			} else {
				ar.Infomation = "移除好友失败";
			}
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/UserListFromChatGroup",method=RequestMethod.GET)
	public ApiResult UserListFromChatGroup(HttpServletRequest request,String chatGroup)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();

		try {
			ObjectNode list = EasemobChatGroups.getAllMemberssByGroupId(chatGroup);
			//data":[{"member":"rdtqwoqo7774"},{"owner":"quevppdw9632"},{"member":"mgjtfhyfutzd361"}]
			JsonNode node = list.get("data");
			String hxUsers = "";
			for(JsonNode item:node)
			{
				String member = "";
				if(item.get("member") != null)
					member = item.get("member").asText();
				else
					member = item.get("owner").asText();
				
				hxUsers += "'"+member + "',";
			}
			
			if(hxUsers.endsWith(","))
				hxUsers = hxUsers.substring(0,hxUsers.length()-1);
			
			ar.Data = contactService.getUserListByHxUsers(hxUsers);
			ar.Result = true;
			
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
}
