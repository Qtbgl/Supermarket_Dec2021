package com.weidong.entity;

import java.util.Date;

public class Purchase {
    int id;
    Date date;
    //顾客和商品
    Customer customer;
    Sale sale;
    //购买数量
    int S;
}
