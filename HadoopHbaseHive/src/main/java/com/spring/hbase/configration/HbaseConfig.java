package com.spring.hbase.configration;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HbaseConfig extends Configuration{

	private Configuration conf;
	
	public HbaseConfig(){
		conf = HBaseConfiguration.create();
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

}
