package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class DeviceEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String device_code;
	private String create_time;
	private int line_id;	
	private String device_name;
	private String device_description;
	private String device_maker;
	private String device_id;
	private String device_version;	
	private String com_type;
	private String com_address;
	private int com_port;
	private String com_protocol;
	private int update_rate;
	private int reconnection_time;
	private String parameter1;
	private String parameter2;
	private String parameter3;	
	private String parameter4;
	private String parameter5;
	private String parameter6;	
	private int parameter7;
	private int parameter8;
	private int parameter9;	
	
	public DeviceEntity() {
	}
	
	public DeviceEntity(int id, String deviceCode, String createTime, int lineId, 
			String deviceName, String deviceDescription,
			String deviceMaker, String deviceId,String deviceVersion, String comType,
			String comAddress, int comPort,String comProtocol, int updateRate,
			int reconnectionTime, String Parameter1, String Parameter2,
			String Parameter3, String Parameter4,String Parameter5, String Parameter6,
			int Parameter7, int Parameter8,int Parameter9		
			) {
		this.id = id;
		this.device_code = deviceCode;
		this.create_time = createTime;
		this.line_id = lineId;
		this.device_name = deviceName;
		this.device_description = deviceDescription;
		this.device_maker = deviceMaker;
		this.device_id = deviceId;
		this.device_version = deviceVersion;
		this.com_type = comType;
		this.com_address = comAddress;
		this.com_port = comPort;
		this.com_protocol = comProtocol;
		this.update_rate = updateRate;
		this.reconnection_time = reconnectionTime;
		this.parameter1 = Parameter1;
		this.parameter2 = Parameter2;
		this.parameter3 = Parameter3;
		this.parameter4 = Parameter4;
		this.parameter5 = Parameter5;
		this.parameter6 = Parameter6;
		this.parameter7 = Parameter7;
		this.parameter8 = Parameter8;
		this.parameter9 = Parameter9;		
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
	public int getLineId() {
		return line_id;
	}
	public void setLineId(int lineId) {
		this.line_id = lineId;
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
	
	public String getDeviceMaker() {
		return device_maker;
	}
	public void setDeviceMaker(String deviceMaker) {
		this.device_maker = deviceMaker;
	}
	public String getDeviceId() {
		return device_id;
	}
	public void setDeviceId(String deviceId) {
		this.device_id = deviceId;
	}
	public String getDeviceVersion() {
		return device_version;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.device_version = deviceVersion;
	}
	public String getComType() {
		return com_type;
	}
	public void setComType(String comType) {
		this.com_type = comType;
	}
	public String getComAddress() {
		return com_address;
	}
	public void setComAddress(String comAddress) {
		this.com_address = comAddress;
	}
	public int getComPort() {
		return com_port;
	}
	public void setComPort(int comPort) {
		this.com_port = comPort;
	}
	public String getComProtocol() {
		return com_protocol;
	}
	public void setComProtocol(String comProtocol) {
		this.com_protocol = comProtocol;
	}
	public int getUpdateRate() {
		return update_rate;
	}
	public void setUpdateRate(int updateRate) {
		this.update_rate = updateRate;
	}
	public int getReconnectionTime() {
		return reconnection_time;
	}
	public void setReconnectionTime(int reconnectionTime) {
		this.reconnection_time = reconnectionTime;
	}	
	public String getParameter1() {
		return parameter1;
	}
	public void setParameter1(String Parameter1) {
		this.parameter1 = Parameter1;
	}
	public String getParameter2() {
		return parameter2;
	}
	public void setParameter2(String Parameter2) {
		this.parameter2 = Parameter2;
	}
	public String getParameter3() {
		return parameter3;
	}
	public void setParameter3(String Parameter3) {
		this.parameter3 = Parameter3;
	}
	public String getParameter4() {
		return parameter4;
	}
	public void setParameter4(String Parameter4) {
		this.parameter4 = Parameter4;
	}
	public String getParameter5() {
		return parameter5;
	}
	public void setParameter5(String Parameter5) {
		this.parameter5 = Parameter5;
	}
	public String getParameter6() {
		return parameter6;
	}
	public void setParameter6(String Parameter6) {
		this.parameter6 = Parameter6;
	}
	public int getParameter7() {
		return parameter7;
	}	
	public void setParameter7(int Parameter7) {
		this.parameter7 = Parameter7;
	}
	public int getParameter8() {
		return parameter8;
	}
	public void setParameter8(int Parameter8) {
		this.parameter8 = Parameter8;
	}
	public int getParameter9() {
		return parameter9;
	}
	public void setParameter9(int Parameter9) {
		this.parameter9 = Parameter9;
	}	
	
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("device_code", this.device_code);
		json.put("create_time", this.create_time);
		json.put("line_id", this.line_id);
		json.put("device_name", this.device_name);
		json.put("device_description", this.device_description);
		json.put("device_maker", this.device_maker);
		json.put("device_id", this.device_id);
		json.put("device_version", this.device_version);
		json.put("com_type", this.com_type);
		json.put("com_address", this.com_address);
		json.put("com_port", this.com_port);
		json.put("com_protocol", this.com_protocol);
		json.put("update_rate", this.update_rate);
		json.put("reconnection_time", this.reconnection_time);
		json.put("parameter1", this.parameter1);
		json.put("parameter2", this.parameter2);
		json.put("parameter3", this.parameter3);
		json.put("parameter4", this.parameter4);
		json.put("parameter5", this.parameter5);
		json.put("parameter6", this.parameter6);
		json.put("parameter7", this.parameter7);
		json.put("parameter8", this.parameter8);
		json.put("parameter9", this.parameter9);
		return json.toJSONString();
	}	
}
