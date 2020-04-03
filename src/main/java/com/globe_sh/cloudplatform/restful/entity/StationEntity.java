package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

public class StationEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String station_code;
	private String create_time;
	private String factory_code;
	private String station_name;
	private String station_description;

	public StationEntity() {
	}
	
	public StationEntity(int id, String stationCode, String createTime, String factoryCode,
			String stationName, String stationDescription) {
		this.id = id;
		this.station_code = stationCode;
		this.create_time = createTime;
		this.factory_code = factoryCode;
		this.station_name = stationName;
		this.station_description = stationDescription;
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
	public String getFactoryCode() {
		return factory_code;
	}
	public void setFactoryCode(String factoryCode) {
		this.factory_code = factoryCode;
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
}
