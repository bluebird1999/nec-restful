package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

public class DataEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private String sample_time;
	private int factory;
	private int line;
	private int device;
	private int data_block;
	private int station;
	private int code;
	private String value;

	public DataEntity() {
	}
	
	public DataEntity(String sampleTime, int factory, int line, int device, 
			int dataBlock, int station, int code, String value) {
		this.sample_time = sampleTime;
		this.factory = factory;
		this.line = line;
		this.device = device;
		this.data_block = dataBlock;
		this.station = station;
		this.code = code;
		this.value = value;
	}
	
	public String getSampleTime() {
		return sample_time;
	}
	public void setSampleTime(String sampleTime) {
		this.sample_time = sampleTime;
	}
	public int getFactory() {
		return factory;
	}
	public void setFactory(int factory) {
		this.factory = factory;
	}	
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getDevice() {
		return device;
	}
	public void setDevice(int device) {
		this.device = device;
	}
	public int getDataBlock() {
		return data_block;
	}
	public void setDataBlock(int dataBlock) {
		this.data_block = dataBlock;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
}
