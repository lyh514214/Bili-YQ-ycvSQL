package com.ahao.service.impl;

import com.ahao.mapper.VaccineMapper;
import com.ahao.pojo.Vaccine;
import com.ahao.service.VaccineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VaccineServiceImpl extends ServiceImpl<VaccineMapper, Vaccine> implements VaccineService {
}
