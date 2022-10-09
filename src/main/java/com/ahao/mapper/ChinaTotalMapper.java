package com.ahao.mapper;

import com.ahao.pojo.ChinaTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface ChinaTotalMapper extends BaseMapper<ChinaTotal> {
    @Select("select * from china_total order by id desc limit 1")
    ChinaTotal queryNewSql();
}
