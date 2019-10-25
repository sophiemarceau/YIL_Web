package com.yyl.thirdpart;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.cloopen.rest.sdk.CCPRestSDK.BodyType;

public class SMSHelper {
	
	private static String Server = "app.cloopen.com"; 
	private static String Port = "8883";
	
	private static String SID = "aaf98f894cc66b36014cd4cf7c9207ca";
	private static String SIDToken = "c1a5c940599942f899d2aec27b72ad23";
	private static String AppID = "aaf98f894cc66b36014cd4d106f307d4";
	
	private static String RegTemplateID = "31860";
	private static String PassTemplateID = "31566";
	
	public static String SendSMS_Reg(String phoneNumber,String verifyCode) 
	{
		HashMap<String, Object> result = null;
		
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(Server,Port);
		restAPI.setAccount(SID, SIDToken);
		restAPI.setAppId(AppID);
		result = restAPI.sendTemplateSMS(phoneNumber,RegTemplateID ,new String[]{verifyCode,"2"});
		
		return result.get("statusCode").toString();
	}
	
	public static String SendSMS_Pass(String phoneNumber,String newPass) 
	{
		HashMap<String, Object> result = null;
		
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(Server,Port);
		restAPI.setAccount(SID, SIDToken);
		restAPI.setAppId(AppID);
		result = restAPI.sendTemplateSMS(phoneNumber,PassTemplateID ,new String[]{newPass});
		
		return result.get("statusCode").toString();
	}
}
