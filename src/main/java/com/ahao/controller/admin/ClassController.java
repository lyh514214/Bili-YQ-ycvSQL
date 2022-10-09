package com.ahao.controller.admin;

import com.ahao.pojo.Class;
import com.ahao.service.ClassService;
import com.ahao.vo.ClassVo;
import com.ahao.vo.DataView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Class")
public class ClassController {

    @Autowired
    private ClassService classService;

    //分页+模糊查询
    @RequestMapping("/classByPage")
    @ResponseBody
    public DataView classByPage(ClassVo classVo){
        IPage<Class> page = new Page<>(classVo.getPage(),classVo.getLimit());
        QueryWrapper<Class> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(classVo.getName()),"name",classVo.getName());
        wrapper.orderByDesc("id");
        classService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }


    //新增或更新
    @RequestMapping("/addOrUpdateClassData")
    @ResponseBody
    public DataView addOrUpdateClassData(ClassVo classVo){
        classService.saveOrUpdate(classVo);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("操作成功!");
        return dataView;
    }

    //删除
    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        classService.removeById(id);
        return new DataView(200L,"删除成功！");
    }


}
