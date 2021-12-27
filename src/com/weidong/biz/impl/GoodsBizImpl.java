package com.weidong.biz.impl;

import com.weidong.biz.GoodsBiz;
import com.weidong.biz.impl.superclass.BusinessImpl;
import com.weidong.entity.*;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.AlreadyExistedAddException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;

import java.util.*;

public class GoodsBizImpl extends BusinessImpl implements GoodsBiz {
    @Override
    public List<Sale> analyseSales(Supermarket_Member member) {
        Goods goods = goodsSQL.queryGoodsAndSaleById(member.getId());
        Set<Sale> compose = goods.getComposeSale();
        //组成商品，不排序
        return new ArrayList<>(compose);
    }

    @Override
    public List<Purchase> analysePurchases(Supermarket_Member member) {
        //获取货品相关信息
        Goods goods = goodsSQL.queryGoodsAndSaleAndCustomerById(member.getId());
        ArrayList<Purchase> list = new ArrayList<>();
        //所有购买记录
        Set<Sale> sales = goods.getComposeSale();
        for (Sale sale : sales) {
            list.addAll(sale.getCustomerPurchase());
        }
        //id代表日期排序
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public void importOld(Import _import) throws IdNotFoundException, ItemCountException {
        //增加进口记录
        Goods goods = _import.getGoods();
        Goods find = goodsSQL.queryGoodsById(goods.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        if (_import.getA() <= 0){
            throw new ItemCountException();
        }
        _import.setDate(_import.getDate()==null? new Date(): _import.getDate());
        int i = goodsSQL.addImport(_import);

        //增加商品数量
        find.setC(find.getC()+ _import.getA());
        int i1 = goodsSQL.updateGoodsCById(find,find.getId());
    }

    @Override
    public void importNew(Import _import) throws AlreadyExistedAddException, ItemCountException {
        //增加供货记录
        Goods goods = _import.getGoods();
        String name = goods.getName();
        String type = goods.getType();
        Goods find = goodsSQL.queryGoodsByNameAndType(name, type);
        if (find!=null){
            throw new AlreadyExistedAddException();
        }
        if (_import.getA() <= 0){
            throw new ItemCountException();
        }
        _import.setDate(_import.getDate()==null? new Date(): _import.getDate());
        int i = goodsSQL.addImport(_import);
        //增加商品数量
        goods.setC(_import.getA());
        int i1 = goodsSQL.addGoods(goods);
    }

    @Override
    public List<Import> seeGoodsImportRecords(Goods goods) {
        Goods find = goodsSQL.queryGoodsById(goods.getId());
        return find.getImports();
    }

    @Override
    public List<Import> seeImportRecords() {
        List<Goods> all = goodsSQL.queryAllGoods();
        List<Import> list = new ArrayList<>();
        for (Goods goods : all) {
            list.addAll(goods.getImports());
        }
        //将进口记录按id排序
        list.sort(new Comparator<Import>() {
            @Override
            public int compare(Import o1, Import o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Goods> seeGoods() {
        //未遗弃的货品，不排序
        return goodsSQL.queryAllGoods();
    }

    @Override
    public List<Goods> searchGoodsLike(String info) {
        //未遗弃，不排序
        return goodsSQL.queryGoodsLike(info);
    }

    @Override
    public List<Goods> searchGoodsLikeName(String info) {
        //未遗弃，不排序
        return goodsSQL.queryGoodsLikeName(info);
    }

    @Override
    public List<Goods> searchGoodsLikeType(String info) {
        //未遗弃，不排序
        return goodsSQL.queryGoodsLikeType(info);
    }

    @Override
    public Goods seeGoodsById(Goods goods) {
        //未遗弃
        return goodsSQL.queryGoodsById(goods.getId());
    }

    @Override
    public List<Goods> seeForbiddenGoods() {
        //遗弃，无日期，不排序
        return goodsSQL.queryForbiddenGoods();
    }

    @Override
    public void removeGoods(Remove remove) throws IdNotFoundException, ItemCountException {
        //检查货品是否在。
        Goods goods = remove.getGoods();
        Goods find = goodsSQL.queryGoodsById(goods.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        //撤除数量过多，抛出异常，撤除失败。
        if (find.getC() < remove.getO()){
            throw new ItemCountException();
        }

        //正常条件，增加撤除记录。
        remove.setDate(remove.getDate()==null? new Date(): remove.getDate());
        int i = goodsSQL.addRemove(remove);

        //改动原货品数量
        find.setC(find.getC()- remove.getO());
        goodsSQL.updateGoodsCById(find,find.getId());
    }

    @Override
    public List<Remove> seeGoodsRemove(Goods goods) {
        Goods find = goodsSQL.queryGoodsById(goods.getId());
        List<Remove> list = find.getRemoves();
        //按移除id先后排序
        list.sort(new Comparator<Remove>() {
            @Override
            public int compare(Remove o1, Remove o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Remove> seeRemove() {
        //撤下的货品记录
        List<Remove> list = new ArrayList<>();
        List<Goods> all = goodsSQL.queryAllGoods();
        for (Goods goods : all) {
            list.addAll(goods.getRemoves());
        }
        //按移除id先后排序
        list.sort(new Comparator<Remove>() {
            @Override
            public int compare(Remove o1, Remove o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }


    @Override
    public void modifyGoods_etc(Goods goods) throws IdNotFoundException, AlreadyExistedAddException {
        //检查货物是否存在
        Goods find = goodsSQL.queryGoodsById(goods.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        //检查是否会重名
        Goods find1 = goodsSQL.queryGoodsByNameAndType(goods.getName(), goods.getType());
        if (find1 != null){
            throw new AlreadyExistedAddException();
        }

        //都满足更改
        int i = goodsSQL.updateGoodsNameAndTypeById(goods, goods.getId());
    }

    @Override
    public void recover(Supermarket_Member member) {
        //恢复遗弃状态
        int i = goodsSQL.addGoodsFromDeleteById(member.getId());
    }

    @Override
    public void forbid(Supermarket_Member member) throws IdNotFoundException, ItemCountException {
        //遗弃货品，先撤除。记录登记。
        Goods find = goodsSQL.queryGoodsById(member.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        Remove remove = new Remove();
        remove.setGoods(find);
        remove.setDate(new Date());
        remove.setO(find.getC());
        //调用本类的撤除函数。
        this.removeGoods(remove);

        //进行遗弃
        int i = goodsSQL.deleteGoodsById(member.getId());
    }
}
