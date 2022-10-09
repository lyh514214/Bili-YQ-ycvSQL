package com.ahao.controller.graphsController;

import com.ahao.pojo.Global;
import com.ahao.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<Global> queryAll(){

        List<Global> list = globalService.list207s();
        return list;
    }

}
