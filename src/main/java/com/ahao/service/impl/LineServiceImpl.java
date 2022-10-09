package com.ahao.service.impl;

import com.ahao.mapper.LineMapper;
import com.ahao.pojo.LineData;
import com.ahao.service.LineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class LineServiceImpl extends ServiceImpl<LineMapper, LineData> implements LineService {

    @Autowired
    private LineMapper lineMapper;

    @Override
    public List<LineData> queryLineAll() {
        return lineMapper.queryLineAll();
    }

    @Override
    public HashMap<Object, Object> toLines() {
        List<LineData> list = lineMapper.queryLineAll();
        //反转函数！！！太屌啦，直接让倒序查询后的列表结果变成正序
        Collections.reverse(list);
        ArrayList<Integer> confirm = new ArrayList<>();
        ArrayList<Integer> isolation = new ArrayList<>();
        ArrayList<Integer> cure = new ArrayList<>();
        ArrayList<Integer> dead = new ArrayList<>();
        ArrayList<Integer> similar = new ArrayList<>();
        for (LineData lineData : list) {
            confirm.add(lineData.getConfirm());
            isolation.add(lineData.getIsolation());
            cure.add(lineData.getCure());
            dead.add(lineData.getDead());
            similar.add(lineData.getSimilar());
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("confirmL",confirm);
        map.put("isolationL",isolation);
        map.put("cureL",cure);
        map.put("deadL",dead);
        map.put("similarL",similar);
        return map;
    }
}
