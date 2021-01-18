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
import com.globe_sh.cloudplatform.restful.entity.DataEntity;
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
import com.github.pagehelper.PageInfo;
import com.globe_sh.cloudplatform.common.cache.JedisOperater;
import com.globe_sh.cloudplatform.common.util.StaticMethod;
import com.globe_sh.cloudplatform.common.util.StaticOperater;
import com.globe_sh.cloudplatform.common.util.StaticVariable;

import com.globe_sh.cloudplatform.restful.utils.ResponseUtil;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "/api")
public class DataBlockController {
	@Autowired
	private DataBlockDAO dataBlockDao;	
	
//	private static Logger logger = org.slf4j.LoggerFactory(DataBlockController.class);
	  
//************************DataBlock************************	
		@RequestMapping(value = "/datablocks", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getDataBlockAll(
	    		@RequestParam(value="device",required=false) String device,
	    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
	    		@RequestParam(value="page_size",required=false,defaultValue="99999999") String page_size,
	    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
	    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type	    		
	    		) {
			try {
				JSONObject jb = new JSONObject();
				JSONArray res = new JSONArray();
				PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
				Page<DataBlockEntity> rs = dataBlockDao.getDataBlockAllParam(device);
				
				for( DataBlockEntity obj: rs)
				{
					JSONObject jo = new JSONObject();
					jo.put("id",obj.getId());
					jo.put("code",obj.getDataBlockCode());
					jo.put("create",obj.getCreateTime());
					jo.put("device",obj.getDeviceId());
					jo.put("name",obj.getDataBlockName());		
					jo.put("description",obj.getDataBlockDescription());
					jo.put("length",obj.getDataBlockLength());
					res.add(jo);
				}
				PageInfo<DataBlockEntity> info = new PageInfo<>(rs);
				
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
		
		@RequestMapping(value = "/datablocks/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getDataBlockById(@PathVariable("id") int id) {
			try {
				JSONObject res = new JSONObject();
				DataBlockEntity rs = dataBlockDao.getDataBlock(id);
				res.put("id",rs.getId());
				res.put("code",rs.getDataBlockCode());
				res.put("create",rs.getCreateTime());
				res.put("device",rs.getDeviceId());
				res.put("name",rs.getDataBlockName());		
				res.put("description",rs.getDataBlockDescription());
				res.put("length",rs.getDataBlockLength());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		@RequestMapping(value = "/datablocks", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createDataBlock( @RequestBody JSONArray jsonParam ) {
			try {
				JSONObject datablock = null;
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				//iterate
				for(int i = 0; i < jsonParam.size(); i++) {
					datablock = jsonParam.getJSONObject(i);
					if(datablock!=null) {
						DataBlockEntity st = new DataBlockEntity();

						//required parameters
						st.setCreateTime(dt);
						if(datablock.containsKey("device"))
							if( datablock.getString("device").length()>0)
								st.setDeviceId(datablock.getIntValue("device"));
							else
								continue;
						else
							continue;						
						//other fileds
						if(datablock.containsKey("code"))
							st.setDataBlockCode(datablock.getString("code"));						
						if(datablock.containsKey("name"))
							st.setDataBlockName(datablock.getString("name"));
						if(datablock.containsKey("description"))
							st.setDataBlockDescription(datablock.getString("description"));
						if(datablock.containsKey("length"))
							st.setDataBlockLength(datablock.getIntValue("length"));						
						
						int rs = dataBlockDao.insertDataBlock(st);
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
		
		@RequestMapping(value = "/datablocks/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject deleteDataBlock(
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
					rs = deleteDataBlockSingle(idList);					
				}
				else {
					idList.add( String.valueOf(id) );
					rs = deleteDataBlockSingle(idList);					
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
		
		public int deleteDataBlockSingle(List<String> idList) {
			int rs=0;
			for(int i=0;i<idList.size();i++) {
				DataBlockEntity st = dataBlockDao.getDataBlock( Integer.parseInt(idList.get(i)) );				
				rs = rs + dataBlockDao.deleteDataBlock( Integer.parseInt(idList.get(i)) );					
			}
			return rs;
		}
		
		@RequestMapping(value = "/datablocks/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateDataBlock(
	    		@PathVariable("id") int id,
	    		@RequestBody JSONObject jsonParam
	    		) {
			try {
				DataBlockEntity st = dataBlockDao.getDataBlock(id);

				if(jsonParam.containsKey("code"))
					st.setDataBlockCode(jsonParam.getString("code"));
				if(jsonParam.containsKey("device"))
					st.setDeviceId(jsonParam.getIntValue("device"));						
				if(jsonParam.containsKey("name"))
					st.setDataBlockName(jsonParam.getString("name"));
				if(jsonParam.containsKey("description"))
					st.setDataBlockDescription(jsonParam.getString("description"));
				if(jsonParam.containsKey("length"))
					st.setDataBlockLength(jsonParam.getIntValue("length"));
				
				JSONObject res = new JSONObject();
				int rs = dataBlockDao.updateDataBlock(id,st);
				res.put("result",rs);			
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }	
}
