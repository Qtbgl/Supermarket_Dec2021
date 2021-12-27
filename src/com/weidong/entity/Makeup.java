package com.weidong.entity;

import java.util.Set;

public class Makeup {
    Set<Node> makeup;
    Sale sale;

    public static class Node{
        Goods goods;
        int N;

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public int getN() {
            return N;
        }

        public void setN(int n) {
            N = n;
        }
    }
    //内部静态类

    public Set<Node> getMakeup() {
        return makeup;
    }

    public void setMakeup(Set<Node> makeup) {
        this.makeup = makeup;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
