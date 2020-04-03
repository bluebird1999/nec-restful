package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

public class DataBlockEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String data_block_code;
	private String create_time;
	private String device_code;
	private String data_block_name;
	private String data_block_description;

	public DataBlockEntity() {
	}
	
	public DataBlockEntity(int id, String dataBlockCode, String createTime, String deviceCode, 
			String dataBlockName, String dataBlockDescription) {
		this.id = id;
		this.data_block_code = dataBlockCode;
		this.create_time = createTime;
		this.device_code = deviceCode;
		this.data_block_name = dataBlockName;
		this.data_block_description = dataBlockDescription;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataBlockCode() {
		return data_block_code;
	}
	public void setDataBlockCode(String dataBlockCode) {
		this.data_block_code = dataBlockCode;
	}
	public String getCreateTime() {
		return create_time;
	}
	public void setCreateTime(String createTime) {
		this.create_time = createTime;
	}
	public String getDeviceCode() {
		return device_code;
	}
	public void setDeviceCode(String deviceCode) {
		this.device_code = deviceCode;
	}	
	public String getDataBlockName() {
		return data_block_name;
	}
	public void setDataBlockName(String dataBlockName) {
		this.data_block_name = dataBlockName;
	}
	public String getDataBlockDescription() {
		return data_block_description;
	}
	public void setDataBlockDescription(String dataBlockDescription) {
		this.data_block_description = dataBlockDescription;
	}
}
