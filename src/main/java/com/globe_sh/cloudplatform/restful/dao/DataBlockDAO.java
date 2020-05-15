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

    @Select("SELECT * FROM c_data_block WHERE id = #{id}")
    public DataBlockEntity getDataBlock(@Param("id") int id);

    @Insert("INSERT INTO c_data_block (data_block_code, create_time, device_id, data_block_name, data_block_description, data_block_length) " +
            " VALUES (#{data_block_code}, #{create_time}, #{device_id}, #{data_block_name}, #{data_block_description}, #{data_block_length})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertDataBlock(DataBlockEntity se);  
    
    @Delete("DELETE FROM c_data_block WHERE id = #{id}")
    public int deleteDataBlock(@Param("id") int id); 
    
    @Update("UPDATE c_data_block SET data_block_code=#{st.data_block_code}, device_id=#{st.device_id}, " +
    		"data_block_name=#{st.data_block_name}, data_block_description=#{st.data_block_description}, " +
    		"data_block_length=#{st.data_block_length} " +
    		"WHERE id = #{id}")
    public int updateDataBlock(@Param("id") int id, @Param("st") DataBlockEntity st);          
}