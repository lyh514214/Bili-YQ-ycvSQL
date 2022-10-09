package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("nocv_data")
public class NocvData {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer value;
    @TableField("update_time")
    private Date updateTime;

    public NocvData() {
    }

    public NocvData(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
