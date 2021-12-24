package com.weidong.datebase;

import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;

import java.util.List;

public interface SaleSQL {
    //获取商品及其组成，即完整的商品
    List<Sale> queryAllSale();
    //Sale的name可重复
    Sale querySaleById(int id);
    //获取完整的商品，和顾客购买记录
    List<Sale> queryAllSaleAndPurchase();
    Sale querySaleAndPurchaseById(int id);

    //增加一种完整的商品，使用已有的货品id
    int addSale(Sale sale);
    //增加一笔购买记录，使用商品id，和顾客id
    int addPurchase(Purchase purchase);

    //不对任意属性直接更改，包括其组成成分。

    //伪删除
    int deleteSaleById(Sale sale);
}
