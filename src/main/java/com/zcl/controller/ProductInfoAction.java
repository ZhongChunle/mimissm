package com.zcl.controller;

import com.github.pagehelper.PageInfo;
import com.zcl.pojo.ProductInfo;
import com.zcl.service.ProductInfoService;
import com.zcl.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
    @Autowired
    ProductInfoService productInfoMapper;

    /**
     * 定义每页查询的条数
     */
    private static final int PAGE_SIZE = 5;

    /**
     * 提高异步上传图片的名称
     */
    String saveFileName = "";

    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        // 给上传的文件名称复制
        info.setpImage(saveFileName);
        // 给添加商品的时间为当前时间
        info.setpDate(new Date());
        // 调用service的保存数据方法
        int num = -1;
        try {
            num = productInfoMapper.save(info);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg","商品增加成功");
        }else{
            request.setAttribute("msg","商品增加失败");
        }
        // 保存成功之后，应该重新定向访问数据层，所以跳到分页显示的action上
        return "forward:/prod/split.action";
    }

    /**
     * ajax异步图片上传
     * @param pimage 对应上传图片的name
     * @return
     */
    @ResponseBody
    @RequestMapping("ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        // 1、调用工具类生成上文文件的32为名称和后缀
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        System.out.println("上传图片的名称："+saveFileName);
        // 2、得到项目中图片的存储路径
        String path = request.getServletContext().getRealPath("/image_big");
        // 3、转存图片到本地,File.separator代表反斜杠
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 4、返回客户端json对象，封装图片的路径，为了在页面及时回显【导入了json依赖坐标】
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl",saveFileName);
        // 想要返回json格式需要进行转换，这是json工具的特点
        return jsonObject.toString();
    }

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
