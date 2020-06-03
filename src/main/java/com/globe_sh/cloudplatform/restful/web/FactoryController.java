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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.globe_sh.cloudplatform.common.cache.JedisOperater;
import com.globe_sh.cloudplatform.common.util.StaticMethod;
import com.globe_sh.cloudplatform.common.util.StaticOperater;
import com.globe_sh.cloudplatform.common.util.StaticVariable;

import org.apache.logging.log4j.*;

import com.globe_sh.cloudplatform.restful.utils.ResponseUtil;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "/api")
public class FactoryController {

	@Autowired
	private FactoryDAO factoryDao;
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(FactoryController.class);
	  
//************************Factory************************	
	@RequestMapping(value = "/factories", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getFactoryAll(
    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
    		@RequestParam(value="page_size",required=false,defaultValue="10") String page_size,
    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type
    		) {
		try {
			JSONArray res = new JSONArray();
			PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
			Page<FactoryEntity> rs = factoryDao.getFactoryAll();
			
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
	@RequestMapping(value = "/factories/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getFactoryByCode(@PathVariable("id") int id) {
		try {
			JSONObject res = new JSONObject();
			FactoryEntity rs = factoryDao.getFactory(id);
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
    public JSONObject createFactory( @RequestBody JSONArray jsonParam ) {
		try {
			JSONObject factory = null;
			JSONObject res = new JSONObject();
			String dt = StaticMethod.getTimeString(0);
			//iterate
			for(int i = 0; i < jsonParam.size(); i++) {
				factory = jsonParam.getJSONObject(i);
				if(factory!=null) {
					FactoryEntity st = new FactoryEntity();

					//required parameters
					//other fileds
					st.setCreateTime(dt);
					if(factory.containsKey("code"))
						st.setFactoryCode(factory.getString("code"));					
					if(factory.containsKey("name"))
						st.setFactoryName(factory.getString("name"));
					if(factory.containsKey("description"))
						st.setFactoryDescription(factory.getString("description"));
					
					int rs = factoryDao.insertFactory(st);
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
	
	@RequestMapping(value = "/factories/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public JSONObject deleteFactory(
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
				rs = deleteFactorySingle(idList);					
			}
			else {
				idList.add( String.valueOf(id) );
				rs = deleteFactorySingle(idList);					
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
	
	public int deleteFactorySingle(List<String> idList) {
		int rs=0;
		for(int i=0;i<idList.size();i++) {
			rs = rs + factoryDao.deleteFactory( Integer.parseInt(idList.get(i)) );		
		}
		return rs;		
	}
	
	@RequestMapping(value = "/factories/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public JSONObject updateFactory(
    		@PathVariable("id") int id,
    		@RequestBody JSONObject jsonParam
    		) {
		try {
			FactoryEntity st = factoryDao.getFactory(id);
			
			if(jsonParam.containsKey("code"))
				st.setFactoryCode(jsonParam.getString("code"));
			if(jsonParam.containsKey("name"))
				st.setFactoryName(jsonParam.getString("name"));
			if(jsonParam.containsKey("description"))
				st.setFactoryDescription(jsonParam.getString("description"));
			
			JSONObject res = new JSONObject();
			int rs = factoryDao.updateFactory(id,st);
			res.put("result",rs);			
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }			
}