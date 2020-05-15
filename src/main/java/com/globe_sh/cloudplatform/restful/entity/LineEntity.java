package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class LineEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String line_code;
	private String create_time;
	private int factory_id;
	private String line_name;
	private String line_description;

	public LineEntity() {
	}
	
	public LineEntity(int id, String lineCode, String createTime, int factoryId, 
			String lineName, String lineDescription) {
		this.id = id;
		this.line_code = lineCode;
		this.create_time = createTime;
		this.factory_id = factoryId;
		this.line_name = lineName;
		this.line_description = lineDescription;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLineCode() {
		return line_code;
	}
	public void setLineCode(String lineCode) {
		this.line_code = lineCode;
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
	public String getLineName() {
		return line_name;
	}
	public void setLineName(String lineName) {
		this.line_name = lineName;
	}
	public String getLineDescription() {
		return line_description;
	}
	public void setLineDescription(String lineDescription) {
		this.line_description = lineDescription;
	}
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("line_code", this.line_code);
		json.put("create_time", this.create_time);
		json.put("factory_id", this.factory_id);
		json.put("line_name", this.line_name);
		json.put("line_description", this.line_description);
		return json.toJSONString();
	}	
}
