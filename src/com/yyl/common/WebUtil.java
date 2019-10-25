package com.yyl.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.springframework.context.support.StaticApplicationContext;


public class WebUtil {

	

    public static String UrlEncode(String url) {
		// TODO Auto-generated method stub
		try {
			return java.net.URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
    
    public static String UrlUnEncode(String url) {
		// TODO Auto-generated method stub
		try {
			return java.net.URLDecoder.decode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
    
    public static boolean isSqlIn(String str)
    {
    	return !str.equals(SafeRequestString(str)); 
    }
    
    public static String SafeRequestString(String str)
    {
    	if(str == null) return str;
    	
    	str = str.replaceAll("'", "");
    	str = str.replaceAll("\"", "");
    	str = str.replaceAll("\\'", "");
    	str = str.replaceAll("\\\\", "");
    	str = str.replaceAll("\\(", "（");
    	str = str.replaceAll("\\)", "）");
    	str = str.replaceAll(";", "；");
    	str = str.replaceAll("--", "");
    	str = str.replaceAll("\\+", "");
    	str = str.replaceAll("#", "");
    	
 
    	str = str.replaceAll("(?i)select", "");
    	str = str.replaceAll("(?i)delete", "");
    	str = str.replaceAll("(?i)insert", "");
    	str = str.replaceAll("(?i)drop", "");
    	
    	return str;
    }
    
    public static boolean isXSS(String str)
    {
    	return !str.equals(cleanXSS(str)); 
    }
    
    public static String cleanXSS(String value) 
    {
        //You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		
		value = value.replaceAll("script", "");
		value = value.replaceAll("<img", "");
		value = value.replaceAll("<iframe", "");
		value = value.replaceAll("<video", "");
		value = value.replaceAll("onload", "");
		
		return value;
    }
    
    public static int SafeRequestString(int iStr)
    {
    	if(iStr < 0) iStr = 0;
    	if(iStr >= Integer.MAX_VALUE) iStr = 0;
    	
    	return iStr;
    }
    
    /**
     * 文件上传白名单
     * @param extName
     * @param type 1=文档类 2=视频类 3=听力题语音
     * @return
     */
    public static boolean IsVailFile(String extName,int type)
    {
    	final String[][] allList = {
    			{"doc","pdf","docm","docx","dot","dotm","dotx","html","rtf","txt","wps","xml"
    					,"xps","xls"
    					,"xlsx","pps"
    					,"ppt","pptx"
    					,"pptm","wmf"},
			{"mp4","wmv","avi","rmvb"},
			{"mp3","wav"},
			{"jpg","jpeg","png","gif","bmp"}
			};
    	
    	String[] fileter = allList[type-1];
    	return Arrays.asList(fileter).contains(extName);
    }
}
