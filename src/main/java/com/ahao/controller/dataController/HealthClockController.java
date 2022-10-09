package com.ahao.controller.dataController;

import com.ahao.pojo.HealthClock;
import com.ahao.service.HealthClockService;
import com.ahao.vo.DataView;
import com.ahao.vo.HealthDataVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthClockController {

    @Autowired
    private HealthClockService healthClockService;

    //分页查询
    @RequestMapping("/healthDataByPage")
    @ResponseBody
    public DataView healthDataByPage(HealthDataVo healthDataVo){
        //分页条件
        IPage<HealthClock> page = new Page<>(healthDataVo.getPage(),healthDataVo.getLimit());
        //创建模糊查询
        QueryWrapper<HealthClock> wrapper = new QueryWrapper<>();
        wrapper.like(!(healthDataVo.getUsername() == null),"username",healthDataVo.getUsername());
        wrapper.like(!(healthDataVo.getPhone()==null),"phone",healthDataVo.getPhone());
        //id倒序
        wrapper.orderByDesc("id");
        //查询数据库并打包
        healthClockService.page(page,wrapper);
        //返回数据
        DataView dataView = new DataView(page.getTotal(),page.getRecords());
        return dataView;
    }

    //删除
    @RequestMapping("/deleteHealthClockById")
    @ResponseBody
    public DataView deleteHealthClockById(HealthClock healthClock){
        boolean del = healthClockService.removeById(healthClock.getId());
        DataView dataView = new DataView();
        if (del){
            dataView.setCode(200);
            dataView.setMsg("成功删除健康打卡数据");
            return dataView;
        }
        dataView.setCode(500);
        dataView.setMsg("删除失败");
        return dataView;
    }

    //新增或者更新
    @RequestMapping("/addOrUpdateById")
    @ResponseBody
    public DataView addOrUpdateById(HealthClock healthClock){
        DataView dataView = new DataView();
        boolean update = healthClockService.saveOrUpdate(healthClock);
        if (update){
            dataView.setCode(200);
            dataView.setMsg("更新健康打卡数据成功");
            return dataView;
        }
        dataView.setCode(200);
        return dataView;
    }



}
