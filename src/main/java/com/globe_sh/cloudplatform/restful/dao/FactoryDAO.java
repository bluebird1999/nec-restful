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
import com.globe_sh.cloudplatform.restful.entity.FactoryEntity;

@Mapper
public interface FactoryDAO {
    @Select("select * from c_factory")
    public Page < FactoryEntity > getFactoryAll();

    @Select("SELECT * FROM c_factory WHERE id = #{id}")
    public FactoryEntity getFactory(@Param("id") int id);
    
    @Insert("INSERT INTO c_factory (factory_code, create_time, factory_name, factory_description) " +
            " VALUES (#{factory_code}, #{create_time}, #{factory_name}, #{factory_description})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertFactory(FactoryEntity se);  
    
    @Delete("DELETE FROM c_factory WHERE id = #{id}")
    public int deleteFactory(@Param("id") int id); 
    
    @Update("UPDATE c_factory SET factory_code=#{st.factory_code}, " +
    		"factory_name=#{st.factory_name}, factory_description=#{st.factory_description} " +
    		"WHERE id = #{id}")
    public int updateFactory(@Param("id") int id, @Param("st") FactoryEntity st);   
}