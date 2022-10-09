package com.ahao.controller.admin;

import com.ahao.pojo.Class;
import com.ahao.pojo.College;
import com.ahao.pojo.Role;
import com.ahao.pojo.User;
import com.ahao.service.ClassService;
import com.ahao.service.CollegeService;
import com.ahao.service.RoleService;
import com.ahao.service.UserService;
import com.ahao.vo.DataView;
import com.ahao.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private RoleService roleService;

    //分页+模糊+跨表
    @RequestMapping("/loadAllUser")
    @ResponseBody
    public DataView dataView(UserVo userVo){
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(userVo.getUsername()),"username",userVo.getUsername());
        wrapper.like(StringUtils.isNotBlank(userVo.getPhone()),"phone",userVo.getPhone());
        userService.page(page,wrapper);
        //装载用户的 班级名称
        for (User user: page.getRecords()){
            if (user.getClassId()!=null){
                Class aClass = classService.getById(user.getClassId());
                user.setClassName(aClass.getName());
            }
        }
        //装载用户的 学院名称
        for (User user : page.getRecords()){
            if (user.getCollegeId()!=null){
                College aCollege = collegeService.getById(user.getCollegeId());
                user.setCollegeName(aCollege.getName());
            }
        }
        //装载用户的 老师名称
        for (User user : page.getRecords()){
            if (user.getTeacherId()!=null){
                User aTeacher = userService.getById(user.getTeacherId());
                user.setTeacherName(aTeacher.getUsername());
            }
        }
        return new DataView(page.getTotal(),page.getRecords());
    }

    //添加 所属班级下拉
    @RequestMapping("/listAllClass")
    @ResponseBody
    public List<Class> listAllClass(){
        return classService.list();
    }
    //添加 所属学院下拉
    @RequestMapping("/listAllCollege")
    @ResponseBody
    public List<College> listAllCollege(){
        return collegeService.list();
    }
    //添加  所属教师下拉
    @RequestMapping("/listAllTeacher")
    @ResponseBody
    public List<User> listAllTeacher(){
        return userService.list();
    }

    //新增
    @RequestMapping("/addUser")
    @ResponseBody
    public DataView addUser(UserVo userVo){
        userService.save(userVo);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加成功！");
        return dataView;
    }

    //修改
    @RequestMapping("/updateUser")
    @ResponseBody
    public DataView updateUser(UserVo userVo){
        userService.updateById(userVo);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改成功！");
        return dataView;
    }

    //删除
    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public DataView deleteUser(@PathVariable("id") Integer id){
        userService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除成功！");
        return dataView;
    }

    //重置密码 123456
    @RequestMapping("/resetPwd")
    @ResponseBody
    public DataView resetPwd(Integer id){
        User user = new User();
        user.setId(Long.valueOf(id));
        user.setPassword("123456");
        userService.updateById(user);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("重置密码成功！");
        return dataView;
    }

    //初始化角色菜单
    @RequestMapping("/initRoleByUserId")
    @ResponseBody
    public DataView initRoleByUserId(Integer id){
        // 1. 查询所有的角色
        List<Map<String, Object>> maps = roleService.listMaps();
        // 2. 查询这个用户锁拥有的角色
        List<Integer> currentRids =  userService.allRidByUid(id);
        for (Map<String, Object> map : maps){
            boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rId : currentRids){
                if (rId.equals(roleId)) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataView((long) maps.size(),maps);
    }
    //保存用户角色
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public DataView saveUserRole(Integer uid,Integer rids[]){
        if (rids!=null && rids.length>0){
            userService.delRidAboutUid(uid);
            for (Integer rid : rids){
                userService.saveRidByUid(uid,rid);
            }
        }
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("分配角色成功！");
        return dataView;
    }


}
