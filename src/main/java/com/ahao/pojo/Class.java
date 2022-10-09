package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("class")
public class Class {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer collegeId;

    @TableField(exist = false)
    private String collegeName;

}
