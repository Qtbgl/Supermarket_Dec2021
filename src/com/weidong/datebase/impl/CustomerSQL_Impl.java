package com.weidong.datebase.impl;

import com.weidong.datebase.CustomerSQL;
import com.weidong.datebase.impl.superclass.BaseSQL;
import com.weidong.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CustomerSQL_Impl extends BaseSQL implements CustomerSQL {

    public static void main(String[] args) {
        CustomerSQL_Impl impl = new CustomerSQL_Impl();
        int i = impl.deleteCustomerById(3197);
        System.out.println(i);
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
        String sql = "select * from customer where customer_name = ? and "+CustomerSQL.IS_NORMAL_CUSTOMER;
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
        String sql = "select * from customer where customer_id = ? and "+CustomerSQL.IS_NORMAL_CUSTOMER;
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
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "select * from customer where customer_name = ? and "+CustomerSQL.IS_ANY_CUSTOMER;
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

    /*本类的方法提炼-1。
    * 使用：适合数据库访问正进行到一半时。
    * 前提：customer的各个属性已载入或初始完成，
    * 否则：可能会有空指针。
    * 提示：本方法中不会关闭conn。方法创建并关闭statement和resultSet，不影响主调函数的操作。
    * */
    private void loadPurchasesToCustomer(Customer customer, int id, Connection conn){
        PreparedStatement pStmt2 = null;
        PreparedStatement pStmt3 = null;
        PreparedStatement pStmt4 = null;
        PreparedStatement pStmt4_1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;
        ResultSet rs4_1 = null;
        String sql2 = "select purchase.purchase_id, purchase_date, number_s, " +
                "sale.sale_id, sale_name, sale_price " +
                "from purchase, sale " +
                "where purchase.sale_id = sale.sale_id " +
                "and purchase.customer_id = ?";

        String sql3 = "select goods.goods_id, goods_name, goods_type, number_c, number_n " +
                "from makeup, goods " +
                "where makeup.goods_id = goods.goods_id " +
                "and makeup.sale_id = ?";

        String sql4 = "select * from import where import.goods_id = ?";

        String sql4_1 = "select * from remove where remove.goods_id = ?";

        try {
            pStmt2 = conn.prepareStatement(sql2);
            pStmt2.setObject(1,customer.getId());
            rs2 = pStmt2.executeQuery();
            while (rs2.next()) {
                Purchase purchase = new Purchase();
                purchase.setSale(new Sale());
                purchase.getSale().setId(rs2.getInt("sale_id"));
                purchase.getSale().setName(rs2.getString("sale_name"));
                purchase.getSale().setPrice(rs2.getDouble("sale_price"));
                purchase.getSale().setCustomerPurchase(null);  //不向上层载入
                purchase.getSale().setSaleMakeup(new Makeup()); //Makeup是特例。
                purchase.getSale().getSaleMakeup().setMakeup(new HashSet<>());
                purchase.getSale().getSaleMakeup().setSale(purchase.getSale());
                purchase.setId(rs2.getInt("purchase_id"));
                purchase.setCustomer(customer);
                purchase.setDate(rs2.getDate("purchase_date"));
                purchase.setS(rs2.getInt("number_s"));
                customer.getSalePurchase().add(purchase);
                //第二层，purchase对应sale完全载入。//将通过customer的salePurchase数据返回。
                pStmt3 = conn.prepareStatement(sql3);
                pStmt3.setObject(1,purchase.getSale().getId());
                rs3 = pStmt3.executeQuery();
                while (rs3.next()) {
                    Makeup.Node node = new Makeup.Node();  //Makeup是一个封装的Node集合。
                    node.setGoods(new Goods());
                    node.getGoods().setId(rs3.getInt("goods_id"));
                    node.getGoods().setName(rs3.getString("goods_name"));
                    node.getGoods().setType(rs3.getString("goods_type"));
                    node.getGoods().setC(rs3.getInt("number_c"));
                    node.getGoods().setComposeSale(null);  //不向上层载入
                    node.getGoods().setImports(new ArrayList<>());
                    node.getGoods().setRemoves(new ArrayList<>());
                    node.setN(rs3.getInt("number_n"));
                    purchase.getSale().getSaleMakeup().getMakeup().add(node);
                    //第三层，node对应goods全载入完成.
                    pStmt4 = conn.prepareStatement(sql4);
                    pStmt4.setObject(1,node.getGoods().getId());
                    rs4 = pStmt4.executeQuery();
                    while (rs4.next()) {
                        Import imp = new Import();
                        imp.setId(rs4.getInt("import_id"));
                        imp.setGoods(node.getGoods());
                        imp.setA(rs4.getInt("number_a"));
                        imp.setDate(rs4.getDate("import_date"));
                        node.getGoods().getImports().add(imp);
                        //第四层，imp全载入完成。
                    }
                    pStmt4_1 = conn.prepareStatement(sql4_1);
                    pStmt4_1.setObject(1,node.getGoods().getId());
                    rs4_1 = pStmt4_1.executeQuery();
                    while (rs4_1.next()) {
                        Remove rem = new Remove();
                        rem.setId(rs4_1.getInt("remove_id"));
                        rem.setGoods(node.getGoods());
                        rem.setDate(rs4_1.getDate("remove_date"));
                        rem.setO(rs4_1.getInt("number_o"));
                        node.getGoods().getRemoves().add(rem);
                        //第四层，rem全载入完成。
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt2,rs2);
            closeAll(null,pStmt3,rs3);
            closeAll(null,pStmt4,rs4);
            closeAll(null,pStmt4_1,rs4_1);
        }
    }

    @Override
    public List<Customer> queryAnyAllCustomerAndPurchase() {
        Connection conn = null;
        PreparedStatement pStmt1 = null;
        ResultSet rs1 = null;
        List<Customer> list = new ArrayList<>();
        String sql1 = "select * from customer where "+CustomerSQL.IS_ANY_CUSTOMER;
        try {
            conn = getConn();
            pStmt1 = conn.prepareStatement(sql1);
            rs1 = pStmt1.executeQuery();
            while (rs1.next()){
                Customer customer = new Customer();
                customer.setId(rs1.getInt("customer_id"));
                customer.setName(rs1.getString("customer_name"));
                customer.setPwd(rs1.getString("customer_pwd"));
                customer.setVip(rs1.getInt("customer_vip"));
                customer.setSalePurchase(new ArrayList<>());
                list.add(customer);
                //第一层，customer完全载入。
                this.loadPurchasesToCustomer(customer,customer.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt1,rs1);
        }
        return list;
    }

    @Override
    public Customer queryAnyCustomerAndPurchaseById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "select * from customer where customer_id = ? and "+CustomerSQL.IS_ANY_CUSTOMER;
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
                customer.setSalePurchase(new ArrayList<>());
                //customer完全初始赋值。
                this.loadPurchasesToCustomer(customer,customer.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return customer;
    }

    @Override
    public List<Customer> queryDeletedCustomer() {
        return null;
    }

    @Override
    public Customer queryDeletedCustomerById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Customer customer = null;
        String sql = "select * from customer where customer_id = ? and "+CustomerSQL.IS_LOGOUT_CUSTOMER;
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return customer;
    }

    @Override
    public int addCustomer(Customer customer) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "insert into customer(customer_name,customer_pwd,customer_vip) " +
                "values(?,?,?)";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,customer.getName());
            pStmt.setObject(2,customer.getPwd());
            pStmt.setObject(3,customer.getVip());
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return i;
    }

    @Override
    public int addCustomerFromDeleteById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "update customer set logout = 0 " +
                "where customer_id = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return i;
    }

    @Override
    public int addPurchase(int customerId, int saleId, int S, Date date) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "insert into purchase(customer_id, sale_id, purchase_date, number_s) " +
                "values(?,?,?,?)";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,customerId);
            pStmt.setObject(2,saleId);
            pStmt.setObject(3,date);
            pStmt.setObject(4,S);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return i;
    }

    @Override
    public int updateCustomerById(Customer customer, int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "update customer set customer_name = ?, customer_pwd = ?, customer_vip = ? where customer_id = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,customer.getName());
            pStmt.setObject(2,customer.getPwd());
            pStmt.setObject(3,customer.getVip());
            pStmt.setObject(4,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return i;
    }

    @Override
    public int deleteCustomerByName(String name) {
        return 0;
    }

    @Override
    public int deleteCustomerById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "update customer set logout = 1 " +
                "where customer_id = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return i;
    }
}
