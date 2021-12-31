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
    //与数据库约束相同
    public static final int pwdLength = 6;

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", vip=" + vip +
                ", salePurchase=" + salePurchase +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public List<Purchase> getSalePurchase() {
        return salePurchase;
    }

    public void setSalePurchase(List<Purchase> salePurchase) {
        this.salePurchase = salePurchase;
    }
}
