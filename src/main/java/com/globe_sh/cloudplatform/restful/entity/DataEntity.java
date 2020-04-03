package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

public class DataEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private String sample_time;
	private String station;
	private String device;
	private String data_block;
	private String code;
	private String value;

	public DataEntity() {
	}
	
	public DataEntity(String sampleTime, String station, String device, 
			String dataBlock, String code, String value) {
		this.sample_time = sampleTime;
		this.station = station;
		this.device = device;
		this.data_block = dataBlock;
		this.code = code;
		this.value = value;
	}
	
	public String getSampleTime() {
		return sample_time;
	}
	public void setSampleTime(String sampleTime) {
		this.sample_time = sampleTime;
	}	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getDataBlock() {
		return data_block;
	}
	public void setDataBlock(String dataBlock) {
		this.data_block = dataBlock;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
}
