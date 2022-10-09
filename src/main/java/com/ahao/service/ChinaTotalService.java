package com.ahao.service;

import com.ahao.pojo.ChinaTotal;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChinaTotalService extends IService<ChinaTotal> {
    ChinaTotal queryNew();
}
