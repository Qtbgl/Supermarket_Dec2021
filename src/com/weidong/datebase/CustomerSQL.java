package com.weidong.datebase;

import com.weidong.entity.Customer;
import com.weidong.entity.Purchase;

import java.util.Date;
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
    //以上获取的都是未注销的顾客。

    //获取仅仅顾客
    List<Customer> queryAllAnyCustomer();
    Customer queryAnyCustomerById(int id);
    Customer queryAnyCustomerByName(String name);
    List<Customer> queryAnyAllCustomerAndPurchase();
    Customer queryAnyCustomerAndPurchaseById(int id);
    //以上获取顾客无论是否注销

    //获取注销的顾客。
    List<Customer> queryDeletedCustomer();
    Customer queryDeletedCustomerById(int id);
    //以上获取的都是注销的顾客

    //增加新顾客，不需要id。
    int addCustomer(Customer customer);
    //从伪删除处恢复
    int addCustomerFromDeleteById(int id);
    /*增加一笔购买记录，使用商品id和顾客id，不需要Purchase的id。
     * 不会影响货品实体的数量C。
     * */
    int addPurchase(int customerId, int saleId, int S, Date date);

    //更改顾客姓名，密码，vip。通过顾客id，无论注销。
    int updateCustomerById(Customer customer, int id);

    //伪删除
    int deleteCustomerByName(String name);
    int deleteCustomerById(int id);
}
