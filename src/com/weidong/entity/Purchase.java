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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }
}
