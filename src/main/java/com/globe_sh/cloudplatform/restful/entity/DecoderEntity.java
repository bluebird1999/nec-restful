package com.globe_sh.cloudplatform.restful.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class DecoderEntity implements Serializable {

	private static final long serialVersionUID = 3558195671797896609L;

	private int id;
	private String data_code;
	private String create_time;
	private int data_block;
	private String data_name;
	private String data_description;
	private String data_type;
	private String data_kind;
	private int start_byte;
	private int start_bit;
	private int data_length;
	private float data_precision;
	private int data_deviation;
	private int data_unit;
	private String data_dictionary;	
	private int low_precede;
	
	public DecoderEntity() {
	}
	
	public DecoderEntity(int id, String dataCode, String createTime, int dataBlock,
			String dataName, String dataDescription, String dataType, String dataKind,
			int startByte, int startBit, int dataLength, float dataPrecision, 
			int dataDeviation, int dataUnit, String dataDictionary, int low_precede) {
		this.id = id;
		this.data_code = dataCode;
		this.create_time = createTime;
		this.data_block = dataBlock;
		this.data_name = dataName;
		this.data_description = dataDescription;
		this.data_type = dataType;
		this.data_kind = dataKind;
		this.start_byte = startByte;
		this.start_bit = startBit;
		this.data_length = dataLength;
		this.data_precision = dataPrecision;
		this.data_deviation = dataDeviation;
		this.data_unit = dataUnit;
		this.data_dictionary = dataDictionary;
		this.low_precede = low_precede;	
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataCode() {
		return data_code;
	}
	public void setDataCode(String dataCode) {
		this.data_code = dataCode;
	}
	public String getCreateTime() {
		return create_time;
	}
	public void setCreateTime(String createTime) {
		this.create_time = createTime;
	}
	public int getDataBlock() {
		return data_block;
	}
	public void setDataBlock(int dataBlock) {
		this.data_block = dataBlock;
	}	
	public String getDataName() {
		return data_name;
	}	
	public void setDataName(String dataName) {
		this.data_name = dataName;
	}	
	public String getDataDescription() {
		return data_description;
	}
	public void setDataDescription(String dataDescription) {
		this.data_description = dataDescription;
	}
	public String getDataType() {
		return data_type;
	}
	public void setDataType(String dataType) {
		this.data_type = dataType;
	}	
	public String getDataKind() {
		return data_kind;
	}
	public void setDataKind(String dataKind) {
		this.data_kind = dataKind;
	}
	public int getStartByte() {
		return start_byte;
	}
	public void setStartByte(int startByte) {
		this.start_byte = startByte;
	}
	public int getStartBit() {
		return start_bit;
	}
	public void setStartBit(int startBit) {
		this.start_bit = startBit;
	}	
	public int getDataLength() {
		return data_length;
	}
	public void setDataLength(int dataLength) {
		this.data_length = dataLength;
	}
	public float getDataPrecision() {
		return data_precision;
	}
	public void setDataPrecision(float dataPrecision) {
		this.data_precision = dataPrecision;
	}	
	public int getDataDeviation() {
		return data_deviation;
	}
	public void setDataDeviation(int dataDeviation) {
		this.data_deviation = dataDeviation;
	}
	public int getDataUnit() {
		return data_unit;
	}
	public void setDataUnit(int dataUnit) {
		this.data_unit = dataUnit;
	}	
	public String getDataDictionary() {
		return data_dictionary;
	}
	public void setDataDictionary(String dataDictionary) {
		this.data_dictionary = dataDictionary;
	}	
	public void setLowPrecede(int low_precede) {
		this.low_precede = low_precede;
	}	
	public int getLowPrecede() {
		return low_precede;
	}	
	public String getJsonString() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("data_code", this.data_code);
		json.put("create_time", this.create_time);
		json.put("data_block", this.data_block);
		json.put("data_name", this.data_name);
		json.put("data_description", this.data_description);
		json.put("data_type", this.data_type);
		json.put("data_kind", this.data_kind);
		json.put("start_byte", this.start_byte);
		json.put("start_bit", this.start_bit);
		json.put("data_length", this.data_length);
		json.put("data_precision", this.data_precision);
		json.put("data_deviation", this.data_deviation);
		json.put("data_unit", this.data_unit);
		json.put("data_dictionary", this.data_dictionary);
		json.put("low_precede", this.low_precede);	
		return json.toJSONString();
	}	
}
