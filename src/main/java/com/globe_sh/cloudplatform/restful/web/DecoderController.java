package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.dao.DecoderDAO;
import com.globe_sh.cloudplatform.restful.entity.DecoderEntity;
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
public class DecoderController {

	@Autowired
	private DecoderDAO decoderDao;
	
	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RolesController.class);
	  
	@RequestMapping(value = "/decoders", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getDecoderAll() {
		try {
			JSONArray res = new JSONArray();
			List<DecoderEntity> rs = decoderDao.getDecoderAll();
			
			for( DecoderEntity obj: rs)
			{
				JSONObject jo = new JSONObject();
				jo.put("id",obj.getId());
				jo.put("code",obj.getDataCode());
				jo.put("create",obj.getCreateTime());
				jo.put("data_block",obj.getDataBlockCode());
				jo.put("name",obj.getDataName());		
				jo.put("description",obj.getDataDescription());
				jo.put("type",obj.getDataType());
				jo.put("kind",obj.getDataKind());
				jo.put("start_byte",obj.getStartByte());
				jo.put("start_bit",obj.getStartBit());
				jo.put("length",obj.getDataLength());		
				jo.put("precision",obj.getDataPrecision());
				jo.put("deviation",obj.getDataDeviation());
				jo.put("unit",obj.getDataUnit());
				jo.put("dictionary",obj.getDataDictionary());	
				
				res.add(jo);
			}
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			return ResponseUtil.serious();
		}

    }
	
	@RequestMapping(value = "/decoders/{code}", method = RequestMethod.GET, produces = "application/json")
    public JSONObject getDecoderByCode(@PathVariable("code") String code) {
		try {
			JSONObject res = new JSONObject();
			DecoderEntity rs = decoderDao.getDecoderByCode(code);
			res.put("id",rs.getId());
			res.put("code",rs.getDataCode());
			res.put("create",rs.getCreateTime());
			res.put("data_block",rs.getDataBlockCode());
			res.put("name",rs.getDataName());		
			res.put("description",rs.getDataDescription());
			res.put("type",rs.getDataType());
			res.put("kind",rs.getDataKind());
			res.put("start_byte",rs.getStartByte());
			res.put("start_bit",rs.getStartBit());
			res.put("length",rs.getDataLength());		
			res.put("precision",rs.getDataPrecision());
			res.put("deviation",rs.getDataDeviation());
			res.put("unit",rs.getDataUnit());
			res.put("dictionary",rs.getDataDictionary());			
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			return ResponseUtil.serious();
		}
    }
}
