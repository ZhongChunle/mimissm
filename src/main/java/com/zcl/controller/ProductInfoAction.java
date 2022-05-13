package com.zcl.controller;

import com.github.pagehelper.PageInfo;
import com.zcl.pojo.ProductInfo;
import com.zcl.service.ProductInfoService;
import com.zcl.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
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

    /**
     * 根据结束的批量删除的字符串id删除数据
     * @param pids
     * @param request
     * @return
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        // 将页面传递过来的id通过逗号进行拆分成字符串数组pids[1,2,3]
        String[] ps = pids.split(",");
        try {
        int num = productInfoMapper.deleyeBatch(ps);
            if(num > 0){
                request.setAttribute("msg","商品批量删除成功");
            }else{
                request.setAttribute("msg", "商品批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品信息不可删除");
        }
        // 一定要注意跳转的路径
        return "forward:/prod/deleteAjaxSplit.action";
    }

    /**
     * 接收删除数据的id
     * @param pid
     * @return
     */
    @RequestMapping("/delete")
    public String delete(int pid,HttpServletRequest request){
        int num = -1;
        try {
            num = productInfoMapper.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 进行成功的提示
        if(num > 0){
            request.setAttribute("msg","商品数据删除成功");
        }else {
            request.setAttribute("msg", "商品删除失败");
        }
        // 删除结束后跳转ajax分页处理
        return "forward:/prod/deleteAjaxSplit.action";
    }


    /**
     * 删除数据成功后的查询第一页数据返回
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        // 获取第一页的分页数据
        PageInfo info = productInfoMapper.splitPage(1,PAGE_SIZE);
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    /**
     * 更新商品数据
     * @param info
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        // 对重新上传图片信息进行处理，如果上传图片就重新赋值
        if(!saveFileName.equals("")){
            info.setpImage(saveFileName);
        }
        int num = -1;
        try {
            num = productInfoMapper.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num > 0){
            request.setAttribute("msg","商品数据更新成功");
        }else {
            request.setAttribute("msg", "商品信息更新失败");
        }
        // 再次清空文件上传变量为空
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    /**
     * 根据商品id查询数据回显
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/one")
    public String one(int pid, Model model){
        ProductInfo info = productInfoMapper.getByID(pid);
        model.addAttribute("prod",info);
        return "update";
    }

    /**
     * 保存商品数据
     * @param info
     * @param request
     * @return
     */
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
        // 清空saveFileName，为了下一步的增加或修改的异步上传处理
        saveFileName = "";
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
