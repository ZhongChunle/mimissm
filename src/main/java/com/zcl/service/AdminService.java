package com.zcl.service;

import com.zcl.pojo.Admin;

/**
 * 项目名称：mimissm
 * 描述：登录业务逻辑层判断
 *
 * @author zhong
 * @date 2022-05-12 10:55
 */
public interface AdminService {
    Admin login(String name,String pwd);
}
