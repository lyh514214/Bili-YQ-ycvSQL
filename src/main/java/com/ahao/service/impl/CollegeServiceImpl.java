package com.ahao.service.impl;

import com.ahao.mapper.CollegeMapper;
import com.ahao.pojo.College;
import com.ahao.service.CollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper,College> implements CollegeService {
}
