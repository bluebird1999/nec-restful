package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.globe_sh.cloudplatform.restful.entity.DataEntity;
import com.globe_sh.cloudplatform.restful.entity.DecoderEntity;

@Mapper
public interface DecoderDAO {
 //   @Select("select * from c_data_decoder")
 //   public List < DecoderEntity > getDecoderAll();

    public List < DecoderEntity > getDecoderAll(
    		@Param("station") String station,
    		@Param("device") String device,
    		@Param("datablock") String datablock
    		);
    
    @Select("SELECT * FROM c_data_decoder WHERE id = #{id}")
    public DecoderEntity getDecoderById(@Param("id") int id);

    @Insert("INSERT INTO c_data_decoder (data_code, create_time, data_block, data_name, data_description," +
    		" data_type, data_kind, start_byte, start_bit, data_length, data_precision, data_deviation, " +
    		" data_unit, data_dictionary) " +
            " VALUES (#{data_code}, #{create_time}, #{data_block}, #{data_name}, #{data_description}, " + 
    		" #{data_type}, #{data_kind}, #{start_byte}, #{start_bit}, #{data_length}, #{data_precision}, " + 
            " #{data_deviation}, #{data_unit}, #{data_dictionary})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertDecoder(DecoderEntity se);  
    
    @Delete("DELETE FROM c_data_decoder WHERE id = #{id}")
    public int deleteDecoder(@Param("id") int id); 
    
    @Update("UPDATE c_data_decoder SET data_code=#{st.data_code}, data_block=#{st.data_block}, " +
    		"data_name=#{st.data_name}, data_description=#{st.data_description}, " +
    		"data_type=#{st.data_type}, " +
    		"data_kind=#{st.data_kind}, " +
    		"start_byte=#{st.start_byte}, " +
    		"start_bit=#{st.start_bit}, " +
    		"data_length=#{st.data_length}, " +
    		"data_precision=#{st.data_precision}, " +
    		"data_deviation=#{st.data_deviation}, " +
    		"data_unit=#{st.data_unit}, " +
    		"data_dictionary=#{st.data_dictionary} " +
    		"WHERE id = #{id}")
    public int updateDecoder(@Param("id") int id, @Param("st") DecoderEntity st);      
}