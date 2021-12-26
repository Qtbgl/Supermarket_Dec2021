package com.weidong.entity;

import com.weidong.datebase.CustomerSQL;
import com.weidong.entity.superclass.Supermarket_Member;

import java.util.List;

public class Customer extends Supermarket_Member {
    String name;
    String pwd;
    int vip;
    //list：有次序的购买记录
    List<Purchase> salePurchase;

}
