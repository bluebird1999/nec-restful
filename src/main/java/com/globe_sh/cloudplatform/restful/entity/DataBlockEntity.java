package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class DataBlockEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String data_block_code;
	private String create_time;
	private int device_id;
	private String data_block_name;
	private String data_block_description;
	private int	data_block_length;

	public DataBlockEntity() {
	}
	
	public DataBlockEntity(int id, String dataBlockCode, String createTime, int deviceId, 
			String dataBlockName, String dataBlockDescription,int dataBlockLength) {
		this.id = id;
		this.data_block_code = dataBlockCode;
		this.create_time = createTime;
		this.device_id = deviceId;
		this.data_block_name = dataBlockName;
		this.data_block_description = dataBlockDescription;
		this.data_block_length = dataBlockLength;
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
	public int getDeviceId() {
		return device_id;
	}
	public void setDeviceId(int deviceId) {
		this.device_id = deviceId;
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
	public int getDataBlockLength() {
		return data_block_length;
	}
	public void setDataBlockLength(int dataBlockLength) {
		this.data_block_length = dataBlockLength;
	}	
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("data_block_code", this.data_block_code);
		json.put("create_time", this.create_time);
		json.put("device_id", this.device_id);
		json.put("data_block_name", this.data_block_name);
		json.put("data_block_description", this.data_block_description);
		json.put("data_block_length", this.data_block_length);
		return json.toJSONString();
	}		
}
