package com.ahao.service.impl;

import com.ahao.mapper.NewsMapper;
import com.ahao.pojo.News;
import com.ahao.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> showNews3() {
        return newsMapper.showNews3();
    }
}
