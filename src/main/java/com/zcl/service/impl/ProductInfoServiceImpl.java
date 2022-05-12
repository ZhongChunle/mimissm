package com.zcl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcl.mapper.ProductInfoMapper;
import com.zcl.pojo.ProductInfo;
import com.zcl.pojo.ProductInfoExample;
import com.zcl.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：商品service层接口实现类
 *
 * @author zhong
 * @date 2022-05-12 15:14
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    /**
     * 不分页查询所有数据
     * @return
     */
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    /**
     * 分页条件按查询商城数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        // 1、分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum, pageSize);
        // 2、进行pageinfo数据封装
        // 3、进行有条件的查询操作，必须要创建ProductInfoExample
        ProductInfoExample example = new ProductInfoExample();
        // 4、设置排序，按主键降序
        example.setOrderByClause("p_id desc");
        // 5、设置完排序后，取集合，切记：一定要在取集合前设置【分页创建使用】
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        // 将查询到的数据封装到pageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
