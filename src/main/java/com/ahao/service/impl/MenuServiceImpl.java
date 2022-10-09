package com.ahao.service.impl;

import com.ahao.mapper.MenuMapper;
import com.ahao.pojo.Menu;
import com.ahao.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
