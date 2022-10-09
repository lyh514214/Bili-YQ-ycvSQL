package com.ahao.service;

import com.ahao.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {

    //通过rid查询mid  在role_menu表
    List<Integer> midQuery(Integer i);

    void delMidsByRid(Integer rid);

    void saveMidsByRid(Integer rid, Integer mid);
}
