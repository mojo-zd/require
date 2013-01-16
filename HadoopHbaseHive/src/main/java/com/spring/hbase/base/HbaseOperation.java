package com.spring.hbase.base;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.hbase.bean.LogsBean;


public class HbaseOperation {

	private ApplicationContext content = null;
	private HBaseAdmin hbaseAdmin = null;
	private Configuration conf = null;
	
	public static void main(String[] args) throws Exception{
		
		HbaseOperation operation = new HbaseOperation();
		LogsBean logsBean = new LogsBean();
		logsBean.setCount(2);
		logsBean.setTime("2012");
		logsBean.setType("intel");
		String []atrs = operation.getColumnFamilys(logsBean);
		operation.createTable("logs", atrs);
		operation.writeRow("logs", atrs, logsBean);
		//operation.dropTable("logs");
		//operation.deleteRow("logs", "rows1");
		//operation.selectRow("logs", "rows1");
		//operation.scaner("logs");
	}
	
	/**
	 * 获取创建表所需要的column family
	 * @param logs
	 * @return
	 */
	public String[] getColumnFamilys(LogsBean logs){
		
		Field []f = logs.getClass().getDeclaredFields();
		String []cfs = new String[f.length];
		
		int i = 0;
		for (Field field : f) {
			String attrName = field.toString().substring(field.toString().lastIndexOf(".")+1);
			cfs[i] = attrName;
			i++;
		}
		
		return cfs;
	}
	
	
	public HbaseOperation(){
		content = new ClassPathXmlApplicationContext("spring-hive-config.xml");
		hbaseAdmin = (HBaseAdmin) content.getBean("hBaseAdmin");
		conf = hbaseAdmin.getConfiguration();
	}
	
	/**
	 * "获取属性"方法的名称
	 * @param Attrbute
	 * @return
	 */
	public  String getAttrbute(String Attrbute){
		String method = "get" + Attrbute.substring(0,1).toUpperCase() + Attrbute.substring(1);
		return method;
	}

	/**
	 * 创建表
	 * @param tablename table name
	 * @param cfs columns family names
	 */
	public void createTable(String tablename, String[] cfs) {
		
		try {
			if (hbaseAdmin.tableExists(tablename)) {
				System.out.println("表已经存在！");
			} else {
				HTableDescriptor tableDesc = new HTableDescriptor(tablename);
				for (int i = 0; i < cfs.length; i++) {
					tableDesc.addFamily(new HColumnDescriptor(cfs[i]));
				}
				hbaseAdmin.createTable(tableDesc);
				System.out.println("表创建成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 插入一行记录
	 * @param tablename
	 * @param cfs
	 */
	public void writeRow(String tablename, String[] cfs,LogsBean logs) {
		
		HTable table = null;
		try {
			table = new HTable(conf, tablename);
			Put put = new Put(Bytes.toBytes("rows2"));
			
			for (int j = 0; j < cfs.length; j++) {
				
				if(cfs[j].equals("count")){
					put.add(Bytes.toBytes(cfs[j]),
							Bytes.toBytes(String.valueOf(1)),
							Bytes.toBytes(""+logs.getCount()));
				}else if(cfs[j].equals("time")){
					put.add(Bytes.toBytes(cfs[j]),
							Bytes.toBytes(String.valueOf(1)),
							Bytes.toBytes(logs.getTime()));
				}else if(cfs[j].equals("type")){
					put.add(Bytes.toBytes(cfs[j]),
							Bytes.toBytes(String.valueOf(1)),
							Bytes.toBytes(logs.getType()));
				}
				
				table.put(put);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				table.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	    
	     
	}
	
	/**
	 * 删除表
	 * @param tablename
	 * @throws Exception
	 */
	public void dropTable(String tablename) throws Exception{
	    try {
	    	
	    	hbaseAdmin.disableTable(tablename);
	    	hbaseAdmin.deleteTable(tablename);
	        System.out.println("表删除成功！");
	        
	    } catch (MasterNotRunningException e) {
	        e.printStackTrace();
	    } catch (ZooKeeperConnectionException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * 删除一行记录
	 * @param tablename
	 * @param rowkey
	 * @throws IOException
	 */
	public void deleteRow(String tablename, String rowkey){
		HTable table = null;
		try {
			table = new HTable(conf, tablename);
			List<Delete> list = new ArrayList<Delete>();
			Delete d1 = new Delete(rowkey.getBytes());
			System.out.println(rowkey);
			list.add(d1);
			table.delete(list);
			System.out.println("删除行成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查找一行记录
	 * @param tablename
	 * @param rowkey
	 */
	public  void selectRow(String tablename, String rowKey) {
		HTable table = null;
		try {
			table = new HTable(conf, tablename);
			Get g = new Get(rowKey.getBytes());
			Result rs = table.get(g);
			for (KeyValue kv : rs.raw()) {
				System.out.print(new String(kv.getRow()) + "  ");
				System.out.print(new String(kv.getFamily()) + ":");
				System.out.print(new String(kv.getQualifier()) + "  ");
				System.out.print(kv.getTimestamp() + "  ");
				System.out.println(new String(kv.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				table.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 查询表中所有行
	 * @param tablename
	 */
	public void scaner(String tablename) {
		 
		HTable table = null;
		try {
			table = new HTable(conf, tablename);
			Scan s = new Scan();
			ResultScanner rs = table.getScanner(s);
			for (Result r : rs) {
				KeyValue[] kv = r.raw();
				for (int i = 0; i < kv.length; i++) {
					System.out.print(new String(kv[i].getRow()) + "  ");
					System.out.print(new String(kv[i].getFamily()) + ":");
					System.out.print(new String(kv[i].getQualifier()) + "  ");
					System.out.print(kv[i].getTimestamp() + "  ");
					System.out.println(new String(kv[i].getValue()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
}
