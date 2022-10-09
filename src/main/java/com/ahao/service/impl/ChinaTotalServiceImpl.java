package com.ahao.service.impl;

import com.ahao.mapper.ChinaTotalMapper;
import com.ahao.pojo.ChinaTotal;
import com.ahao.service.ChinaTotalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChinaTotalServiceImpl extends ServiceImpl<ChinaTotalMapper, ChinaTotal> implements ChinaTotalService {

    @Autowired
    private ChinaTotalMapper chinaTotalMapper;

    @Override
    public ChinaTotal queryNew() {
        ChinaTotal chinaTotal = chinaTotalMapper.queryNewSql();
        return chinaTotal;
    }
}
