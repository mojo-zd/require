package com.spring.mybatis.test;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.spring.mybatis.service.GoodsService;

public class TestGoodsService{

	Logger logger = Logger.getLogger("TestAccountService");
	
	GoodsService  goodsService = null;
	
	
	public static void init () throws Exception{
		ApplicationContext  act = new FileSystemXmlApplicationContext("classpath:spring.xml");
	}
	
	public static void main(String[] args) throws Exception{
		init();
	}
}
