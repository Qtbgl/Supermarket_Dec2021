package com.weidong.role;

import com.weidong.entity.Customer;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.role.superclass.Role;

import java.util.ArrayList;
import java.util.List;

public class CustomerRole extends Role {
    //限制顾客的访问权限

    public static class SaleMessage extends Sale{
        @Override
        public Makeup getSaleMakeup() {
            return null;
        }

        @Override
        public List<Purchase> getCustomerPurchase() {
            return null;
        }

        public static SaleMessage confine(Sale sale){
            SaleMessage s = new SaleMessage();
            s.setCustomerPurchase(sale.getCustomerPurchase());
            s.setName(sale.getName());
            s.setPrice(sale.getPrice());
            s.setSaleMakeup(sale.getSaleMakeup());
            return s;
        }

        public static List<SaleMessage> confine(List<Sale> sales){
            List<SaleMessage> list = new ArrayList<>();
            for (Sale sale : sales) {
                list.add(confine(sale));
            }
            return list;
        }
    }

    public static class PurchaseAnalysis {
        private List<Purchase> purchases;

        public void setPurchases(List<Purchase> purchases) {
            this.purchases = purchases;
        }

        //统计购买总数
        public int countBuyAmount(){
            int countS = 0;
            for (Purchase purchase : purchases) {
                countS+=purchase.getS();
            }
           return countS;
        }
        //统计购买人数
        public int countPeople(){
            return purchases.size();
        }
    }

    //查看超市所有商品
    public List<SaleMessage> seeSale(){
       return SaleMessage.confine(saleBiz.seeSale());
    }
    //查看指定商品，需要id
    public SaleMessage seeSaleById(Sale sale){
        return SaleMessage.confine(saleBiz.seeSaleById(sale));
    }
    //搜索某名称的商品
    public List<SaleMessage> searchSaleLikeName(String info){
        return SaleMessage.confine(saleBiz.searchSaleLikeName(info));
    }
    //**统计商品的修改记录**
    public List<SaleMessage> getSaleModifyRecords(Sale sale){
        return SaleMessage.confine(saleBiz.analyseSales(sale));
    }
    //**统计商品的购买情况（商品数、人数）**
    public PurchaseAnalysis getSalePurchaseRecords(Sale sale){
        PurchaseAnalysis analysis = new PurchaseAnalysis();
        analysis.setPurchases(saleBiz.analysePurchases(sale));
        return analysis;
    }
    //**购买商品**
    public void buy(Purchase purchase){
        customerBiz.buy(purchase);
    }
    //**统计以往的购买记录**
    public List<Purchase> getCustomerPurchaseRecords(Customer customer){
        List<Purchase> ps = customerBiz.analysePurchases(customer);
        for (Purchase p : ps) {
            Sale s = p.getSale();
            p.setSale(SaleMessage.confine(s));
        }
        return ps;
    }
    //**统计购买过的商品**
    public List<SaleMessage> getCustomerSaleRecords(Customer customer){
        return SaleMessage.confine(customerBiz.analyseSales(customer));
    }
    //修改名字，需要id
    public void modifyCustomerName(Customer customer){
        customerBiz.modifyCustomerName(customer);
    }
    //修改密码，需要id
    public void modifyCustomerPassword(Customer customer){
        customerBiz.modifyCustomerPassword(customer);
    }
    //**注销**
    public void deregister(Customer customer){
        customerBiz.remove(customer);
    }
}
