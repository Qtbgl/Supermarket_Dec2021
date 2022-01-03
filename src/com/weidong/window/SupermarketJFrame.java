package com.weidong.window;

import com.weidong.biz.impl.CustomerBizImpl;
import com.weidong.biz.impl.GoodsBizImpl;
import com.weidong.biz.impl.SaleBizImpl;
import com.weidong.datebase.impl.CustomerSQL_Impl;
import com.weidong.datebase.impl.GoodsSQL_Impl;
import com.weidong.datebase.impl.SaleSQL_Impl;
import com.weidong.entity.*;
import com.weidong.exception.biz.IdNotFoundException;
import com.weidong.exception.biz.ItemCountException;
import com.weidong.exception.biz.ValueUnreasonException;
import com.weidong.exception.window.NothingSelectException;
import com.weidong.role.MgrRole;
import com.weidong.window.tools.OneSale;
import com.weidong.window.tools.TableGoods;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class SupermarketJFrame extends JFrame {
    private static class SaleObject{
        Sale sale;

        @Override
        public String toString() {
            return sale.getName();
        }

        public SaleObject() {
        }

        public SaleObject(Sale sale) {
            this.sale = sale;
        }
    }

    private static class GoodsObject{
        Goods goods;

        Sale belongSale;

        @Override
        public String toString() {
            return goods.getName();
        }

        public GoodsObject() {
        }

        public GoodsObject(Goods goods, Sale belongSale) {
            this.goods = goods;
            this.belongSale = belongSale;
        }
    }

    //载入类

    Container contentPane;
    JPanel panelSide;
    JTree tree; //树结点可以存数据。
    OneSale oneSale;
    //本类的辅助功能-1
    private void printToTree(List<Sale> sales){
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        rootNode.setUserObject("超市");
        for (Sale sale : sales) {
            DefaultMutableTreeNode saleNode = new DefaultMutableTreeNode();
            saleNode.setUserObject(new SaleObject(sale));
            rootNode.add(saleNode);
            //创建商品结点
            Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
            for (Makeup.Node node : makeup) {
                DefaultMutableTreeNode goodsNode = new DefaultMutableTreeNode();
                goodsNode.setUserObject(new GoodsObject(node.getGoods(),sale));
                saleNode.add(goodsNode);
                //创建货品结点
            }
        }
        //全部载入完成，到rootNode中。
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }


    JPanel panelBody;
    JTable goodsTable; //主表不能存数据
    TableGoods tableGoods;



    /*以上为tree和table组键的辅助类、函数*/


    boolean isSystemOperating;
    public final static int TABLE_COLUMNS = 5;
    public final static int TABLE_ROWS = 17;
    JPanel panelBottom;
    JLabel saleNameLabel;
    JLabel salePriceLabel;
    JTextField saleNameTextField;
    JTextField salePriceTextField;
    JButton saleNameButton;
    JButton salePriceButton;
    JButton saleLookButton;

    //角色：超市管理员
    MgrRole user;

    public SupermarketJFrame() throws HeadlessException {
        //基本属性
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(600,400));
        this.setLocationRelativeTo(null);
        //布局
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout(10,10));
        panelSide = new JPanel();
        panelSide.setLayout(new GridLayout(1,1));
        contentPane.add(panelSide,BorderLayout.WEST);
        panelBody = new JPanel();
        panelBody.setLayout(new BorderLayout(10,10));
        contentPane.add(panelBody,BorderLayout.CENTER);
        panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPane.add(panelBottom,BorderLayout.SOUTH);
        //右侧的主表组件
        Object[] columnNames = {"货品名称","货品类型","现存数量","组成数量","状态"};
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        goodsTable = new JTable(rowData,columnNames);
        panelBody.add(goodsTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(goodsTable,BorderLayout.CENTER);
        tableGoods = new TableGoods(this.oneSale,this.goodsTable); //空初始

        //左侧的树组件
        tree = new JTree();
        tree.setShowsRootHandles(true);
        tree.setEditable(false);
        panelSide.add(tree);
        oneSale = new OneSale(this.saleNameTextField,this.salePriceTextField); //空初始
        isSystemOperating = false;

        //下侧的功能栏组件
        saleLookButton = new JButton("查看详情");
        panelBottom.add(saleLookButton);

        saleNameLabel = new JLabel("商品名称");
        saleNameTextField = new JTextField(8);
        saleNameButton = new JButton("更改名称");
        panelBottom.add(saleNameLabel);
        panelBottom.add(saleNameTextField);
        panelBottom.add(saleNameButton);

        salePriceLabel = new JLabel("价格");
        salePriceTextField = new JTextField(8);
        salePriceButton = new JButton("更改价格");
        panelBottom.add(salePriceLabel);
        panelBottom.add(salePriceTextField);
        panelBottom.add(salePriceButton);

        //事件监听-1
        tree.addTreeSelectionListener(new TreeSelectRespond());
        //事件监听-2
        goodsTable.getModel().addTableModelListener(new TableRespond());
        //事件监听-3
        saleNameButton.addActionListener(new SaleNameRespond());
        salePriceButton.addActionListener(new SalePriceRespond());
    }

    public void init(){
        //界面功能调用-1
        /*List<Goods> list = user.seeGoods();
        List<Sale> sales = user.seeSale();
        //渲染侧树的信息。
        this.printToTree(sales);
        //渲染出初始的货品栏信息，未选中任何商品。
        tableGoods.setList(list);
        tableGoods.printToTable();*/
    }

    private class SaleNameRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = saleNameTextField.getText();
            System.out.println(name);
            Sale copy = null;
            try {
                if (oneSale.isEmpty()) {
                    throw new NothingSelectException();
                }
                //获取更改id
                copy = oneSale.getCopy();
                //界面功能调用-3
                user.modifySaleName(copy.getId(),copy.getName());
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "商品名更改成功！",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NothingSelectException nothingSelectException) {
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "您还未选择商品！",
                        "更改失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (ValueUnreasonException valueUnreasonException) {
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "商品名称不能为空！",
                        "更改失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (IdNotFoundException idNotFoundException) {
                idNotFoundException.printStackTrace();
            } finally {
                //重新渲染商品树和货品栏
                List<Sale> sales = user.seeSale();
                List<Goods> list = user.seeGoods();
                tableGoods.setList(list);
                //判断原先是否选中。
                if (copy != null) {
                    //oneSale刷新成新商品的选中状态。
                    try {
                        Sale sale = user.seeFrontBySaleId(copy.getId());
                        oneSale.setSale(sale);
                    } catch (IdNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                //oneSale载入后，tableGoods将自动打印相关信息。
                SupermarketJFrame.this.printToTree(sales);
                oneSale.printToBottom();
                tableGoods.printToTable();
            }
        }
    }

    private class SalePriceRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String priceInfo = salePriceTextField.getText();
            System.out.println(priceInfo);
            Sale copy = null;
            try {
                double price = Double.parseDouble(priceInfo);
                if (oneSale.isEmpty()) {
                    throw new NothingSelectException();
                }
                //获取更改id
                copy = oneSale.getCopy();
                //界面功能调用-4
                user.modifySalePrice(copy.getId(),price);
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "商品名更改成功！",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException numberFormatException){
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "价格异常！",
                        "更改失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (NothingSelectException numberFormatException){
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "您还未选择商品！",
                        "更改失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (ValueUnreasonException valueUnreasonException) {
                JOptionPane.showMessageDialog(
                        saleNameButton,
                        "价格不能低于零！",
                        "更改失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (IdNotFoundException idNotFoundException) {
                idNotFoundException.printStackTrace();
            } finally {
                //重新渲染商品树和货品栏
                List<Sale> sales = user.seeSale();
                List<Goods> list = user.seeGoods();
                tableGoods.setList(list);
                if (copy != null){
                    try {
                        Sale sale = user.seeFrontBySaleId(copy.getId());
                        oneSale.setSale(sale);
                    } catch (IdNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                //oneSale载入后，tableGoods将自动打印相关信息。
                SupermarketJFrame.this.printToTree(sales);
                oneSale.printToBottom();
                tableGoods.printToTable();
            }
        }
    }

    private class TreeSelectRespond implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node != null){ //如果node不是空，则没有选择柄。                
                Object userObject = node.getUserObject();
                System.out.println(userObject);
                if (userObject instanceof String) {
                    System.out.println("选中了超市");
                    //界面功能调用-2
                    List<Goods> list = user.seeGoods();
                    List<Sale> sales = user.seeSale();
                    //重新初始化渲染信息
                    SupermarketJFrame.this.printToTree(sales);
                    tableGoods.setList(list);
                    tableGoods.printToTable();

                } else if (userObject instanceof SaleObject saleObject) {
                    System.out.println(saleObject.sale);
                    //设置选中对象
                    oneSale.setSale(saleObject.sale);
                    oneSale.printToBottom();
                    //主表自动打印选择信息。不需要更新数据。
                    tableGoods.printToTable();

                } else if (userObject instanceof GoodsObject goodsObject) {
                    System.out.println(goodsObject.goods);
                    //设置选中对象
                    oneSale.setSale(goodsObject.belongSale);
                    oneSale.printToBottom();
                    //主表自动打印选择信息。不需要更新数据。
                    tableGoods.printToTable();
                }
            } else {
                System.out.println("选择到了树柄。");
            }
        }
    }

    /*以上为侧树的事件响应*/

    private class TableRespond implements TableModelListener{
        @Override
        public void tableChanged(TableModelEvent e) {
            //System.out.println("Table改变事件，isSystemOperating = "+isSystemOperating);
            if (!isSystemOperating){
                int row = e.getFirstRow();
                int col = e.getColumn();
                //更改列3：货品组成数量。

            }

        }
    }

    /*以上是主表事件的响应*/


    public static void main(String[] args) {
        SupermarketJFrame supermarketJFrame = new SupermarketJFrame();
        CustomerSQL_Impl customerSQL_ = new CustomerSQL_Impl();
        SaleSQL_Impl saleSQL_ = new SaleSQL_Impl();
        GoodsSQL_Impl goodsSQL_ = new GoodsSQL_Impl();
        //SQL层依赖注入。
        supermarketJFrame.user = new MgrRole(
                new CustomerBizImpl(customerSQL_,goodsSQL_,saleSQL_),
                new GoodsBizImpl(customerSQL_,goodsSQL_,saleSQL_),
                new SaleBizImpl(customerSQL_,goodsSQL_,saleSQL_)
        );
        supermarketJFrame.init();
        supermarketJFrame.setVisible(true);
    }

}
