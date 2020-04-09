package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class DeviceEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String device_code;
	private String create_time;
	private String station_code;
	private String device_name;
	private String device_description;

	public DeviceEntity() {
	}
	
	public DeviceEntity(int id, String deviceCode, String createTime, String stationCode, 
			String deviceName, String deviceDescription) {
		this.id = id;
		this.device_code = deviceCode;
		this.create_time = createTime;
		this.station_code = stationCode;
		this.device_name = deviceName;
		this.device_description = deviceDescription;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceCode() {
		return device_code;
	}
	public void setDeviceCode(String deviceCode) {
		this.device_code = deviceCode;
	}
	public String getCreateTime() {
		return create_time;
	}
	public void setCreateTime(String createTime) {
		this.create_time = createTime;
	}
	public String getStationCode() {
		return station_code;
	}
	public void setStationCode(String stationCode) {
		this.station_code = stationCode;
	}	
	public String getDeviceName() {
		return device_name;
	}
	public void setDeviceName(String deviceName) {
		this.device_name = deviceName;
	}
	public String getDeviceDescription() {
		return device_description;
	}
	public void setDeviceDescription(String deviceDescription) {
		this.device_description = deviceDescription;
	}
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("device_code", this.device_code);
		json.put("create_time", this.create_time);
		json.put("station_code", this.station_code);
		json.put("device_name", this.device_name);
		json.put("device_description", this.device_description);
		return json.toJSONString();
	}	
}
