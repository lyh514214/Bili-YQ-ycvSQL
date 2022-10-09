package com.ahao.controller.dataController;

import com.ahao.pojo.NocvData;
import com.ahao.service.NocvService;
import com.ahao.vo.DataView;
import com.ahao.vo.NocvDataVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChinaDataController {

    @Autowired
    private NocvService nocvService;

    //分页查询功能
    @RequestMapping("/nocvDataByPage")
    @ResponseBody
    public DataView nocvDataByPage(NocvDataVo nocvDataVo) {
        //1.创建分页对象 （当前页，每页大小）
        IPage<NocvData> page = new Page<>(nocvDataVo.getPage(), nocvDataVo.getLimit());
        //2.创建模糊查询条件
        QueryWrapper<NocvData> wrapper = new QueryWrapper<>();
        wrapper.like(!(nocvDataVo.getName() == null), "name", nocvDataVo.getName());
        //3.确诊人数倒序
        wrapper.orderByDesc("id");
        //4.查询数据库并打包
        nocvService.page(page, wrapper);
        //5.返回数据
        DataView dataView = new DataView(page.getTotal(), page.getRecords());
        return dataView;
    }

    //删除渲染
    @RequestMapping("/china/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        nocvService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("成功删除中国疫情数据");
        return dataView;
    }

    //更新或添加
    @RequestMapping("/china/addOrUpdateData")
    @ResponseBody
    public DataView addOrUpdateData(NocvDataVo nocvDataVo){
        boolean save = nocvService.saveOrUpdate(nocvDataVo);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改中国疫情数据成功");
        return dataView;
    }
}
