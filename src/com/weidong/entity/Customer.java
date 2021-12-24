package com.weidong.entity;

import com.weidong.datebase.CustomerSQL;

import java.util.List;

public class Customer {
    int id;
    String name;
    String pwd;
    int vip;
    //list：有次序的购买记录
    List<Purchase> salePurchase;

}
