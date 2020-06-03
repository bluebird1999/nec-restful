package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.globe_sh.cloudplatform.restful.entity.DeviceEntity;
import com.globe_sh.cloudplatform.restful.entity.LineEntity;

@Mapper
public interface LineDAO {
    @Select("select * from c_line")
    public List < LineEntity > getLineAll();

    public Page < LineEntity > getLineAllParam(
    		/*
    		@Param("factory") String factory,
    		@Param("line") String line,
    		@Param("device") String device,
    		*/
    		@Param("factory") String factory
    		);
    
    @Select("SELECT * FROM c_line WHERE id = #{id}")
    public LineEntity getLine(@Param("id") int id);

    @Insert("INSERT INTO c_line (line_code, create_time, factory_id, line_name, line_description) " +
            " VALUES (#{line_code}, #{create_time}, #{factory_id}, #{line_name}, #{line_description})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertLine(LineEntity se);  
    
    @Delete("DELETE FROM c_line WHERE id = #{id}")
    public int deleteLine(@Param("id") int id); 
    
    @Update("UPDATE c_line SET line_code=#{st.line_code}, factory_id=#{st.factory_id}, " +
    		"line_name=#{st.line_name}, line_description=#{st.line_description} " +
    		"WHERE id = #{id}")
    public int updateLine(@Param("id") int id, @Param("st") LineEntity st);          
    
}