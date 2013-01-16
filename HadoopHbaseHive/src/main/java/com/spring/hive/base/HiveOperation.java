package com.spring.hive.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class HiveOperation {

	ApplicationContext content = null;
	JdbcTemplate template = null;
	Map<String, Object> map = null;

	public static void main(String[] args) {
		HiveOperation hive = new HiveOperation();
		hive.find();
	}

	public HiveOperation() {
		content = new ClassPathXmlApplicationContext("spring-hive-config.xml");
		template = (JdbcTemplate) content.getBean("template");
	}

	public void find() {
		List<Map<String, Object>> list = template.queryForList("select * from hbase_hive");
		Iterator<Map<String, Object>> it = list.iterator();

		while (it.hasNext()) {

			Map<String, Object> userMap = (Map<String, Object>) it.next();
			System.out.println(userMap.get("key") + "\t" + userMap.get("value"));

		}
	}
}
