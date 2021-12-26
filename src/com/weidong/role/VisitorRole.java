package com.weidong.role;

import com.weidong.entity.Customer;
import com.weidong.entity.Sale;
import com.weidong.role.superclass.Role;

import java.util.List;

import static com.weidong.role.CustomerRole.SaleMessage;

public class VisitorRole extends Role {
    //查看超市所有商品
    public List<CustomerRole.SaleMessage> seeSale(){
        return SaleMessage.confine(saleBiz.seeSale());
    }
    //查看指定商品，需要id
    public CustomerRole.SaleMessage seeSaleById(Sale sale){
        return SaleMessage.confine(saleBiz.seeSaleById(sale));
    }
    //搜索某名称的商品
    public List<CustomerRole.SaleMessage> searchSaleLikeName(String info){
        return SaleMessage.confine(saleBiz.searchSaleLikeName(info));
    }
    //注册，不需要id
    public void register(Customer customer){
        customerBiz.register(customer);
    }
}
