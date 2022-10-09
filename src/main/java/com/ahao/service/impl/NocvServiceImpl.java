package com.ahao.service.impl;

import com.ahao.mapper.NocvMapper;
import com.ahao.pojo.NocvData;
import com.ahao.service.NocvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NocvServiceImpl extends ServiceImpl<NocvMapper, NocvData> implements NocvService {

    @Autowired
    private NocvMapper nocvMapper;

    @Override
    //地图、饼状图逻辑
    public List<NocvData> queryNewAll() {
        List<NocvData> list = nocvMapper.queryNewAll();
        return list;
    }

    @Override
    //柱状图逻辑
    public HashMap<Object, Object> queryToBar() {
        List<NocvData> list = nocvMapper.queryNewAll();
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<String> cityList = new ArrayList<>();
        ArrayList<Integer> dataList = new ArrayList<>();
        for (NocvData nocvData : list) {
            cityList.add(nocvData.getName());
            dataList.add(nocvData.getValue());
        }
        map.put("cityL",cityList);
        map.put("dataL",dataList);
        return map;
    }
}
