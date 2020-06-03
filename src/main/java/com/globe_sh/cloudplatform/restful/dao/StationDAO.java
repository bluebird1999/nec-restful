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
import com.globe_sh.cloudplatform.restful.entity.StationEntity;

@Mapper
public interface StationDAO {
    @Select("select * from c_station")
    public Page < StationEntity > getStationAll();

    @Select("SELECT * FROM c_station WHERE id = #{id}")
    public StationEntity getStation(@Param("id") int id);
    
    @Insert("INSERT INTO c_station (station_code, create_time, factory_id, line_id, device_id, fid, lid, did, device_number, station_name, station_description, station_status) " +
            " VALUES (#{station_code}, #{create_time}, #{factory_id}, #{line_id}, #{device_id}, #{fid}, #{lid}, #{did}, #{device_number}, #{station_name}, #{station_description}, #{station_status})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertStation(StationEntity se);  
    
    @Delete("DELETE FROM c_station WHERE id = #{id}")
    public int deleteStation(@Param("id") int id); 
    
    @Update("UPDATE c_station SET station_code=#{st.station_code}, factory_id=#{st.factory_id}, " +
    		"line_id=#{st.line_id}, device_id=#{st.device_id}, " + 
    		"fid=#{st.fid}, lid=#{st.lid}, did=#{st.did}, device_number=#{st.device_number}," +
    		"station_name=#{st.station_name}, station_description=#{st.station_description} " +
    		"WHERE id = #{id}")
    public int updateStation(@Param("id") int id, @Param("st") StationEntity st);         
}