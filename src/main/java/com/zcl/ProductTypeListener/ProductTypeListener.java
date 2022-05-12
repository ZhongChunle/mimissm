package com.zcl.ProductTypeListener;

import com.zcl.pojo.ProductType;
import com.zcl.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 项目名称：mimissm
 * 描述：商品类型全局监听器
 *
 * @author zhong
 * @date 2022-05-12 20:49
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 手动从spring容器中取出ProductTypeServiceImpl的对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applictionContext_*.xml");
        ProductTypeService plicationContext = (ProductTypeService) context.getBean("ProductTypeServiceImpl");
        // 调用dao层查询数据
        List<ProductType> typeList = plicationContext.getAll();
        // 放入全局作用域中，供所有需要用到商品类型的页面使用
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
