package com.globe_sh.cloudplatform.restful.web;

import com.globe_sh.cloudplatform.restful.RestfulMain;
import com.globe_sh.cloudplatform.restful.entity.FactoryEntity;
import com.globe_sh.cloudplatform.restful.dao.FactoryDAO;
import com.globe_sh.cloudplatform.restful.entity.LineEntity;
import com.globe_sh.cloudplatform.restful.dao.LineDAO;
import com.globe_sh.cloudplatform.restful.entity.StationEntity;
import com.globe_sh.cloudplatform.restful.dao.StationDAO;
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

//import org.slf4j.Logger.*;

import com.globe_sh.cloudplatform.restful.utils.ResponseUtil;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping(value = "/api")
public class LineController {

	@Autowired
	private LineDAO lineDao;
	
//	private static Logger logger = org.slf4j.LoggerFactory(LineController.class);
	  
//************************Line************************	
		@RequestMapping(value = "/lines", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getLineAll(
	    		@RequestParam(value="factory",required=false) String factory,
	    		@RequestParam(value="page_start",required=false,defaultValue="1") String page_start,
	    		@RequestParam(value="page_size",required=false,defaultValue="99999999") String page_size,
	    		@RequestParam(value="order_field",required=false,defaultValue="id") String order_field,
	    		@RequestParam(value="order_type",required=false,defaultValue="asc") String order_type	    		
	    		) {
			try {
				JSONObject jb = new JSONObject();
				JSONArray res = new JSONArray();
				PageHelper.startPage(Integer.valueOf(page_start), Integer.valueOf(page_size), order_field + " " + order_type);
				Page<LineEntity> rs = lineDao.getLineAllParam(factory);
				
				for( LineEntity obj: rs)
				{
					JSONObject jo = new JSONObject();
					jo.put("id",obj.getId());
					jo.put("code",obj.getLineCode());
					jo.put("create",obj.getCreateTime());
					jo.put("factory",obj.getFactoryId());
					jo.put("name",obj.getLineName());		
					jo.put("description",obj.getLineDescription());
					res.add(jo);
				}
				PageInfo<LineEntity> info = new PageInfo<>(rs);
				
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
		
		@RequestMapping(value = "/lines/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	    public JSONObject getLineByCode(@PathVariable("id") int id) {
			try {
				JSONObject res = new JSONObject();
				LineEntity rs = lineDao.getLine(id);
				res.put("id",rs.getId());
				res.put("code",rs.getLineCode());
				res.put("create",rs.getCreateTime());
				res.put("factory",rs.getFactoryId());
				res.put("name",rs.getLineName());		
				res.put("description",rs.getLineDescription());
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }
		
		@RequestMapping(value = "/lines", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	    public JSONObject createLine( @RequestBody JSONArray jsonParam ) {
			try {
				JSONObject line = null;
				JSONObject res = new JSONObject();
				String dt = StaticMethod.getTimeString(0);
				//iterate
				for(int i = 0; i < jsonParam.size(); i++) {
					line = jsonParam.getJSONObject(i);
					if(line!=null) {
						LineEntity st = new LineEntity();

						//required parameters
						st.setCreateTime(dt);
						if(line.containsKey("factory"))
							if( line.getString("factory").length()>0)
								st.setFactoryId(line.getIntValue("factory"));
							else
								continue;
						else
							continue;						
						//other fileds
						if(line.containsKey("code"))
							st.setLineCode(line.getString("code"));						
						if(line.containsKey("name"))
							st.setLineName(line.getString("name"));
						if(line.containsKey("description"))
							st.setLineDescription(line.getString("description"));
						
						int rs = lineDao.insertLine(st);
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
		@RequestMapping(value = "/lines/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=UTF-8")
	    public JSONObject deleteLine(
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
					rs = deleteLineSingle(idList);					
				}
				else {
					idList.add( String.valueOf(id) );
					rs = deleteLineSingle(idList);					
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
		
		public int deleteLineSingle(List<String> idList) {
			int rs=0;
			for(int i=0;i<idList.size();i++) {
				rs = rs + lineDao.deleteLine( Integer.parseInt(idList.get(i)) );		
			}
			return rs;		
		}
		
		@RequestMapping(value = "/lines/{id}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	    public JSONObject updateLine(
	    		@PathVariable("id") int id,
	    		@RequestBody JSONObject jsonParam
	    		) {
			try {
				LineEntity st = lineDao.getLine(id);
				
				if(jsonParam.containsKey("code"))
					st.setLineCode(jsonParam.getString("code"));
				if(jsonParam.containsKey("factory"))
					st.setFactoryId(jsonParam.getIntValue("factory"));						
				//other fileds
				if(jsonParam.containsKey("name"))
					st.setLineName(jsonParam.getString("name"));
				if(jsonParam.containsKey("description"))
					st.setLineDescription(jsonParam.getString("description"));
				
				JSONObject res = new JSONObject();
				int rs = lineDao.updateLine(id,st);
				res.put("result",rs);	
		        return ResponseUtil.success(res);			
			} catch (Exception e) {
				JSONObject res = new JSONObject();
				res.put("result",0);
				return ResponseUtil.failureMore(502,e.getMessage(),res);
			}
	    }	
}
