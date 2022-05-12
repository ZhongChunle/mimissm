package com.zcl.controller;

import com.zcl.pojo.Admin;
import com.zcl.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目名称：mimissm
 * 描述：登录控制器
 *
 * @author zhong
 * @date 2022-05-12 14:43
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
    // 所有的控制器，都一定会有Service层的对象

    @Autowired
    AdminService adminAction;

    @RequestMapping("login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminAction.login(name, pwd);
        // 判断是否有数据
        if(admin!=null){
            request.setAttribute("admin",admin);
            return "main";
        }else{
            request.setAttribute("errmsg","用户名或密码错误请检查");
            return "login";
        }
    }
}
