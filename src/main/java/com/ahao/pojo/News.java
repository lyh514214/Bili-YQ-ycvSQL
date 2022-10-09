package com.ahao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("nocv_news")
public class News {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private String publishby;

}
