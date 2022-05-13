package com.zcl.Test;

import com.zcl.mapper.ProductInfoMapper;
import com.zcl.pojo.ProductInfo;
import com.zcl.pojo.Vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：测试多条件查询映射文件
 *
 * @author zhong
 * @date 2022-05-13 13:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applictionContext_Dao.xml","classpath:applictionContext_Service.xml"})
public class MyTest {
    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("4");
        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(4000);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}
