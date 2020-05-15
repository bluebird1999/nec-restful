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

    @Select("SELECT * FROM c_device WHERE id = #{id}")
    public DeviceEntity getDevice(@Param("id") int id);

    @Insert("INSERT INTO c_device (device_code, create_time, line_id, device_name, device_description,  " +
    		" device_maker, device_id, device_version, com_type, com_address, com_port, com_protocol, " +
    		" update_rate, reconnection_time, " +
    		" parameter1, parameter2, parameter3, parameter4, parameter5, parameter6, parameter7, " +
    		" parameter8, parameter9) " + 
            " VALUES (#{device_code}, #{create_time}, #{line_id}, #{device_name}, #{device_description}, " +
            " #{device_maker}, #{device_id}, #{device_version}, #{com_type}, #{com_address}, #{com_port}, #{com_protocol}, " +
            " #{update_rate}, #{reconnection_time}, " +
            " #{parameter1}, #{parameter2}, #{parameter3}, #{parameter4}, #{parameter5}, #{parameter6}, #{parameter7}," +
            " #{parameter8}, #{parameter9}) ")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", keyColumn="id", before=false, resultType=int.class)
    public int insertDevice(DeviceEntity se);  
    
    @Delete("DELETE FROM c_device WHERE id = #{id}")
    public int deleteDevice(@Param("id") int id); 
    
    @Update("UPDATE c_device SET device_code=#{st.device_code}, line_id=#{st.line_id}, " +
    		"device_name=#{st.device_name}, device_description=#{st.device_description}, " +
    		"device_maker=#{st.device_maker}, device_id=#{st.device_id}, " +
    		"device_version=#{st.device_version}, com_type=#{st.com_type}, " +
    		"com_address=#{st.com_address}, com_port=#{st.com_port}, com_protocol=#{st.com_protocol}," +
    		"update_rate=#{st.update_rate}, reconnection_time=#{st.reconnection_time}, " +
    		"parameter1=#{st.parameter1}, parameter2=#{st.parameter2}, " +
    		"parameter3=#{st.parameter3}, parameter4=#{st.parameter4}, " +
    		"parameter5=#{st.parameter5}, parameter6=#{st.parameter6}, " +
    		"parameter7=#{st.parameter7}, parameter8=#{st.parameter8}, " +
    		"parameter9=#{st.parameter9} " +
    		"WHERE id = #{id}")
    public int updateDevice(@Param("id") int id, @Param("st") DeviceEntity st);          
    
}