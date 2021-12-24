package com.weidong.datebase;

import com.weidong.entity.Goods;
import com.weidong.entity.Import;
import com.weidong.entity.Remove;

import java.util.List;

public interface GoodsSQL {
    //获取商品，包含它的供应和撤除记录
    List<Goods> queryAllGoods();
    //Goods的name和type组合后唯一
    Goods queryGoodsById(int id);
    Goods queryGoodsByNameAndType(String name, String type);
    //获取Goods和包含它的商品
    List<Goods> queryAllGoodsAndSale();
    Goods queryGoodsAndSaleById(int id);
    Goods queryGoodsAndSaleByNameAndType(String name, String type);
    //获取Goods、包含它的商品、商品的顾客情况
    Goods queryAllGoodsAndSaleAndCustomer();
    Goods queryGoodsAndSaleAndCustomerById(int id);
    Goods queryGoodsAndSaleAndCustomerByNameAndType(String name, String type);

    //增加货品进口记录，使用货品id
    int addImport(Import _import);
    //增加单纯新货品
    int addGoods(Goods goods);
    //增加货品撤除记录，使用货品id
    int addRemove(Remove remove);

    //增加单纯的货品数量
    int updateGoodsAddById(Goods goods);
    //减少单纯的货品数量
    int updateGoodsDecById(Goods goods);
    //变更其他货品的属性
    int updateGoodsNameAndTypeById(Goods goods);

    //伪删除，即更新数量使其为0。
    int deleteGoodsById(Goods goods);
}
