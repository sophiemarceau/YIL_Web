package com.yyl.apicontroller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyl.common.UtiltyHelper;
import com.yyl.common.WebUtil;
import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;
import com.yyl.service.EventService;
import com.yyl.service.UsersService;
import com.yyl.thirdpart.EasemobHelper;

@RequestMapping("/Api/Events")
@Controller
public class EventsApiController extends BaseApiController{
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UsersService usersService;

	/**
	 * 创建活动
	 * @param title
	 * @param uid
	 * @param gender
	 * @param userLevel
	 * @param dateTime
	 * @param payType
	 * @param address
	 * @param latitude
	 * @param longitude
	 * @param coins
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/CreateEvent",method=RequestMethod.POST)
	public ApiResult CreateEvent(HttpServletRequest request,
			String title,
			int gender,
			int userLevel,
			String dateTime,
			int payType,
			String address,
			String latitude,
			String longitude,
			int coins,
			String des,
			int memberLimit,
			int needAccept)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try
		{
			title 		= WebUtil.SafeRequestString(title);
			dateTime 	= WebUtil.SafeRequestString(dateTime);
			address 	= WebUtil.SafeRequestString(address);
			latitude 	= WebUtil.SafeRequestString(latitude);
			longitude 	= WebUtil.SafeRequestString(longitude);
			des 		= WebUtil.SafeRequestString(des);
			
			memberLimit 	= WebUtil.SafeRequestString(memberLimit);
			needAccept 		= WebUtil.SafeRequestString(needAccept);
			
			payType = 0;
			int ret = eventService.CreateEvent(title, ti.uid, gender, userLevel, dateTime, payType, address, latitude, longitude, coins,des,0,memberLimit,needAccept);
			if(ret == -1)
			{
				ar.Infomation = "友币数量不足,无法创建活动";
			}
			else 
			{
				ar.Result = true;
				ar.Infomation = "创建活动成功";
			}
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	/**
	 * 参加活动
	 * @param request
	 * @param eid
	 * @param act 0=加入 1=退出
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/JoinEvent",method=RequestMethod.POST)
	public ApiResult JoinEvent(HttpServletRequest request,int eid,int act)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try
		{
			if(act == 0)
			{
				int ret = eventService.JoinEvent(ti.uid,eid);
				switch (ret) {
				case 0:
					ar.Result = true;
					ar.Infomation = "报名成功，请准时参加";
					break;
				case 1:
					ar.Result = true;
					ar.Infomation = "已提交报名，请等待发起人通过";
					break;
	
				case -1:
					ar.Infomation = "活动不存在或已过期 无法参加";
					break;
					
				case -2:
					ar.Infomation = "性别不符合 无法参加";
					break;
					
				case -3:
					ar.Infomation = "当前的用户等级不符 无法参加";
					break;
					
				case -4:
					ar.Infomation = "您已报名了这个活动";
					break;
					
				case -5:
					ar.Infomation = "该活动已满员，无法再报名";
					break;
				}
			}
			else if(act == 1)
			{
				eventService.ExitEvent(ti.uid, eid);
				ar.Result = true;
				ar.Infomation = "退出活动";
			}
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/AcceptJoinEvent",method=RequestMethod.POST)
	public ApiResult AcceptJoinEvent(HttpServletRequest request,int eid,int uid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try
		{
			eventService.AccecpJoinEvent(eid, uid);
			
			Map target = (Map)usersService.GetUserInfoByUID(uid);
			Map sender = (Map)usersService.GetUserInfoByUID(ti.uid);
			String targetId = target.get("hxUser").toString();
			String senderId = sender.get("NickName").toString();
			
			Map eventInfo = eventService.GetEventInfo(eid, uid);
			String title = eventInfo.get("Title").toString();
			EasemobHelper.SendMessage("quevppdw9632", targetId, "【系统消息】：" + senderId + "已通过了您参加活动["+title+"]的请求。");
			
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
	
		return ar;
	}
	
	/**
	 * 查看活动
	 * @param request
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ViewEvent",method=RequestMethod.GET)
	public ApiResult ViewEvent(HttpServletRequest request,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try 
		{
			eventService.ViewEvent(eid);
			ar.Result = true;
		} 
		catch (Exception e) 
		{
			ar.Infomation = e.toString();
		}
		
		return ar;
		
	}
	
	/**
	 * 获取一个活动具体内容
	 * @param request
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/EventInfo",method=RequestMethod.GET)
	public ApiResult EventInfo(HttpServletRequest request,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try 
		{
			Map eventInfo = eventService.GetEventInfo(eid,ti.uid);
			int isPending = usersService.IsPendingBuddy(ti.uid, Integer.parseInt(eventInfo.get("UID").toString()));
			eventInfo.put("BuddyStatus", isPending);
			
			ar.Data = eventInfo;
			ar.Result = true;
		} 
		catch (Exception e) 
		{
			ar.Infomation = e.toString();
		}
		
		return ar;
		
	}
	
	/**
	 * 举报活动
	 * @param request
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ReportEvent",method=RequestMethod.POST)
	public ApiResult ReportEvent(HttpServletRequest request,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try
		{
			eventService.ReportEvent(eid,ti.uid);
			ar.Result = true;
			ar.Infomation = "举报活动成功";
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	/**
	 * 签到活动
	 * @param request
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/SignInEvent",method=RequestMethod.POST)
	public ApiResult SignInEvent(HttpServletRequest request,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
				
		ApiResult ar = new ApiResult();
		
		try
		{
			eventService.SignInEvent(ti.uid, eid);
			ar.Result = true;
			ar.Infomation = "签到成功";
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	/**
	 * 获取活动成员列表
	 * @param request
	 * @param page
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/EventMemberList")
	public ApiResult EventMemberList(HttpServletRequest request,int page,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			List list = eventService.EventMembers(eid, page);
			for(int i=0;i<list.size();i++)
			{
				Map member = (Map)list.get(i);
				int uid = Integer.parseInt(member.get("UID").toString());
				Map userInfo = (Map)usersService.GetUserInfoByUID(uid);
				member.put("myEventCnt",userInfo.get("myEventCnt"));
				member.put("myJoinCnt",userInfo.get("myJoinCnt"));
			}
			
			ar.Data = list;
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	/**
	 * 
	 * @param request
	 * @param uid
	 * @param type 1=发起的活动  2=参与的活动
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/EventListByUID")
	public ApiResult EventList2(HttpServletRequest request,int uid,int type,int page)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}

		ApiResult ar = new ApiResult();

		try {
			switch (type) {
			case 1:
				ar.Data = eventService.MyEventList(page, uid);
				ar.Result = true;
				break;

			case 2:
				ar.Data = eventService.MyJoinEventList(page, uid);
				ar.Result = true;
				break;
			}
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 活动列表
	 * @param request
	 * @param type 类型 1=热门 2=附近 3=好友 4=我的活动 5=我参加的活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/EventList")
	public ApiResult EventList(HttpServletRequest request,int type,int page,
			String latitude,
			String longitude)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			switch (type) {
			case 1:
				ar.Data = eventService.EventListTop(page);
				ar.Result = true;
				break;

			case 2:
				ar.Data = eventService.EventListNearBy(page, latitude, longitude);
				ar.Result = true;
				break;
				
			case 3:
				//String firends = EasemobHelper.LoadMyFirends(ti.hxUserName);
				ar.Data = eventService.EventListFirend(page,ti.uid);
				ar.Result = true;
				break;
				
			case 4:
				ar.Data = eventService.MyEventList(page,ti.uid);
				ar.Result = true;
				break;
				
			case 5:
				ar.Data = eventService.MyJoinEventList(page,ti.uid);
				ar.Result = true;
				break;
			}
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	/**
	 * 当前活动排行榜
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/EventTopList")
	public ApiResult EventTopList(HttpServletRequest request)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			ar.Data = eventService.EventTopList();
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
		
	/**
	 * 活动搜索
	 * @param request
	 * @param page
	 * @param title
	 * @param Date
	 * @param address
	 * @param payType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/EventSearch")
	public ApiResult EventSearch(HttpServletRequest request,int page,
			String title,String Date,String address,String payType,String userLevel,int gender)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			title 	= UtiltyHelper.GetStringFromUrl(title);
			address = UtiltyHelper.GetStringFromUrl(address);
			
			int pt = 0;
			if(payType!=null && !payType.isEmpty())
				pt = Integer.parseInt(payType);
			
			int iUserLevel = 0;
			if(userLevel!=null && !userLevel.isEmpty())
				iUserLevel = Integer.parseInt(userLevel);
			
			ar.Data = eventService.EventSearch(page, title, Date, address, pt,iUserLevel,gender);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/AddEventTopic",method=RequestMethod.POST)
	public ApiResult AddEventTopic(HttpServletRequest request,String content,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			eventService.AddEventTopic(content, ti.uid, eid);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping("/EventTopicList")
	public ApiResult EventTopicList(HttpServletRequest request,int page,int eid)
	{
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		ApiResult ar = new ApiResult();
		
		try
		{
			ar.Data = eventService.EventTopicList(eid, page);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
}
