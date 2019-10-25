package com.yyl.apicontroller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyl.common.YYLPropertiesUtils;
import com.yyl.modal.ApiResult;
import com.yyl.service.ResourceService;

@RequestMapping("/Api/Resource")
@Controller
public class ResourceApiController extends BaseApiController{

	@Autowired
	ResourceService resService;
	
	/**
	 * 获取地区列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/AreaList")
	public ApiResult LoadAresList()
	{
		ApiResult ar = new ApiResult();
		try
		{
			ar.Data = resService.LoadAresList();
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
	
	/**
	 * 获取月薪列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/SalaryList")
	public ApiResult LoadSalaryList()
	{
		ApiResult ar = new ApiResult();
		try
		{
			ar.Data = resService.LoadSalaryList();
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
	
	/**
	 * 获取职业列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/JobList")
	public ApiResult LoadJobList()
	{
		ApiResult ar = new ApiResult();
		try
		{
			ar.Data = resService.LoadJobList();
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping("/Configure")
	public ApiResult Configure()
	{
		ApiResult ar = new ApiResult();
		try
		{
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("ServeVersion", YYLPropertiesUtils.getProperties().getProperty("Serve_Version"));
			data.put("Heartbeat", YYLPropertiesUtils.getProperties().getProperty("Heartbeat"));
			data.put("UserPageCount", YYLPropertiesUtils.getProperties().getProperty("PageCount"));
			
			data.put("UpdateURLIOS", YYLPropertiesUtils.getProperties().getProperty("Update_URL_IOS"));
			data.put("UpdateURLAndroid", YYLPropertiesUtils.getProperties().getProperty("Update_URL_Android"));
			
			
			ar.Data = data;
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
}
