package com.ahao.mapper;

import com.ahao.pojo.NocvData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface NocvMapper extends BaseMapper<NocvData> {
    List<NocvData> queryNewAll();
}
