package com.yyl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import sun.net.www.content.text.plain;

import com.yyl.common.UtiltyHelper;
import com.yyl.modal.PagerInfo;
import com.yyl.thirdpart.SMSHelper;

@Service
public class UsersService extends BaseService{

	@Transactional
	public List LoadUsersList()
	{
		Session db = getCurrentSession();
		String sql = "select * from UserInfos";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(10);
		q.setFirstResult(0);
		
		return q.list();
	}
	
	@Transactional
	public void ForgetPassword(String phoneNumber)
	{
		Session db = getCurrentSession();
		String rand = UtiltyHelper.CreateRandomNumber();
		String sql = "update UserInfos set UserPass='"+rand+"' where PhoneNumber='"+phoneNumber+"'";
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "insert into SMSPost(Body,PhoneNumber,CreateTime, PostState, SMSType)"+
				 " values(:BODY,:PN,:TIME,:PS,:TYPE)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("BODY", rand);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("PN", phoneNumber);
		q.setParameter("PS", 0);
		q.setParameter("TYPE", 2);
		q.executeUpdate();
		
		SMSHelper.SendSMS_Pass(phoneNumber, rand);
	}
	
	@Transactional
	public void CreateVerifyNumber(String phoneNumber,String code)
	{
		Session db = getCurrentSession();
		String sql = "insert into VerifyCode(Code,CreateTime,PhoneNumber,State)"+
					 " values(:CODE,:TIME,:PN,:STATE)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("CODE", code);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("PN", phoneNumber);
		q.setParameter("STATE", 1);
		q.executeUpdate();
		
		sql = "insert into SMSPost(Body,PhoneNumber,CreateTime, PostState, SMSType)"+
				 " values(:BODY,:PN,:TIME,:PS,:TYPE)";
		q = db.createSQLQuery(sql);
		q.setParameter("BODY", code);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("PN", phoneNumber);
		q.setParameter("PS", 0);
		q.setParameter("TYPE", 1);
		q.executeUpdate();
		
		SMSHelper.SendSMS_Reg(phoneNumber, code);
	}
	
	@Transactional
	public boolean VerifyCode(String phoneNumber,String code)
	{
		Session db = getCurrentSession();
		String sql = "select * from VerifyCode where PhoneNumber='"+phoneNumber+"' and Code='"+code+"' and State=1";
		Object result = db.createSQLQuery(sql).uniqueResult();
		if(result == null) return false;
		
		return true;
	}
	
	@Transactional
	public boolean RegistUser(
			String phoneNumber,
			String userPass,
			String nickName,
			String gender,
			String brithday,
			String signText,
			int salaryID,
			int areaID,
			int jobID,
			String userPic,
			String hxUser,
			String hxPass)
	{
		Session db = getCurrentSession();
		
		String sql = "select * from UserInfos where PhoneNumber='"+phoneNumber+"'";
		Object result = db.createSQLQuery(sql).uniqueResult();
		if(result != null) return false;
		
		sql = "insert into UserInfos(PhoneNumber,UserPass, hxUser, hxPass,NickName, Gender, Brithday,signtext, SalaryID,AreaID, JobID,MyCoin, UserPic,CreateTime,Age)"+
					" values(:PN,:UP,:hxUser,:hxPass,:NICK,:G,:B,:SIGN,:SALARY,:AREA,:JOB,:COIN,:UPIC,:TIME,:AGE)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("PN", phoneNumber);
		q.setParameter("UP", userPass);
		q.setParameter("hxUser", hxUser);
		q.setParameter("hxPass", hxPass);
		q.setParameter("NICK", nickName);
		q.setParameter("G", gender);
		q.setParameter("B", brithday);
		q.setParameter("SIGN", signText);
		q.setParameter("SALARY", salaryID);
		q.setParameter("AREA", areaID);
		q.setParameter("JOB", jobID);
		q.setParameter("COIN", 0);
		q.setParameter("UPIC", userPic);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("AGE", UtiltyHelper.getAgeByBirthday(brithday));
		q.executeUpdate();
		
		return true;
	}
	
	@Transactional
	public void UpdateUserInfo(int uid,
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
		Session db = getCurrentSession();
		String sql = "update UserInfos set";
		
		if(userPass != null && !userPass.isEmpty())
		{
			sql += " UserPass='"+userPass+"',";
		}
		
		if(nickName != null && !nickName.isEmpty())
		{
			sql += " NickName='"+nickName+"',";
		}
		
		if(gender != null && !gender.isEmpty())
		{
			sql += " Gender='"+gender+"',";
		}
		
		if(brithday != null && !brithday.isEmpty())
		{
			sql += " Brithday='"+brithday+"',";
			int age = UtiltyHelper.getAgeByBirthday(brithday);
			sql += " Age="+age+",";
		}
		
		if(signText != null && !signText.isEmpty())
		{
			sql += " signtext='"+signText+"',";
		}
		
		if(userPic != null && !userPic.isEmpty())
		{
			sql += " UserPic='"+userPic+"',";
		}
		
		if(email != null && !email.isEmpty())
		{
			sql += " EMail='"+email+"',";
		}
		if(hobbies != null && !hobbies.isEmpty())
		{
			sql += " Hobbies='"+hobbies+"',";
		}
		
		
		if(areaId != null && !areaId.isEmpty())
		{
			sql += " AreaID='"+areaId+"',";
		}
		if(jobId != null && !jobId.isEmpty())
		{
			sql += " JobID='"+jobId+"',";
		}
		if(salaryId != null && !salaryId.isEmpty())
		{
			sql += " SalaryID='"+salaryId+"',";
		}
		
		if(sql.endsWith(","))
			sql = sql.substring(0,sql.length()-1);
		
		sql += " where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public boolean RegistUser2(
			String phoneNumber,
			String userPass,
			String hxUser,
			String hxPass)
	{
		Session db = getCurrentSession();
		
		String sql = "select * from UserInfos where PhoneNumber='"+phoneNumber+"'";
		Object result = db.createSQLQuery(sql).uniqueResult();
		if(result != null) return false;
		
		sql = "insert into UserInfos(PhoneNumber,UserPass, hxUser, hxPass,CreateTime,MyCoin,CreditPoint,UserLevel,Gender,UserPic,BuddyRequest,NickName)"+
					" values(:PN,:UP,:hxUser,:hxPass,:TIME,0,100,1,1,'default.jpg',1,'')";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("PN", phoneNumber);
		q.setParameter("UP", userPass);
		q.setParameter("hxUser", hxUser);
		q.setParameter("hxPass", hxPass);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.executeUpdate();
		
		return true;
	}
	
	@Transactional
	public Map UserLogon(String phoneNumber,String password)
	{
		Session db = getCurrentSession();
		String sql = "select * from UserInfos where PhoneNumber='"+phoneNumber+"'";
		Object result = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		if(result == null) return null;
		
		Map userInfo = (Map)result;
		String UserPass = userInfo.get("UserPass").toString();
		if(!UserPass.equals(password))
		{
			return null;
		}
		
		return userInfo;
	}
	
	@Transactional
	public void UpdateUserIcon(int uid,String picFile)
	{
		Session db = getCurrentSession();
		String sql = "update UserInfos set UserPic='"+picFile+"' where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public Object GetUserInfoByUID(int uid)
	{
		Session db = getCurrentSession();
		String sql = "select UID,hxUser,NickName,Gender,Brithday,signtext,UserPic,Age,CreditPoint,UserLevel,AreaName,AreaID,SalaryName,JobName,JobID,Email,Hobbies,BuddyRequest,x1.myEventCnt,x2.myJoinCnt from userInfo_view,(select count(*) myEventCnt from Events_view where UID="+uid+") x1,(select count(*) myJoinCnt from Events_view a,EventJoin b where a.EID=b.EID and b.UID="+uid+") x2 where UID="+uid;
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.uniqueResult();
	}
	
	@Transactional
	public int IsPendingBuddy(int uid,int buddyId) 
	{
		Session db = getCurrentSession();
		
		String sql = "select * from UserBuddys where UID="+uid+" and BuddyID="+buddyId;
		int cnt = db.createSQLQuery(sql).list().size();
		if(cnt>0) return 2;
		
		sql = "select * from BuddyRequest where Status=0 and FromUID="+uid+" and ToUID="+buddyId;
		cnt = db.createSQLQuery(sql).list().size();
		if(cnt>0) return 1;
		
		return 0;
	}
	
	@Transactional
	public Object GetUserInfoByHXID(String username)
	{
		Session db 	 = getCurrentSession();
		String sql 	 = "select UID from UserInfos where hxUser='"+username+"'";
		Map userInfo = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		int uid 	 = Integer.parseInt(userInfo.get("UID").toString());
		
		return GetUserInfoByUID(uid);
	}
	
	@Transactional
	public List SearchUsers(
			String phoneNumber,
			String nickName,
			String Gender,
			String Area,
			String JobName,
			String age,
			int page)
	{
		Session db = getCurrentSession();
		String sql = "select UID,hxUser,NickName,Gender,Brithday,signtext,UserPic,Age,CreditPoint,UserLevel,AreaName,SalaryName,JobName from userInfo_view where 1=1";
		
		if(phoneNumber!=null && !phoneNumber.isEmpty())
		{
			sql += " and PhoneNumber='"+phoneNumber+"'";
		}
		
		if(nickName!=null && !nickName.isEmpty())
		{
			sql += " and NickName like '%"+nickName+"%'";
		}
		
		if(Gender!=null && !Gender.isEmpty())
		{
			sql += " and Gender="+Gender;
		}
		
		if(JobName!=null && !JobName.isEmpty())
		{
			sql += " and JobID="+JobName;
		}
		
		if(Area!=null && !Area.isEmpty())
		{
			sql += " and AreaID="+Area;
		}
		
		if(age!=null && !age.isEmpty())
		{
			sql += " and Age<"+age;
		}
		
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1)*PageSize);
		return q.list();
	}
	
	@Transactional
	public PagerInfo SearchUsers2(
			String phoneNumber,
			String nickName,
			String Gender,
			String Area,
			String JobName,
			String Salary,
			int page)
	{
		Session db = getCurrentSession();
		String sql = "select count(*) from userInfo_view where 1=1";
		
		String where = "";
		if(phoneNumber!=null && !phoneNumber.isEmpty())
		{
			where += " and PhoneNumber like '%"+phoneNumber+"%'";
		}
		
		if(nickName!=null && !nickName.isEmpty())
		{
			where += " and NickName like '%"+nickName+"%'";
		}
		
		if(Gender!=null && !Gender.isEmpty() && !Gender.equals("0"))
		{
			where += " and Gender="+Gender;
		}
		
		if(JobName!=null && !JobName.isEmpty() && !JobName.equals("0"))
		{
			where += " and JobID="+JobName;
		}
		
		if(Area!=null && !Area.isEmpty() && !Area.equals("0"))
		{
			where += " and AreaID="+Area;
		}
		
		if(Salary!=null && !Salary.isEmpty() && !Salary.equals("0"))
		{
			where += " and SalaryID="+Salary;
		}
		
		int total = Integer.parseInt(db.createSQLQuery(sql+where).uniqueResult().toString());
		PagerInfo pi = new PagerInfo();
		pi.CaclePager(total, PageSize, page);
		
		sql = "select * from userInfo_view where 1=1";
		Query q = db.createSQLQuery(sql+where).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((pi.getCurrentPage()-1)*PageSize);
		pi.setDataList(q.list());
		
		return pi;
	}
	
	@Transactional
	public void UpdateLocatedUser(int uid,String latitude,String longitude)
	{
		Session db = getCurrentSession();
		
		String sql = "delete from UserLocation where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "insert into UserLocation(latitude,longitude,UID,CreateTime) "+
					 "values(:LA,:LO,:UID,:TIME)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("LA", latitude);
		q.setParameter("LO", longitude);
		q.setParameter("UID", uid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.executeUpdate();
	}
	
	@Transactional
	public List UserListNearBy(int page,String latitude,String longitude)
	{
		Session db = getCurrentSession();
		String sql = "select a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext,round(6378.138*2*asin(sqrt(pow(sin( ("+latitude+"*pi()/180-b.Latitude*pi()/180)/2),2)+cos("+latitude+"*pi()/180)*cos(b.Latitude*pi()/180)* pow(sin( ("+longitude+"*pi()/180-b.Longitude*pi()/180)/2),2)))*1000) distance from UserLocation b,userInfo_view a where a.UID=b.UID order by distance";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
}
