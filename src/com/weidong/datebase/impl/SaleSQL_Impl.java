package com.weidong.datebase.impl;

import com.weidong.datebase.SaleSQL;
import com.weidong.datebase.impl.superclass.BaseSQL;
import com.weidong.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaleSQL_Impl extends BaseSQL implements SaleSQL {
    public static void main(String[] args) {
        SaleSQL_Impl impl = new SaleSQL_Impl();

    }

    @Override
    public List<Sale> queryAllSale() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where "+SaleSQL.IS_NORMAL_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return list;
    }

    /*本类的方法提炼-1。
     * 使用：适合数据库访问正进行到一半时。
     * 前提：sale的各个属性已载入或初始完成，
     * 否则：可能会有空指针。
     * 提示：本方法中不会关闭conn。方法创建并关闭statement和resultSet，不影响主调函数的操作。
     * */
    private void loadMakeupToSale(Sale sale, int id, Connection conn){
        PreparedStatement pStmt2 = null;
        PreparedStatement pStmt3 = null;
        PreparedStatement pStmt3_1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs3_1 = null;

        String sql2 = "select goods.goods_id, goods_name, goods_type, number_c, number_n " +
                "from makeup, goods " +
                "where makeup.goods_id = goods.goods_id " +
                "and makeup.sale_id = ?";

        String sql3 = "select * from import where import.goods_id = ?";

        String sql3_1 = "select * from remove where remove.goods_id = ?";

        try {
            pStmt2 = conn.prepareStatement(sql2);
            pStmt2.setObject(1,id);
            rs2 = pStmt2.executeQuery();
            while (rs2.next()) {
                Makeup.Node node = new Makeup.Node();
                node.setGoods(new Goods());
                node.getGoods().setId(rs2.getInt("goods_id"));
                node.getGoods().setName(rs2.getString("goods_name"));
                node.getGoods().setType(rs2.getString("goods_type"));
                node.getGoods().setC(rs2.getInt("number_c"));
                node.getGoods().setComposeSale(null);  //不向上层载入
                node.getGoods().setImports(new ArrayList<>());
                node.getGoods().setRemoves(new ArrayList<>());
                node.setN(rs2.getInt("number_n"));
                sale.getSaleMakeup().getMakeup().add(node);
                //第二层，node对应goods全载入完成.
                pStmt3 = conn.prepareStatement(sql3);
                pStmt3.setObject(1,node.getGoods().getId());
                rs3 = pStmt3.executeQuery();
                while (rs3.next()) {
                    Import imp = new Import();
                    imp.setId(rs3.getInt("import_id"));
                    imp.setGoods(node.getGoods());
                    imp.setA(rs3.getInt("number_a"));
                    imp.setDate(rs3.getDate("import_date"));
                    node.getGoods().getImports().add(imp);
                    //第三层，imp全载入完成。
                }
                pStmt3_1 = conn.prepareStatement(sql3_1);
                pStmt3_1.setObject(1,node.getGoods().getId());
                rs3_1 = pStmt3_1.executeQuery();
                while (rs3_1.next()) {
                    Remove rem = new Remove();
                    rem.setId(rs3_1.getInt("remove_id"));
                    rem.setGoods(node.getGoods());
                    rem.setDate(rs3_1.getDate("remove_date"));
                    rem.setO(rs3_1.getInt("number_o"));
                    node.getGoods().getRemoves().add(rem);
                    //第三层，rem全载入完成。
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt2,rs2);
            closeAll(null,pStmt3,rs3);
            closeAll(null,pStmt3_1,rs3_1);
        }
    }

    @Override
    public Sale querySaleById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Sale sale = null;
        String sql1 = "select * from sale where sale_id = ? and "+SaleSQL.IS_NORMAL_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //sale初步全载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return sale;
    }

    @Override
    public List<Sale> querySaleLikeName(String info) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where sale_name like ? and "+SaleSQL.IS_NORMAL_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,"%"+info+"%"); //模糊查询，%在此处加。
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return list;
    }

    @Override
    public List<Sale> querySaleAndPurchaseLikeName(String info) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where sale_name like ? and "+SaleSQL.IS_NORMAL_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,"%"+info+"%");
            //模糊查询，%注入。
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(new ArrayList<>());
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
                //循环传输下层：载入商品组成相关信息
                this.loadPurchaseToSale(sale, sale.getId(),conn);
                //循环传输下层：载入商品购买相关信息
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return list;
    }

    @Override
    public List<Sale> queryAllSaleAndPurchase() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where "+SaleSQL.IS_NORMAL_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(new ArrayList<>());
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
                //循环传输下层：载入商品组成相关信息
                this.loadPurchaseToSale(sale, sale.getId(),conn);
                //循环传输下层：载入商品购买相关信息
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return list;
    }

    /*本类的方法提炼-2。
     * 使用：适合数据库访问正进行到一半时。
     * 前提：sale的各个属性已载入或初始完成，
     * 否则：可能会有空指针。
     * 提示：本方法中不会关闭conn。方法创建并关闭statement和resultSet，不影响主调函数的操作。
     * */
    private void loadPurchaseToSale(Sale sale, int id, Connection conn){
        PreparedStatement pStmt2 = null;
        ResultSet rs2 = null;
        String sql2 = "select purchase_id, purchase_date, number_s, " +
                "customer.customer_id, customer_name, customer_pwd, customer_vip " +
                "from purchase, customer " +
                "where purchase.customer_id = customer.customer_id " +
                "and purchase.sale_id = ?";
        try {
            pStmt2 = conn.prepareStatement(sql2);
            pStmt2.setObject(1,id);
            rs2 = pStmt2.executeQuery();
            while (rs2.next()) {
                Purchase purchase = new Purchase();
                purchase.setCustomer(new Customer());
                purchase.getCustomer().setId(rs2.getInt("customer_id"));
                purchase.getCustomer().setName(rs2.getString("customer_name"));
                purchase.getCustomer().setPwd(rs2.getString("customer_pwd"));
                purchase.getCustomer().setVip(rs2.getInt("customer_vip"));
                purchase.getCustomer().setSalePurchase(null); //不向上层载入
                purchase.setSale(sale);
                purchase.setId(rs2.getInt("purchase_id"));
                purchase.setS(rs2.getInt("number_s"));
                purchase.setDate(rs2.getDate("purchase_date"));
                sale.getCustomerPurchase().add(purchase);
                //第二层，purchase对应的customer全载入完成。
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt2,rs2);
        }

    }

    @Override
    public Sale querySaleAndPurchaseById(int id) {
        return null;
    }

    @Override
    public List<Sale> queryFrontSale() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return list;

    }

    @Override
    public Sale queryFrontSaleById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Sale sale = null;
        String sql1 = "select * from sale where sale_id = ? and "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //sale初步全载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return sale;
    }

    @Override
    public List<Sale> queryAllFrontSaleAndPurchase() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(new ArrayList<>());
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
                //循环传输下层：载入商品组成相关信息
                this.loadPurchaseToSale(sale, sale.getId(),conn);
                //循环传输下层：载入商品购买相关信息
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return list;
    }

    @Override
    public int queryLastSaleId() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int maxSaleId = 0;
        String sql = "select max(sale_id) as max_sale_id " +
                "from sale";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            if (rs.next()) { //正常都有一个max_sale_id
                maxSaleId = rs.getInt("max_sale_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return maxSaleId;
    }

    @Override
    public int queryPidByOldSaleId(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int salePid = 0;
        String sql = "select sale_pid from sale " +
                "where sale_id = ? and "+SaleSQL.IS_OLD_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                salePid = rs.getInt("sale_pid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return salePid;
    }

    @Override
    public List<Sale> queryFrontDeletedSale() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Sale> list = new ArrayList<>();
        String sql1 = "select * from sale where "+SaleSQL.IS_FRONT_DELETED_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //makeup准备初始。
                list.add(sale);
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return list;

    }

    @Override
    public Sale queryFrontDeletedSaleById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Sale sale = null;
        String sql1 = "select * from sale where sale_id = ? and "+SaleSQL.IS_FRONT_DELETED_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //sale初步全载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return sale;
    }

    @Override
    public Sale queryAnySaleById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Sale sale = null;
        String sql1 = "select * from sale where sale_id = ? and "+SaleSQL.IS_ANY_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(null);
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //sale初步全载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return sale;
    }

    @Override
    public Sale queryAnySaleAndPurchaseById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Sale sale = null;
        String sql1 = "select * from sale where sale_id = ? and "+SaleSQL.IS_ANY_SALE;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            //模糊查询，%注入。
            rs = pStmt.executeQuery();
            if (rs.next()) {
                sale = new Sale();
                sale.setId(rs.getInt("sale_id"));
                sale.setName(rs.getString("sale_name"));
                sale.setPrice(rs.getDouble("sale_price"));
                sale.setCustomerPurchase(new ArrayList<>());
                sale.setSaleMakeup(new Makeup());
                sale.getSaleMakeup().setSale(sale);
                sale.getSaleMakeup().setMakeup(new HashSet<>());
                //第一层，sale初始载入完成。
                this.loadMakeupToSale(sale,sale.getId(),conn);
                //循环传输下层：载入商品组成相关信息
                this.loadPurchaseToSale(sale, sale.getId(),conn);
                //循环传输下层：载入商品购买相关信息
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return sale;
    }

    @Override
    public List<Sale> queryOldSaleByPid(int pid) {
        return null;
    }

    @Override
    public List<Sale> queryOldSaleAndPurchaseByPid(int pid) {
        return null;
    }

    @Override
    public int addSale(Sale sale) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "insert into sale(sale_name, sale_price) " +
                "values(?,?)";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,sale.getName());
            pStmt.setObject(2,sale.getPrice());
            //新记录不加sale_pid，它默认为null。将在约束上是：新代上架商品。
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }
        return i;
    }

    @Override
    public int addSaleMakeup(int saleId, Set<Makeup.Node> makeup) {
        Connection conn = null;
        PreparedStatement[] pStmtArr = new PreparedStatement[makeup.size()];
        //pStmtArr[j]需要被迭代。
        ResultSet rs = null;
        int i = 0;
        String sql = "insert into makeup(sale_id, goods_id, number_n) " +
                "values(?,?,?)";
        try {
            conn = getConn();
            int j = 0;
            //创建和设置每个：pStmtArr[j]。
            for (Makeup.Node node : makeup) {
                pStmtArr[j] = conn.prepareStatement(sql);
                pStmtArr[j].setObject(1,saleId);
                pStmtArr[j].setObject(2,node.getGoods().getId());
                pStmtArr[j].setObject(3,node.getN());
                j++;
            }
            //一次性执行所有sql更新。
            for (PreparedStatement pStmt : pStmtArr) {
                i += pStmt.executeUpdate();
                //更新操作无rs。
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            for (PreparedStatement pStmt : pStmtArr) {
                closeAll(null, pStmt, rs);
            }
            closeAll(conn,null,null);
        }
        return i;
    }

    @Override
    public int addSaleFromDeleteById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        PreparedStatement pStmt_ex = null;
        ResultSet rs = null;
        ResultSet rs_ex = null;
        int i = 0;

        String sql = "update sale set logout = 0 " +
                "where sale_id = ?";

        String sql_ex = "select * from sale " +
                "where sale_id = ? and "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt_ex = conn.prepareStatement(sql_ex);
            pStmt_ex.setObject(1,id);
            rs_ex = pStmt_ex.executeQuery();
            //如果是迭代品，就找不到任何数据。
            if (!rs_ex.next()) {
                throw new SQLException("重新上架某商品。【sql约束】只能是新代品，不能是迭代品。");
            }
            //检查约束正常后。
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt_ex,rs_ex);
            closeAll(null,pStmt,rs);
            closeAll(conn,null,null);
        }

        return i;
    }

    @Override
    public int updateSalePid(int id, int pid) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        PreparedStatement pStmt_ex = null;
        ResultSet rs = null;
        ResultSet rs_ex = null;
        int i = 0;

        String sql = "update sale set sale_pid = ? " +
                "where sale_id = ?";

        String sql_ex = "select * from sale " +
                "where sale_id = ? and "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt_ex = conn.prepareStatement(sql_ex);
            pStmt_ex.setObject(1,pid);
            rs_ex = pStmt_ex.executeQuery();
            //参看修改值pid是否对应一个新代。
            if (!rs_ex.next()) {
                throw new SQLException("更改指定id商品的pid。【sql约束】pid必须是一个新代。");
            }
            //检查约束正常后。
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,pid);
            pStmt.setObject(2,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt_ex,rs_ex);
            closeAll(null,pStmt,rs);
            closeAll(conn,null,null);
        }

        return i;
    }

    @Override
    public int updateSalePidGroup(int pid_former, int pid_now) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        PreparedStatement pStmt_ex = null;
        ResultSet rs = null;
        ResultSet rs_ex = null;
        int i = 0;

        String sql = "update sale set sale_pid = ? " +
                "where sale_pid = ?";

        String sql_ex = "select * from sale " +
                "where sale_id = ? and "+SaleSQL.IS_FRONT_SALE;
        try {
            conn = getConn();
            pStmt_ex = conn.prepareStatement(sql_ex);
            pStmt_ex.setObject(1,pid_now);
            rs_ex = pStmt_ex.executeQuery();
            //参看修改值pid是否对应一个新代。
            if (!rs_ex.next()) {
                throw new SQLException("更改商品所有pid_former为pid_now。【sql约束】pid_now必须是一个新代。");
            }
            //检查约束正常后。
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,pid_now);
            pStmt.setObject(2,pid_former);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt_ex,rs_ex);
            closeAll(null,pStmt,rs);
            closeAll(conn,null,null);
        }

        return i;
    }

    @Override
    public int deleteSaleById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int i = 0;
        String sql = "update sale set logout = 1 " +
                "where sale_id = ?";
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql);
            pStmt.setObject(1,id);
            i = pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }
}
