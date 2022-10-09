package com.ahao.controller.admin;

import com.ahao.pojo.Menu;
import com.ahao.pojo.Role;
import com.ahao.service.MenuService;
import com.ahao.service.RoleService;
import com.ahao.utils.TreeNode;
import com.ahao.vo.DataView;
import com.ahao.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    //分页+模糊查询
    @RequestMapping("/loadAllRole")
    @ResponseBody
    public DataView loadAllRole(RoleVo roleVo){
        IPage<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        wrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        wrapper.orderByAsc("id");
        roleService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    //添加
    @RequestMapping("/addRole")
    @ResponseBody
    public DataView addRole(RoleVo roleVo) {
        DataView dataView = new DataView();
        boolean save = roleService.save(roleVo);
        if (save){
            dataView.setCode(200);
            dataView.setMsg("添加成功！");
            return dataView;
        }
        dataView.setCode(500);
        dataView.setMsg("添加失败！");
        return dataView;
    }

    //删除
    @RequestMapping("/deleteRole")
    @ResponseBody
    public DataView deleteRole(Integer id){
        DataView dataView = new DataView();
        boolean del = roleService.removeById(id);
        if (del){
            dataView.setCode(200);
            dataView.setMsg("删除成功！");
            return dataView;
        }
        dataView.setCode(500);
        dataView.setMsg("删除失败！");
        return dataView;
    }

    //更新
    @RequestMapping("/updateRole")
    @ResponseBody
    public DataView updateRole(RoleVo roleVo){
        DataView dataView = new DataView();
        boolean upd = roleService.updateById(roleVo);
        if (upd){
            dataView.setCode(200);
            dataView.setMsg("更新成功！");
            return dataView;
        }else {
            dataView.setCode(500);
            dataView.setData("更新失败！");
            return dataView;
        }
    }

    //分配权限 ： 1.初始化分配菜单树
    @RequestMapping("/initPermissionByRoleId")
    @ResponseBody
    public DataView initPermissionByRoleId(Integer roleId){
        // 1. 查询所有的菜单
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        List<Menu> allMenuList = menuService.list();
        // 2. 根据角色rid查询所有的菜单mid     get: mid-->menu表的id
        List<Integer> currentHasMids = roleService.midQuery(roleId);
        // 3. 通过mid 去查menu表里面具体的内容
        List<Menu> currentMenuList = null;
        if (currentHasMids.size()>0) {        //如果当前角色(rid)有权限(mid)
            currentMenuList = menuService.listByIds(currentHasMids);
        }else {
            currentMenuList = new ArrayList<>();
        }
        // 4. currentMenuList包装成treeNode格式 返回给前台   put: (id,pid,title,spread,checkArr)
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Menu allMenus : allMenuList){
            String checkArr = "0";
            for (Menu currentMenus : currentMenuList){
                if (allMenus.getId().equals(currentMenus.getId())) {
                    checkArr = "1";       //0为选过，1为没选过  用于前台简单显示原本这个角色是否有这个菜单权限
                    break;
                }
            }
            boolean open = allMenus.getOpen()==null || allMenus.getOpen() == 1;
            treeNodes.add(new TreeNode(allMenus.getId(),allMenus.getPid(),allMenus.getTitle(),open,checkArr));
        }
        return new DataView(treeNodes);
    }

    //分配权限
    @RequestMapping("/saveRolePermission")
    @ResponseBody
    public DataView saveRolePermission(Integer rid,Integer[] mids){

        // 1. 根据rid删除原本的存在的mid
        roleService.delMidsByRid(rid);
        // 2. 保存前台返回的mids[]
        if (mids!=null && mids.length>0){
            for (Integer mid : mids){
                roleService.saveMidsByRid(rid,mid);
            }
        }
        DataView dataView = new DataView();
        dataView.setMsg("权限修改成功!");
        return dataView;
    }
}
