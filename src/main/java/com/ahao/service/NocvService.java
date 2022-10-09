package com.ahao.service;

import com.ahao.pojo.NocvData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

public interface NocvService extends IService<NocvData> {

    List<NocvData> queryNewAll();

    HashMap<Object,Object> queryToBar();

}
