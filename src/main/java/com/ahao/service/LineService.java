package com.ahao.service;

import com.ahao.pojo.LineData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

public interface LineService extends IService<LineData> {

    //查询
    List<LineData> queryLineAll();

    HashMap<Object, Object> toLines();
}
