package com.weidong.entity;

import java.util.Date;

public class Import {
    int id;
    Date date;
    Goods goods;
    //进口数量
    int A;

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

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }
}
