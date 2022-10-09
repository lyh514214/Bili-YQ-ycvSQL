package com.ahao.controller;

import com.ahao.pojo.ChinaTotal;
import com.ahao.pojo.News;
import com.ahao.service.ChinaTotalService;
import com.ahao.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import java.util.List;

@Controller
public class ViewController {

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    //中国地图 并且更新页面顶部总数
    @Autowired
    private ChinaTotalService chinaTotalService;
    @Autowired
    private NewsService newsService;

    //全球总数+新闻
    @RequestMapping("/toChina")
    public String chinaTopData(Model model){
        // 1. 建立redis连接，前端首先查询redis
        Jedis jedis = new Jedis("127.0.0.1");
        if (jedis!=null){
            // 2.从redis获取这些字段的value
            String confirm = jedis.get("confirm");
            String input = jedis.get("input");
            String heal = jedis.get("heal");
            String dead = jedis.get("dead");
            if (StringUtils.isNoneBlank(confirm,input,heal,dead)){     //判断是否redis里面是否有这些值
                //从redis里面传给前端
                ChinaTotal chinaTotal = new ChinaTotal(Integer.valueOf(confirm), Integer.valueOf(input), Integer.valueOf(heal), Integer.valueOf(dead));
                model.addAttribute("chinaTotal",chinaTotal);
            } else {               //当redis没有的时候就去查库
                //从DB传给前端
                ChinaTotal chinaTotal = chinaTotalService.queryNew();
                model.addAttribute("chinaTotal",chinaTotal);
                //把数据库查询到的值缓存在redis中
                jedis.set("confirm",String.valueOf(chinaTotal.getConfirm()));
                jedis.set("input",String.valueOf(chinaTotal.getInput()));
                jedis.set("heal",String.valueOf(chinaTotal.getHeal()));
                jedis.set("dead",String.valueOf(chinaTotal.getDead()));
            }
        }
        List<News> news = newsService.showNews3();
        model.addAttribute("news",news);
        return "nocv/china";
    }

    //饼状图
    @RequestMapping("/pieView")
    public String pieView(){
        return "nocv/pie";
    }

    //柱状图
    @RequestMapping("/barView")
    public String barViews(){
        return "nocv/bar";
    }

    //折线图
    @RequestMapping("/lineView")
    public String lineView(){
        return "nocv/line";
    }

    //世界地图
    @RequestMapping("/globalView")
    public String globalView(){
        return "nocv/global";
    }

    //中国疫情信息
    @RequestMapping("/ChinaDataView")
    public String ChinaDataView(){
        return "admin/chinaNocvData";
    }

    //健康打卡
    @RequestMapping("/healthView")
    public String healthClock(){
        return "admin/healthclock";
    }

    //菜单管理
    @RequestMapping("/menuView")
    public String menuView(){
        return "menu/menu";
    }

    //角色管理
    @RequestMapping("/roleView")
    public String roleView(){
        return "role/role";
    }

    //用户管理
    @RequestMapping("/userView")
    public String userView(){
        return "user/user";
    }

    //个人信息
    @RequestMapping("/userInfo")
    public String userInfo(){
        return "user/userInfo";
    }

    //修改密码
    @RequestMapping("/changePassword")
    public String changePassword(){
        return "user/changepassword";
    }

    //新闻管理
    @RequestMapping("/newsView")
    public String newsView(){
        return "news/news";
    }

    //学院管理
    @RequestMapping("/collegeView")
    public String collegeView(){
        return "schoolAdmin/college";
    }

    //班级管理
    @RequestMapping("/classView")
    public String classView(){
        return "schoolAdmin/class";
    }

    //核酸检测管理
    @RequestMapping("/nucleicAciView")
    public String nucleicAciView(){
        return "nucleicAci/nucleicAci";
    }

    //疫苗接种管理
    @RequestMapping("/vaccineView")
    public String vaccine(){
        return "vaccine/vaccine";
    }

}
