package com.weidong.biz.impl;

import com.weidong.biz.SaleBiz;
import com.weidong.biz.impl.superclass.BusinessImpl;
import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.exception.ValueUnreasonException;

import java.util.*;

public class SaleBizImpl extends BusinessImpl implements SaleBiz {
    /*检查商品的saleMakeup属性：
     * 1.检查每一个货品id在数据库中已存在实体，不存在则抛出异常。
     * 2.检查每一个货品组成数量N是否大于0，否则抛出异常。
     * */
    private void checkSaleMakeup(Sale sale) throws IdNotFoundException, ItemCountException {
        Makeup saleMakeup = sale.getSaleMakeup();
        Set<Makeup.Node> makeup = saleMakeup.getMakeup();
        for (Makeup.Node node : makeup) {
            //检查组成的货品id是否都存在
            if (goodsSQL.queryGoodsById(node.getGoods().getId()) == null) {
                throw new IdNotFoundException();
            }
            //检查货品组成数量N是否合理
            if (node.getN() <= 0) {
                throw new ItemCountException();
            }
        }
    }

    @Override
    public void recover(Supermarket_Member member) throws IdNotFoundException {
        //将指定id的下架商品，变为未下架商品。
        //检查是已下架的商品
        Sale find = saleSQL.queryDeletedSaleById(member.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //find商品是新代，queryDeletedSaleById保证
        int i = saleSQL.addSaleFromDeleteById(member.getId());
    }

    @Override
    public void remove(Supermarket_Member member) throws IdNotFoundException {
        //检查是上架的，否则操作无意义。
        Sale find = saleSQL.querySaleById(member.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        int i = saleSQL.deleteSaleById(member.getId());
    }

    @Override
    public List<Sale> seeRemovedSale() {
        List<Sale> list = saleSQL.queryAllDeletedSale();
        //按sale的物理自增id排序
        list.sort(new Comparator<Sale>() {
            @Override
            public int compare(Sale o1, Sale o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public void createSale(Sale sale) throws IdNotFoundException, ItemCountException, ValueUnreasonException {
        //检查相关的货品，是否存在。组成数量N是否合理。
        this.checkSaleMakeup(sale);
        //商品自身的信息：名称可重，价格小数。不需要商品id
        if (sale.getPrice() <0 || sale.getName() == null){
            throw new ValueUnreasonException();
        }
        int i = saleSQL.addSale(sale);
    }

    @Override
    public List<Sale> seeNowSale() {
        //不需要排序，以后可能有需求对sale各种排序。
        return saleSQL.queryAllSale();
    }

    @Override
    public List<Sale> seeAnySale() {
        //不需要排序，以后可能有需求对sale各种排序。
        return saleSQL.queryFrontSale();
    }

    @Override
    public Sale seeSaleById(Sale sale) {
        //获取无论上架，包括迭代品的sale
        return saleSQL.queryAnySaleById(sale.getId());
    }

    @Override
    public List<Sale> searchSaleLikeName(String info) {
        //获取正上架的sale
        return saleSQL.querySaleLikeName(info);
    }

    @Override
    public List<Purchase> searchPurchaseLikeName(String info){
        List<Purchase> list = new ArrayList<>();
        List<Sale> all = saleSQL.querySaleAndPurchaseLikeName(info);
        for (Sale sale : all) {
            list.addAll(sale.getCustomerPurchase());
        }
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public void modifySaleMakeup(Sale sale) throws IdNotFoundException, ItemCountException{
        //检查商品是否有原id
        Sale find = saleSQL.querySaleById(sale.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //检查货品完整合理。
        this.checkSaleMakeup(sale);
        //使名称与原来相同
        sale.setName(find.getName());
        //使价格与原来相同
        sale.setPrice(find.getPrice());
        //满足货品组成、价格、名称后。

        //加入一个新商品。数据库中多了一个正上架、无更新关联的新代。
        int i = saleSQL.addSale(sale);
        int lastId = saleSQL.queryLastSaleId(); //逻辑上，获取的是新商品的物理id。
        //原新代下架，才能换掉。
        int i1 = saleSQL.deleteSaleById(sale.getId());
        int i2 = saleSQL.updateSalePid(sale.getId(), lastId);
        //修改原更新组的pid。
        int i3 = saleSQL.updateSalePidGroup(sale.getId(), lastId);
    }

    @Override
    public void modifySalePrice(Sale sale) throws IdNotFoundException, ValueUnreasonException {
        //检查商品是否有原id
        Sale find = saleSQL.querySaleById(sale.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //使货品组成与原来一样。
        sale.setSaleMakeup(find.getSaleMakeup());
        //使名称与原来相同
        sale.setName(find.getName());
        //检查价格是否合理
        if (sale.getPrice() < 0) {
            throw new ValueUnreasonException();
        }
        //满足货品组成、价格、名称后。

        saleSQL.addSale(sale);
        int lastId = saleSQL.queryLastSaleId();
        saleSQL.deleteSaleById(sale.getId());
        saleSQL.updateSalePid(sale.getId(),lastId);
        saleSQL.updateSalePidGroup(sale.getId(),lastId);
    }

    @Override
    public void modifySaleName(Sale sale) throws IdNotFoundException, ValueUnreasonException {
        //检查商品是否有原id
        Sale find = saleSQL.querySaleById(sale.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //使货品组成与原来一样。
        sale.setSaleMakeup(find.getSaleMakeup());
        //检查名称不为空
        if (sale.getName() == null) {
            throw new ValueUnreasonException();
        }
        //使价格与原来相同
        sale.setPrice(find.getPrice());
        //满足货品组成、价格、名称后。

        saleSQL.addSale(sale);
        int lastId = saleSQL.queryLastSaleId();
        saleSQL.deleteSaleById(sale.getId());
        saleSQL.updateSalePid(sale.getId(),lastId);
        saleSQL.updateSalePidGroup(sale.getId(),lastId);
    }

    @Override
    public List<Sale> analysePastSales(Supermarket_Member member) throws IdNotFoundException {
        //商品的修改情况，即更新历史。
        //检查商品存在，无论下架，但不能是迭代品。
        Sale find = saleSQL.queryFrontSaleById(member.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        //获取该商品所有的迭代品，无论下架。不获取购买记录。
        List<Sale> list = saleSQL.queryOldSaleByPid(member.getId());
        //排序，迭代品也有创建的物理编号
        list.sort(new Comparator<Sale>() {
            @Override
            public int compare(Sale o1, Sale o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Sale> analysePastSalesPurchase(Supermarket_Member member) throws IdNotFoundException{
        //商品的修改情况，以及购买记录。
        //检查商品存在，无论下架，但不能是迭代品。
        Sale find = saleSQL.queryFrontSaleById(member.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        //获取该商品所有的迭代品，无论下架。不获取购买记录。
        List<Sale> list = saleSQL.queryOldSaleAndPurchaseByPid(member.getId());
        //排序，迭代品也有创建的物理编号
        list.sort(new Comparator<Sale>() {
            @Override
            public int compare(Sale o1, Sale o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Purchase> analyseSalePurchases(Supermarket_Member member) throws IdNotFoundException {
        //可以是迭代品，查看购买情况。
        Sale find = saleSQL.queryAnySaleById(member.getId());
        if (find == null){
            throw new IdNotFoundException();
        }
        //查询操作不管上架
        Sale sale = saleSQL.queryAnySaleAndPurchaseById(member.getId());
        List<Purchase> list = sale.getCustomerPurchase();
        //按购买的物理id排序
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Purchase> seeNowPurchase() {
        List<Purchase> list = new ArrayList<>();
        //只获取现上架的
        List<Sale> all = saleSQL.queryAllSaleAndPurchase();
        for (Sale sale : all) {
            List<Purchase> purchaseList = sale.getCustomerPurchase();
            list.addAll(purchaseList);
        }
        //按照购买物理自增id排序
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

    @Override
    public List<Purchase> seeAnyPurchase(){
        List<Purchase> list = new ArrayList<>();
        //获取无论上架
        List<Sale> all = saleSQL.queryAllFrontSaleAndPurchase();
        for (Sale sale : all) {
            List<Purchase> purchaseList = sale.getCustomerPurchase();
            list.addAll(purchaseList);
        }
        //按照购买物理自增id排序
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        return list;
    }

}
