package com.weidong.entity;

import com.weidong.entity.superclass.Supermarket_Member;

import java.util.List;
import java.util.Set;

public class Sale extends Supermarket_Member {
    //商品名称
    String name;
    //每一种组成货品，对应组成数量。
    Makeup saleMakeup;
    double price;
    //list：有次序的顾客记录
    List<Purchase> customerPurchase;

    @Override
    public String toString() {
        return "Sale{" +
                "name='" + name + '\'' +
                ", saleMakeup=" + saleMakeup +
                ", price=" + price +
                ", customerPurchase=" + customerPurchase +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Makeup getSaleMakeup() {
        return saleMakeup;
    }

    public void setSaleMakeup(Makeup saleMakeup) {
        this.saleMakeup = saleMakeup;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Purchase> getCustomerPurchase() {
        return customerPurchase;
    }

    public void setCustomerPurchase(List<Purchase> customerPurchase) {
        this.customerPurchase = customerPurchase;
    }
}
