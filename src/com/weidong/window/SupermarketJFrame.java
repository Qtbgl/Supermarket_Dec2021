package com.weidong.window;

import com.weidong.entity.*;
import com.weidong.role.MgrRole;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

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
            return "SaleObject{" +
                    "sale=" + sale +
                    '}';
        }
    }

    private static class GoodsObject{
        Goods goods;

        @Override
        public String toString() {
            return "GoodsObject{" +
                    "goods=" + goods +
                    '}';
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

    private static class ImportObject{
        Import anImport;

        @Override
        public String toString() {
            return "ImportObject{" +
                    "anImport=" + anImport +
                    '}';
        }

        public Import getAnImport() {
            return anImport;
        }

        public void setAnImport(Import anImport) {
            this.anImport = anImport;
        }

        public ImportObject() {
        }

        public ImportObject(Import anImport) {
            this.anImport = anImport;
        }
    }

    private static class RemoveObject{
        Remove remove;

        public RemoveObject() {
        }

        @Override
        public String toString() {
            return "RemoveObject{" +
                    "remove=" + remove +
                    '}';
        }

        public Remove getRemove() {
            return remove;
        }

        public void setRemove(Remove remove) {
            this.remove = remove;
        }

        public RemoveObject(Remove remove) {
            this.remove = remove;
        }
    }
    //载入类

    Container contentPane;
    JPanel panelTree;
    JTree tree;
    JPanel panelSide;
    JPanel panel1;
    JPanel panel2;
    JButton button1;
    JButton button2;

    //角色：超市管理员
    MgrRole user;

    public SupermarketJFrame() throws HeadlessException {
        //基本属性
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(600,400));
        this.setLocationRelativeTo(null);
        //布局
        contentPane = this.getContentPane();
        contentPane.setLayout(new GridLayout(1,2));
        panelTree = new JPanel();
        panelTree.setLayout(new GridLayout(1,1));
        contentPane.add(panelTree);
        panelSide = new JPanel();
        panelSide.setLayout(new GridLayout(1,1));
        contentPane.add(panelSide);
        //容器组件
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,1));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,1));
        //左侧的树组件
        JTree tree = new JTree();
        tree.setShowsRootHandles(true); //设置树显示根节点句柄
        tree.setEditable(false); //设置树节点可编辑
        panelTree.add(tree);


        //右侧一系列组件
        button1 = new JButton("按钮一号");
        button1.addActionListener(new Btn1Respond());
        button2 = new JButton("按钮二号");
        button2.addActionListener(new Btn2Respond());
        panel1.add(button1);
        panel2.add(button2);
        //初始操作
        panelSide.add(panel1);

        /*DefaultMutableTreeNode nodeSale1 = new DefaultMutableTreeNode("商品1");
        DefaultMutableTreeNode nodeSale2 = new DefaultMutableTreeNode("商品2");
        DefaultMutableTreeNode nodeSale3 = new DefaultMutableTreeNode("商品3");
        DefaultMutableTreeNode nodeSale4 = new DefaultMutableTreeNode("商品4");
        DefaultMutableTreeNode nodeSale5 = new DefaultMutableTreeNode("商品5");
        DefaultMutableTreeNode nodeSale6 = new DefaultMutableTreeNode("商品6");
        rootNode.add(nodeSale1);
        rootNode.add(nodeSale2);
        rootNode.add(nodeSale3);
        rootNode.add(nodeSale4);
        rootNode.add(nodeSale5);
        rootNode.add(nodeSale6);
        DefaultMutableTreeNode nodeGoods1 = new DefaultMutableTreeNode("货品1");
        DefaultMutableTreeNode nodeGoods2 = new DefaultMutableTreeNode("货品2");
        DefaultMutableTreeNode nodeGoods3 = new DefaultMutableTreeNode("货品3");
        DefaultMutableTreeNode nodeGoods4 = new DefaultMutableTreeNode("货品4");
        nodeSale1.add(nodeGoods1);
        nodeSale2.add(nodeGoods1);
        nodeSale2.add(nodeGoods2);
        nodeSale3.add(nodeGoods3);
        nodeSale4.add(nodeGoods4);
        nodeSale5.add(nodeGoods3);
        nodeSale5.add(nodeGoods4);
        nodeSale6.add(nodeGoods1);
        nodeSale6.add(nodeGoods3);
        nodeSale6.add(nodeGoods2);*/

    }

    //无数据可测试
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
                for (Import imp : node.getGoods().getImports()) {
                    DefaultMutableTreeNode impNode = new DefaultMutableTreeNode();
                    impNode.setUserObject(new ImportObject(imp));
                    goodsNode.add(impNode);
                    //创建进口记录结点
                }
                for (Remove rem : node.getGoods().getRemoves()) {
                    DefaultMutableTreeNode remNode = new DefaultMutableTreeNode();
                    remNode.setUserObject(new RemoveObject(rem));
                    goodsNode.add(remNode);
                    //创建出口记录结点
                }
            }
        }
        //全部载入完成，到rootNode中。
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }

    public static void main(String[] args) {
        SupermarketJFrame supermarketJFrame = new SupermarketJFrame();
        supermarketJFrame.setVisible(true);
    }

    private class Btn1Respond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("按钮一号事件");
            panelSide.removeAll();
            panelSide.repaint();
            panelSide.add(panel2);
            panelSide.revalidate();
        }
    }
    private class Btn2Respond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("按钮二号事件");
            panelSide.removeAll();
            panelSide.repaint();
            panelSide.add(panel1);
            panelSide.revalidate();
        }
    }
}
