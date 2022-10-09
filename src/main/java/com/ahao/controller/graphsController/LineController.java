package com.ahao.controller.graphsController;

import com.ahao.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class LineController {

    @Autowired
    private LineService lineService;

    //折线图数据
    @RequestMapping("/queryLine")
    @ResponseBody
    public HashMap<Object, Object> queryLine(){
        HashMap<Object, Object> map = lineService.toLines();
        return map;
    }
}
