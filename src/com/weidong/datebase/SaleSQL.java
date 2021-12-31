package com.weidong.datebase;

import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;

import java.util.List;

public interface SaleSQL {
    //获取商品及其组成货品，即完整的商品
    List<Sale> queryAllSale();
    Sale querySaleById(int id);
    //Sale的name可重复
    List<Sale> querySaleLikeName(String info);
    List<Sale> querySaleAndPurchaseLikeName(String info);
    //获取完整的商品，和顾客购买记录
    List<Sale> queryAllSaleAndPurchase();
    Sale querySaleAndPurchaseById(int id);
    //以上获取的商品都是未下架的，包括指定id的也满足。

    //获取下架或上架的所有新代。
    List<Sale> queryFrontSale();
    Sale queryFrontSaleById(int id);
    List<Sale> queryAllFrontSaleAndPurchase();
    //获取最新加入的商品的物理id。理论上是正上架的新代。
    int queryLastSaleId();
    //以上获取无论上架，新代商品，不获取迭代品。

    //获取所有下架商品，都是新代，不获取迭代品。
    List<Sale> queryAllDeletedSale();
    //获取指定下架商品。不是新代，则返回无。
    Sale queryDeletedSaleById(int id);
    //以上获取的都是下架的。

    Sale queryAnySaleById(int id);
    Sale queryAnySaleAndPurchaseById(int id);
    //获取迭代品。直接将入参pid作为查找的pid。入参id商品可以上架，可以下架。
    List<Sale> queryOldSaleByPid(int pid);
    List<Sale> queryOldSaleAndPurchaseByPid(int pid);
    //以上可以获取迭代品。

    /*增加单纯的商品，不管为了创建还是更新。
    * 使用sale组成货品的id。不需要sale本身id。
    * 如果是新代的商品，相关商品组的父id不更换。
    * */
    //使用商品信息，商品组成。需要组成货品的id
    int addSale(Sale sale);
    //重新上架某商品。【sql约束】只能是新代品，不能是迭代品。
    int addSaleFromDeleteById(int id);


    /*原则：不对商品一般属性、组成成分更改。
    * 情形：一般是增加新品时，将原新代淘汰，使其变成迭代品。
    * 操作：只改指定原新代的pid，和修改所有原来迭代品的pid改成新的pid.
    * 入参：原新代id，需要更改换成的pid。
    * */
    //只改指定id商品的pid。【sql约束】pid必须是一个新代。
    int updateSalePid(int id, int pid);
    //该所有原来是pid_former的商品的pid。【sql约束】同上。
    int updateSalePidGroup(int pid_former, int pid_now);


    //伪删除，下架标志位。
    int deleteSaleById(int id);
}
