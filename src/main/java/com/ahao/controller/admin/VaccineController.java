package com.ahao.controller.admin;

import com.ahao.pojo.Vaccine;
import com.ahao.service.VaccineService;
import com.ahao.vo.DataView;
import com.ahao.vo.VaccineVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    //查  分页+模糊
    @RequestMapping("/vaccineDataByPage")
    @ResponseBody
    public DataView vaccineDataByPage(VaccineVo vaccineVo){
        IPage<Vaccine> page = new Page<>(vaccineVo.getPage(), vaccineVo.getLimit());
        QueryWrapper<Vaccine> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(vaccineVo.getName()),"name",vaccineVo.getName());
        wrapper.like(StringUtils.isNotBlank(vaccineVo.getPhone()),"phone",vaccineVo.getPhone());
        wrapper.orderByDesc("id");
        vaccineService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    //增
    @RequestMapping("/addVaccine")
    @ResponseBody
    public DataView addVaccine(VaccineVo vaccineVo){
        vaccineService.save(vaccineVo);
        return new DataView(200,"添加成功!");
    }

    //改
    @RequestMapping("/updateVaccine")
    @ResponseBody
    public DataView updateVaccine(VaccineVo vaccineVo){
        vaccineService.updateById(vaccineVo);
        return new DataView(200,"修改成功!");
    }

    //删
    @RequestMapping("/deleteVaccine")
    @ResponseBody
    public DataView deleteVaccine(Integer id){
        vaccineService.removeById(id);
        return new DataView(200,"删除成功!");
    }

}
