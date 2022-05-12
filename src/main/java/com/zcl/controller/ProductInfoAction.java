package com.zcl.controller;

import com.github.pagehelper.PageInfo;
import com.zcl.pojo.ProductInfo;
import com.zcl.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：商品展示控制器
 *
 * @author zhong
 * @date 2022-05-12 15:36
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    // 定义每页查询的条数
    private static final int PAGE_SIZE = 5;

    @Autowired
    ProductInfoService productInfoMapper;

    /**
     * 不分页查询数据
     * @param request
     * @return
     */
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoMapper.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    /**
     * 第一页五条数据的分页信息查询
     * @param request
     * @return
     */
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = productInfoMapper.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }

    /**
     * ajax分页效果
     * @param page 当前页数
     * @param session
     */
    @ResponseBody
    @RequestMapping("ajaxsplit")
    public void ajaxsplit(int page, HttpSession session){
        // 得到当前分页查询的数据
        PageInfo  info = productInfoMapper.splitPage(page,PAGE_SIZE);
        session.setAttribute("info",info);
    }
}
