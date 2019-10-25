package com.yyl.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yyl.modal.AdminUser;
import com.yyl.modal.PagerInfo;
import com.yyl.service.EventService;

@RequestMapping("/Event")
@Controller
public class EventController extends BaseController{

	@Autowired
	EventService eventService;
	
	@RequestMapping("/eventList")
	public ModelAndView EventList(HttpServletRequest request)
	{
		ModelAndView mv = getAuthedMV("event2", request);
		if(mv == null) return lgoinPage();
		
		mv.setViewName("/Event/eventlist");
		
		
		return mv;
	}
	
	@RequestMapping("/eventReportList")
	public ModelAndView EventReportList(HttpServletRequest request)
	{
		ModelAndView mv = getAuthedMV("event3", request);
		if(mv == null) return lgoinPage();
		
		mv.setViewName("/Event/eventReportList");
		
		
		return mv;
	}
	
	@RequestMapping("/loadeventlist")
	public ModelAndView LoadEventlist(HttpServletRequest request,
			String title,
			String address,
			String paytype,
			String date,
			String method,
			int page)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
		try {
			
			title 	= URLDecoder.decode(title,"UTF-8");
			address = URLDecoder.decode(address,"UTF-8");
			
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
		
		PagerInfo pi = eventService.EventSearchForWeb(page, title, date, address, Integer.parseInt(paytype));
		mv.addObject("list", pi);
		mv.setViewName("/Event/_eventlist");
		
		return mv;
	}
	
	@RequestMapping("/loadReportEventList")
	public ModelAndView LoadReportEventList(
			HttpServletRequest request,
			String method,
			int page)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
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
		
		PagerInfo pi = eventService.EventReportListWeb(page);
		mv.addObject("list", pi);
		mv.setViewName("/Event/_eventReportList");
		
		return mv;
	}
	
	@RequestMapping("/loadEventMemberList")
	public ModelAndView LoadEventMemberlist(HttpServletRequest request,int eid)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
		mv.addObject("list", eventService.EventMembers(eid));
		mv.setViewName("/Event/_eventMemberList");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/closeEvent")
	public String CloseEvent(int eid)
	{
		String ret = "1";
		
		try{
			eventService.CloseEvent(eid);
		}
		catch(Exception ex)
		{
			ret = "0";
		}
		
		return ret;
	}
	
	@ResponseBody
	@RequestMapping("/resumeEvent")
	public String ResumeEvent(int eid)
	{
		String ret = "1";
		
		try{
			eventService.ResumeEvent(eid);
		}
		catch(Exception ex)
		{
			ret = "0";
		}
		
		return ret;
	}
	
	/**
	 * 置顶活动
	 * @param eid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/topEvent")
	public String TopEvent(int eid,int type)
	{
		String ret = "1";
		
		try{
			eventService.TopEvent(eid, type);
		}
		catch(Exception ex)
		{
			ret = "0";
		}
		
		return ret;
	}
	
	@RequestMapping("/EventCommentList")
	public ModelAndView EventCommentList(HttpServletRequest request,int eid,int fromPage)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
		mv.addObject("fromPage",fromPage);
		mv.addObject("eid", eid);
		mv.setViewName("/Event/eventCommentList");
		
		return mv;
	}
	
	@RequestMapping("/loadEventCommentList")
	public ModelAndView LoadEventCommentList(
			HttpServletRequest request,
			int eid,
			String method,
			int page)
	{
		ModelAndView mv = getAuthedMV("", request);
		if(mv == null) return null;
		
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
		
		PagerInfo pi = eventService.EventTopicListWeb(eid, page);
		mv.addObject("list", pi);
		mv.setViewName("/Event/_eventCommentList");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/deleteComment")
	public int DeleteComment(HttpServletRequest request,int etid) 
	{
		AdminUser u = getLogonUser(request);
		if(u == null) return -2;
		
		eventService.DeleteComment(etid);
		return 0;
	}
	
	@RequestMapping("/createEvent")
	public ModelAndView createEvent(HttpServletRequest request)
	{
		ModelAndView mv = getAuthedMV("event1", request);
		if(mv == null) return null;
		
		mv.setViewName("/Event/createEvent");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/doCreateEvent")
	public int DoCreateEvent(
			HttpServletRequest request,
			String title,
			String add,
			String lon,
			String la,
			String data,
			String hour,
			String min,
			String gender,
			String userLevel,
			String payType,
			String onTop,
			String des)
	{
		ModelAndView mv = getAuthedMV("event1", request);
		if(mv == null) return -1;
		
		int ret = 0;
		
		try 
		{
			int g = 0;
			if(gender!=null && !gender.isEmpty())
				g = Integer.parseInt(gender);
			
			int ul = 0;
			if(userLevel!=null && !userLevel.isEmpty())
				ul = Integer.parseInt(userLevel);
			
			int pt = 0;
			if(payType!=null && !payType.isEmpty())
				pt = Integer.parseInt(payType);
			
			String dateTime = data + " " + hour+":"+min+":00";
			eventService.CreateEvent(title, 1, g, ul, dateTime, pt, add, la, lon, 0, des,Integer.parseInt(onTop),100,0);
		} 
		catch (Exception e) {
			// TODO: handle exception
			
			ret = 1;
		}
		
		
		return ret;
	}
}
