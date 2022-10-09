package com.ahao.service.impl;

import com.ahao.mapper.ClassMapper;
import com.ahao.pojo.Class;
import com.ahao.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
}
