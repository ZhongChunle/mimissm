package com.zcl.service.impl;

import com.zcl.mapper.AdminMapper;
import com.zcl.pojo.Admin;
import com.zcl.pojo.AdminExample;
import com.zcl.service.AdminService;
import com.zcl.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：登录业务逻辑层的实现类
 *
 * @author zhong
 * @date 2022-05-12 10:56
 */
@Service
public class AdminServiceImpl implements AdminService {
    // 在业务逻辑层中，一定会有数据访问层的对象

    @Autowired
    AdminMapper adminMapper;

    /**
     * 登录业务逻辑实现
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public Admin login(String name, String pwd) {
        // 根据闯入的用户名或到DB中查询相应用户对象
        // 如果有条件，则一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /**添加用户名a_name条件
         * select * from admin where a_name = 'admin'
         */
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        //判断是否查询搭配数据
        if(list.size() > 0){
            Admin admin = list.get(0);
            // 因为密码存储的是密文形式，需要将对比的密码进行同算法加密再跟查询出的数据密码进行对比
            String md5 = MD5Util.getMD5(pwd);
            if(md5.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
