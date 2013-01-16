package com.spring.hbase.daoimpl;

import java.lang.reflect.Field;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.TableCallback;

import com.spring.hbase.bean.CpuLogBean;

@SuppressWarnings("rawtypes")
public class TableCallbackImpl implements TableCallback{

	private CpuLogBean cpuLogBean;
	
	public TableCallbackImpl(CpuLogBean cpuLogBean ){
		this.cpuLogBean = cpuLogBean;
	}
	
	public Object doInTable(HTable table) throws Throwable {
		Put p = new Put(Bytes.toBytes("row1"));
		
		Field[]atrs = cpuLogBean.getClass().getDeclaredFields();
		
		for(Field f : atrs){
			String name = f.toString().substring(f.toString().lastIndexOf(".")+1);
			p.add(Bytes.toBytes(name), null, Bytes.toBytes(cpuLogBean.getCpuType()));
			table.put(p);
		}
	    return null;
	}

}
