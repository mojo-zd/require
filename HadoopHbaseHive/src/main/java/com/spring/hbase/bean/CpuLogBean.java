package com.spring.hbase.bean;

public class CpuLogBean {

	private String cpuType;
	private int coreCount;
	private double percent;
	
	public String getCpuType() {
		return cpuType;
	}
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}
	public int getCoreCount() {
		return coreCount;
	}
	public void setCoreCount(int coreCount) {
		this.coreCount = coreCount;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
}
