package com.zcl.service;

import com.zcl.pojo.ProductType;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：商品类型查询
 *
 * @author zhong
 * @date 2022-05-12 20:36
 */
public interface ProductTypeService {
    List<ProductType> getAll();
}
