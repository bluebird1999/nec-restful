package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.entity.FactoryEntity;
import com.globe_sh.cloudplatform.restful.dao.FactoryDAO;
import com.globe_sh.cloudplatform.restful.entity.LineEntity;
import com.globe_sh.cloudplatform.restful.dao.LineDAO;
import com.globe_sh.cloudplatform.restful.entity.StationEntity;
import com.globe_sh.cloudplatform.restful.dao.StationDAO;
import com.globe_sh.cloudplatform.restful.entity.DeviceEntity;
import com.globe_sh.cloudplatform.restful.dao.DeviceDAO;
import com.globe_sh.cloudplatform.restful.entity.DataBlockEntity;
import com.globe_sh.cloudplatform.restful.entity.DecoderEntity;
import com.globe_sh.cloudplatform.restful.dao.DataBlockDAO;

import com.globe_sh.cloudplatform.common.cache.JedisOperater;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.globe_sh.cloudplatform.common.cache.JedisOperater;
import com.globe_sh.cloudplatform.common.util.StaticMethod;
import com.globe_sh.cloudplatform.common.util.StaticOperater;
import com.globe_sh.cloudplatform.common.util.StaticVariable;

import org.apache.logging.log4j.*;

import com.globe_sh.cloudplatform.restful.utils.ResponseUtil;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "/api")
public class DeviceController {
	@Autowired
	private DeviceDAO deviceDao;
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(DeviceController.class);
	  
//************************Device************************	
		@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getDeviceAll() {
			try {
				JSONArray res = new JSONArray();
				List<DeviceEntity> rs = deviceDao.getDeviceAll();
				
				for( DeviceEntity obj: rs)
				{
					JSONObject jo = new JSONObject();
					jo.put("id",obj.getId());
					jo.put("code",obj.getDeviceCode());
					jo.put("create",obj.getCreateTime());
					jo.put("line",obj.getLineId());
					jo.put("name",obj.getDeviceName());		
					jo.put("description",obj.getDeviceDescription());
					jo.put("device_maker",obj.getDeviceMaker());	
					jo.put("device_id",obj.getDeviceId());	
					jo.put("device_version",obj.getDeviceVersion());	
					jo.put("com_type",obj.getComType());	
					jo.put("com_address",obj.getComAddress());	
					jo.put("com_port",obj.getComPort());	
					jo.put("com_protocol",obj.getComProtocol());	
					jo.put("update_rate",obj.getUpdateRate());	
					jo.put("reconnection_time",obj.getReconnectionTime());	
					jo.put("paramter1",obj.getParameter1());	
					jo.put("paramter2",obj.getParameter2());	
					jo.put("paramter3",obj.getParameter3());	
					jo.put("paramter4",obj.getParameter4());	
					jo.put("paramter5",obj.getParameter5());	
					jo.put("paramter6",obj.getParameter6());	
					jo.put("paramter7",obj.getParameter7());	
					jo.put("paramter8",obj.getParameter8());	
					jo.put("paramter9",obj.getParameter9());	
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}

	    }
		@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getDeviceByCode(@PathVariable("id") int id) {
			try {
				JSONObject res = new JSONObject();
				DeviceEntity rs = deviceDao.getDevice(id);
				res.put("id",rs.getId());
				res.put("code",rs.getDeviceCode());
				res.put("create",rs.getCreateTime());
				res.put("line",rs.getLineId());
				res.put("name",rs.getDeviceName());		
				res.put("description",rs.getDeviceDescription());
				res.put("device_maker",rs.getDeviceMaker());	
				res.put("device_id",rs.getDeviceId());	
				res.put("device_version",rs.getDeviceVersion());	
				res.put("com_type",rs.getComType());	
				res.put("com_address",rs.getComAddress());	
				res.put("com_port",rs.getComPort());	
				res.put("com_protocol",rs.getComProtocol());	
				res.put("update_rate",rs.getUpdateRate());	
				res.put("reconnection_time",rs.getReconnectionTime());	
				res.put("paramter1",rs.getParameter1());	
				res.put("paramter2",rs.getParameter2());	
				res.put("paramter3",rs.getParameter3());	
				res.put("paramter4",rs.getParameter4());	
				res.put("paramter5",rs.getParameter5());	
				res.put("paramter6",rs.getParameter6());	
				res.put("paramter7",rs.getParameter7());	
				res.put("paramter8",rs.getParameter8());	
				res.put("paramter9",rs.getParameter9());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		@RequestMapping(value = "/devices", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createDevice(@RequestBody JSONArray jsonParam) {
			try {
				JSONObject device = null;
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				//iterate
				for(int i = 0; i < jsonParam.size(); i++) {
					device = jsonParam.getJSONObject(i);
					if(device!=null) {
						DeviceEntity st = new DeviceEntity();
						//required parameters
						st.setCreateTime(dt);
						if(device.containsKey("line"))
							if( device.getString("line").length()>0)
								st.setLineId(device.getIntValue("line"));
							else
								continue;
						else
							continue;
						//other fileds		
						if(device.containsKey("code"))
							st.setDeviceCode(device.getString("code"));						
						if(device.containsKey("name"))
							st.setDeviceName(device.getString("name"));
						if(device.containsKey("description"))
							st.setDeviceDescription(device.getString("description"));
						if(device.containsKey("device_maker"))
							st.setDeviceMaker(device.getString("device_maker"));
						if(device.containsKey("device_id"))
							st.setDeviceId(device.getString("device_id"));
						if(device.containsKey("device_version"))
							st.setDeviceVersion(device.getString("device_version"));
						if(device.containsKey("com_type"))
							st.setComType(device.getString("com_type"));						
						if(device.containsKey("com_address"))
							st.setComAddress(device.getString("com_address"));
						if(device.containsKey("com_port"))
							st.setComPort(device.getIntValue("com_port"));	
						if(device.containsKey("com_protocol"))
							st.setComProtocol(device.getString("com_protocol"));						
						if(device.containsKey("update_rate"))
							st.setUpdateRate(device.getIntValue("update_rate"));
						if(device.containsKey("reconnection_time"))
							st.setReconnectionTime(device.getIntValue("reconnection_time"));						
						if(device.containsKey("parameter1"))
							st.setParameter1(device.getString("parameter1"));
						if(device.containsKey("parameter2"))
							st.setParameter2(device.getString("parameter2"));
						if(device.containsKey("parameter3"))
							st.setParameter3(device.getString("parameter3"));
						if(device.containsKey("parameter4"))
							st.setParameter4(device.getString("parameter4"));
						if(device.containsKey("parameter5"))
							st.setParameter5(device.getString("parameter5"));
						if(device.containsKey("parameter6"))
							st.setParameter6(device.getString("parameter6"));
						if(device.containsKey("parameter7"))
							st.setParameter7(device.getIntValue("parameter7"));
						if(device.containsKey("parameter8"))
							st.setParameter8(device.getIntValue("parameter8"));
						if(device.containsKey("parameter9"))
							st.setParameter9(device.getIntValue("parameter9"));					
						int rs = deviceDao.insertDevice(st);
						res.put("result",rs);
						res.put("id", st.getId());
					}
				}
				//return
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				res.put("id", -1);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }		
		
	
		@RequestMapping(value = "/devices/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject createDevice(@PathVariable("id") int id ) {
			try {
				JSONObject res = new JSONObject();
				int rs = deviceDao.deleteDevice(id);
				res.put("result",rs);	
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		@RequestMapping(value = "/devices/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateDevice(
	    		@PathVariable("id") int id,
	    		@RequestBody JSONObject jsonParam
	    		) {
			try {
				DeviceEntity st = deviceDao.getDevice(id);
				
				if(jsonParam.containsKey("code"))
					st.setDeviceCode(jsonParam.getString("code"));
				if(jsonParam.containsKey("line"))
					st.setLineId(jsonParam.getIntValue("line"));				
				if(jsonParam.containsKey("name"))
					st.setDeviceName(jsonParam.getString("name"));
				if(jsonParam.containsKey("description"))
					st.setDeviceDescription(jsonParam.getString("description"));
				if(jsonParam.containsKey("device_maker"))
					st.setDeviceMaker(jsonParam.getString("device_maker"));
				if(jsonParam.containsKey("device_id"))
					st.setDeviceId(jsonParam.getString("device_id"));
				if(jsonParam.containsKey("device_version"))
					st.setDeviceVersion(jsonParam.getString("device_version"));
				if(jsonParam.containsKey("com_type"))
					st.setComType(jsonParam.getString("com_type"));						
				if(jsonParam.containsKey("com_address"))
					st.setComAddress(jsonParam.getString("com_address"));
				if(jsonParam.containsKey("com_port"))
					st.setComPort(jsonParam.getIntValue("com_port"));	
				if(jsonParam.containsKey("com_protocol"))
					st.setComProtocol(jsonParam.getString("com_protocol"));						
				if(jsonParam.containsKey("update_rate"))
					st.setUpdateRate(jsonParam.getIntValue("update_rate"));
				if(jsonParam.containsKey("reconnection_time"))
					st.setReconnectionTime(jsonParam.getIntValue("reconnection_time"));						
				if(jsonParam.containsKey("parameter1"))
					st.setParameter1(jsonParam.getString("parameter1"));
				if(jsonParam.containsKey("parameter2"))
					st.setParameter2(jsonParam.getString("parameter2"));
				if(jsonParam.containsKey("parameter3"))
					st.setParameter3(jsonParam.getString("parameter3"));
				if(jsonParam.containsKey("parameter4"))
					st.setParameter4(jsonParam.getString("parameter4"));
				if(jsonParam.containsKey("parameter5"))
					st.setParameter5(jsonParam.getString("parameter5"));
				if(jsonParam.containsKey("parameter6"))
					st.setParameter6(jsonParam.getString("parameter6"));
				if(jsonParam.containsKey("parameter7"))
					st.setParameter7(jsonParam.getIntValue("parameter7"));
				if(jsonParam.containsKey("parameter8"))
					st.setParameter8(jsonParam.getIntValue("parameter8"));
				if(jsonParam.containsKey("parameter9"))
					st.setParameter9(jsonParam.getIntValue("parameter9"));	
				
				JSONObject res = new JSONObject();
				int rs = deviceDao.updateDevice(id,st);
				res.put("result",rs);	
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }	
}
