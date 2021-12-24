package com.weidong.entity;

import java.util.List;
import java.util.Set;

public class Sale {
    int id;
    //商品名称
    String name;
    //每一种组成货品，对应组成数量。
    Set<Makeup> goodsMakeup;
    double price;
    //list：有次序的顾客记录
    List<Purchase> customerPurchase;

}
