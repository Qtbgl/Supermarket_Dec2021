package com.weidong.biz;

import com.weidong.entity.Customer;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.AlreadyExistedAddException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.exception.ValueUnreasonException;

import java.util.List;

public interface CustomerBiz{
    //**购买商品**，需要顾客id和商品id。顾客未注销
    public void buy(Purchase purchase) throws IdNotFoundException, ValueUnreasonException, ItemCountException;
    //**统计以往的购买记录**，无论注销
    public List<Sale> analyseSales(Supermarket_Member member);
    //**统计购买过的商品**，无论注销
    public List<Purchase> analysePurchases(Supermarket_Member member);
    //注册，不需要id
    public void register(Customer customer) throws AlreadyExistedAddException, ValueUnreasonException;
    //修改名字，需要id。顾客未注销
    public void modifyCustomerName(Customer customer) throws IdNotFoundException, AlreadyExistedAddException;
    //修改密码，需要id。顾客未注销
    public void modifyCustomerPassword(Customer customer) throws IdNotFoundException, ValueUnreasonException;
    //修改vip，需要id。顾客未注销
    public void modifyCustomerVip(Customer customer) throws IdNotFoundException, ValueUnreasonException;
    //**注销**
    public void remove(Supermarket_Member member) throws IdNotFoundException;
    //**恢复注销的用户**
    public void recover(Supermarket_Member member) throws IdNotFoundException;
}
