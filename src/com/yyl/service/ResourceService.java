package com.yyl.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceService extends BaseService{

	@Transactional
	public List LoadAresList()
	{
		Session db = getCurrentSession();
		String sql = "select * from AreaTable";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Transactional
	public List LoadSalaryList()
	{
		Session db = getCurrentSession();
		String sql = "select * from SalaryTable";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	@Transactional
	public List LoadJobList()
	{
		Session db = getCurrentSession();
		String sql = "select * from JobTable";
		return db.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
