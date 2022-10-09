package com.ahao.service;

import com.ahao.pojo.News;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NewsService extends IService<News> {
    //查询三条最新新闻
    List<News> showNews3();
}
