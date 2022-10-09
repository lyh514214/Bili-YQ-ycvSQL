package com.ahao.service.impl;

import com.ahao.mapper.UserMapper;
import com.ahao.pojo.User;
import com.ahao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.login(username,password);
    }

    //查询某用户所拥有的角色id
    @Override
    public List<Integer> allRidByUid(Integer uid) {
        return userMapper.allRidByUid(uid);
    }

    //先清空之前的数据再插入现在的数据
    @Override
    public void delRidAboutUid(Integer uid) {
        userMapper.delRidAboutUid(uid);
    }
    @Override
    public void saveRidByUid(Integer uid, Integer rid) {
        userMapper.saveRidByUid(uid,rid);
    }

}
