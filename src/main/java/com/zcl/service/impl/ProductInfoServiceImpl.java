package com.zcl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcl.mapper.ProductInfoMapper;
import com.zcl.pojo.ProductInfo;
import com.zcl.pojo.ProductInfoExample;
import com.zcl.pojo.Vo.ProductInfoVo;
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
     * 多条件查询分页效果
     * @param vo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        // 分页插件的硬性要求，必须的
        PageHelper.startPage(vo.getPage(), pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }

    /**
     * 多条件查询商品数据
     * @param vo
     * @return
     */
    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    /**
     * 批量删除商品数据
     * @param ids
     * @return
     */
    @Override
    public int deleyeBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

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

    /**
     * 保存商品信息
     * @param info
     * @return
     */
    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    /**
     * 根据商品id完成数据查询
     * @param id
     * @return
     */
    @Override
    public ProductInfo getByID(int id) {
        return productInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新商品的操作
     * @param info
     * @return
     */
    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    /**
     * 根据商品id上传数据
     * @param pid
     * @return
     */
    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }
}
