package com.weidong.entity;

import java.util.List;
import java.util.Set;

public class Goods {
    int id;
    //货品名称
    String name;
    //货品类型
    String type;
    //货品可出售的数量
    int C;
    //组合进该货品的商品
    Set<Makeup> saleMakeup;
    //货品进口记录
    List<Import> imports;
    //货品撤除记录
    List<Remove> removes;
}
