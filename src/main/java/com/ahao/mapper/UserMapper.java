package com.ahao.mapper;

import com.ahao.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    //通过用户名和密码查看用户是否存在
    @Select("select * from user where username= #{username} and password= #{password}")
    User login(@Param("username") String username, @Param("password") String password);

    @Select("select rid from user_role where uid = #{uid}")
    List<Integer> allRidByUid(Integer uid);

    @Delete("delete from user_role where uid = #{uid}")
    void delRidAboutUid(Integer uid);
    @Insert("insert into user_role (uid,rid) values (#{uid},#{rid})")
    void saveRidByUid(@Param("uid") Integer uid, @Param("rid") Integer rid);



}
