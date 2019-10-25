package com.yyl.service;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSettingService extends BaseService{

	@Transactional
	public void AddBuddyVerify(int uid,int verify)
	{
		Session db= getCurrentSession();
		String sql = "update UserInfos set BuddyRequest="+verify+" where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public int SignInDay(int uid)
	{
		Session db= getCurrentSession();
		String sql = "update UserInfos set CreditPoint=CreditPoint+1,SignInTime=CURDATE() where SignInTime<CURDATE() and UID="+uid;
		return db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public Map MyCode(int uid)
	{
		Session db= getCurrentSession();
		String sql = "select hxUser,NickName,Gender,UserPic from UserInfos where UID="+uid;
		return (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
	}
	
	@Transactional
	public int GetUserCoins(int uid)
	{
		Session db= getCurrentSession();
		String sql = "select MyCoin from UserInfos where UID="+uid;
		return Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
	}
}
