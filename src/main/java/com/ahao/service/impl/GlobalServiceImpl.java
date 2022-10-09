package com.ahao.service.impl;

import com.ahao.mapper.GlobalMapper;
import com.ahao.pojo.Global;
import com.ahao.service.GlobalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalServiceImpl extends ServiceImpl<GlobalMapper, Global> implements GlobalService {

    @Autowired
    private GlobalMapper globalMapper;

    @Override
    public List<Global> list207s() {
        List<Global> globals207List = globalMapper.list207s();
        return globals207List;
    }
}
