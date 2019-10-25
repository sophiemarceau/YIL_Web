package com.yyl.apicontroller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.easemob.server.example.comm.Constants;
import com.yyl.common.UtiltyHelper;
import com.yyl.common.WebUtil;
import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;
import com.yyl.service.UsersService;
import com.yyl.thirdpart.EasemobHelper;

@Controller
@RequestMapping("/Api/Account")
public class AccountApiController extends BaseApiController{

	@Autowired
	UsersService userService;
	
	@ResponseBody
	@RequestMapping(value="/createVerifyCode",method=RequestMethod.POST)
	public ApiResult createVerifyCode(String phoneNumber)
	{
		ApiResult ar = new ApiResult();
		ar.Result = false;
		
		try
		{
			String pn = WebUtil.SafeRequestString(phoneNumber);
			String number = UtiltyHelper.CreateRandomNumber();
			userService.CreateVerifyNumber(phoneNumber, number);
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping("/verifyCode")
	public ApiResult VerifyCode(String phoneNumber,String code)
	{
		ApiResult ar = new ApiResult();
		ar.Result = false;
		
		try
		{
			String pn 		= WebUtil.SafeRequestString(phoneNumber);
			String code2 	= WebUtil.SafeRequestString(code);
			
			ar.Result = userService.VerifyCode(pn, code2);
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/forgetPassword", method=RequestMethod.POST)
	public ApiResult ForgetPassword(String phoneNumber)
	{
		ApiResult ar = new ApiResult();
		try 
		{
			userService.ForgetPassword(phoneNumber);
			ar.Result = true;
		} 
		catch (Exception e) 
		{
			ar.Infomation = e.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/regist2", method=RequestMethod.POST)
	public ApiResult RegistUser(String phoneNumber,String code,String password)
	{
		ApiResult ar = new ApiResult();
		ar.Result = false;
		
		try
		{
			phoneNumber = WebUtil.SafeRequestString(phoneNumber);
			code 		= WebUtil.SafeRequestString(code);
			password 	= WebUtil.SafeRequestString(password);
			
			if(phoneNumber == null || phoneNumber.isEmpty()) return ar;
			/*
			Boolean verifyCode = userService.VerifyCode(phoneNumber, code);
			if(!verifyCode)
			{
				ar.Infomation = "无效的验证码";
				return ar;
			}
			*/
			
			String hxUser = EasemobHelper.CreateEaseMobUser();
			if(hxUser != null)
			{
				boolean ret = userService.RegistUser2(phoneNumber, password, hxUser, Constants.DEFAULT_PASSWORD);
				if(ret)
				{
					Map userInfo = userService.UserLogon(phoneNumber, password);
					if(userInfo != null)
					{
						String token = CreateAuthToken(userInfo);
						HashMap<String, Object> data = new HashMap<String, Object>();
						data.put("UserInfo", userInfo);
						data.put("token", token);
						ar.Data = data;
					}
				}
				
				ar.Result = ret;
			}
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public ApiResult UpdateUserInfo(HttpServletRequest request,
			String userPass,
			String nickName,
			String gender,
			String brithday,
			String signText,
			String salaryId,
			String areaId,
			String jobId,
			String userPic,
			String email,
			String hobbies)
	{
		ApiResult ar = new ApiResult();
		ar.Result = false;
	
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		
		try
		{
			userPass 	= WebUtil.SafeRequestString(userPass);
			nickName 	= WebUtil.SafeRequestString(nickName);
			gender 		= WebUtil.SafeRequestString(gender);
			brithday 	= WebUtil.SafeRequestString(brithday);
			signText 	= WebUtil.SafeRequestString(signText);
			
			email 		= WebUtil.SafeRequestString(email);
			hobbies 	= WebUtil.SafeRequestString(hobbies);
			
			userService.UpdateUserInfo(ti.uid, 
					userPass,
					 nickName,
					 gender,
					 brithday,
					 signText,
					 salaryId,
					 areaId,
					 jobId,
					 userPic,email,hobbies);
			
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public ApiResult RegistUser(
			String phoneNumber,
			String userPass,
			String nickName,
			String gender,
			String brithday,
			String signText,
			String salary,
			String area,
			String jobName,
			String userPic)
	{
		ApiResult ar = new ApiResult();
		ar.Result = false;
		
		try
		{
			phoneNumber = WebUtil.SafeRequestString(phoneNumber);
			userPass 	= WebUtil.SafeRequestString(userPass);
			nickName 	= WebUtil.SafeRequestString(nickName);
			gender 		= WebUtil.SafeRequestString(gender);
			brithday 	= WebUtil.SafeRequestString(brithday);
			signText 	= WebUtil.SafeRequestString(signText);
			
			salary 		= WebUtil.SafeRequestString(salary);
			if(salary == null || salary.isEmpty()) salary = "0";
			
			area 		= WebUtil.SafeRequestString(area);
			if(area == null || area.isEmpty()) area = "0";
			
			jobName 	= WebUtil.SafeRequestString(jobName);
			if(jobName == null || jobName.isEmpty()) jobName = "0";
			
			userPic 	= WebUtil.SafeRequestString(userPic);
			
			if(phoneNumber == null) return ar;
			
			String hxUser = EasemobHelper.CreateEaseMobUser();
			if(hxUser != null)
			{
				boolean ret = userService.RegistUser(
						phoneNumber, 
						userPass, 
						nickName, 
						gender, 
						brithday, 
						signText, 
						Integer.parseInt(salary), 
						Integer.parseInt(area), 
						Integer.parseInt(jobName), 
						userPic,
						hxUser,
						Constants.DEFAULT_PASSWORD);
				ar.Result = ret;
			}
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value="/logonuser", method=RequestMethod.POST)
	public ApiResult UserLogon(String phoneNumber,String password)
	{
		ApiResult ar = new ApiResult();
		
		try
		{
			phoneNumber = WebUtil.SafeRequestString(phoneNumber);
			password 	= WebUtil.SafeRequestString(password);
			
			Map userInfo = userService.UserLogon(phoneNumber, password);
			if(userInfo == null)
			{
				ar.Infomation = "用户名或密码错误";
				return ar;
			}
			String tokenString = CreateAuthToken(userInfo);
			
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("UserInfo", userInfo);
			data.put("token", tokenString);
			
			ar.Data = data;
			ar.Result = true;
		}
		catch(Exception ex)
		{
			ar.Infomation = ex.toString();
		}
		
		return ar;
	}
	
	@ResponseBody
	@RequestMapping(value = "/UploadMyIcon",method=RequestMethod.POST)
	public ApiResult UoloadUserImage(HttpServletRequest request,int uid){
		/*
		//验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if(ti == null)
		{
			//用户token验证失败
			return AuthFailed();
		}
		*/
		
		ApiResult ar = new ApiResult();
		ar.Result = false;
		try
		{	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件：
			MultipartFile file = multipartRequest.getFile("file");
	
			// 获得文件名：
			String filename = file.getOriginalFilename();
			
			//文件类型
			String fileType = filename.substring(filename.lastIndexOf("."))
					.toLowerCase().substring(1);
			
			//判断是否在允许文件类型范围
			if(!WebUtil.IsVailFile(fileType, 4))
			{
				ar.Infomation = "文件类型不允许";
				ar.Result = false;
				return ar;
			}
			
			// 写入文件
			String path = "/alidata/default/youelink/upload/userpic";

			String writeFileName = UtiltyHelper.CreateRandomFileName(filename);
			String sepString = System.getProperty("file.separator");
			File source = new File(path + sepString + writeFileName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(source));
			stream.write(file.getBytes());
			stream.close();
			
			if(uid != 0)
				userService.UpdateUserIcon(uid,writeFileName);
			
			ar.Result = true;
			ar.Data = writeFileName;
		}catch(Exception ex){
			ar.Infomation = ex.toString();
		}

		return ar;
	}
	
	/**
	 * 根据UID获取用户资料
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/GetUserInfoByID")
	public ApiResult GetUserInfoByID(HttpServletRequest request,int uid)
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
			Map userInfo = (Map)userService.GetUserInfoByUID(uid);
			int buddyId = Integer.parseInt(userInfo.get("UID").toString());
			int isPending = userService.IsPendingBuddy(ti.uid, buddyId);
			userInfo.put("BuddyStatus", isPending);
			
			ar.Data = userInfo;
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
	
	/**
	 * 根据环信ID获取用户资料
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/GetUserInfoByHXUser")
	public ApiResult GetUserInfoByHXUser(HttpServletRequest request,String userName)
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
			Map userInfo = (Map)userService.GetUserInfoByHXID(userName);
			int buddyId = Integer.parseInt(userInfo.get("UID").toString());
			int isPending = userService.IsPendingBuddy(ti.uid, buddyId);
			userInfo.put("BuddyStatus", isPending);
			
			ar.Data = userInfo;
			ar.Result = true;
		}
		catch(Exception ex)
		{
			
		}
		
		return ar;
	}
	
	/**
	 * 用户信息查找
	 * @param request
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/SearchUsers")
	public ApiResult SearchUsers(HttpServletRequest request,
			String phoneNumber,
			String nickName,
			String Gender,
			String AreaID,
			String JobID,
			String age,
			int page)
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
			nickName = UtiltyHelper.GetStringFromUrl(nickName);
			List userList = userService.SearchUsers(phoneNumber,nickName, Gender, AreaID, JobID, age, page);
			for(int i=0;i<userList.size();i++)
			{
				Map userInfo = (Map)userList.get(i);
				int buddyId = Integer.parseInt(userInfo.get("UID").toString());
				int isPending = userService.IsPendingBuddy(ti.uid, buddyId);
				userInfo.put("BuddyStatus", isPending);
			}
			
			ar.Data = userList;
			ar.Result = true;
		}
		catch(Exception ex)
		{
					
		}
				
		return ar;
	}
	
	
	/**
	 * 定时更新用户位置
	 * @param request
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/LocatedUser",method=RequestMethod.POST)
	public ApiResult LocatedUser(HttpServletRequest request,String latitude,String longitude)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}
		
		latitude 	= WebUtil.SafeRequestString(latitude);
		longitude 	= WebUtil.SafeRequestString(longitude);

		ApiResult ar = new ApiResult();

		try {
			userService.UpdateLocatedUser(ti.uid, latitude, longitude);
			ar.Result = true;
		} catch (Exception ex) {

		}

		return ar;
	}
	
	/**
	 * 附近的人列表
	 * @param request
	 * @param latitude
	 * @param longitude
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/NearUserList",method=RequestMethod.GET)
	public ApiResult NearUserList(HttpServletRequest request,String latitude,String longitude,int page)
	{
		// 验证用户token
		TokenInfo ti = VerifyClientUser(request);
		if (ti == null) {
			// 用户token验证失败
			return AuthFailed();
		}
		
		latitude 	= WebUtil.SafeRequestString(latitude);
		longitude 	= WebUtil.SafeRequestString(longitude);

		ApiResult ar = new ApiResult();

		try {
			ar.Data = userService.UserListNearBy(page, latitude, longitude);
			ar.Result = true;
		} catch (Exception ex) {

		}

		return ar;
	}
}
