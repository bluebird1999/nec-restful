package com.globe_sh.cloudplatform.restful.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
import com.globe_sh.cloudplatform.restful.entity.DeviceEntity;

@Mapper
public interface DeviceDAO {
    @Select("select * from c_device")
    public List < DeviceEntity > getDeviceAll();

    @Select("SELECT * FROM c_device WHERE device_code = #{code}")
    public DeviceEntity getDeviceByCode(@Param("code") String code);
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