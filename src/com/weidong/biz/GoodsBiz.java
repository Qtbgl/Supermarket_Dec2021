package com.weidong.biz;

import com.weidong.entity.*;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.AlreadyExistedAddException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;

import java.util.List;

public interface GoodsBiz {
    //进口已有的货品，需要id。不能进口已遗弃货品，必须在恢复后。
    public void importOld(Import _import) throws IdNotFoundException, ItemCountException;
    //进口新的货品，不需要id。
    public void importNew(Import _import) throws AlreadyExistedAddException, ItemCountException;
    //查看货品进口记录，需要id。是否遗弃都能看。
    public List<Import> seeGoodsImportRecords(Goods goods);
    //查看超市所有进口记录。无论货品是否遗弃。
    public List<Import> seeAllImportRecords();
    //查看超市所有货品。指未遗弃的货品。
    public List<Goods> seeGoods();
    //搜索货品，通过名称，类型。未遗弃
    public List<Goods> searchGoodsLike(String info);
    //搜索货品，通过名称。未遗弃
    public List<Goods> searchGoodsLikeName(String info);
    //搜索货品，通过类型。未遗弃
    public List<Goods> searchGoodsLikeType(String info);
    //查看货品，需要id。未遗弃
    public Goods seeGoodsById(Goods goods);
    //查看货品，需要id。无论是否遗弃
    public Goods seeAnyGoodsById(Goods goods);
    //遗弃货品，是彻底废弃一种货品
    public void forbid(Supermarket_Member member) throws IdNotFoundException, ItemCountException;
    //查看所有遗弃的货品
    public List<Goods> seeForbiddenGoods();
    //**撤回遗弃的货品**
    public void recover(Supermarket_Member member);
    //撤下商品，不遗弃只减少数量，同时增加撤下记录
    public void removeGoods(Remove remove) throws IdNotFoundException, ItemCountException;
    //查看某商品的撤下记录，需要id。是否遗弃都能看。
    public List<Remove> seeGoodsRemove(Goods goods);
    //查看超市所有的撤下记录。是否遗弃都能看。
    public List<Remove> seeRemove();
    //修改货品其他，需要id
    public void modifyGoods_etc(Goods goods) throws IdNotFoundException, AlreadyExistedAddException;
    //**统计货品相关商品**。未遗弃的货品的。
    public List<Sale> analyseSales(Supermarket_Member member);
    //**统计货品的购买情况**。未遗弃的货品的。
    public List<Purchase> analysePurchases(Supermarket_Member member);
}
