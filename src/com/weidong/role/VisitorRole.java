package com.weidong.role;

import com.weidong.biz.CustomerBiz;
import com.weidong.biz.GoodsBiz;
import com.weidong.biz.SaleBiz;
import com.weidong.entity.Customer;
import com.weidong.entity.Sale;
import com.weidong.exception.biz.AlreadyExistedAddException;
import com.weidong.exception.biz.IdNotFoundException;
import com.weidong.exception.biz.PassFailedException;
import com.weidong.exception.biz.ValueUnreasonException;
import com.weidong.role.superclass.Role;

import java.util.List;

public class VisitorRole extends Role {
    //依赖注入
    public VisitorRole(CustomerBiz customerBiz, GoodsBiz goodsBiz, SaleBiz saleBiz) {
        super(customerBiz, goodsBiz, saleBiz);
    }

    //查看超市所有商品，未下架。
    public List<Sale> seeSale(){
        return  saleBiz.seeNowSale();
    }
    //查看指定商品，需要id
    public  Sale seeSaleById(int id){
        return  saleBiz.seeSaleById(id);
    }
    //搜索某名称的商品，已上架。
    public List<Sale> searchSaleLikeName(String info){
        return  saleBiz.searchSaleLikeName(info);
    }

    //登录，成功则返回顾客
    public Customer login(String name, String pwd) throws IdNotFoundException, PassFailedException {
        return customerBiz.login(name,pwd);
    }
    //注册，不需要id
    public void register(String name, String pwd) throws AlreadyExistedAddException, ValueUnreasonException {
        customerBiz.register(name,pwd);
    }
}
