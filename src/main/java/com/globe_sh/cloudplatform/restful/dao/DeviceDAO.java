package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import com.globe_sh.cloudplatform.restful.entity.DeviceEntity;

@Mapper
public interface DeviceDAO {
    @Select("select * from c_device")
    public List < DeviceEntity > getDeviceAll();

    @Select("SELECT * FROM c_device WHERE device_code = #{code}")
    public DeviceEntity getDeviceByCode(@Param("code") String code);

    @Insert("INSERT INTO c_device (device_code, create_time, station_code, device_name, device_description) " +
            " VALUES (#{device_code}, #{create_time}, #{station_code}, #{device_name}, #{device_description})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertDevice(DeviceEntity se);  
    
    @Delete("DELETE FROM c_device WHERE device_code = #{code}")
    public int deleteDevice(@Param("code") String code); 
    
    @Update("UPDATE c_device SET device_code=#{st.device_code}, station_code=#{st.station_code}, " +
    		"device_name=#{st.device_name}, device_description=#{st.device_description} " +
    		"WHERE device_code = #{code}")
    public int updateDevice(@Param("code") String code, @Param("st") DeviceEntity st);          
    
}