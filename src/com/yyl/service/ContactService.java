package com.yyl.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easemob.server.example.jersey.apidemo.EasemobIMUsers;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.sun.javafx.image.IntPixelAccessor;
import com.yyl.common.UtiltyHelper;

@Service
public class ContactService extends BaseService{

	@Transactional
	public List SyncContactUsers(String phones)
	{
		String pLine = "";
		String[] pArray = phones.split(",");
		for(String p:pArray)
		{
			pLine += "'"+p+"',";
		}
		if(pLine.endsWith(","))
			pLine = pLine.substring(0,pLine.length()-1);
		
		Session db = getCurrentSession();
		String sql = "select UID,PhoneNumber,hxUser from UserInfos where PhoneNumber in("+pLine+")";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
	}
	
	@Transactional
	public List getBuddyList(int uid)
	{
		Session db = getCurrentSession();
		String sql = "select a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext from UserInfos a,UserBuddys b where a.UID=b.BuddyID and b.UID="+uid;
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Transactional
	public List getUserListByHxUsers(String hxUsers)
	{
		Session db = getCurrentSession();
		String sql = "select a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext from UserInfos a where hxUser in("+hxUsers+")";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Transactional
	public int addBuddy(int buddyId,String buddy_hxId,int uid,String hxId)
	{
		Session db = getCurrentSession();
		
		String sql = "select BuddyRequest from UserInfos where UID="+buddyId;
		int BuddyRequest = Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
		if(BuddyRequest == 1)
		{
			CreateBuddyRequest(uid, buddyId,hxId,buddy_hxId);
			return 1;
		}
		
		ObjectNode objNode = EasemobIMUsers.addFriendSingle(hxId, buddy_hxId);
		if(objNode == null) return -1;
		
		sql = "select * from UserBuddys where UID="+uid+" and BuddyID="+buddyId;
		Object result = db.createSQLQuery(sql).uniqueResult();
		if(result == null)
		{
			sql = "insert into UserBuddys(UID,BuddyID) values(:UID,:BUID)";
			SQLQuery q = db.createSQLQuery(sql);
			q.setParameter("UID", uid);
			q.setParameter("BUID", buddyId);
			q.executeUpdate();
			
			sql = "insert into UserBuddys(UID,BuddyID) values(:UID,:BUID)";
			q = db.createSQLQuery(sql);
			q.setParameter("UID", buddyId);
			q.setParameter("BUID", uid);
			q.executeUpdate();
		}
		
		return 0;
	}
	
	@Transactional
	public void CreateBuddyRequest(int fromUid,int toUid,String fromHx,String toHx)
	{
		Session db = getCurrentSession();
		String sql = "insert into BuddyRequest(FromUID,ToUID, CreateTime, Status,FromHxUser,ToHxUser) values(:FUID,:TUID,:TIME,:STAT,:FHX,:THX)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("FUID", fromUid);
		q.setParameter("TUID", toUid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("STAT", 0);
		q.setParameter("FHX", fromHx);
		q.setParameter("THX", toHx);
		q.executeUpdate();
	}
	
	@Transactional
	public int BuddyRequestReject(int BR_ID)
	{
		Session db = getCurrentSession();
		String sql = "update BuddyRequest set Status=2 where BR_ID="+BR_ID;
		db.createSQLQuery(sql).executeUpdate();
		
		return 0;
	}
	
	@Transactional
	public int BuddyRequestAllow(int BR_ID)
	{
		Session db = getCurrentSession();
		String sql = "select * from BuddyRequest where BR_ID="+BR_ID;
		Map info = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		
		int FromUID = Integer.parseInt(info.get("FromUID").toString());
		int ToUID 	= Integer.parseInt(info.get("ToUID").toString());
		String fromHx = info.get("FromHxUser").toString();
		String toHx = info.get("ToHxUser").toString();
		
		ObjectNode objNode = EasemobIMUsers.addFriendSingle(fromHx, toHx);
		if(objNode == null) return -1;
		
		sql = "select * from UserBuddys where UID="+FromUID+" and BuddyID="+ToUID;
		Object result = db.createSQLQuery(sql).uniqueResult();
		if(result == null)
		{
			sql = "insert into UserBuddys(UID,BuddyID) values(:UID,:BUID)";
			SQLQuery q = db.createSQLQuery(sql);
			q.setParameter("UID", FromUID);
			q.setParameter("BUID", ToUID);
			q.executeUpdate();
			
			sql = "insert into UserBuddys(UID,BuddyID) values(:UID,:BUID)";
			q = db.createSQLQuery(sql);
			q.setParameter("UID", ToUID);
			q.setParameter("BUID", FromUID);
			q.executeUpdate();
		}
		
		sql = "update BuddyRequest set Status=1 where BR_ID="+BR_ID;
		db.createSQLQuery(sql).executeUpdate();
		
		return 0;
	}
	
	@Transactional
	public List BuddyRequestList(int uid,int page)
	{
		Session db = getCurrentSession();
		String sql = "select b.BR_ID,a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext from UserInfos a,BuddyRequest b where b.Status=0 and a.UID=b.FromUID and b.ToUID="+uid+" order by b.CreateTime desc";
		
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(PageSize);
		q.setFirstResult((page-1)*PageSize);
		return q.list();
	}
	
	@Transactional
	public int BuddyRequestListCount(int uid)
	{
		Session db = getCurrentSession();
		String sql = "select b.BR_ID,a.UID,a.NickName,a.hxUser,a.UserPic,a.Gender,a.CreditPoint,a.UserLevel,a.Brithday,a.signtext from UserInfos a,BuddyRequest b where b.Status=0 and a.UID=b.FromUID and b.ToUID="+uid+" order by b.CreateTime desc";
		Query q = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		//q.setMaxResults(PageSize);
		//q.setFirstResult((page-1)*PageSize);
		return q.list().size();
	}
	
	@Transactional
	public int removeBuddy(int buddyId,String buddy_hxId,int uid,String hxId)
	{
		Session db = getCurrentSession();
		ObjectNode objNode = EasemobIMUsers.deleteFriendSingle(hxId, buddy_hxId);
		if(objNode == null) return -1;
		
		String sql = "delete from UserBuddys where UID="+uid+" and BuddyID="+buddyId;
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "delete from UserBuddys where UID="+buddyId+" and BuddyID="+uid;
		db.createSQLQuery(sql).executeUpdate();
		
		return 0;
	}
}
