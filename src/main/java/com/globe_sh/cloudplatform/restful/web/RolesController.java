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

import com.globe_sh.cloudplatform.common.cache.JedisOperater;
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
	@RequestMapping(value = "/factories", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/factories/{code}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/factories", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject createFactory(@RequestParam("code") String code,
    		@RequestParam("name") String name,
    		@RequestParam("description") String description
    			) {
		try {
			JSONObject res = new JSONObject();
			String dt = StaticMethod.getTimeString(0);
			FactoryEntity st = new FactoryEntity(0,code,dt,name,description);
			int rs = factoryDao.insertFactory(st);
			res.put("result",rs);
			res.put("id", st.getId());
			//return
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			res.put("id", -1);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/factories/{code}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public JSONObject createFactory(@PathVariable("code") String code ) {
		try {
			JSONObject res = new JSONObject();
			int rs = factoryDao.deleteFactory(code);
			res.put("result",rs);
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/factories/{code}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public JSONObject updateFactory(
    		@PathVariable("code") String code,
    		@RequestParam("code") String code_new,
    		@RequestParam("name") String name,
    		@RequestParam("description") String description
    		) {
		try {
			FactoryEntity st = factoryDao.getFactoryByCode(code);
			if( !code_new.equals(null) && code_new.length()>0 )
				st.setFactoryCode(code_new);
			if( !name.equals(null) && name.length()>0 )
				st.setFactoryName(name);
			if( !description.equals(null) && description.length()>0 )
				st.setFactoryDescription(description);	
			
			JSONObject res = new JSONObject();
			int rs = factoryDao.updateFactory(code,st);
			res.put("result",rs);			
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }			

//************************Station************************	
		@RequestMapping(value = "/stations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
					jo.put("status",obj.getStationStatus());
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}

	    }
		@RequestMapping(value = "/stations/{code}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
				res.put("status",rs.getStationStatus());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/stations", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createStation(@RequestParam("code") String code,
	    		@RequestParam("factory") String factory,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    			) {
			try {
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				StationEntity st = new StationEntity(0,code,dt,factory,name,description,2);
				int rs = stationDao.insertStation(st);
				res.put("result",rs);
				res.put("id", st.getId());
				//redis
				JedisOperater.addStation(code, st.getJsonString());
				//return
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				res.put("id", -1);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/stations/{code}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject createStation(@PathVariable("code") String code ) {
			try {
				JSONObject res = new JSONObject();
				int rs = stationDao.deleteStation(code);
				res.put("result",rs);
				//redis
				JedisOperater.removeStation(code);				
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/stations/{code}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateStation(
	    		@PathVariable("code") String code,
	    		@RequestParam("code") String code_new,
	    		@RequestParam("factory") String factory,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    		) {
			try {
				StationEntity st = stationDao.getStationByCode(code);
				if( !code_new.equals(null) && code_new.length()>0 )
					st.setStationCode(code_new);
				if( !factory.equals(null) && factory.length()>0 )
					st.setFactoryCode(factory);
				if( !name.equals(null) && name.length()>0 )
					st.setStationName(name);
				if( !description.equals(null) && description.length()>0 )
					st.setStationDescription(description);	
				
				JSONObject res = new JSONObject();
				int rs = stationDao.updateStation(code,st);
				res.put("result",rs);
				//redis
				JedisOperater.addStation(code, st.getJsonString());				
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }			
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
					jo.put("station",obj.getStationCode());
					jo.put("name",obj.getDeviceName());		
					jo.put("description",obj.getDeviceDescription());
					res.add(jo);
				}
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}

	    }
		@RequestMapping(value = "/devices/{code}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/devices", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createDevice(@RequestParam("code") String code,
	    		@RequestParam("station") String station,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    			) {
			try {
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				DeviceEntity st = new DeviceEntity(0,code,dt,station,name,description);
				int rs = deviceDao.insertDevice(st);
				res.put("result",rs);
				res.put("id", st.getId());
				//return
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				res.put("id", -1);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/devices/{code}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject createDevice(@PathVariable("code") String code ) {
			try {
				JSONObject res = new JSONObject();
				int rs = deviceDao.deleteDevice(code);
				res.put("result",rs);	
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/devices/{code}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateDevice(
	    		@PathVariable("code") String code,
	    		@RequestParam("code") String code_new,
	    		@RequestParam("station") String station,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    		) {
			try {
				DeviceEntity st = deviceDao.getDeviceByCode(code);
				if( !code_new.equals(null) && code_new.length()>0 )
					st.setDeviceCode(code_new);
				if( !station.equals(null) && station.length()>0 )
					st.setStationCode(station);
				if( !name.equals(null) && name.length()>0 )
					st.setDeviceName(name);
				if( !description.equals(null) && description.length()>0 )
					st.setDeviceDescription(description);	
				
				JSONObject res = new JSONObject();
				int rs = deviceDao.updateDevice(code,st);
				res.put("result",rs);	
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }	
		
//************************DataBlock************************	
		@RequestMapping(value = "/datablocks", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}

	    }
		@RequestMapping(value = "/datablocks/{code}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/datablocks", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createDataBlock(@RequestParam("code") String code,
	    		@RequestParam("device") String device,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    			) {
			try {
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				DataBlockEntity st = new DataBlockEntity(0,code,dt,device,name,description);
				int rs = dataBlockDao.insertDataBlock(st);
				res.put("result",rs);
				res.put("id", st.getId());
				//return
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				res.put("id", -1);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/datablocks/{code}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject createDataBlock(@PathVariable("code") String code ) {
			try {
				JSONObject res = new JSONObject();
				int rs = dataBlockDao.deleteDataBlock(code);
				res.put("result",rs);
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		@RequestMapping(value = "/datablocks/{code}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateDataBlock(
	    		@PathVariable("code") String code,
	    		@RequestParam("code") String code_new,
	    		@RequestParam("device") String device,
	    		@RequestParam("name") String name,
	    		@RequestParam("description") String description
	    		) {
			try {
				DataBlockEntity st = dataBlockDao.getDataBlockByCode(code);
				if( !code_new.equals(null) && code_new.length()>0 )
					st.setDataBlockCode(code_new);
				if( !device.equals(null) && device.length()>0 )
					st.setDeviceCode(device);
				if( !name.equals(null) && name.length()>0 )
					st.setDataBlockName(name);
				if( !description.equals(null) && description.length()>0 )
					st.setDataBlockDescription(description);	
				
				JSONObject res = new JSONObject();
				int rs = dataBlockDao.updateDataBlock(code,st);
				res.put("result",rs);			
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }	
}
