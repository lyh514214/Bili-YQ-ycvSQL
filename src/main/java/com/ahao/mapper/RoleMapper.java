package com.ahao.mapper;

import com.ahao.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    @Select("select mid from role_menu where rid = #{i}")
    List<Integer> queryByRid(Integer i);

    @Delete("delete from role_menu where rid = #{rid}")
    void delMidsByRid(Integer rid);

    @Insert("insert into role_menu (rid,mid) values (#{rid},#{mid}) ")
    void saveMidsByRid(@Param("rid") Integer rid, @Param("mid") Integer mid);

}
