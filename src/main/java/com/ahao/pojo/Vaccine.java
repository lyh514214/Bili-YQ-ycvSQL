package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("vaccine")
public class Vaccine {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String phone;
    private String card;
    private String zhenci;
    private String pici;
    private String changjia;
    private String danwei;
    private Date createTime;

}
