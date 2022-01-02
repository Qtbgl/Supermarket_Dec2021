package com.weidong.window;

import com.weidong.biz.impl.CustomerBizImpl;
import com.weidong.biz.impl.GoodsBizImpl;
import com.weidong.biz.impl.SaleBizImpl;
import com.weidong.datebase.impl.CustomerSQL_Impl;
import com.weidong.datebase.impl.GoodsSQL_Impl;
import com.weidong.datebase.impl.SaleSQL_Impl;
import com.weidong.entity.*;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.role.MgrRole;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class SupermarketJFrame extends JFrame {
    private static class SaleObject{
        Sale sale;

        public SaleObject() {
        }

        public SaleObject(Sale sale) {
            this.sale = sale;
        }

        public Sale getSale() {
            return sale;
        }

        public void setSale(Sale sale) {
            this.sale = sale;
        }

        @Override
        public String toString() {
            return sale.getName();
        }
    }

    private static class GoodsObject{
        Goods goods;

        @Override
        public String toString() {
            return goods.getName();
        }

        public Goods getGoods() {
            return goods;
        }

        public void setGoods(Goods goods) {
            this.goods = goods;
        }

        public GoodsObject() {
        }

        public GoodsObject(Goods goods) {
            this.goods = goods;
        }
    }

    //载入类

    Container contentPane;
    JPanel panelSide;
    JTree tree;
    Sale oneSale;
    JPanel panelBody;
    JTable goodsTable;
    boolean isSystemOperating;
    final static int TABLE_COLUMNS = 5;
    final static int TABLE_ROWS = 17;
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
        Object[] columnNames = {"货品名称","货品类型","现存数量","组成数量","选择"};
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        goodsTable = new JTable(rowData,columnNames);
        panelBody.add(goodsTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(goodsTable,BorderLayout.CENTER);

        //左侧的树组件
        tree = new JTree();
        tree.setShowsRootHandles(true);
        tree.setEditable(false);
        panelSide.add(tree);
        oneSale = null;
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
        goodsTable.addMouseListener(new PointRespond());
    }

    public void init(){
        //界面功能调用-1
        List<Goods> list = user.seeGoods();
        List<Sale> sales = user.seeSale();

        this.printToTree(sales);

        this.printToTable(list);
    }

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
                goodsNode.setUserObject(new GoodsObject(node.getGoods()));
                saleNode.add(goodsNode);
                //创建货品结点
            }
        }
        //全部载入完成，到rootNode中。
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }

    //本类的辅助功能-2
    private void printToTable(List<Goods> list){
        isSystemOperating = true;   //调节冲突
        for (int i = 0; i < list.size() && i < TABLE_ROWS; ++i){
            Goods goods = list.get(i);
            goodsTable.setValueAt(new GoodsObject(goods),i,0); //0列，放对象实体。
            goodsTable.setValueAt(goods.getType(),i,1);
            goodsTable.setValueAt(goods.getC(),i,2);
            goodsTable.setValueAt("无",i,3);
            goodsTable.setValueAt(null,i,4);
        }
        isSystemOperating = false;
    }

    //本类的辅助功能-3
    private void printToTable(List<Goods> list, Makeup makeup){
        isSystemOperating = true;   //调节冲突
        Map<Integer,Makeup.Node> helpMap = new HashMap<>();
        for (Makeup.Node node : makeup.getMakeup()) {
            helpMap.put(node.getGoods().getId(),node);
        }
        //渲染数据
        for (int i = 0; i < list.size() && i < TABLE_ROWS; ++i){
            Goods goods = list.get(i);
            goodsTable.setValueAt(new GoodsObject(goods),i,0); //0列，放对象实体。
            goodsTable.setValueAt(goods.getType(),i,1);
            goodsTable.setValueAt(goods.getC(),i,2);
            Makeup.Node node = helpMap.get(goods.getId());
            if (node == null){
                goodsTable.setValueAt("无",i,3);
            } else {
                goodsTable.setValueAt(node.getN(),i,3);
            }
            goodsTable.setValueAt(null,i,4);
        }
        isSystemOperating = false;
    }

    //本类的辅助功能-4
    public Sale copyOneSale(){ //到node的级别，没有复制goods。
        if (oneSale == null) {
            return null;
        }
        Sale sale = new Sale();
        sale.setId(oneSale.getId());
        sale.setName(oneSale.getName());
        sale.setPrice(oneSale.getPrice());
        sale.setSaleMakeup(new Makeup());
        sale.getSaleMakeup().setSale(sale);
        sale.getSaleMakeup().setMakeup(new HashSet<>());
        Set<Makeup.Node> makeup1 = sale.getSaleMakeup().getMakeup();
        Set<Makeup.Node> makeup = oneSale.getSaleMakeup().getMakeup();
        for (Makeup.Node node : makeup) {
            Makeup.Node node1 = new Makeup.Node();
            node1.setGoods(node.getGoods());
            node1.setN(node.getN());
            makeup1.add(node1);
        }
        return sale;
    }


    private class PointRespond implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = goodsTable.rowAtPoint(e.getPoint());
            int col = goodsTable.columnAtPoint(e.getPoint());
//            System.out.println("("+row+","+col+")");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class TableRespond implements TableModelListener{

        @Override
        public void tableChanged(TableModelEvent e) {
            //System.out.println("Table改变事件，isSystemOperating = "+isSystemOperating);
            if (!isSystemOperating){
                int row = e.getFirstRow();
                int col = e.getColumn();
                //更改列3：货品组成数量。
                if (col == 3){
                    if (oneSale != null){
                        Makeup.Node find = null; //寻找，记录原来的N值。
                        String infoN = (String) goodsTable.getValueAt(row,col);
                        GoodsObject goodsObject = (GoodsObject) goodsTable.getValueAt(row, 0);
                        System.out.println("准备更新组成商品："+goodsObject);
                        try {
                            boolean flag = false; //标志：是修改，不是去除
                            int dataN = 0;
                            if (infoN.equals("无")){
                                flag = true;
                            }else {
                                dataN = Integer.parseInt(infoN);
                            }
                            //Sale saleCopy = SupermarketJFrame.this.copyOneSale();
                            Goods goods = goodsObject.getGoods();
                            //添加信息到货品组成。
                            for (Makeup.Node node : oneSale.getSaleMakeup().getMakeup()) {
                                if (node.getGoods().getId() == goods.getId()){
                                    find = node; //已锁定到内部成员，直接改值。
                                    break;
                                }
                            }
                            if (flag){
                                if (find == null){ //操作异常，同上一个处理
                                    throw new NumberFormatException("操作异常，同上一个处理");
                                }else { //去除，第二种情况。
                                    oneSale.getSaleMakeup().getMakeup().remove(find);
                                }
                            }else{ //更改和新增
                                if (find == null) {
                                    Makeup.Node node1 = new Makeup.Node();
                                    node1.setN(dataN);
                                    node1.setGoods(goods);  //goods被共占，允许只读。
                                    oneSale.getSaleMakeup().getMakeup().add(node1);
                                } else {
                                    find.setN(dataN);
                                }
                            }
                            //界面功能调用-3
                            user.modifySaleMakeup(oneSale.getId(),oneSale.getSaleMakeup());
                            JOptionPane.showMessageDialog(
                                    SupermarketJFrame.this,
                                    "更改组成数量成功！",
                                    "提示",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } catch (NumberFormatException numberFormatException){
                            //System.out.println("输入不是数字，或者本来是‘无’又选了‘无’。");
                            JOptionPane.showMessageDialog(
                                    SupermarketJFrame.this,
                                    "输入无意义",
                                    "更改失败！",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        } catch (ItemCountException itemCountException) {
                            JOptionPane.showMessageDialog(
                                    SupermarketJFrame.this,
                                    "数量异常",
                                    "更改失败！",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        } catch (IdNotFoundException idNotFoundException) {
                            idNotFoundException.printStackTrace();
                        } finally {
                            //界面功能调用-4
                            //oneSale = user.seeSaleById(oneSale.getId());
                            //有bug，这时的oneSale已经过时了！
                            oneSale = null;
                            List<Sale> sales = user.seeSale();
                            List<Goods> list = user.seeGoods();
                            SupermarketJFrame.this.printToTree(sales);
                            SupermarketJFrame.this.printToTable(list);
                        }
                    }
                }


            }

        }
    }

    /*以上是主表事件的响应*/

    private class TreeSelectRespond implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node != null){ //如果node不是空，则没有选择柄。                
                Object userObject = node.getUserObject();
                System.out.println(userObject);
                if (userObject instanceof String) {
                    System.out.println("选中了超市");
                } else if (userObject instanceof SaleObject saleObject) {
                    System.out.println(saleObject.getSale());
                    //刷新下边栏功能块。
                    oneSale = saleObject.getSale();
                    saleNameTextField.setText(oneSale.getName());
                    salePriceTextField.setText(""+oneSale.getPrice());
                    //界面功能调用-2
                    List<Goods> list = user.seeGoods();
                    SupermarketJFrame.this.printToTable(list,oneSale.getSaleMakeup());

                } else if (userObject instanceof GoodsObject goodsObject) {
                    System.out.println(goodsObject.getGoods());
                }
            } else {
                System.out.println("选择到了树柄。");
            }
        }
    }


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
