package com.ahao.controller.admin;

import com.ahao.pojo.Menu;
import com.ahao.pojo.User;
import com.ahao.service.MenuService;
import com.ahao.service.RoleService;
import com.ahao.service.UserService;
import com.ahao.utils.TreeNode;
import com.ahao.utils.TreeNodeBuilder;
import com.ahao.vo.DataView;
import com.ahao.vo.MenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.nio.file.Watchable;
import java.util.*;

//菜单管理
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //动态index左侧菜单栏
    @RequestMapping("/loadIndexLeftMenuJson")
    @ResponseBody
    public DataView loadIndexLeftMenuJson(HttpSession httpSession){
//        List<Menu> list = menuService.list();

        List<Menu> list = null;

        // 1. 获取当前用户id
        User user = (User) httpSession.getAttribute("user");
        Integer userId = Math.toIntExact(user.getId());

        if (user.getId().equals(1L)){
            list = menuService.list();    //超级管理员获取全部菜单
        }else {                           //普通用户通过角色来判断菜单
            // 2. 获取当前用户角色
            List<Integer> rids = userService.allRidByUid(userId);
            // 3. 通过角色查询菜单
            HashSet<Integer> midSet = new HashSet<>();
            for (Integer rid : rids){
                List<Integer> mids = roleService.midQuery(rid);
                //去重
                midSet.addAll(mids);
            }
            // 4. 通过mid查询menu
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.in("id",midSet);
            list = menuService.list(wrapper);
        }

        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Menu menu:list){
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            String icon = menu.getIcon();
            String href = menu.getHref();
            boolean open = menu.getOpen().equals(1);
            treeNodes.add(new TreeNode(id,pid,title,icon,href,open));
        }
        List<TreeNode> build = TreeNodeBuilder.build(treeNodes, 0);
        return new DataView(build);
    }

    /*增删改查
    * */
    //分页+模糊查询
    @RequestMapping("/menuDataByPage")
    @ResponseBody
    public DataView menuData(MenuVo menuVo){
        IPage<Menu> page = new Page<>(menuVo.getPage(),menuVo.getLimit());
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(menuVo.getTitle()),"title",menuVo.getTitle());
        wrapper.orderByAsc("ordernum");
        menuService.page(page,wrapper);
        DataView dataView = new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }

    //树形态下拉菜单
    @RequestMapping("/loadMenuManagerLeftTreeJson")
    @ResponseBody
    public DataView loadMenuManagerLeftTreeJson(){
        //查询所有的菜单栏
        List<Menu> list = menuService.list();
        //构造层级关系
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu menu:list){
            boolean open = menu.getOpen() == 1;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),open));
        }
        return new DataView(treeNodes);
    }

    //初始化排序码
    @RequestMapping("/loadMenuMaxOrderNum")
    @ResponseBody
    public Map<String,Integer> loadMenuMaxOrderNum(){
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("ordernum");
        Map<String, Integer> map = new HashMap<>();
        IPage<Menu> page = new Page<>(1,1);
        List<Menu> list = menuService.page(page, wrapper).getRecords();

        map.put("value",list.get(0).getOrdernum()+1);
        return map;
    }

    //添加
    @RequestMapping("/addMenu")
    @ResponseBody
    public DataView addMenu(MenuVo menuVo){
        menuVo.setType("menu");
        DataView dataView = new DataView();
        boolean save = menuService.save(menuVo);
        if (save){
            dataView.setCode(200);
            dataView.setMsg("添加成功！");
            return dataView;
        }
        dataView.setCode(500);
        dataView.setMsg("添加失败!");
        return dataView;
    }

    //修改
    @RequestMapping("/updateMenu")
    @ResponseBody
    public DataView dataView(MenuVo menuVo){
        DataView dataView = new DataView();
        menuService.updateById(menuVo);
        dataView.setCode(200);
        dataView.setMsg("修改成功！");
        return dataView;
    }

    //是否存在子节点
    @RequestMapping("/checkMenuHasChildrenNode")
    @ResponseBody
    public Map<String,Object> checkMenuHasChildrenNode(MenuVo menuVo){
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",menuVo.getId());
        List<Menu> list = menuService.list(wrapper);
        if (list.size()>0){
            map.put("value",true);
            return map;
        }else {
            map.put("value",false);
            return map;
        }
    }

    //删除  传来id 返回code和msg
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public DataView deleteMenu(Integer id){
        DataView dataView = new DataView();
        boolean del = menuService.removeById(id);
        if (del){
            dataView.setCode(200);
            dataView.setMsg("删除成功！");
            return dataView;
        }else {
            dataView.setCode(500);
            dataView.setMsg("删除失败！");
            return dataView;
        }
    }
}
