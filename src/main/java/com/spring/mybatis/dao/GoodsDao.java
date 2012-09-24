package com.spring.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.spring.mybatis.bean.Goods;

public class GoodsDao {

	/**
	 * 该类主要用于对 SqlSession 对象给予出处理
	 * @param session
	 * */
	private  SqlSessionFactory  sqlsession;
    private SqlSession session;
    
	public GoodsDao(){}

	/**
	 * 获取 产生 SqlSession 的工厂 
	 * */
	public  SqlSessionFactory getSqlsession() {
		return sqlsession;
	}

	public void setSqlsession(SqlSessionFactory sqlsession) {
		this.sqlsession = sqlsession;
	}
	
	/**
	 * 由 SqlSession 获取 Session 对象
	 * */
	
	public SqlSession getSqlSession(){
		if(sqlsession!=null){
			session = sqlsession.openSession();
		}else{
			System.out.println("由于SqlSessionFactory 没有初始化,不能获取 SqlSession");
			return null;
		}
		return session;
	}
	
	/**
	 * 向数据库中插入数据
	 * */
	
	public void insertGoods(Goods goods){
		session.insert("com.spring.mybatis.GoodsMapper.insertGoods",goods);
	}
	
	/**
	 * 根据id号 从数据库选择出 指定的 Goods
	 * */
	public Goods getGoodsByCateId(){
		Goods goods = (Goods)session.selectOne("com.spring.mybatis.GoodsMapper.selectByCateId");
		return goods;
	}
}
