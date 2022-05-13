package com.zcl.pojo.Vo;

/**
 * 项目名称：mimissm
 * 描述：多条件查询数据实体封装
 *
 * @author zhong
 * @date 2022-05-13 13:12
 */
public class ProductInfoVo {
    // 商品名称
    private String pname;
    // 商品类型
    private Integer typeid;
    // 最低价格
    private Integer lprice;
    // 最高价格
    private Integer hprice;
    // 起始页
    private Integer page = 1;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getLprice() {
        return lprice;
    }

    public void setLprice(Integer lprice) {
        this.lprice = lprice;
    }

    public Integer getHprice() {
        return hprice;
    }

    public void setHprice(Integer hprice) {
        this.hprice = hprice;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pname='" + pname + '\'' +
                ", typeid=" + typeid +
                ", lprice=" + lprice +
                ", hprice=" + hprice +
                ", page=" + page +
                '}';
    }
}
