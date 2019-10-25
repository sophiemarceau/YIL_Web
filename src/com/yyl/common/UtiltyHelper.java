package com.yyl.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

public class UtiltyHelper {

	public static String GetNowDate()
	{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format( now ); 
	}
	public static String GetNowDate2()
	{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format( now ); 
	}
	
	public static String GetDateFromNow(int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,day);
		String theDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		return theDate;
	}
	
	public static String SafeString(String str)
	{
		str = str.replace("'", "");
		str = str.trim();
		
		return str;
	}
	
	public static String GetDateFromNow(String date,int day)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
		    myDate = formatter.parse(date);
		    Calendar c = Calendar.getInstance();
		    c.setTime(myDate);
		    c.add(Calendar.DATE, day);
		    myDate = c.getTime();
		} catch (ParseException e1) {
		    e1.printStackTrace();
		}
		return formatter.format(myDate);
	}
	
	
	/**
	 *  
	 * @param day
	 * @return
	 * @throws ParseException 
	 */
	public static String GetDayDate(int day) throws ParseException{
		
		
		   Date d=new Date();   
		   SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
		    
		   //测试用数据
		   //String s ="2011-01-01" ;
		   
		   Date da= df.parse(df.format(d));
		  
	  return df.format(new Date(da.getTime() + day * 24 * 60 * 60 * 1000));
	}
	
	/**
	 * 根据日期查询星期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String GetDateWeek(String date) throws ParseException{
		
        String dayNames[] = {"周日","周一","周二","周三","周四","周五","周六"};
        Calendar c = Calendar.getInstance();// 获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(sdf.parse(date));

	  return dayNames[c.get(Calendar.DAY_OF_WEEK)-1];
	}
	/**
	 *判断是否为闰年
	 */
	public static boolean getIsRun(int year){
		if((year%4==0 && year %100 !=0)||(year%400==0)){
			return true;
		}
		return false;
	}
	/**
	 *返回某年某月有多少天。
	 */
	public static int getDay(int year ,int month){
		int[] day={31,   28,   31,   30,   31,   30,   31,   31,   30,  31,  30, 31};//闰年月份
		int[] day1={31,   29,   31,   30,   31,   30,   31,   31,   30,   31,   30,   31};//普通月份
		if(getIsRun(year)){
			return day1[month-1];
			
		}else{
			return day[month-1];
		}
	}
	/**
	 *返回这个月的1号是星期几
	 * @throws ParseException 
	 */
	public static int getWeek(int year,int month) throws ParseException{
		//Date date=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(sdf.parse(year+"-"+month+"-"+01));
		return calendar.get(calendar.DAY_OF_WEEK)-1;
	}
	
	public static int getAgeByBirthday(String birthday)
	{
		try
		{
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date=new Date();     
			java.util.Date mydate= myFormatter.parse(birthday);
			long day=(date.getTime()-mydate.getTime())/(24*60*60*1000) + 1;
			return (int) (day/365);
		}
		catch(Exception ex)
		{
			
		}
		
		return 0;
	}
	
	/**
	 * 生成一个随机文件名
	 * @param orgFileName
	 * @return
	 */
	public static String CreateRandomFileName(String orgFileName)
	{
		String[] fileArr = orgFileName.split("\\.");
		String fileExt = fileArr[fileArr.length-1];
		
		Random random = new Random();
		int rnd = Math.abs(random.nextInt())%1000;
		Date now = new Date(); 
		String str = String.format("%d%d.%s",now.getTime(),rnd,fileExt);
		return str;
	}
	
	public static String CreateRandomNumber()
	{
		Random random = new Random();
		int rnd = Math.abs(random.nextInt())%100000;
		return String.format("%d", rnd);
	}
	
	public static String GetStringFromUrl(String str)
	{
		try
		{
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8"); 
		}
		catch(Exception ex)
		{
			
		}
		
		return str;
	}
	
	 /**
	  * 把秒转换成时分秒
	  * @param second 秒
	  * @return
	  */
	public static String calSecond(Integer second){
		int h = 0;
		int d = 0;
		int s = 0;
		if(second!=null&&second!=0){
			int temp = second%3600;
			if(second>3600){
				h= second/3600;
				if(temp!=0){
					if(temp>60){
						d = temp/60;
						if(temp%60!=0){
							s = temp%60;
						}
					}else{
				         s = temp;
					}
				}
		    }else{
		        d = second/60;
		        if(second%60!=0){
		        	s = second%60;
		        }
		    }
		}
		String hh = (h<10)?"0"+h:""+h;
		String dd = (d<10)?"0"+d:""+d;
		String ss = (s<10)?"0"+s:""+s;
		return hh+":"+dd+":"+ss;
	}
	
	/**
	 * 输入两个字符串型的日期，比较两者的大小
	 * @param fromDate String
	 * @param toDate String
	 * @return boolean before为true
	 */
	public static boolean compareTwoDateBigOrSmall(String fromDate, String toDate) {
		Date dateFrom = switchStringToDate(fromDate);
		Date dateTo = switchStringToDate(toDate);
		if (dateFrom.before(dateTo)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将一个日期字符串转化成日期
	 * @param sDate String
	 * @return Date yyyy-MM-dd
	 */
	public static Date switchStringToDate(String sDate) {
		Date date = null;
		if (sDate != null) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				date = df.parse(sDate);
			} catch (Exception e) {}
		}
		return date;
	}
	
	/** 
     * 计算两个日期之间相差的天数 
     * @param date1 小日期
     * @param date2 大日期
     * @return 
     */  
    public static int daysBetween(Date date1,Date date2){
    	int betweenDays = 0;
		try {
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date1);  
			long time1 = cal.getTimeInMillis();
			cal.setTime(date2);  
			long time2 = cal.getTimeInMillis();       
			long between_days = (time2-time1)/(1000*3600*24);
			betweenDays = parseInteger(between_days);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return betweenDays;         
    }
	
	/**
	 * 将一个日期转化成日期字符串(按相应的格式)
	 * @param date
	 * @param format 格式
	 * @return 日期字符串或""
	 */
	public static String dateToString(Date date, String format) {
		if(format==null||format==""){
			format="yyyy-MM-dd";
		}
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if (date != null) {
				newDate = sdf.format(date);
			}
		} catch (Exception e) {
		}
		return newDate;
	}
	
	/**
	 * 将一个日期字符串转化成日期(按相应的格式)
	 * @param date
	 * @param format 格式
	 * @return Date或null
	 */
	public static Date stringToDate(String date, String format) {
		if(format==null||format==""){
			format="yyyy-MM-dd";
		}
		Date newDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			if (date != null && !date.equals("")) {
				newDate = sdf.parse(date);
			}
		} catch (Exception e) {
		}
		return newDate;
	}
	
	 /**
	  * 是否为空白,包括null、""和空格
	  * @param o
	  * @return
	  */
	public static boolean isEmpty(Object o) {
		return o == null || o.toString().trim().length() == 0;
	}
	 /**
	  * 是否为空白,包括null、""和空格
	  * @param o
	  * @return
	  */
	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	/**
	 * 是否为空白,包括null、""和空格
	 * @param o
	 * @return
	 */
	public static String trim(Object o) {
		return o == null?null:o.toString().trim();
	}

	public static String parseString(Object o) {
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	public static Long parseLong(Object o) {
		o=trim(o);
		if (!isNumber(o)) {
			return null;
		}
		Number num= null;
		try {
			num = NumberFormat.getInstance().parse(o.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isNumberType(o)?num.longValue():Long.valueOf(o.toString());
	}

	public static Integer parseInteger(Object o) {
		o=trim(o);
		if (!isNumber(o)) {
			return null;
		}
		Number num= null;
		try {
			num = NumberFormat.getInstance().parse(o.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isNumberType(o)?num.intValue():Integer.valueOf(o.toString());
	}

	public static Float parseFloat(Object o) {
		o=trim(o);
		if (!isNumber(o)) {
			return null;
		}
		Number num= null;
		try {
			num = NumberFormat.getInstance().parse(o.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isNumberType(o)?num.floatValue():Float.valueOf(o.toString());
	}
	
	public static Double parseDouble(Object o) {
		o=trim(o);
		if (!isNumber(o)) {
			return null;
		}
		Number num= null;
		try {
			num = NumberFormat.getInstance().parse(o.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isNumberType(o)?num.doubleValue():Double.valueOf(o.toString());
	}
	
	/**
	 * 判断输入的字符串是否为纯汉字
	 * @param str 传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(Object str) {
		Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
	    return isEmpty(str)?false:pattern.matcher(str.toString()).matches();
	}
	
	/**
	 * 判断是否为数字，包括double和float
	 * @param o 传入Object
	 * @return 是数字返回true,否则返回false
	 */
	public static boolean isNumber(Object o) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+(.[\\d]+)?$");
		return isEmpty(o) ? false : isNumberType(o) || pattern.matcher(o.toString()).matches();
	}

	private static boolean isNumberType(Object o) {
		return (o instanceof Number);
	}
	
	/**
	 * 数字小数点后保留小数位
	 * @param sNum 要处理的数字
	 * @param format 数字格式
	 * @return
	 */
	public static String parseDoubleToString(Double sNum,String format) {
		if(isEmpty(format)){
			format = "#.##";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		String resultStr = decimalFormat.format(sNum);
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}
	
	/**
	 * 产生随机GUID
	 * @return
	 */
	public static final String GenerateGUID(){  
        UUID uuid = UUID.randomUUID();  
        return uuid.toString();       
    }  
	
}