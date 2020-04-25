package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.dao.DataDAO;
import com.globe_sh.cloudplatform.restful.entity.DataEntity;
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
public class DataController {

	@Autowired
	private DataDAO dataDao;
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RolesController.class);
	  
	@RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getData(
    								@RequestParam(value="station",required=false) String station,
    								@RequestParam(value="device",required=false) String device,
    								@RequestParam(value="datablock",required=false) String datablock,
    								@RequestParam(value="data",required=false) String data,
    								@RequestParam(value="start",required=false) String start,
    								@RequestParam(value="end",required=false) String end
    		)	{
		try {
			JSONArray res = new JSONArray();
			List<DataEntity> rs = dataDao.getData(station,device,datablock,data,start,end);
			for( DataEntity obj: rs)
			{
				JSONObject jo = new JSONObject();
				jo.put("time",obj.getSampleTime());
				jo.put("station",obj.getStation());
				jo.put("device",obj.getDevice());
				jo.put("data_block",obj.getDataBlock());		
				jo.put("data",obj.getCode());
				jo.put("value",obj.getValue());
				
				res.add(jo);
			}
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			logger.error("New message processing found exception: ",e);
			return ResponseUtil.serious();
		}

    }
}
