package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.globe_sh.cloudplatform.common.util.StaticMethod;

public class StationEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String station_code;
	private String create_time;
	private int factory_id;
	private int line_id;
	private int device_id;
	private int device_number;
	private String station_name;
	private String station_description;
	private int station_status;
	
	public StationEntity() {
	}
	
	public StationEntity(int id, String stationCode, String createTime, int factoryId,
			int lineId, int deviceId, int deviceNumber,
			String stationName, String stationDescription, int stationStatus) {
		this.id = id;
		this.station_code = stationCode;
		this.create_time = createTime;
		this.factory_id = factoryId;
		this.line_id = lineId;
		this.device_id = deviceId;
		this.device_number = deviceNumber;
		this.station_name = stationName;
		this.station_description = stationDescription;
		this.station_status = stationStatus;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStationCode() {
		return station_code;
	}
	public void setStationCode(String stationCode) {
		this.station_code = stationCode;
	}
	public String getCreateTime() {
		return create_time;
	}
	public void setCreateTime(String createTime) {
		this.create_time = createTime;
	}
	public int getFactoryId() {
		return factory_id;
	}
	public void setFactoryId(int factoryId) {
		this.factory_id = factoryId;
	}	
	public int getLineId() {
		return line_id;
	}
	public void setLineId(int lineId) {
		this.line_id = lineId;
	}
	public int getDeviceId() {
		return device_id;
	}
	public void setDeviceId(int deviceId) {
		this.device_id = deviceId;
	}
	public int getDeviceNumber() {
		return device_number;
	}
	public void setDeviceNumber(int deviceNumber) {
		this.device_number = deviceNumber;
	}	
	public String getStationName() {
		return station_name;
	}
	public void setStationName(String stationName) {
		this.station_name = stationName;
	}
	public String getStationDescription() {
		return station_description;
	}
	public void setStationDescription(String stationDescription) {
		this.station_description = stationDescription;
	}
	public int getStationStatus() {
		return station_status;
	}
	public void setStationStatus(int station_status) {
		this.station_status = station_status;
	}
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("station_code", this.station_code);
		json.put("create_time", this.create_time);
		json.put("factory_id", this.factory_id);
		json.put("line_id", this.line_id);
		json.put("device_id", this.device_id);
		json.put("device_number", this.device_number);
		json.put("station_name", this.station_name);
		json.put("station_description", this.station_description);
		json.put("station_status", this.station_status);
		return json.toJSONString();
	}	
}
