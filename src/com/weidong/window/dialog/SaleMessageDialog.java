package com.weidong.window.dialog;

import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Purchase;
import com.weidong.entity.Sale;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SaleMessageDialog extends JDialog {
    public static class SaleObject{
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
    Container contentPane;
    JPanel panelBody;
    JTree tree;   //后期换tree的model
    JPanel panelSide;
    JLabel nameLabel;
    JTextField nameTextField;
    JLabel priceLabel;
    JTextField priceTextField;
    JLabel detailLabel;
    JTextField detailTextField;
    JLabel peopleLabel;
    JTextField peopleTextField;
    JLabel allSaleSLabel;
    JTextField allSaleSTextField;
    List<Sale> saleGroup;

    //saleGroup: 新代商品排前
    public SaleMessageDialog(Frame owner, boolean modal, Component parentComponent, List<Sale> saleGroup) {
        super(owner,modal);
        //基本设置
        this.setBounds(new Rectangle(400,200));
        this.setLocationRelativeTo(parentComponent);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.saleGroup = saleGroup;
        this.setTitle("商品统计情况");
        //布局设置
        contentPane = this.getContentPane();
        this.setLayout(new BorderLayout(10,10));
        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(1,1));
        contentPane.add(panelBody,BorderLayout.CENTER);
        panelSide = new JPanel();
        panelSide.setLayout(new GridLayout(5,1));
        contentPane.add(panelSide,BorderLayout.WEST);
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p4.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p5.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelSide.add(p1);
        panelSide.add(p2);
        panelSide.add(p3);
        panelSide.add(p4);
        panelSide.add(p5);
        //组件设置
        nameLabel = new JLabel("商品名称");
        nameTextField = new JTextField(16);
        nameTextField.setEditable(false);
        p1.add(nameLabel);
        p1.add(nameTextField);

        priceLabel = new JLabel("商品价格");
        priceTextField = new JTextField(16);
        priceTextField.setEditable(false);
        p2.add(priceLabel);
        p2.add(priceTextField);

        detailLabel = new JLabel("详细描述");
        detailTextField = new JTextField(16);
        detailTextField.setEditable(false);
        p3.add(detailLabel);
        p3.add(detailTextField);

        peopleLabel = new JLabel("购买人数");
        peopleTextField = new JTextField(16);
        peopleTextField.setEditable(false);
        p4.add(peopleLabel);
        p4.add(peopleTextField);

        allSaleSLabel = new JLabel("销量");
        allSaleSTextField = new JTextField(16);
        allSaleSTextField.setEditable(false);
        p5.add(allSaleSLabel);
        p5.add(allSaleSTextField);

        //创建空树，后期在加treeModel
        tree = new JTree();
        panelBody.add(tree);

        //事件监听-1
        tree.addTreeSelectionListener(new selectRespond());
    }

    public void init(){

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        //界面功能-1
        //list商品渲染
        if (saleGroup.size()>=1){
            Sale now = saleGroup.get(0); //元素至少有一个，新代商品排前。
            rootNode.setUserObject(new SaleObject(now));
            // 创建二级节点
            for (int i = 1; i < saleGroup.size(); i++) {
                Sale past = saleGroup.get(i);
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode();
                childNode.setUserObject(new SaleObject(past));
                rootNode.add(childNode);
            }
            //侧边组件的数据渲染
            /*this.printToPanelSide(now);*/
        }
        // 使用根节点创建treeModel树组件
        //注意: treeModel的构造，需要在rootNode都添加设置完毕后。
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }

    //内部的辅助方法

    private void printToPanelSide(Sale saleGetPurchased){
        //商品详细信息，如：草莓1份，橘子2份，袋子1份
        StringBuilder saleDetail = new StringBuilder();
        Set<Makeup.Node> makeup = saleGetPurchased.getSaleMakeup().getMakeup();
        for (Makeup.Node node : makeup) {
            Goods goods = node.getGoods();
            int n = node.getN();
            saleDetail.append(goods.getName()).append(n).append("份，");
        }
        saleDetail.deleteCharAt(saleDetail.lastIndexOf("，"));
        //基本数据渲染
        nameTextField.setText(saleGetPurchased.getName());
        priceTextField.setText(""+saleGetPurchased.getPrice());
        detailTextField.setText(saleDetail.toString());
        //统计值：商品购买人数和总数
        Set<Integer> people = new HashSet<>();
        int allSaleS = 0;
        for (Purchase purchase : saleGetPurchased.getCustomerPurchase()) {
            allSaleS += purchase.getS();
            people.add(purchase.getCustomer().getId());
        }
        peopleTextField.setText(""+people.size());
        allSaleSTextField.setText(""+allSaleS);
    }

    //事件监听器

    private class selectRespond implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            SaleObject saleObject = (SaleObject) node.getUserObject();
            System.out.println(saleObject);
            System.out.println(saleObject.getSale());
            //界面功能-2
            /*Sale sale = saleObject.getSale();
            SaleMessageDialog.this.printToPanelSide(sale);*/
        }
    }
}
