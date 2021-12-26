package com.weidong.biz;

import com.weidong.entity.Goods;
import com.weidong.entity.Import;
import com.weidong.entity.Remove;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.AddAlreadyExistedException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;

import java.util.List;

public interface GoodsBiz extends Recoverable,Analysable{
    //进口已有的货品，需要id
    public void importOld(Import _import) throws IdNotFoundException, ItemCountException;
    //进口新的货品，不需要id
    public void importNew(Import _import) throws AddAlreadyExistedException, ItemCountException;
    //查看货品进口记录，需要id
    public List<Import> seeGoodsImportRecords(Goods goods);
    //查看超市所有进口记录
    public List<Import> seeImportRecords();
    //查看超市所有货品
    public List<Goods> seeGoods();
    //搜索货品，通过名称，类型
    public List<Goods> searchGoodsLike(String info);
    //搜索货品，通过名称
    public List<Goods> searchGoodsLikeName(String info);
    //搜索货品，通过类型
    public List<Goods> searchGoodsLikeType(String info);
    //查看货品，需要id
    public Goods seeGoodsById(Goods goods);
    //遗弃货品，是彻底废弃一种货品
    public void forbid(Supermarket_Member member) throws IdNotFoundException, ItemCountException;
    //查看所有遗弃的货品
    public List<Goods> seeForbiddenGoods();
    //**撤回遗弃的货品**
    //撤下商品，不遗弃只减少数量，同时增加撤下记录
    public void removeGoods(Remove remove) throws IdNotFoundException, ItemCountException;
    //查看某商品的撤下记录，需要id
    public List<Remove> seeGoodsRemove(Goods goods);
    //查看超市所有的撤下记录
    public List<Remove> seeRemove();
    //修改货品其他，需要id
    public void modifyGoods_etc(Goods goods) throws IdNotFoundException, AddAlreadyExistedException;
    //**统计货品相关商品**
    //**统计货品的购买情况**
}
