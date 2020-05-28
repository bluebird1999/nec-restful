package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.entity.StationEntity;
import com.globe_sh.cloudplatform.restful.dao.StationDAO;

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
public class StationController {

	@Autowired
	private StationDAO stationDao;
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(StationController.class);
	  
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
					jo.put("factory",obj.getFid());
					jo.put("line",obj.getLid());
					jo.put("device",obj.getDid());
					jo.put("device_number",obj.getDeviceNumber());
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
		
		@RequestMapping(value = "/stations/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getStationByCode(@PathVariable("id") int id) {
			try {
				JSONObject res = new JSONObject();
				StationEntity rs = stationDao.getStation(id);
				res.put("id",rs.getId());
				res.put("code",rs.getStationCode());
				res.put("create",rs.getCreateTime());
				res.put("factory",rs.getFid());
				res.put("line",rs.getLid());
				res.put("device",rs.getDid());
				res.put("device_number",rs.getDeviceNumber());				
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
	    public JSONObject createStation( @RequestBody JSONArray jsonParam ) {
			try {
				JSONObject station = null;
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				//iterate
				for(int i = 0; i < jsonParam.size(); i++) {
					station = jsonParam.getJSONObject(i);
					if(station!=null) {
						StationEntity st = new StationEntity();

						//required parameters
						st.setCreateTime(dt);
						st.setStationStatus(2);
/*						if(station.containsKey("factory"))
							if( station.getString("factory").length()>0)
								st.setFactoryId(station.getIntValue("factory"));
							else
								continue;
						else
							continue;
*/							
						
						//other fileds
						if(station.containsKey("code"))
							st.setStationCode(station.getString("code"));	
						if(station.containsKey("factory"))
							st.setFid(station.getString("factory"));						
						if(station.containsKey("line"))
							st.setLid(station.getString("line"));
						if(station.containsKey("device"))
							st.setDid(station.getString("device"));
						if(station.containsKey("device_number"))
							st.setDeviceNumber(station.getIntValue("device_number"));						
						if(station.containsKey("name"))
							st.setStationName(station.getString("name"));
						if(station.containsKey("description"))
							st.setStationDescription(station.getString("description"));
						
						int rs = stationDao.insertStation(st);
						res.put("result",rs);
						res.put("id", st.getId());
						//redis
						JedisOperater.addStation( String.valueOf(st.getId()), st.getJsonString());						
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
		
		@RequestMapping(value = "/stations/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject createStation(@PathVariable("id") int id ) {
			try {
				JSONObject res = new JSONObject();
				int rs = stationDao.deleteStation(id);
				res.put("result",rs);
				//redis
				JedisOperater.removeStation(String.valueOf(id));				
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		@RequestMapping(value = "/stations/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateStation(
	    		@PathVariable("id") int id,
	    		@RequestBody JSONObject jsonParam
	    		) {
			try {
				StationEntity st = stationDao.getStation(id);

				if(jsonParam.containsKey("code"))
					st.setStationCode(jsonParam.getString("code"));				
				if(jsonParam.containsKey("factory"))
					st.setFid(jsonParam.getString("factory"));
				if(jsonParam.containsKey("line"))
					st.setLid(jsonParam.getString("line"));
				if(jsonParam.containsKey("device"))
					st.setDid(jsonParam.getString("device"));
				if(jsonParam.containsKey("device_number"))
					st.setDeviceNumber(jsonParam.getIntValue("device_number"));						
				if(jsonParam.containsKey("name"))
					st.setStationName(jsonParam.getString("name"));
				if(jsonParam.containsKey("description"))
					st.setStationDescription(jsonParam.getString("description"));
				
				
				JSONObject res = new JSONObject();
				int rs = stationDao.updateStation(id,st);
				res.put("result",rs);
				//redis
				JedisOperater.removeStation(String.valueOf(id));
				JedisOperater.addStation(String.valueOf(st.getId()), st.getJsonString());				
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }			
}
