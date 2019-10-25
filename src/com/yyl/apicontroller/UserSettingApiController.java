package com.yyl.apicontroller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;
import com.yyl.service.UserSettingService;

@RequestMapping("/Api/UserSetting")
@Controller
public class UserSettingApiController extends BaseApiController{

	@Autowired
	UserSettingService usService;
	
	/**
	 * 设定好友申请验证 
	 * @param request
	 * @param verify 0=不需要验证 1=需要验证
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/BuddyVerify",method=RequestMethod.POST)
	public ApiResult BuddyVerify(HttpServletRequest request,int verify) 
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
			usService.AddBuddyVerify(ti.uid, verify);
			ar.Result = true;
			ar.Infomation = "设定成功";
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 用户每日签到
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/SignInDay",method=RequestMethod.POST)
	public ApiResult SignInDay(HttpServletRequest request) 
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
			int ret = usService.SignInDay(ti.uid);
			ar.Result = true;
			ar.Infomation = (ret==1?"签到成功":"今日已签到");
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/MyCode",method=RequestMethod.GET)
	public ApiResult MyCode(HttpServletRequest request) 
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
			Map info = usService.MyCode(ti.uid);
			String str =
					info.get("NickName").toString() + "|" +
					info.get("Gender").toString() + "|" +
					ti.uid + "|" +
					info.get("UserPic").toString() + "|" +
					info.get("hxUser").toString();
			
			ar.Data = str;
			ar.Result = true;
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 获取我的友币数量
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/GetMyCoins",method=RequestMethod.GET)
	public ApiResult GetMyCoins(HttpServletRequest request) 
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
			int coins = usService.GetUserCoins(ti.uid);
			
			ar.Data = coins;
			ar.Result = true;
		} catch (Exception ex) {
			ar.Infomation = ex.toString();
		}

		return ar;
	}
}
