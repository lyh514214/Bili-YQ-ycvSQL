package com.ahao.mapper;

import com.ahao.pojo.Global;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GlobalMapper extends BaseMapper<Global> {

    @Select("select * from nocv_global_data order by id desc limit 207")
    List<Global> list207s();

}
