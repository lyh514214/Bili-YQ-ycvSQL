package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@TableName("china_total")
public class ChinaTotal {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer confirm;
    private Integer input;
    private Integer severe;
    private Integer heal;
    private Integer dead;
    private Integer suspect;
    @TableField("update_time")
    private Date updateTime;

    public ChinaTotal() {
    }

    public ChinaTotal(Integer confirm, Integer input, Integer heal, Integer dead) {
        this.confirm = confirm;
        this.input = input;
        this.heal = heal;
        this.dead = dead;
    }
}
