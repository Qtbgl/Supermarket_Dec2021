package com.weidong.entity;

import com.weidong.entity.superclass.Supermarket_Member;

import java.util.List;
import java.util.Set;

public class Goods extends Supermarket_Member {
    //货品名称
    String name;
    //货品类型
    String type;
    //货品可出售的数量
    int C;
    //组合进该货品的商品
    Set<Goods.Node> composeSale;
    //货品进口记录
    List<Import> imports;
    //货品撤除记录
    List<Remove> removes;

    public static class Node{
        Sale sale;
        int N;

        public Sale getSale() {
            return sale;
        }

        public void setSale(Sale sale) {
            this.sale = sale;
        }

        public int getN() {
            return N;
        }

        public void setN(int n) {
            N = n;
        }
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", C=" + C +
                ", composeSale=" + composeSale +
                ", imports=" + imports +
                ", removes=" + removes +
                ", id=" + id +
                '}';
    }

    public Set<Node> getComposeSale() {
        return composeSale;
    }

    public void setComposeSale(Set<Node> composeSale) {
        this.composeSale = composeSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public List<Import> getImports() {
        return imports;
    }

    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    public List<Remove> getRemoves() {
        return removes;
    }

    public void setRemoves(List<Remove> removes) {
        this.removes = removes;
    }
}
