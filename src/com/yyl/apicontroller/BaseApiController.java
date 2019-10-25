package com.yyl.apicontroller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

//import com.sun.corba.se.impl.ior.EncapsulationUtility;
import com.yyl.common.EncryptionHelper;
import com.yyl.common.UtiltyHelper;
import com.yyl.modal.ApiResult;
import com.yyl.modal.TokenInfo;

@Controller
public class BaseApiController {

	protected String CreateAuthToken(Map userInfo)
	{
		String token = 
				userInfo.get("UID").toString() + "," +
				userInfo.get("PhoneNumber").toString() + "," +
				userInfo.get("hxUser").toString() + "," +
				UtiltyHelper.GetNowDate();
				
		token = EncryptionHelper.getBase64(token);
		return token;
	}
	
	protected TokenInfo VerifyClientUser(HttpServletRequest request) {
		try{
			String token = request.getHeader("Authentication");
			if(token == null || token.equals("")) return null;
			
			TokenInfo ti = new TokenInfo(token);
			return ti;
		}catch(Exception ex){}
		return null;
	}
	
	protected ApiResult AuthFailed()
	{
		ApiResult ar = new ApiResult();
		ar.Infomation = "用户身份验证失败";
		return ar;
	}
}
