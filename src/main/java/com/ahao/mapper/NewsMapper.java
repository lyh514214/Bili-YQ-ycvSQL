package com.ahao.mapper;

import com.ahao.pojo.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsMapper extends BaseMapper<News> {

    @Select("select * from nocv_news order by id desc limit 3")
    List<News> showNews3();
}
