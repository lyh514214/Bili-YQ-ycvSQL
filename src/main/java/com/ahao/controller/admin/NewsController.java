package com.ahao.controller.admin;

import com.ahao.pojo.News;
import com.ahao.service.NewsService;
import com.ahao.vo.DataView;
import com.ahao.vo.NewsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    //分页+模糊查询
    @RequestMapping("/newsByPage")
    @ResponseBody
    public DataView newsByPage(NewsVo newsVo){
        IPage<News> page = new Page<>(newsVo.getPage(),newsVo.getLimit());
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(newsVo.getTitle()),"title",newsVo.getTitle());
        wrapper.orderByDesc("id");
        newsService.page(page,wrapper);
        return new DataView(page.getTotal(),page.getRecords());
    }

    //新增或更新
    @RequestMapping("/addOrUpdateNewsData")
    @ResponseBody
    public DataView addOrUpdateNewsData(NewsVo newsVo){
        newsService.saveOrUpdate(newsVo);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("操作成功！");
        return dataView;

    }

    //删除
    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        newsService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除成功！");
        return dataView;
    }

}
