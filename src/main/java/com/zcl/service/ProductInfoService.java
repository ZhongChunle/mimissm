package com.zcl.service;

import com.github.pagehelper.PageInfo;
import com.zcl.pojo.ProductInfo;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：商品Service层接口
 *
 * @author zhong
 * @date 2022-05-12 15:13
 */
public interface ProductInfoService {
    /**
     * 1、查询所有商城信息（不分页）
     * @return
     */
    List<ProductInfo> getAll();

    /**
     * 分页功能是实现
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo splitPage(int pageNum,int pageSize);
}
