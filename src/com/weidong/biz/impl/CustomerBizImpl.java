package com.weidong.biz.impl;

import com.weidong.biz.CustomerBiz;
import com.weidong.biz.impl.superclass.BusinessImpl;
import com.weidong.datebase.CustomerSQL;
import com.weidong.datebase.GoodsSQL;
import com.weidong.datebase.SaleSQL;
import com.weidong.entity.*;
import com.weidong.entity.superclass.Supermarket_Member;
import com.weidong.exception.biz.*;

import java.util.*;

public class CustomerBizImpl extends BusinessImpl implements CustomerBiz {
    public CustomerBizImpl(CustomerSQL customerSQL, GoodsSQL goodsSQL, SaleSQL saleSQL) {
        super(customerSQL, goodsSQL, saleSQL);
    }

    @Override
    public List<Sale> analyseSales(Supermarket_Member member) {
        //无论注销
        Customer customer = customerSQL.queryAnyCustomerAndPurchaseById(member.getId());
        List<Purchase> all = customer.getSalePurchase();
        //购买的物理id排序
        all.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o1.getId() - o2.getId();
            }
        });
        //排序后在一次载入。
        List<Sale> list = new ArrayList<>();
        for (Purchase purchase : all) {
            list.add(purchase.getSale());
        }
        //载入的商品可能下架，可能是迭代品
        return list;
    }

    @Override
    public List<Purchase> analysePurchases(Supermarket_Member member) {
        //无论注销
        Customer customer = customerSQL.queryAnyCustomerAndPurchaseById(member.getId());
        List<Purchase> list = customer.getSalePurchase();
        //购买的物理id排序
        list.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase o1, Purchase o2) {
                return o2.getId() - o1.getId();
            }
        });
        return list;
    }

    @Override
    public void buy(int customerId, int saleId, int S) throws IdNotFoundException, ValueUnreasonException, ItemCountException {
        //检查顾客id存在，未注销顾客
        Customer find_customer = customerSQL.queryCustomerById(customerId);
        if (find_customer == null) {
            throw new IdNotFoundException(IdNotFoundException.CUSTOMER);
        }
        //检查商品id存在
        Sale find_sale = saleSQL.querySaleById(saleId);
        if (find_sale == null) {
            throw new IdNotFoundException(IdNotFoundException.SALE);
        }
        //检查购买数量S合理
        if (S <= 0) {
            throw new ValueUnreasonException();
        }
        //检查商品的每一个货品的数量C，都充足于每一个对应N与S的乘积。
        Set<Makeup.Node> makeup = find_sale.getSaleMakeup().getMakeup();
        for (Makeup.Node node : makeup) {
            int C = node.getGoods().getC();
            int N = node.getN();
            if (C < N * S) {
                throw new ItemCountException();
            }
        }
        //以上购买条件：日期、顾客、商品、数量足够。

        //增加购买记录
        int i = customerSQL.addPurchase(customerId,saleId,S,new Date());
        //更新每一个货品的C值
        for (Makeup.Node node : makeup) {
            Goods goods = node.getGoods();
            int C = goods.getC();
            int N = node.getN();
            goods.setC(C - N * S);
            int i1 = goodsSQL.updateGoodsCById(goods, 0);
        }
        //不对货品进口，撤销记录处理。
    }

    @Override
    public Customer login(String name, String pwd) throws IdNotFoundException, PassFailedException {
        //检查顾客是否存在，且未注销
        Customer find = customerSQL.queryCustomerByName(name);
        if (find == null) {
            throw new IdNotFoundException();
        }
        //检查密码是否匹配
        if (pwd.equals(find.getPwd())){
            throw new PassFailedException();
        }
        return find;
    }

    @Override
    public void register(String name, String pwd) throws AlreadyExistedAddException, ValueUnreasonException {
        //检查与已有顾客重名，无论注销
        Customer find = customerSQL.queryAnyCustomerByName(name);
        if (find != null) {
            throw new AlreadyExistedAddException();
        }
        //检查密码合理
        if (pwd.length() != Customer.pwdLength){
            throw new ValueUnreasonException();
        }
        //vip缺省赋值。默认=0
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPwd(pwd);
        customer.setVip(0);

        //增加新顾客到数据库
        int i = customerSQL.addCustomer(customer);
    }

    @Override
    public void modifyCustomerName(int id, String name) throws IdNotFoundException, AlreadyExistedAddException {
        //检查顾客存在。未注销
        Customer find = customerSQL.queryCustomerById(id);
        if (find == null) {
            throw new IdNotFoundException();
        }
        //检查是否会重名。无论注销
        Customer find1 = customerSQL.queryAnyCustomerByName(name);
        if (find1 != null) {
            throw new AlreadyExistedAddException();
        }
        //定义customer，用于SQL一次性写入。
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        //密码不变
        customer.setPwd(find.getPwd());
        //vip不变
        customer.setVip(find.getVip());
        //更新使用：顾客姓名，密码，vip
        int i = customerSQL.updateCustomerById(customer, customer.getId());
    }

    @Override
    public void modifyCustomerPassword(int id, String pwd) throws IdNotFoundException, ValueUnreasonException {
        //检查顾客存在。未注销
        Customer find = customerSQL.queryCustomerById(id);
        if (find == null) {
            throw new IdNotFoundException();
        }
        //检查新密码合理
        if (pwd.length() != Customer.pwdLength) {
            throw new ValueUnreasonException();
        }

        //定义customer，用于SQL一次性写入。
        Customer customer = new Customer();
        customer.setId(id);
        customer.setPwd(pwd);
        //名称不变
        customer.setName(find.getName());
        //vip不变
        customer.setVip(find.getVip());
        //更新使用：顾客姓名，密码，vip
        int i = customerSQL.updateCustomerById(customer, customer.getId());
    }

    @Override
    public void modifyCustomerVip(Customer customer) throws IdNotFoundException, ValueUnreasonException {
        //检查顾客存在。未注销
        Customer find = customerSQL.queryCustomerById(customer.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //名称不变
        customer.setName(find.getName());
        //密码不变
        customer.setPwd(find.getPwd());
        //检查vip是否合理
        if (customer.getVip() < 0 || customer.getVip() > 3){
            throw new ValueUnreasonException();
        }
        //更新使用：顾客姓名，密码，vip
        int i = customerSQL.updateCustomerById(customer, customer.getId());
    }

    @Override
    public void remove(Supermarket_Member member) throws IdNotFoundException {
        //检查顾客是否存在。未注销
        Customer find = customerSQL.queryCustomerById(member.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //注销
        int i = customerSQL.deleteCustomerById(member.getId());
    }

    @Override
    public void recover(Supermarket_Member member) throws IdNotFoundException {
        Customer find = customerSQL.queryDeletedCustomerById(member.getId());
        if (find == null) {
            throw new IdNotFoundException();
        }
        //恢复
        int i = customerSQL.addCustomerFromDeleteById(member.getId());
    }
}
