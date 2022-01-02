package com.weidong.datebase;

import com.weidong.entity.Goods;
import com.weidong.entity.Import;
import com.weidong.entity.Remove;

import java.util.List;

public interface GoodsSQL {
    final public static String IS_NORMAL_GOODS = "logout = 0";
    final public static String IS_DELETED_GOODS = "logout = 1";
    final public static String IS_ANY_GOODS = "1 = 1";
    //获取货品，包含它的供应和撤除记录
    List<Goods> queryAllGoods();
    //Goods的name和type组合后唯一
    Goods queryGoodsById(int id);
    Goods queryGoodsByNameAndType(String name, String type);
    //获取Goods和包含它的商品
    List<Goods> queryAllGoodsAndSale();
    Goods queryGoodsAndSaleById(int id);
    Goods queryGoodsAndSaleByNameAndType(String name, String type);
    //name或许type匹配，只匹配名字，只匹配类型
    List<Goods> queryGoodsLike(String info);
    List<Goods> queryGoodsLikeName(String info);
    List<Goods> queryGoodsLikeType(String info);
    //获取Goods、包含它的商品、商品的顾客情况
    Goods queryAllGoodsAndSaleAndCustomer();
    Goods queryGoodsAndSaleAndCustomerById(int id);
    Goods queryGoodsAndSaleAndCustomerByNameAndType(String name, String type);
    //以上的是未遗弃的货品，logout=0

    List<Goods> queryAllAnyGoods();
    Goods queryAnyGoodsById(int id);
    Goods queryAnyGoodsByNameAndType(String name, String type);
    //以上获取货品不管遗弃

    //获取被标注为遗弃的货品，也会有供应和撤除记录。
    Goods queryForbiddenGoodsById(int id);
    List<Goods> queryForbiddenGoods();
    //以上获取遗弃的货品。

    //增加货品进口记录，使用货品id
    int addImport(Import _import);
    //增加单纯新货品
    int addGoods(Goods goods);
    //增加货品撤除记录，使用货品id
    int addRemove(Remove remove);
    //恢复遗弃状态，变回C=0的新货品
    int addGoodsFromDeleteById(int id);

    //变更单纯的货品数量
    int updateGoodsCById(Goods goods, int id);
    //变更其他货品的属性
    int updateGoodsNameAndTypeById(Goods goods, int id);

    //伪删除，设置标志位，遗弃货物。
    int deleteGoodsById(int id);
}
