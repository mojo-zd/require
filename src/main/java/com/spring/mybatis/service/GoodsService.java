package com.spring.mybatis.service;

import com.spring.mybatis.bean.Goods;
import com.spring.mybatis.dao.GoodsDao;

public class GoodsService {

	private GoodsDao goodsDao;
	
	/**
	 * 新增加一个用户
	 * @param goods
	 * return 
	 * */
	public void InsertGoods(Goods goods){
		goodsDao.insertGoods(goods);
	}
	
	public GoodsDao getGoodsDao(){
		return goodsDao;
	}

	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
}
