package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.dao.DecoderDAO;
import com.globe_sh.cloudplatform.restful.entity.DataEntity;
import com.globe_sh.cloudplatform.restful.entity.DecoderEntity;

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

@RestController
@RequestMapping(value = "/api")
public class DecoderController {

	@Autowired
	private DecoderDAO decoderDao;
	
//	private static Logger logger = org.slf4j.LoggerFactory(DecoderController.class);
	  
	@RequestMapping(value = "/decoders", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getDecoderAll(
    		/*
    		@RequestParam(value="factory",required=false) String factory,
    		@RequestParam(value="line",required=false) String line,
			@RequestParam(value="device",required=false) String device,
			*/
			@RequestParam(value="data_block",required=false) String data_block,
    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
    		@RequestParam(value="page_size",required=false,defaultValue="99999999") String page_size,
    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type			
    		) {
		try {
			JSONObject jb = new JSONObject();
			JSONArray res = new JSONArray();
			PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
			Page<DecoderEntity> rs;
			
			rs = decoderDao.getDecoderAllParam(data_block);
			
			for( DecoderEntity obj: rs)
			{
				JSONObject jo = new JSONObject();
				jo.put("id",obj.getId());
				jo.put("code",obj.getDataCode());
				jo.put("create",obj.getCreateTime());
				jo.put("data_block",obj.getDataBlock());
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
				jo.put("low_precede",obj.getLowPrecede());	
				
				res.add(jo);
			}
			PageInfo<DecoderEntity> info = new PageInfo<>(rs);
			
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
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}

    }
	
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getDecoderById(@PathVariable("id") int id) {
		try {
			JSONObject res = new JSONObject();
			DecoderEntity rs = decoderDao.getDecoder(id);
			res.put("id",rs.getId());
			res.put("code",rs.getDataCode());
			res.put("create",rs.getCreateTime());
			res.put("data_block",rs.getDataBlock());
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
			res.put("low_precede",rs.getLowPrecede());	
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
	@RequestMapping(value = "/decoders", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject createDecoder(@RequestBody JSONArray jsonParam) {
		try {
			JSONObject decoder = null;
			float pre;
			JSONObject res = new JSONObject();
			String dt = StaticMethod.getTimeString(0);
			//iterate
			for(int i = 0; i < jsonParam.size(); i++) {
				decoder = jsonParam.getJSONObject(i);
				if(decoder!=null) {
					DecoderEntity st = new DecoderEntity();
					if(decoder.containsKey("precision"))
						 pre = decoder.getFloat("precision");
					else
						pre = 1.0f;
					//required parameters
					st.setCreateTime(dt);
					if(decoder.containsKey("type"))
						if( decoder.getString("type").length()>0)
							st.setDataType(decoder.getString("type"));
						else
							continue;
					else
						continue;
					if(decoder.containsKey("start_byte"))
						st.setStartByte(decoder.getIntValue("start_byte"));
					else
						continue;
					if(decoder.containsKey("start_bit"))
						st.setStartBit(decoder.getIntValue("start_bit"));
					else
						continue;
					//other fileds
					if(decoder.containsKey("code"))
						st.setDataCode(decoder.getString("code"));					
					if(decoder.containsKey("data_block"))
						st.setDataBlock(decoder.getIntValue("data_block"));
					if(decoder.containsKey("name"))
						st.setDataName(decoder.getString("name"));
					if(decoder.containsKey("description"))
						st.setDataDescription(decoder.getString("description"));
					if(decoder.containsKey("kind"))
						st.setDataKind(decoder.getString("kind"));
					if(decoder.containsKey("length"))
						st.setDataLength(decoder.getIntValue("length"));					
					st.setDataPrecision(pre);
					if(decoder.containsKey("deviation"))
						st.setDataDeviation(decoder.getIntValue("deviation"));					
					if(decoder.containsKey("unit"))
						st.setDataUnit(decoder.getIntValue("unit"));
					if(decoder.containsKey("dictionary"))
						st.setDataDictionary(decoder.getString("dictionary"));	
					if(decoder.containsKey("low_precede"))
						st.setLowPrecede(decoder.getIntValue("low_precede"));
					else
						st.setLowPrecede(0);
					
					int rs = decoderDao.insertDecoder(st);
					res.put("result",rs);
					res.put("id", st.getId());
					//redis
					JedisOperater.addDataDecoder( String.valueOf(st.getDataBlock()), st.getId(), st.getJsonString());						
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
    
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public JSONObject deleteDecoder(
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
				rs = deleteDecoderSingle(idList);					
			}
			else {
				idList.add( String.valueOf(id) );
				rs = deleteDecoderSingle(idList);					
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
	
	public int deleteDecoderSingle(List<String> idList) {
		int rs=0;
		for(int i=0;i<idList.size();i++) {
			DecoderEntity st = decoderDao.getDecoder( Integer.parseInt(idList.get(i)) );
			//redis		
			JedisOperater.removeDataDecoder( String.valueOf(st.getDataBlock()), Integer.parseInt(idList.get(i)) );
			rs = rs + decoderDao.deleteDecoder( Integer.parseInt(idList.get(i)) );		
		}
		return rs;		
	}
	
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public JSONObject updateDecoder(
    		@PathVariable("id") int id,
    		@RequestBody JSONObject jsonParam  		
    		) {
		try {
			DecoderEntity st = decoderDao.getDecoder(id);
			JedisOperater.removeDataDecoder( String.valueOf(st.getDataBlock()), id );
			
			if(jsonParam.containsKey("code"))
				st.setDataCode(jsonParam.getString("code"));
			if(jsonParam.containsKey("data_block"))
				st.setDataBlock(jsonParam.getIntValue("data_block"));
			if(jsonParam.containsKey("name"))
				st.setDataName(jsonParam.getString("name"));
			if(jsonParam.containsKey("description"))
				st.setDataDescription(jsonParam.getString("description"));			
			if(jsonParam.containsKey("type"))
				st.setDataType(jsonParam.getString("type"));
			if(jsonParam.containsKey("kind"))
				st.setDataKind(jsonParam.getString("kind"));			
			if(jsonParam.containsKey("start_byte"))
				st.setStartByte(jsonParam.getIntValue("start_byte"));
			if(jsonParam.containsKey("start_bit"))
				st.setStartBit(jsonParam.getIntValue("start_bit"));
			if(jsonParam.containsKey("length"))
				st.setDataLength(jsonParam.getIntValue("length"));
			if(jsonParam.containsKey("precision"))
				st.setDataPrecision(jsonParam.getFloat("precision"));
			if(jsonParam.containsKey("deviation"))
				st.setDataDeviation(jsonParam.getIntValue("deviation"));					
			if(jsonParam.containsKey("unit"))
				st.setDataUnit(jsonParam.getIntValue("unit"));
			if(jsonParam.containsKey("dictionary"))
				st.setDataDictionary(jsonParam.getString("dictionary"));	
			if(jsonParam.containsKey("low_precede"))
				st.setLowPrecede(jsonParam.getIntValue("low_precede"));			
					
			JSONObject res = new JSONObject();
			int rs = decoderDao.updateDecoder(id,st);
			res.put("result",rs);
			//redis
			JedisOperater.addDataDecoder( String.valueOf(st.getDataBlock()), id, st.getJsonString());
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }				
}
