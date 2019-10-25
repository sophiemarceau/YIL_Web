package com.yyl.service;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyl.modal.AdminUser;

@Service
public class AdminService extends BaseService{

	@Transactional
	public AdminUser UserLogonCheck(String user,String password)
	{
		Session db = getCurrentSession();
		String sql = "select * from AdminUsers where UserName='"+user+"'";
		Object resutl = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		if(resutl == null) return null;
		
		Map userInfo = (Map)resutl;
		String Password = userInfo.get("Password").toString();
		if(!Password.equals(password)) return null;
		
		AdminUser adminuser = new AdminUser();
		adminuser.UID = Integer.parseInt(userInfo.get("UID").toString());
		adminuser.UserName = user;
		adminuser.nickName = userInfo.get("NickName").toString();
		adminuser.UserLevel = Integer.parseInt(userInfo.get("UserLevel").toString());
		
		return adminuser;
	}
	
	@Transactional
	public List LoadAdminList()
	{
		Session db = getCurrentSession();
		String sql = "select * from AdminUsers";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Transactional
	public String CreateAdminUser(String userName,String nick,String password,int level)
	{
		Session db = getCurrentSession();
		String sql = "select * from AdminUsers where UserName='"+userName+"'";
		Object resultObject = db.createSQLQuery(sql).uniqueResult();
		if(resultObject != null)
		{
			return "已存在这个用户";
		}
		
		sql = "insert into AdminUsers(UserName,Password,NickName,UserLevel)"+
		" values(:UN,:PASS,:NN,:UL)";
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("UN", userName);
		q.setParameter("PASS", password);
		q.setParameter("NN", nick);
		q.setParameter("UL", level);
		q.executeUpdate();
		
		return "创建账户成功";
	}
	
	@Transactional
	public void DeleteAdminUser(int uid)
	{
		Session db = getCurrentSession();
		String sql = "delete from AdminUsers where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public int ChangeUserPass(int uid,String oldPass,String newPass)
	{
		Session db = getCurrentSession();
		String sql = "select * from AdminUsers where Password='"+oldPass+"' and UID="+uid;
		Object resultObject = db.createSQLQuery(sql).uniqueResult();
		if(resultObject == null)
		{
			return -1;
		}
		
		sql = "update AdminUsers set Password='"+newPass+"' where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
		
		return 0;
	}
}
