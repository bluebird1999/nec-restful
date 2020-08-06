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
import org.slf4j.Logger;
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

@RestController
@RequestMapping(value = "/api")
public class DataController {

	@Autowired
	private DataDAO dataDao;
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestfulMain.class);
	  
	@RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getData(   		
    								@RequestParam(value="factory",required=false) String factory,
    								@RequestParam(value="line",required=false) String line,
    								@RequestParam(value="device",required=false) String device,
    								@RequestParam(value="datablock",required=false) String datablock,
    								@RequestParam(value="data",required=false) String data,
    								@RequestParam(value="start",required=false) String start,
    								@RequestParam(value="end",required=false) String end,
    					    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
    					    		@RequestParam(value="page_size",required=false,defaultValue="99999999") String page_size,
    					    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
    					    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type    								
    		)	{
		try {
			JSONObject jb = new JSONObject();
			JSONArray res = new JSONArray();
			PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
			Page<DataEntity> rs = dataDao.getData(factory,line,device,datablock,data,start,end);
			for( DataEntity obj: rs)
			{
				JSONObject jo = new JSONObject();
				jo.put("time",obj.getSampleTime());
				jo.put("factory",obj.getFactory());
				jo.put("line",obj.getLine());				
				jo.put("device",obj.getDevice());
				jo.put("data_block",obj.getDataBlock());		
				jo.put("data",obj.getCode());
				jo.put("value",obj.getValue());
				
				res.add(jo);
			}
			PageInfo<DataEntity> info = new PageInfo<>(rs);
			
			int page_num = ((int)info.getTotal() - 1) / Integer.valueOf(page_size) + 1;
			jb.put("data", res);
			jb.put("page_number", page_num);
			jb.put("page_id", page_start);
			if( Integer.valueOf(page_size) == 99999999)
				jb.put("page_size", (int)info.getTotal());
			else
				jb.put("page_size", page_size);
	        return ResponseUtil.success(jb);			
		} catch (Exception e) {
			logger.error("New message processing found exception: ",e);
			return ResponseUtil.serious();
		}

    }
}
