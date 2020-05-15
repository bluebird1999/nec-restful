package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.globe_sh.cloudplatform.restful.entity.DataEntity;;

@Mapper
public interface DataDAO {
/*	@Select("<script>select b.sample_time from i_decoded_data_batch b " +
 			"<where><if test='station!= null'>AND b.station=#{station}</if><if test='station!= null'>AND b.device=#{device}</if></where>" +
    		"</script>"
    		)	
*/    		
 /*	
  * @Select("&lt;script>select b.sample_time,b.station,b.device,b.data_block,d.code,d.value " +
    		"FROM i_decoded_data d,i_decoded_data_batch b " +
 			"&lt;where>d.data_batch_id=b.data_batch_id" +
    		"&lt;if test='station!= null'> and b.station=#{station}&lt;/if>" +
    		"&lt;if test='device!= null'> and b.device=#{device}&lt;/if>" +
    		"&lt;if test='datablock!= null'> and b.data_block=#{datablock}&lt;/if>" +
    		"&lt;if test='data!= null'> and d.code=#{data}&lt;/if>" +
    		"&lt;if test='start!= null'> and b.sample_time>=#{start}&lt;/if>" +
    		"&lt;if test='end!= null'> and b.sample_time<=#{end}&lt;/if>" +
    		"&lt;/where>" +
    		"&lt;/script>"
    		)
*/
/*    
 	@Select("select * from (SELECT i_decoded_data_batch.sample_time,i_decoded_data_batch.station," +
    		"i_decoded_data_batch.device,i_decoded_data_batch.data_block,i_decoded_data.code,i_decoded_data.value " +
    		"FROM i_decoded_data INNER JOIN i_decoded_data_batch ON " +
    		"i_decoded_data.data_batch_id = i_decoded_data_batch.data_batch_id) as abc where #{sql}")
*/    		
//    public List <DataEntity> getData(@Param("st") String st);
    public List < DataEntity > getData(
    		@Param("factory") String factory,
    		@Param("line") String line,
    		@Param("device") String device,
    		@Param("data_block") String data_block,
    		@Param("data") String data,
    		@Param("start") String start,
    		@Param("end") String end
    		);

//	public List <DataEntity> getData(@Param("device") String device);

/*
    @Delete("DELETE FROM raw WHERE id = #{id}")
    public int deleteById(String id);

    @Insert("INSERT INTO raw(id, first_name, last_name,email_address) " +
        " VALUES (#{id}, #{firstName}, #{lastName}, #{emailId})")
    public int insert(FactoryEntity raw);

    @Update("Update raw set first_name=#{firstName}, " +
        " last_name=#{lastName}, email_address=#{emailId} where id=#{id}")
    public int update(FactoryEntity employee);
*/    
}