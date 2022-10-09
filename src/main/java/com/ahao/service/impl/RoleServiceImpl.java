package com.ahao.service.impl;

import com.ahao.mapper.RoleMapper;
import com.ahao.pojo.Role;
import com.ahao.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Integer> midQuery(Integer i) {
        return roleMapper.queryByRid(i);
    }

    @Override
    //删除mids
    public void delMidsByRid(Integer rid) {
        roleMapper.delMidsByRid(rid);
    }

    @Override
    //保存mids
    public void saveMidsByRid(Integer rid, Integer mid) {
        roleMapper.saveMidsByRid(rid,mid);
    }

}
