package com.weidong.biz;

import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.exception.ValueUnreasonException;

import java.util.Date;
import java.util.List;

public interface SaleBiz{
    //新增商品，不需要id，但需要相关的货品。
    public void createSale(Sale sale) throws IdNotFoundException, ItemCountException, ValueUnreasonException;
    //查看超市所有商品，已上架的。
    public List<Sale> seeNowSale();
    //查看包括下架的商品
    public List<Sale> seeAnySale();
    //查看指定商品，无论上架包括迭代品，需要id。
    public Sale seeSaleById(int id);
    //搜索某名称的商品，已上架
    public List<Sale> searchSaleLikeName(String info);
    //搜索购买记录，通过某货品的名称
    public List<Purchase> searchPurchaseLikeName(String info);
    //搜索含某货品的商品，需要id
    /*public List<Sale> searchSaleByGood(Goods goods);
    * 此业务与GoodsBiz的：List<Sale> analyseSales(Supermarket_Member member)
    * **统计货品相关商品** 功能重复，在GoodsBiz实现类可用。
    * */
    //修改商品组合，需要id
    public void modifySaleMakeup(int saleId, Makeup saleMakeup) throws IdNotFoundException, ItemCountException;
    //修改商品价格，需要id
    public void modifySalePrice(Sale sale) throws IdNotFoundException, ValueUnreasonException;
    //修改商品其他，需要id
    public void modifySaleName(Sale sale) throws IdNotFoundException, ValueUnreasonException;
    //**统计商品的修改记录**，返回迭代品。新代，无论上架，不能是迭代品。
    public List<Sale> analysePastSales(Supermarket_Member member) throws IdNotFoundException;
    //**修改商品的购买记录**。同上，返回迭代品和它们的购买记录。
    public List<Sale> analysePastSalesPurchase(Supermarket_Member member) throws IdNotFoundException;
    //**统计商品的购买情况** ，无论上架，可以是迭代品。
    public List<Purchase> analyseSalePurchases(Supermarket_Member member) throws IdNotFoundException;
    //获取所有购买记录，现上架的
    public List<Purchase> seeNowPurchase();
    //获取所有购买记录，现上架和下架的
    public List<Purchase> seeAnyPurchase();
    //获取某天的购买记录
    /*public List<Purchase> seePurchaseByDate(Date date);*/
    //**下架商品**
    public void remove(Supermarket_Member member) throws IdNotFoundException;
    //查看下架的商品，不包括迭代品。
    public List<Sale> seeRemovedSale();
    //**撤回下架的商品**
    public void recover(Supermarket_Member member) throws IdNotFoundException;
}
