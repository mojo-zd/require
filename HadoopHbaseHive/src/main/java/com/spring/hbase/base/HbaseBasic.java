package com.spring.hbase.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import com.spring.hbase.bean.CpuLogBean;
import com.spring.hbase.daoimpl.TableCallbackImpl;

public class HbaseBasic {

	public static void main(String[] args) {
		
		ApplicationContext  content = new ClassPathXmlApplicationContext("spring-hive-config.xml");
		HbaseTemplate htemplate = (HbaseTemplate) content.getBean("htemplate");
		
		CpuLogBean cpuBean = new CpuLogBean();
		cpuBean.setCoreCount(2);
		cpuBean.setCpuType("intel");
		cpuBean.setPercent(80.0);
		
		new HbaseBasic().insertLog(cpuBean,htemplate);
	}
	
	/**
	 * 插入日志
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean insertLog(CpuLogBean cpuBean,HbaseTemplate htemplate){
		
		try {
			htemplate.execute("test", new TableCallbackImpl(cpuBean));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
}
