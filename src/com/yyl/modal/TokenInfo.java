package com.yyl.modal;

import com.yyl.common.EncryptionHelper;


public class TokenInfo {
	
	public int uid;
	public String phoneNumber;
	public String hxUserName;
	
	public TokenInfo(String tokenString) 
	{
		tokenString = EncryptionHelper.getFromBase64(tokenString);
		String[] arr 		= tokenString.split(",");
		this.uid 			= Integer.parseInt(arr[0]);
		this.phoneNumber 	= arr[1];
		this.hxUserName 	= arr[2];
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getHxUserName() {
		return hxUserName;
	}
	public void setHxUserName(String hxUserName) {
		this.hxUserName = hxUserName;
	}

}
