package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.entity.FactoryEntity;
import com.globe_sh.cloudplatform.restful.dao.FactoryDAO;
import com.globe_sh.cloudplatform.restful.entity.StationEntity;
import com.globe_sh.cloudplatform.restful.dao.StationDAO;
import com.globe_sh.cloudplatform.restful.entity.DeviceEntity;
import com.globe_sh.cloudplatform.restful.dao.DeviceDAO;
import com.globe_sh.cloudplatform.restful.entity.DataBlockEntity;
import com.globe_sh.cloudplatform.restful.dao.DataBlockDAO;

import java.util.List;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping(value = "/api")
public class RolesController {

	@Autowired
	private FactoryDAO factoryDao;
	@Autowired
	private StationDAO stationDao;
	@Autowired
	private DeviceDAO deviceDao;
	@Autowired
	private DataBlockDAO dataBlockDao;	
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RolesController.class);
	  
//************************Factory************************	
	@RequestMapping(value = "/factories", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getFactoryAll() {
		try {
			JSONArray res = new JSONArray();
			List<FactoryEntity> rs = factoryDao.getFactoryAll();
			
			for( FactoryEntity obj: rs)
			{
				JSONObject jo = new JSONObject();
				jo.put("id",obj.getId());
				jo.put("code",obj.getFactoryCode());
				jo.put("create",obj.getCreateTime());
				jo.put("name",obj.getFactoryName());		
				jo.put("description",obj.getFactoryDescription());	
				
				res.add(jo);
			}
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			return ResponseUtil.serious();
		}

    }
	
	@RequestMapping(value = "/factories/{code}", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getFactoryByCode(@PathVariable("code") String code) {
		try {
			JSONObject res = new JSONObject();
			FactoryEntity rs = factoryDao.getFactoryByCode(code);
			res.put("id",rs.getId());
			res.put("code",rs.getFactoryCode());
			res.put("create",rs.getCreateTime());
			res.put("name",rs.getFactoryName());		
			res.put("description",rs.getFactoryDescription());
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			return ResponseUtil.serious();
		}
    }

//************************Station************************	
		@RequestMapping(value = "/stations", method = RequestMethod.GET, produces = "application/json")
	    public JSONObject getStationAll() {
			try {
				JSONArray res = new JSONArray();
				List<StationEntity> rs = stationDao.getStationAll();
				
				for( StationEntity obj: rs)
				{
					JSONObject jo = new JSONObject();
					jo.put("id",obj.getId());
					jo.put("code",obj.getStationCode());
					jo.put("create",obj.getCreateTime());
					jo.put("factory",obj.getFactoryCode());
					jo.put("name",obj.getStationName());		
					jo.put("description",obj.getStationDescription());	
					
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}

	    }
		
		@RequestMapping(value = "/stations/{code}", method = RequestMethod.GET, produces = "application/json")
	    public JSONObject getStationByCode(@PathVariable("code") String code) {
			try {
				JSONObject res = new JSONObject();
				StationEntity rs = stationDao.getStationByCode(code);
				res.put("id",rs.getId());
				res.put("code",rs.getStationCode());
				res.put("create",rs.getCreateTime());
				res.put("factory",rs.getFactoryCode());
				res.put("name",rs.getStationName());		
				res.put("description",rs.getStationDescription());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}
	    }
		
//************************Device************************	
		@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = "application/json")
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
					jo.put("station",obj.getStationCode());
					jo.put("name",obj.getDeviceName());		
					jo.put("description",obj.getDeviceDescription());	
					
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}

	    }
		
		@RequestMapping(value = "/devices/{code}", method = RequestMethod.GET, produces = "application/json")
	    public JSONObject getDeviceByCode(@PathVariable("code") String code) {
			try {
				JSONObject res = new JSONObject();
				DeviceEntity rs = deviceDao.getDeviceByCode(code);
				res.put("id",rs.getId());
				res.put("code",rs.getDeviceCode());
				res.put("create",rs.getCreateTime());
				res.put("station",rs.getStationCode());	
				res.put("name",rs.getDeviceName());		
				res.put("description",rs.getDeviceDescription());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}
	    }
		
//************************DataBlock************************	
		@RequestMapping(value = "/datablocks", method = RequestMethod.GET, produces = "application/json")
	    public JSONObject getDataBlockAll() {
			try {
				JSONArray res = new JSONArray();
				List<DataBlockEntity> rs = dataBlockDao.getDataBlockAll();
				
				for( DataBlockEntity obj: rs)
				{
					JSONObject jo = new JSONObject();
					jo.put("id",obj.getId());
					jo.put("code",obj.getDataBlockCode());
					jo.put("create",obj.getCreateTime());
					jo.put("device",obj.getDeviceCode());
					jo.put("name",obj.getDataBlockName());		
					jo.put("description",obj.getDataBlockDescription());	
					
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}

	    }
		
		@RequestMapping(value = "/datablocks/{code}", method = RequestMethod.GET, produces = "application/json")
	    public JSONObject getDataBlockByCode(@PathVariable("code") String code) {
			try {
				JSONObject res = new JSONObject();
				DataBlockEntity rs = dataBlockDao.getDataBlockByCode(code);
				res.put("id",rs.getId());
				res.put("code",rs.getDataBlockCode());
				res.put("create",rs.getCreateTime());
				res.put("device",rs.getDeviceCode());
				res.put("name",rs.getDataBlockName());		
				res.put("description",rs.getDataBlockDescription());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				return ResponseUtil.serious();
			}
	    }
		
//***************Test*************		
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public JSONObject HelloWorld() {
		JSONObject res = new JSONObject();
		logger.info("HelloWorld Test!");
	    res.put("data", "hello world!");
	    res.put("errCode", 0);
	    return res;
	}	
}
