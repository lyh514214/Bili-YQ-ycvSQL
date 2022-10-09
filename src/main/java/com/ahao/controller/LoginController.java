package com.ahao.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.ahao.pojo.User;
import com.ahao.service.UserService;
import com.ahao.vo.DataView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //验证码
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //HuTool定义图形验证码的长和宽,验证码的位数，干扰线的条数
        //lineCaptcha: 图形验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(108, 33, 4, 10);
        session.setAttribute("code", lineCaptcha.getCode());
//        System.out.println(lineCaptcha.getCode());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登录逻辑
    @RequestMapping("/login/login")
    @ResponseBody
    public DataView login(@RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("code") String code
            , HttpSession session) {
        DataView dataView = new DataView();

        if (code != null && session.getAttribute("code").equals(code)) {
            //登录逻辑
//            //使用session登录
//            User user = userService.login(username,password);
            //使用shiro登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                dataView.setCode(200);
                dataView.setMsg("登录成功！");
                session.setAttribute("user", user);
                return dataView;
            } catch (UnknownAccountException e) {
                dataView.setCode(500);
                dataView.setMsg("用户不存在!");
                return dataView;
            } catch (IncorrectCredentialsException e) {
                dataView.setCode(500);
                dataView.setMsg("密码错误!");
                return dataView;
            }
        }
            dataView.setCode(100);
            dataView.setMsg("验证码错误！");
            return dataView;
    }

    //注销逻辑
    @RequestMapping("/login/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

}
