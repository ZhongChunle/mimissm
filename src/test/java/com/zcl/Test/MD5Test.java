package com.zcl.Test;

import com.zcl.utils.MD5Util;
import org.junit.Test;

/**
 * 项目名称：mimissm
 * 描述：MD5加密测试类
 *
 * @author zhong
 * @date 2022-05-12 10:42
 */
public class MD5Test {
    @Test
    public void test1(){
        String md5 = MD5Util.getMD5("000000");
        System.out.println(md5);
    }
}
