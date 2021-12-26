package com.weidong.biz;

import com.weidong.entity.Customer;

public interface CustomerBiz extends Buyable,Analysable,Removable,Recoverable{
    //**购买商品**
    //**统计以往的购买记录**
    //**统计购买过的商品**
    //注册，不需要id
    public void register(Customer customer);
    //修改名字，需要id
    public void modifyCustomerName(Customer customer);
    //修改密码，需要id
    public void modifyCustomerPassword(Customer customer);
    //**注销**
    //**恢复注销的用户**
}
