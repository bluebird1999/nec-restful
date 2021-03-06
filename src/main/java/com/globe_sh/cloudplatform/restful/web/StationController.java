package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.entity.DataEntity;
import com.globe_sh.cloudplatform.restful.entity.DecoderEntity;
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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.globe_sh.cloudplatform.common.cache.JedisOperater;
import com.globe_sh.cloudplatform.common.util.StaticMethod;
import com.globe_sh.cloudplatform.common.util.StaticOperater;
import com.globe_sh.cloudplatform.common.util.StaticVariable;

import com.globe_sh.cloudplatform.restful.utils.ResponseUtil;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "/api")
public class StationController {

	@Autowired
	private StationDAO stationDao;
	
//	private static Logger logger = org.slf4j.LoggerFactory(StationController.class);
	  
//************************Station************************	
		@RequestMapping(value = "/stations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getStationAll(
	    		@RequestParam(value="code",required=false,defaultValue="") String code,
	    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
	    		@RequestParam(value="page_size",required=false,defaultValue="99999999") String page_size,
	    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
	    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type	    		) {
			try {
				JSONObject jb = new JSONObject();
				JSONArray res = new JSONArray();
				PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
				Page<StationEntity> rs = stationDao.getStationAll(code);
				
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
				PageInfo<StationEntity> info = new PageInfo<>(rs);
				
				int page_num = ((int)info.getTotal() - 1) / Integer.valueOf(page_size) + 1;
				jb.put("data", res);
				jb.put("size", info.getTotal());
				jb.put("page_number", page_num);
				jb.put("page_id", page_start);
				if( Integer.valueOf(page_size) == 99999999)
					jb.put("page_size", (int)info.getTotal());
				else
					jb.put("page_size", page_size);
		        return ResponseUtil.success(jb);		
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
	    public JSONObject deleteStation(
	    		@PathVariable("id") int id,
	    		@RequestParam(value="ids",required=false) String ids
	    		) {
			try {
				List<String> idList = new ArrayList<String>();
				int rs;
				if( ids!=null && ids.length()>0 ) {
					String input[] = ids.split(",");
					if( input.length>0 ) {
						for(int i = 0; i < input.length; i++) {
							idList.add(input[i]);
						}				
					}	
					if( idList.size() == 0) {
						idList.add( String.valueOf(id) );
					}
					rs = deleteStationSingle(idList);					
				}
				else {
					idList.add( String.valueOf(id) );
					rs = deleteStationSingle(idList);					
				}
				
				JSONObject res = new JSONObject();
				res.put("result",rs);			
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		public int deleteStationSingle(List<String> idList) {
			int rs=0;
			for(int i=0;i<idList.size();i++) {
				StationEntity st = stationDao.getStation( Integer.parseInt(idList.get(i)) );
				//redis		
				JedisOperater.removeStation( idList.get(i) );
				rs = rs + stationDao.deleteStation( Integer.parseInt(idList.get(i)) );		
			}
			return rs;		
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
