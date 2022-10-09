package com.ahao.service;

import com.ahao.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {
    User login(String username, String password);

    //通过uid查询rid 在user_role表中
    List<Integer> allRidByUid(Integer uid);

    void delRidAboutUid(Integer uid);
    void saveRidByUid(Integer uid, Integer rid);


}
