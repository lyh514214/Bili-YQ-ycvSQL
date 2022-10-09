package com.ahao.controller.admin;

import com.ahao.pojo.NucleicAci;
import com.ahao.service.NucleicAciService;
import com.ahao.vo.DataView;
import com.ahao.vo.NucleicAciVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/nucleicAci")
public class NucleicAciController {

    @Autowired
    private NucleicAciService nucleicAciService;

    //分页+模糊查询
    @RequestMapping("/nucleicAciDataByPage")
    @ResponseBody
    public DataView nucleicAciDataByPage(NucleicAciVo nucleicAciVo){
        IPage<NucleicAci> page = new Page<>(nucleicAciVo.getPage(),nucleicAciVo.getLimit());
        QueryWrapper<NucleicAci> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(nucleicAciVo.getName()),"name",nucleicAciVo.getName());
        wrapper.like(StringUtils.isNotBlank(nucleicAciVo.getPhone()),"phone",nucleicAciVo.getPhone());
        wrapper.orderByDesc("id");
        nucleicAciService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    //添加
    @RequestMapping("/addNucleicAci")
    @ResponseBody
    public DataView addNucleicAci(NucleicAciVo nucleicAciVo){
        nucleicAciService.save(nucleicAciVo);
        return new DataView(200,"添加成功!");
    }

    //修改
    @RequestMapping("/updateNucleicAci")
    @ResponseBody
    public DataView updateNucleicAci(NucleicAciVo nucleicAciVo){
        nucleicAciService.updateById(nucleicAciVo);
        return new DataView(200,"修改成功!");
    }

    //删除
    @RequestMapping("/deleteNucleicAci")
    @ResponseBody
    public DataView deleteNucleicAci(Integer id){
        nucleicAciService.removeById(id);
        return new DataView(200,"删除成功!");
    }

}
