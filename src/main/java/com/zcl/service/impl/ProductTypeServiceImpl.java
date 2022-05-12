package com.zcl.service.impl;

import com.zcl.mapper.ProductTypeMapper;
import com.zcl.pojo.ProductType;
import com.zcl.pojo.ProductTypeExample;
import com.zcl.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：查询商品接口实现类
 *
 * @author zhong
 * @date 2022-05-12 20:39
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;

    /**
     * 查询所有商品类型
     * @return
     */
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
