package com.weidong.datebase.impl;

import com.weidong.datebase.GoodsSQL;
import com.weidong.datebase.impl.superclass.BaseSQL;
import com.weidong.entity.Goods;
import com.weidong.entity.Import;
import com.weidong.entity.Remove;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsSQL_Impl extends BaseSQL implements GoodsSQL {
    public static void main(String[] args) {
        GoodsSQL_Impl impl = new GoodsSQL_Impl();

    }

    /*本类的方法提炼-1。
     * 使用：适合数据库访问正进行到一半时。
     * 前提：goods的各个属性已载入或初始完成，
     * 否则：可能会有空指针。
     * 提示：本方法中不会关闭conn。方法创建并关闭statement和resultSet，不影响主调函数的操作。
     * */
    private void loadRecordsToGoods(Goods goods, int id, Connection conn){
        PreparedStatement pStmt2 = null;
        PreparedStatement pStmt2_1 = null;
        ResultSet rs2 = null;
        ResultSet rs2_1 = null;

        String sql2 = "select * from import where import.goods_id = ?";

        String sql2_1 = "select * from remove where remove.goods_id = ?";

        try {
            pStmt2 = conn.prepareStatement(sql2);
            pStmt2.setObject(1,id);
            rs2 = pStmt2.executeQuery();
            while (rs2.next()) {
                Import imp = new Import();
                imp.setId(rs2.getInt("import_id"));
                imp.setGoods(goods);
                imp.setA(rs2.getInt("number_a"));
                imp.setDate(rs2.getDate("import_date"));
                goods.getImports().add(imp);
                //第二层，imp全载入完成。
            }
            pStmt2_1 = conn.prepareStatement(sql2_1);
            pStmt2_1.setObject(1,id);
            rs2_1 = pStmt2_1.executeQuery();
            while (rs2_1.next()) {
                Remove rem = new Remove();
                rem.setId(rs2_1.getInt("remove_id"));
                rem.setGoods(goods);
                rem.setDate(rs2_1.getDate("remove_date"));
                rem.setO(rs2_1.getInt("number_o"));
                goods.getRemoves().add(rem);
                //第二层，rem全载入完成。
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(null,pStmt2,rs2);
            closeAll(null,pStmt2_1,rs2_1);
        }
    }

    @Override
    public List<Goods> queryAllGoods() {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        List<Goods> list = new ArrayList<>();
        String sql1 = "select * from goods where "+GoodsSQL.IS_NORMAL_GOODS;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("goods_id"));
                goods.setName(rs.getString("goods_name"));
                goods.setType(rs.getString("goods_type"));
                goods.setC(rs.getInt("number_c"));
                goods.setComposeSale(null);
                goods.setImports(new ArrayList<>());
                goods.setRemoves(new ArrayList<>());
                list.add(goods);
                //goods初步全载入完成。
                this.loadRecordsToGoods(goods,goods.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return list;
    }

    @Override
    public Goods queryGoodsById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Goods goods = null;
        String sql1 = "select * from goods " +
                "where goods_id = ? and "+GoodsSQL.IS_NORMAL_GOODS;
        try {
            conn = getConn();
            pStmt = conn.prepareStatement(sql1);
            pStmt.setObject(1,id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                goods = new Goods();
                goods.setId(rs.getInt("goods_id"));
                goods.setName(rs.getString("goods_name"));
                goods.setType(rs.getString("goods_type"));
                goods.setC(rs.getInt("number_c"));
                goods.setComposeSale(null);
                goods.setImports(new ArrayList<>());
                goods.setRemoves(new ArrayList<>());
                //goods初步全载入完成。
                this.loadRecordsToGoods(goods,goods.getId(),conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pStmt,rs);
        }

        return goods;
    }

    @Override
    public Goods queryGoodsByNameAndType(String name, String type) {
        return null;
    }

    @Override
    public List<Goods> queryAllGoodsAndSale() {
        return null;
    }

    @Override
    public Goods queryGoodsAndSaleById(int id) {
        return null;
    }

    @Override
    public Goods queryGoodsAndSaleByNameAndType(String name, String type) {
        return null;
    }

    @Override
    public List<Goods> queryGoodsLike(String info) {
        return null;
    }

    @Override
    public List<Goods> queryGoodsLikeName(String info) {
        return null;
    }

    @Override
    public List<Goods> queryGoodsLikeType(String info) {
        return null;
    }

    @Override
    public Goods queryAllGoodsAndSaleAndCustomer() {
        return null;
    }

    @Override
    public Goods queryGoodsAndSaleAndCustomerById(int id) {
        return null;
    }

    @Override
    public Goods queryGoodsAndSaleAndCustomerByNameAndType(String name, String type) {
        return null;
    }

    @Override
    public List<Goods> queryAllAnyGoods() {
        return null;
    }

    @Override
    public Goods queryAnyGoodsById(int id) {
        return null;
    }

    @Override
    public Goods queryAnyGoodsByNameAndType(String name, String type) {
        return null;
    }

    @Override
    public Goods queryForbiddenGoodsById(int id) {
        return null;
    }

    @Override
    public List<Goods> queryForbiddenGoods() {
        return null;
    }

    @Override
    public int addImport(Import _import) {
        return 0;
    }

    @Override
    public int addGoods(Goods goods) {
        return 0;
    }

    @Override
    public int addRemove(Remove remove) {
        return 0;
    }

    @Override
    public int addGoodsFromDeleteById(int id) {
        return 0;
    }

    @Override
    public int updateGoodsCById(Goods goods, int id) {
        return 0;
    }

    @Override
    public int updateGoodsNameAndTypeById(Goods goods, int id) {
        return 0;
    }

    @Override
    public int deleteGoodsById(int id) {
        return 0;
    }
}
