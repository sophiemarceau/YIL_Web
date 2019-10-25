package com.yyl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.dialect.DB2390Dialect;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.sun.glass.events.ViewEvent;
import com.yyl.common.UtiltyHelper;
import com.yyl.modal.PagerInfo;

@Service
public class EventService extends BaseService{

	@Transactional
	public int CreateEvent(
			String title,
			int uid,
			int gender,
			int userLevel,
			String dateTime,
			int payType,
			String address,
			String latitude,
			String longitude,
			int coins,
			String des,
			int onTop,
			int memberLimit,
			int needAccept)
	{
		Session db = getCurrentSession();
		
		if(coins>0)
		{
			String sql = "select MyCoin from UserInfos where UID="+uid;
			int myCoin = Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
			if(myCoin >= coins)
			{
				sql = "update UserInfos set MyCoin=MyCoin-"+coins+" where UID="+uid;
				db.createSQLQuery(sql).executeUpdate();
			}
			else 
			{
				return -1;
			}
		}
		
		String sql = "insert into Events(Title,UID,CreateTime,Gender,UserLevel, PayType,EventDate,EventAddress,Latitude, Longitude, MemberCount, Coins, OnTop, Description,IsEnable,ViewCount,CommentCount,MemberLimit,NeedAccept) "+
					 "values(:TITLE,:UID,:TIME,:GENDER,:UL,:PT,:DATE,:ADD,:LA,:LO,0,:COINS,:ONTOP,:DES,1,0,0,:MEMBERLIMIT,:ACCEPT)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("TITLE", title);
		q.setParameter("UID", uid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("GENDER", gender);
		q.setParameter("UL", userLevel);
		q.setParameter("PT", payType);
		q.setParameter("DATE", dateTime);
		q.setParameter("ADD", address);
		q.setParameter("LA", latitude);
		q.setParameter("LO", longitude);
		q.setParameter("COINS", coins);
		q.setParameter("DES", des);
		q.setParameter("ONTOP", onTop);
		q.setParameter("MEMBERLIMIT", memberLimit);
		q.setParameter("ACCEPT", needAccept);
		q.executeUpdate();
		
		return 0;
	}
	
	@Transactional
	public void ExitEvent(int uid,int eid) 
	{
		Session db = getCurrentSession();
		String sql = "select * from EventJoin where EID="+eid+" and UID="+uid;
		Object isInObject = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		if(isInObject == null) return;
		
		Map eventInfo 	= (Map)isInObject;
		int IsAccept = Integer.parseInt(eventInfo.get("IsAccept").toString());
		
		sql = "delete from EventJoin where EID="+eid+" and UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
		
		if(IsAccept == 1)
		{
			sql = "update Events set MemberCount=MemberCount-1 where EID="+eid;
			db.createSQLQuery(sql).executeUpdate();
		}
	}
	
	@Transactional
	public int JoinEvent(int uid,int eid)
	{
		Session db = getCurrentSession();
		String sql = "select * from Events where EventDate>='"+UtiltyHelper.GetNowDate()+"' and EID="+eid;
		Object eventObj = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		if(eventObj == null) return -1;
		
		Map eventInfo 	= (Map)eventObj;
		int gender 		= Integer.parseInt(eventInfo.get("Gender").toString());
		int UserLevel 	= Integer.parseInt(eventInfo.get("UserLevel").toString());
		
		int MemberLimit = Integer.parseInt(eventInfo.get("MemberLimit").toString());
		int MemberCount = Integer.parseInt(eventInfo.get("MemberCount").toString());
		int NeedAccept 	= Integer.parseInt(eventInfo.get("NeedAccept").toString());
		
		sql = "select * from UserInfos where UID="+uid;
		Map userInfo = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		
		int user_gender 	= Integer.parseInt(userInfo.get("Gender").toString());
		int user_userLevel 	= Integer.parseInt(userInfo.get("UserLevel").toString());
		
		if(gender != 0 && gender != user_gender)
		{
			return -2;
		}
		
		if(UserLevel > user_userLevel)
		{
			return -3;
		}
		
		sql = "select * from EventJoin where EID="+eid+" and UID="+uid;
		Object isInObject = db.createSQLQuery(sql).uniqueResult();
		if(isInObject != null)
		{
			return -4;
		}
		
		int isAccept = 1;
		if(NeedAccept == 1)
		{
			isAccept = 0;
		}
		else 
		{
			if(MemberCount >= MemberLimit)
			{
				return -5;
			}
			else 
			{
				isAccept = 1;
			}
		}
		
		sql = "insert into EventJoin(EID,UID,CreateTime,IsSignin,IsAccept) values(:EID,:UID,:TIME,0,:ACCEPT)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("EID", eid);
		q.setParameter("UID", uid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("ACCEPT", isAccept);
		q.executeUpdate();
		
		if(isAccept == 1)
		{
			sql = "update Events set MemberCount=MemberCount+1 where EID="+eid;
			db.createSQLQuery(sql).executeUpdate();
			return 0;
		}
		else {
			return 1;
		}
	}
	
	@Transactional
	public void AccecpJoinEvent(int eid,int uid) 
	{
		Session db = getCurrentSession();
		String sql = "update EventJoin set IsAccept=1 where EID="+eid+" and UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public void ViewEvent(int eid)
	{
		Session db = getCurrentSession();
		String sql = "update Events set ViewCount=ViewCount+1 where EID="+eid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public void ReportEvent(int eid,int uid)
	{
		Session db = getCurrentSession();
		String sql = "insert into EventReport(UID,CreateTime,EID) values(:UID,:TIME,:EID)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("EID", eid);
		q.setParameter("UID", uid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.executeUpdate();
	}
	
	@Transactional
	public void SignInEvent(int uid,int eid)
	{
		Session db = getCurrentSession();
		String sql = "update EventJoin set IsSignin=1,SignInTime='"+UtiltyHelper.GetNowDate()+"' where EID="+eid+" and UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "update UserInfos set CreditPoint=CreditPoint+2 where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public List EventTopList()
	{
		Session db = getCurrentSession();
		String sql = "select coins from Events_view where EventDate>Now() and IsEnable=1 order by coins desc";
		List list = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@Transactional
	public List EventListTop(int page)
	{
		Session db = getCurrentSession();
		List list = null;
		if(page == 1)
		{
			String now = UtiltyHelper.GetNowDate();
			
			String sql = "select * from Events_view where OnTop=1 and IsEnable=1 and EventDate>='"+now+"'";
			List list1 = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
			sql = "select * from Events_view where OnTop=0 and IsEnable=1 and EventDate>='"+now+"' order by Coins desc";
			Query q =  db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			q.setMaxResults(PageSize);
			q.setFirstResult((page-1) * PageSize);
			List list2 = q.list();
			
			list = new ArrayList<Object>();
			for(Object o : list1)
			{
				list.add(o);
			}
			for(Object o : list2)
			{
				list.add(o);
			}
			
			int cnt = list2.size();
			if(cnt < PageSize)
			{
				int leftCnt = PageSize - cnt;
				sql = "select * from Events_view where OnTop=0 and IsEnable=1 and EventDate<'"+now+"' order by Coins desc";
				q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setMaxResults(leftCnt);
				if(cnt == 0)
					q.setFirstResult((page-1) * leftCnt);
				else
					q.setFirstResult(0);
				
				List list3 = q.list();
				for(Object o : list3)
				{
					list.add(o);
				}
			}
		}
		else
		{
			String now = UtiltyHelper.GetNowDate();
			
			String sql = "select * from Events_view where OnTop=0 and IsEnable=1 and EventDate>='"+now+"' order by Coins desc";
			Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			q.setMaxResults(PageSize);
			q.setFirstResult((page-1) * PageSize);
			List list1 = q.list();
			
			int cnt = list1.size();
			if(cnt < PageSize)
			{
				int leftCnt = PageSize - cnt;
				sql = "select * from Events_view where OnTop=0 and IsEnable=1 and EventDate<'"+now+"' order by Coins desc";
				q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				q.setMaxResults(leftCnt);
				if(cnt == 0)
					q.setFirstResult((page-1) * leftCnt);
				else
					q.setFirstResult(0);
				
				List list2 = q.list();
				
				list = new ArrayList<Object>();
				for(Object o : list1)
				{
					list.add(o);
				}
				for(Object o : list2)
				{
					list.add(o);
				}
			}
			else {
				list = list1;
			}
			
		}
		
		return list;
	}
	
	@Transactional
	public List EventListNearBy(int page,String latitude,String longitude)
	{
		String now = UtiltyHelper.GetNowDate();
		Session db = getCurrentSession();
		String sql = "select *,round(6378.138*2*asin(sqrt(pow(sin( ("+latitude+"*pi()/180-Latitude*pi()/180)/2),2)+cos("+latitude+"*pi()/180)*cos(Latitude*pi()/180)* pow(sin( ("+longitude+"*pi()/180-Longitude*pi()/180)/2),2)))*1000) distance from Events_view where IsEnable=1 and EventDate>='"+now+"' order by distance";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public List EventListFirend(int page,int uid)
	{
		Session db = getCurrentSession();
		
		String sql = "select a.UID from UserInfos a,UserBuddys b where a.UID=b.BuddyID and b.UID="+uid;
		List friendsList = db.createSQLQuery(sql).list();
		if(friendsList.size() > 0)
		{
			String firends = "";
			for(Object userid:friendsList)
			{
				firends += userid.toString()+",";
			}
			if(firends.endsWith(","))
				firends = firends.substring(0,firends.length()-1);
			
			sql = "select * from Events_view where UID in("+firends+") and IsEnable=1 order by EventDate desc";
			
			Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			q.setMaxResults(PageSize);
			q.setFirstResult((page-1) * PageSize);
			return q.list();
		}
		
		return new ArrayList();
	}
	
	@Transactional
	public List MyEventList(int page,int uid)
	{
		Session db = getCurrentSession();
		
		String sql = "select * from Events_view where UID="+uid +" order by EventDate desc";

		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public List MyJoinEventList(int page,int uid)
	{
		Session db = getCurrentSession();
		
		String sql = "select a.*,b.IsAccept from Events_view a,EventJoin b where a.EID=b.EID and b.UID="+uid+" order by a.EventDate desc";

		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public List EventSearch(int page,String title,String Date,String address,int payType,int userLevel,int gender)
	{
		Session db = getCurrentSession();
		String sql = "select * from Events_view where IsEnable=1";
		
		if(title!=null && !title.isEmpty())
			sql += " and Title like '%"+title+"%'";
		
		if(address!=null && !address.isEmpty())
			sql += " and EventAddress like '%"+address+"%'";
		
		if(Date!=null && !Date.isEmpty())
			sql += " and EventDate like '%"+Date+"%'";
		
		if(payType != 0)
			sql += " and PayType="+payType;
		
		if(userLevel != 0)
			sql += " and UserLevel ="+userLevel;
		
		if(gender != 0)
			sql += " and Gender="+gender;
		
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public PagerInfo EventSearchForWeb(int page,String title,String Date,String address,int payType)
	{
		Session db = getCurrentSession();
		String sql = "select count(*) from Events_view where 1=1";
		
		String where = "";
		if(title!=null && !title.isEmpty())
			where += " and Title like '%"+title+"%'";
		
		if(address!=null && !address.isEmpty())
			where += " and EventAddress like '%"+address+"%'";
		
		if(Date!=null && !Date.isEmpty())
			where += " and EventDate like '%"+Date+"%'";
		
		if(payType != 0)
			where += " and PayType="+payType;
		
		PagerInfo pi = new PagerInfo();
		int total = Integer.parseInt(db.createSQLQuery(sql + where).uniqueResult().toString());
		pi.CaclePager(total, PageSize, page);
		
		sql = "select * from Events_view where 1=1";
		
		String order = " order by CreateTime desc";
		
		Query q = db.createSQLQuery(sql+where+order).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((pi.getCurrentPage()-1) * PageSize);
		pi.setDataList(q.list());
		return pi;
	}
	
	@Transactional
	public Map GetEventInfo(int eid,int uid)
	{
		Session db = getCurrentSession();
		String sql = "select * from Events_view where EID="+eid;
		Map info = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		
		sql = "select * from EventJoin where EID="+eid+" and UID="+uid;
		Object isInObject = db.createSQLQuery(sql).uniqueResult();
		if(isInObject != null)
		{
			info.put("IsJoined", "1");
		}
		else {
			info.put("IsJoined", "0");
		}
		
		return info;
	}
	
	@Transactional
	public void AddEventTopic(String body,int uid,int eid)
	{
		Session db = getCurrentSession();
		String sql = "insert into EventTopic(body,UID,EID,CreateTime) values(:BODY,:UID,:EID,:TIME)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("BODY", body);
		q.setParameter("UID", uid);
		q.setParameter("EID", eid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.executeUpdate();
		
		sql = "update Events set CommentCount=CommentCount+1 where EID="+eid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public List EventTopicList(int eid,int page)
	{
		Session db = getCurrentSession();
		String sql = "select a.*,b.NickName,b.Gender,b.UserPic from EventTopic a,UserInfos b where a.UID=b.UID and a.EID="+eid+" order by a.CreateTime desc";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public PagerInfo EventTopicListWeb(int eid,int page)
	{
		Session db = getCurrentSession();
		String sql = "select count(*) from EventTopic where EID="+eid;
		int total = Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
		
		PagerInfo pi = new PagerInfo();
		pi.CaclePager(total, PageSize, page);
		
		sql = "select a.*,b.UserPic,b.NickName from EventTopic a,UserInfos b where a.UID=b.UID and a.EID="+eid+" order by a.CreateTime desc";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((pi.getCurrentPage()-1) * PageSize);
		pi.setDataList(q.list());
		return pi;
	}
	
	/**
	 * 被举报的活动
	 * @param eid
	 * @param page
	 * @return
	 */
	@Transactional
	public PagerInfo EventReportListWeb(int page)
	{
		Session db = getCurrentSession();
		String sql = "select count(distinct  a.EID) from Events_view a,EventReport b where a.EID=b.EID";
		int total = Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
		
		PagerInfo pi = new PagerInfo();
		pi.CaclePager(total, PageSize, page);
		
		sql = "select distinct a.EID,a.Title,a.EventDate,a.EventAddress,a.Gender,a.UserLevel,a.PayType,a.Coins,a.OnTop,a.Description,a.UserPic,a.NickName,a.IsEnable from Events_view a,EventReport b where a.EID=b.EID order by a.CreateTime desc";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((pi.getCurrentPage()-1) * PageSize);
		pi.setDataList(q.list());
		return pi;
	}
	
	@Transactional
	public List EventMembers(int eid,int page)
	{
		Session db = getCurrentSession();
		String sql = "select a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext,b.CreateTime as JoinTime,b.IsAccept from EventJoin b,UserInfos a where a.UID=b.UID and b.EID="+eid;
		
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1) * PageSize);
		return q.list();
	}
	
	@Transactional
	public List EventMembers(int eid)
	{
		Session db = getCurrentSession();
		String sql = "select a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext from EventJoin b,UserInfos a where a.UID=b.UID and b.EID="+eid;
		
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.list();
	}
	
	@Transactional
	public void CloseEvent(int eid)
	{
		Session db = getCurrentSession();
		String sql = "update Events set IsEnable=0 where eid="+eid;
		
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public void ResumeEvent(int eid)
	{
		Session db = getCurrentSession();
		String sql = "update Events set IsEnable=1 where eid="+eid;
		
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public void TopEvent(int eid,int type)
	{
		Session db = getCurrentSession();
		String sql = "update Events set OnTop="+type+" where eid="+eid;
		
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public void DeleteComment(int etid)
	{
		Session db = getCurrentSession();
		
		String sql = "update Events set CommentCount=CommentCount-1 where EID=(select EID from EventTopic where ET_ID="+etid+")";
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "delete from EventTopic where ET_ID="+etid;
		db.createSQLQuery(sql).executeUpdate();
	}
}
