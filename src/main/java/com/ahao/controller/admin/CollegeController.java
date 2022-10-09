package com.ahao.controller.admin;

import com.ahao.pojo.College;
import com.ahao.service.CollegeService;
import com.ahao.vo.CollegeVo;
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
@RequestMapping("/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    //模糊+分页查询
    @RequestMapping("/collegeByPage")
    @ResponseBody
    public DataView collegeByPage(CollegeVo collegeVo){
        IPage<College> page = new Page<>(collegeVo.getPage(),collegeVo.getLimit());
        QueryWrapper<College> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(collegeVo.getName()),"name",collegeVo.getName());
        wrapper.orderByDesc("id");
        collegeService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    //新增或更新
    @RequestMapping("/addOrUpdateCollegeData")
    @ResponseBody
    public DataView addOrUpdateCollegeData(CollegeVo collegeVo){
        collegeService.saveOrUpdate(collegeVo);
        return new DataView(200,"操作成功！");
    }

    //删除
    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        collegeService.removeById(id);
        return new DataView(200,"删除成功！");
    }

}
