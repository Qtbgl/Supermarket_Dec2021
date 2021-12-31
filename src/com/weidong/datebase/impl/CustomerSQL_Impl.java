package com.weidong.datebase.impl;

import com.weidong.datebase.CustomerSQL;
import com.weidong.datebase.impl.superclass.BaseSQL;
import com.weidong.entity.Customer;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class CustomerSQL_Impl extends BaseSQL implements CustomerSQL {

    public static void main(String[] args) {
        CustomerSQL_Impl impl = new CustomerSQL_Impl();
        Customer customer = impl.queryCustomerById(3176);
        System.out.println(customer);
    }

    @Override
    public List<Customer> queryAllCustomer() {
        return null;
    }

    @Override
    public Customer queryCustomerByName(String name) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "select * from customer where customer_name = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,name);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setPwd(rs.getString("customer_pwd"));
                customer.setVip(rs.getInt("customer_vip"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return customer;
    }


    @Override
    public Customer queryCustomerById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "select * from customer where customer_id = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setPwd(rs.getString("customer_pwd"));
                customer.setVip(rs.getInt("customer_vip"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return customer;
    }

    @Override
    public List<Customer> queryAllCustomerAndPurchase() {
        return null;
    }

    @Override
    public Customer queryCustomerAndPurchaseByName(String name) {
        return null;
    }

    @Override
    public Customer queryCustomerAndPurchaseById(int id) {
        return null;
    }

    @Override
    public List<Customer> queryAllAnyCustomer() {
        return null;
    }

    @Override
    public Customer queryAnyCustomerById(int id) {
        return null;
    }

    @Override
    public Customer queryAnyCustomerByName(String name) {
        return null;
    }

    @Override
    public List<Customer> queryAnyAllCustomerAndPurchase() {
        return null;
    }

    @Override
    public Customer queryAnyCustomerAndPurchaseById(int id) {
        return null;
    }

    @Override
    public List<Customer> queryDeletedCustomer() {
        return null;
    }

    @Override
    public Customer queryDeletedCustomerById(int id) {
        return null;
    }

    @Override
    public int addCustomer(Customer customer) {
        return 0;
    }

    @Override
    public int addCustomerFromDeleteById(int id) {
        return 0;
    }

    @Override
    public int addPurchase(int customerId, int saleId, int S, Date date) {
        return 0;
    }

    @Override
    public int updateCustomerById(Customer customer, int id) {
        return 0;
    }

    @Override
    public int deleteCustomerByName(String name) {
        return 0;
    }

    @Override
    public int deleteCustomerById(int id) {
        return 0;
    }
}
