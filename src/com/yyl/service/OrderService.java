package com.yyl.service;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyl.common.UtiltyHelper;

@Service
public class OrderService extends BaseService{

	@Transactional
	public List LoadPurchaseList() 
	{
		Session db = getCurrentSession();
		String sql = "select * from Purchase where State=1";
		List list = db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@Transactional
	public int CreateOrder(int pid,String src,int uid)
	{
		Session db = getCurrentSession();
		String code = CreateOrderNumber();
		String sql = "insert into OrderInfo(OrderNumber,UID,CreateTime,State,PID,Source) "+
				 "values(:CODE,:UID,:TIME,1,:PID,:SRC)";
		
		SQLQuery q = db.createSQLQuery(sql);
		q.setParameter("CODE", code);
		q.setParameter("UID", uid);
		q.setParameter("TIME", UtiltyHelper.GetNowDate());
		q.setParameter("PID", pid);
		q.setParameter("SRC", src);
		q.executeUpdate();
		
		sql = "select OID from OrderInfo where UID="+uid+" order by OID desc limit 1";
		int oid = Integer.parseInt(db.createSQLQuery(sql).uniqueResult().toString());
		return oid;
	}
	
	@Transactional
	public void CompleteOrder(int oid)
	{
		Session db = getCurrentSession();
		
		String sql = "select * from OrderInfo where OID="+oid;
		Map info = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		int uid = Integer.parseInt(info.get("UID").toString());
		
		int pid = Integer.parseInt(info.get("PID").toString());
		sql = "select * from Purchase where ID="+pid;
		Map pinfo = (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		int val = Integer.parseInt(pinfo.get("Val").toString());
		
		sql = "update OrderInfo set State=2 where OID="+oid;
		db.createSQLQuery(sql).executeUpdate();
		
		sql = "update UserInfos set MyCoin=MyCoin+"+val+" where UID="+uid;
		db.createSQLQuery(sql).executeUpdate();
	}
	
	@Transactional
	public Map GetPurchaseInfo(int pid)
	{
		Session db = getCurrentSession();
		String sql = "select * from Purchase where ID="+pid;
		return (Map)db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
	}
	
	private String CreateOrderNumber()
	{
		String rndString = UtiltyHelper.CreateRandomNumber();
		String date = UtiltyHelper.GetNowDate2();
		
		return date+rndString;
	}

}
