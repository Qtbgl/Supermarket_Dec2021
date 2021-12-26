package com.weidong.biz;

import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;

import java.util.Date;
import java.util.List;

public interface SaleBiz extends Analysable,Removable,Recoverable{
    //新增商品，不需要id
    public void createSale(Sale sale);
    //查看超市所有商品
    public List<Sale> seeSale();
    //查看指定商品，需要id
    public Sale seeSaleById(Sale sale);
    //搜索某名称的商品
    public List<Sale> searchSaleLikeName(String info);
    //搜索含某货品的商品，需要id
    public List<Sale> searchSaleByGood(Goods goods);
    //修改商品组合，需要id
    public void modifySaleMakeup(Sale sale);
    //修改商品价格，需要id
    public void modifySalePrice(Sale sale);
    //修改商品其他，需要id
    public void modifySale_etc(Sale sale);
    //**统计商品的修改记录**
    //**统计商品的购买情况**
    //获取所有购买记录
    public List<Purchase> seePurchase();
    //获取某天的购买记录
    public List<Purchase> seePurchaseByDate(Date date);
    //**下架商品**
    //查看下架的商品
    public List<Sale> seeRemovedSale();
    //**撤回下架的商品**
}
