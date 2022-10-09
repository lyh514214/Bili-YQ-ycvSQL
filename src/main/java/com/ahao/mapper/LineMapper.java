package com.ahao.mapper;

import com.ahao.pojo.LineData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface LineMapper extends BaseMapper<LineData> {
    List<LineData> queryLineAll();
}
