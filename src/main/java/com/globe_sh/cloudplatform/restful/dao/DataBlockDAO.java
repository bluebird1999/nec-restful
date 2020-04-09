package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import com.globe_sh.cloudplatform.restful.entity.DataBlockEntity;

@Mapper
public interface DataBlockDAO {
    @Select("select * from c_data_block")
    public List < DataBlockEntity > getDataBlockAll();

    @Select("SELECT * FROM c_data_block WHERE data_block_code = #{code}")
    public DataBlockEntity getDataBlockByCode(@Param("code") String code);

    @Insert("INSERT INTO c_data_block (data_block_code, create_time, device_code, data_block_name, data_block_description) " +
            " VALUES (#{data_block_code}, #{create_time}, #{device_code}, #{data_block_name}, #{data_block_description})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertDataBlock(DataBlockEntity se);  
    
    @Delete("DELETE FROM c_data_block WHERE data_block_code = #{code}")
    public int deleteDataBlock(@Param("code") String code); 
    
    @Update("UPDATE c_data_block SET data_block_code=#{st.data_block_code}, device_code=#{st.device_code}, " +
    		"data_block_name=#{st.data_block_name}, data_block_description=#{st.data_block_description} " +
    		"WHERE data_block_code = #{code}")
    public int updateDataBlock(@Param("code") String code, @Param("st") DataBlockEntity st);          
}