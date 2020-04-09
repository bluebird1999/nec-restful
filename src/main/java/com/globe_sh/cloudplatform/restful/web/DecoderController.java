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
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
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
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	
	@RequestMapping(value = "/decoders", method = RequestMethod.POST, produces = "application/json")
    public JSONObject createDecoder(
    		@RequestParam("code") String code,
    		@RequestParam("data_block") String data_block,
    		@RequestParam("name") String name,
    		@RequestParam("description") String description,
    		@RequestParam("type") String type,
    		@RequestParam("kind") String kind,
    		@RequestParam("start_byte") int start_byte,
    		@RequestParam("start_bit") int start_bit,
    		@RequestParam("length") int length,
    		@RequestParam("precision") float precision,
    		@RequestParam("deviation") int deviation,
    		@RequestParam("unit") int unit,
    		@RequestParam("dictionary") String dictionary    		
    			) {
		try {
			JSONObject res = new JSONObject();
			String dt = StaticMethod.getTimeString(0);
			DecoderEntity st = new DecoderEntity(0,code,dt,data_block,name,description,
					type,kind,start_byte,start_bit,length,precision,deviation,unit,dictionary
					);
			int rs = decoderDao.insertDecoder(st);
			res.put("result",rs);
			res.put("id", st.getId());
			//redis
			JedisOperater.addDataDecoder(st.getDataBlockCode(), code, st.getJsonString());
			//return
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			res.put("id", -1);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/decoders/{code}", method = RequestMethod.DELETE, produces = "application/json")
    public JSONObject createDecoder(@PathVariable("code") String code ) {
		try {
			JSONObject res = new JSONObject();
			DecoderEntity st = decoderDao.getDecoderByCode(code);
			int rs = decoderDao.deleteDecoder(code);
			res.put("result",rs);
			//redis
			JedisOperater.removeDataDecoder(st.getDataBlockCode(), code);
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/decoders/{code}", method = RequestMethod.PUT, produces = "application/json")
    public JSONObject updateDecoder(
    		@PathVariable("code") String code,
    		@RequestParam("code") String code_new,
    		@RequestParam("data_block") String data_block,
    		@RequestParam("name") String name,
    		@RequestParam("description") String description,
    		@RequestParam("type") String type,
    		@RequestParam("kind") String kind,
    		@RequestParam("start_byte") String start_byte,
    		@RequestParam("start_bit") String start_bit,
    		@RequestParam("length") String length,
    		@RequestParam("precision") String precision,
    		@RequestParam("deviation") String deviation,
    		@RequestParam("unit") String unit,
    		@RequestParam("dictionary") String dictionary   		
    		) {
		try {
			DecoderEntity st = decoderDao.getDecoderByCode(code);
			if( !code_new.equals(null) && code_new.length()>0 )
				st.setDataCode(code_new);
			if( !data_block.equals(null) && data_block.length()>0 )
				st.setDataBlockCode(data_block);
			if( !name.equals(null) && name.length()>0 )
				st.setDataName(name);
			if( !description.equals(null) && description.length()>0 )
				st.setDataDescription(description);	
			if( !type.equals(null) && type.length()>0 )
				st.setDataType(type);
			if( !kind.equals(null) && kind.length()>0 )
				st.setDataKind(kind);
			if( !start_byte.equals(null) && start_byte.length()>0 )
				st.setStartByte(Integer.parseInt(start_byte));			
			if( !start_bit.equals(null) && start_bit.length()>0 )
				st.setStartBit(Integer.parseInt(start_bit));
			if( !length.equals(null) && length.length()>0 )
				st.setDataLength(Integer.parseInt(length));
			if( !precision.equals(null) && precision.length()>0 )
				st.setDataPrecision(Float.parseFloat(precision));
			if( !deviation.equals(null) && deviation.length()>0 )
				st.setDataDeviation(Integer.parseInt(deviation));
			if( !unit.equals(null) && unit.length()>0 )
				st.setDataUnit(Integer.parseInt(unit));
			if( !dictionary.equals(null) && dictionary.length()>0 )
				st.setDataDictionary(dictionary);		
			JSONObject res = new JSONObject();
			int rs = decoderDao.updateDecoder(code,st);
			res.put("result",rs);
			//redis
			JedisOperater.addDataDecoder( st.getDataBlockCode(), code, st.getJsonString());
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }				
}
