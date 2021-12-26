package com.weidong.entity;

import java.util.Date;

public class Remove {
    int id;
    Date date;
    Goods goods;
    //撤除数量
    int O;

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

    public int getO() {
        return O;
    }

    public void setO(int o) {
        O = o;
    }
}
