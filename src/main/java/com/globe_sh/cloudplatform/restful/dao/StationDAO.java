package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import com.globe_sh.cloudplatform.restful.entity.StationEntity;

@Mapper
public interface StationDAO {
    @Select("select * from c_station")
    public List < StationEntity > getStationAll();

    @Select("SELECT * FROM c_station WHERE station_code = #{code}")
    public StationEntity getStationByCode(@Param("code") String code);
    
    @Insert("INSERT INTO c_station (station_code, create_time, factory_code, station_name, station_description, station_status) " +
            " VALUES (#{station_code}, #{create_time}, #{factory_code}, #{station_name}, #{station_description}, #{station_status})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertStation(StationEntity se);  
    
    @Delete("DELETE FROM c_station WHERE station_code = #{code}")
    public int deleteStation(@Param("code") String code); 
    
    @Update("UPDATE c_station SET station_code=#{st.station_code}, factory_code=#{st.factory_code}, " +
    		"station_name=#{st.station_name}, station_description=#{st.station_description} " +
    		"WHERE station_code = #{code}")
    public int updateStation(@Param("code") String code, @Param("st") StationEntity st);         
}