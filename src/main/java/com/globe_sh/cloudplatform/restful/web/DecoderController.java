package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.dao.DecoderDAO;
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
	  
	@RequestMapping(value = "/decoders", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getDecoderAll(
    		@RequestParam(value="factory",required=false) String factory,
    		@RequestParam(value="station",required=false) String station,
			@RequestParam(value="device",required=false) String device,
			@RequestParam(value="datablock",required=false) String datablock  		
    		) {
		try {
			JSONArray res = new JSONArray();
			List<DecoderEntity> rs = decoderDao.getDecoderAll(factory,station,device,datablock);
			
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
				
				res.add(jo);
			}
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}

    }
	
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getDecoderById(@PathVariable("id") int id) {
		try {
			JSONObject res = new JSONObject();
			DecoderEntity rs = decoderDao.getDecoderById(id);
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
					if(decoder.containsKey("code"))
						if( decoder.getString("code").length()>0)
							st.setDataCode(decoder.getString("code"));
						else
							continue;
					else
						continue;
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
					if(decoder.containsKey("length"))
						st.setDataLength(decoder.getIntValue("length"));
					else
						continue;
					//other fileds
					if(decoder.containsKey("data_block"))
						st.setDataBlock(decoder.getIntValue("data_block"));
					if(decoder.containsKey("name"))
						st.setDataName(decoder.getString("name"));
					if(decoder.containsKey("description"))
						st.setDataDescription(decoder.getString("description"));
					if(decoder.containsKey("kind"))
						st.setDataKind(decoder.getString("kind"));
					st.setDataPrecision(pre);
					if(decoder.containsKey("deviation"))
						st.setDataDeviation(decoder.getIntValue("deviation"));					
					if(decoder.containsKey("unit"))
						st.setDataUnit(decoder.getIntValue("unit"));
					if(decoder.containsKey("dictionary"))
						st.setDataDictionary(decoder.getString("dictionary"));					
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
/*	
	@RequestMapping(value = "/decoders", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject createDecoderGroup(
    		@RequestParam(value="code") String code,
    		@RequestParam(value="data_block",required=false) String data_block,
    		@RequestParam(value="name",required=false) String name,
    		@RequestParam(value="description",required=false) String description,
    		@RequestParam(value="type") String type,
    		@RequestParam(value="kind",required=false) String kind,
    		@RequestParam(value="start_byte") String start_byte,
    		@RequestParam(value="start_bit") String start_bit,
    		@RequestParam(value="length") String length,
    		@RequestParam(value="precision",required=false) String precision,
    		@RequestParam(value="deviation",required=false) String deviation,
    		@RequestParam(value="unit",required=false) String unit,
    		@RequestParam(value="dictionary",required=false) String dictionary    		
    			) {
		try {
			float pre;
			JSONObject res = new JSONObject();
			String dt = StaticMethod.getTimeString(0);
			if( precision==null || precision.length()==0) {
				pre = 1.0f;
			}
			else {
				pre = Float.parseFloat(precision);
			}
			DecoderEntity st = new DecoderEntity();
			st.setDataCode(code);
			if( data_block!=null && data_block.length()>0 )
				st.setDataBlock(Integer.parseInt(data_block));
			st.setCreateTime(dt);
			if( name!=null && name.length()>0 )
				st.setDataName(name);
			if( description!=null && description.length()>0 )
				st.setDataDescription(description);	
			st.setDataType(type);
			if( kind!=null && kind.length()>0 )
				st.setDataKind(kind);
			st.setStartByte(Integer.parseInt(start_byte));			
			st.setStartBit(Integer.parseInt(start_bit));
			st.setDataLength(Integer.parseInt(length));
			st.setDataPrecision(pre);
			if( deviation!=null && deviation.length()>0 )
				st.setDataDeviation(Integer.parseInt(deviation));
			if( unit!=null && unit.length()>0 )
				st.setDataUnit(Integer.parseInt(unit));
			if( dictionary!=null && dictionary.length()>0 )
				st.setDataDictionary(dictionary);	
			logger.info("code="+code);
			int rs = decoderDao.insertDecoder(st);
			res.put("result",rs);
			res.put("id", st.getId());
			//redis
			JedisOperater.addDataDecoder( String.valueOf(st.getDataBlock()), st.getId(), st.getJsonString());
			//return
	        return ResponseUtil.success(res);			
		} catch (Exception e) {
			JSONObject res = new JSONObject();
			res.put("result",0);
			res.put("id", -1);
			return ResponseUtil.failureMore(502,e.getMessage(),res);
		}
    }
*/    
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
    public JSONObject deleteDecoder(
    		@PathVariable("id") int id, 
    		@RequestParam(value="ids",required=false) String ids) {
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
			DecoderEntity st = decoderDao.getDecoderById( Integer.parseInt(idList.get(i)) );
			//redis		
			JedisOperater.removeDataDecoder( String.valueOf(st.getDataBlock()), Integer.parseInt(idList.get(i)) );
			rs = rs + decoderDao.deleteDecoder( Integer.parseInt(idList.get(i)) );		
		}
		return rs;		
	}
	
	@RequestMapping(value = "/decoders/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
    public JSONObject updateDecoder(
    		@PathVariable("id") int id,
    		@RequestParam(value="code",required=false) String code_new,
    		@RequestParam(value="data_block",required=false) String data_block,
    		@RequestParam(value="name",required=false) String name,
    		@RequestParam(value="description",required=false) String description,
    		@RequestParam(value="type",required=false) String type,
    		@RequestParam(value="kind",required=false) String kind,
    		@RequestParam(value="start_byte",required=false) String start_byte,
    		@RequestParam(value="start_bit",required=false) String start_bit,
    		@RequestParam(value="length",required=false) String length,
    		@RequestParam(value="precision",required=false) String precision,
    		@RequestParam(value="deviation",required=false) String deviation,
    		@RequestParam(value="unit",required=false) String unit,
    		@RequestParam(value="dictionary",required=false) String dictionary   		
    		) {
		try {
			DecoderEntity st = decoderDao.getDecoderById(id);
			JedisOperater.removeDataDecoder( String.valueOf(st.getDataBlock()), id );
			if( code_new!=null && code_new.length()>0 )
				st.setDataCode(code_new);
			if( data_block!=null && data_block.length()>0 )
				st.setDataBlock(Integer.parseInt(data_block));
			if( name!=null && name.length()>0 )
				st.setDataName(name);
			if( description!=null && description.length()>0 )
				st.setDataDescription(description);	
			if( type!=null && type.length()>0 )
				st.setDataType(type);
			if( kind!=null && kind.length()>0 )
				st.setDataKind(kind);
			if( start_byte!=null && start_byte.length()>0 )
				st.setStartByte(Integer.parseInt(start_byte));			
			if( start_bit!=null && start_bit.length()>0 )
				st.setStartBit(Integer.parseInt(start_bit));
			if( length!=null && length.length()>0 )
				st.setDataLength(Integer.parseInt(length));
			if( precision!=null && precision.length()>0 )
				st.setDataPrecision(Float.parseFloat(precision));
			if( deviation!=null && deviation.length()>0 )
				st.setDataDeviation(Integer.parseInt(deviation));
			if( unit!=null && unit.length()>0 )
				st.setDataUnit(Integer.parseInt(unit));
			if( dictionary!=null && dictionary.length()>0 )
				st.setDataDictionary(dictionary);		
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
