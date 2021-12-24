package com.weidong.datebase;

import com.weidong.entity.Customer;

import java.util.List;

public interface CustomerSQL {
    //获取仅仅顾客
    List<Customer> queryAllCustomer();
    //Customer的name不重复
    Customer queryCustomerByName(String name);
    Customer queryCustomerById(int id);
    //获取顾客、购买商品的记录、包括商品的详细组成
    List<Customer> queryAllCustomerAndPurchase();
    Customer queryCustomerAndPurchaseByName(String name);
    Customer queryCustomerAndPurchaseById(int id);

    int addCustomer(Customer customer);
    //从伪删除处恢复
    int addCustomerFromDeleteById(Customer customer);

    int updateCustomerName(String oldName, String newName);
    int updateCustomerNameById(Customer customer);
    int updateCustomerPassword(String name, String pwd);
    int updateCustomerPasswordById(Customer customer);
    int updateCustomerVip(String name, int vip);
    int updateCustomerVipById(Customer customer);

    //伪删除
    int deleteCustomer(String name);
    int deleteCustomerById(Customer customer);
}
