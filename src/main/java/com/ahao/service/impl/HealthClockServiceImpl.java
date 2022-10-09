package com.ahao.service.impl;

import com.ahao.mapper.HealthClockMapper;
import com.ahao.pojo.HealthClock;
import com.ahao.service.HealthClockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HealthClockServiceImpl extends ServiceImpl<HealthClockMapper,HealthClock> implements HealthClockService {
}
