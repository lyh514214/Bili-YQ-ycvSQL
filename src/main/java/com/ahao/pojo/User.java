package com.ahao.pojo;

import com.ahao.pojo.Enums.SexEnums;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String password;

    //基本信息
    private String nickname;
    private SexEnums sex;
    private Integer age;
    private String address;
    private String img;
    private String phone;
    private String card;

    //外键
    private Integer classId;
    private Integer teacherId;
    private Integer collegeId;
    private String salt;

    //拓展(不存在)
    @TableField(exist = false)
    private String className;  //class表 ： name字段
    @TableField(exist = false)
    private String collegeName;  //college表 ：  name字段
    @TableField(exist = false)
    private String teacherName;  //user表  :  username字段

}
