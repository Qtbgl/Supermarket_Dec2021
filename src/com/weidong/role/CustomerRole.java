package com.weidong.role;

import com.weidong.biz.CustomerBiz;
import com.weidong.biz.GoodsBiz;
import com.weidong.biz.SaleBiz;
import com.weidong.entity.Customer;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.*;
import com.weidong.role.superclass.Role;

import java.util.ArrayList;
import java.util.List;

public class CustomerRole extends Role {
    //登录的顾客，记录下来。
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerRole(CustomerBiz customerBiz, GoodsBiz goodsBiz, SaleBiz saleBiz, Customer customer) {
        super(customerBiz, goodsBiz, saleBiz);
        this.customer = customer;
    }

    //查看超市所有商品，上架中的。
    public List<Sale> seeSale(){
       return saleBiz.seeNowSale();
    }
    //查看指定商品，需要id，无论上架
    public Sale seeSaleById(int id){
        return saleBiz.seeSaleById(id);
    }
    //搜索某名称的商品
    public List<Sale> searchSaleLikeName(String info){
        return saleBiz.searchSaleLikeName(info);
    }
    //搜索购买记录，通过某货品的名称。
    public List<Purchase> searchPurchaseLikeName(String info){
        return saleBiz.searchPurchaseLikeName(info);
    }
    //**统计商品的修改记录**。无论上架，但必须新代。
    public List<Sale> getSaleModifyRecords(int id) throws IdNotFoundException {
        return saleBiz.analysePastSales(new Supermarket_Member(id));
    }
    //**统计:修改商品的购买记录**，返回迭代品，包括购买记录。
    public List<Sale> getPurchasedSaleModifyRecords(int id) throws IdNotFoundException {
        return saleBiz.analysePastSalesPurchase(new Supermarket_Member(id));
    }

    //**统计商品的购买情况**
    public List<Purchase> getSalePurchaseRecords(Sale sale) throws IdNotFoundException {
        return saleBiz.analyseSalePurchases(sale);
    }

    /*以上是商品业务*/

    //**购买商品**
    public void buy(int customerId, int saleId, int S) throws ItemCountException, ValueUnreasonException, IdNotFoundException {
        customerBiz.buy(customerId,saleId,S);
    }
    //**统计以往的购买记录**
    public List<Purchase> getCustomerPurchaseRecords(int id){
        return customerBiz.analysePurchases(new Supermarket_Member(id));
    }
    //**统计购买过的商品**
    public List<Sale> getCustomerSaleRecords(Customer customer){
        return customerBiz.analyseSales(customer);
    }
    //修改名字，需要id
    public void modifyCustomerName(int id, String name) throws AlreadyExistedAddException, IdNotFoundException {
        customerBiz.modifyCustomerName(id,name);
    }
    //修改密码，需要id
    public void modifyCustomerPassword(int id, String pwd) throws ValueUnreasonException, IdNotFoundException {
        customerBiz.modifyCustomerPassword(id,pwd);
    }
    //登录
    public Customer login(String name, String pwd) throws IdNotFoundException, PassFailedException {
        return customerBiz.login(name,pwd);
    }

    //**注销**
    public void deregister(int id) throws IdNotFoundException {
        customerBiz.remove(new Supermarket_Member(id));
        //customer的内存记录清空。
        //this.customer = null; //不做
    }
}
