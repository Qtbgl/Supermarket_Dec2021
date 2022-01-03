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

        Sale belongSale;

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

        public Sale getBelongSale() {
            return belongSale;
        }

        public void setBelongSale(Sale belongSale) {
            this.belongSale = belongSale;
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
                goodsNode.setUserObject(new GoodsObject(node.getGoods()));
                saleNode.add(goodsNode);
                //创建货品结点
            }
        }
        //全部载入完成，到rootNode中。
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }

    private class OneSale{
        private Sale sale;
        //正常的商品：有组成，没有购买情况。
        //用于记录商品树中选中的一个。
        //只能set，get只能克隆一个，克隆程度（goods之上）。以防止被误改
        //增加判空方法，判断有没有注入。
        //更新通过set方法。
        //封装一些辅助功能，如查找，匹配，提供给其他方法。
        //还可以有输出到组件的功能。
        //JFrame构建时创建，那时无sale。

        //用于方便查找goods，与sale伴生。
        private Map<Integer,Makeup.Node> map;
        //选中的属于该商品的货品。依赖此类，被访问保护，但不同步。
        private GoodsObject oneGoods;

        //通过临时类，选中侧树上的货品，等同于选中了所属的商品。
        public void setOneGoods(GoodsObject oneGoods) {
            this.oneGoods = oneGoods;
        }

        public void setSale(Sale sale) {
            this.sale = sale;
            if (sale == null){
                map = null; //同步释放
            } else {
                Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
                map = new HashMap<>();
                for (Makeup.Node node : makeup) { //同步载入
                    map.put(node.getGoods().getId(),node);
                }
            }
        }

        public boolean isEmpty(){
            return sale == null;
        }

        public boolean hasGoods(int goodsId){
            return map.containsKey(goodsId);
        }

        //获取的set也是克隆过的。
        public Set<Makeup.Node> getGoodsMakeup(){
            Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
            Set<Makeup.Node> makeup1 = new HashSet<>();
            for (Makeup.Node node : makeup) {
                Makeup.Node node1 = new Makeup.Node();
                node1.setN(node.getN());  //载入N
                node1.setGoods(node.getGoods()); //载入goods
                makeup1.add(node1);
                //node1全载入完成
            }
            return makeup1;
        }

        //获取oneGoods在第一个的组成货品
        public List<Makeup.Node> getGoodsMakeupInOrder(){
            List<Makeup.Node> list = new ArrayList<>(this.getGoodsMakeup());
            if (oneGoods == null) {
                return list;
            }
            //一个商品至少有一个货品。
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getGoods().getId() == oneGoods.getGoods().getId()){
                    Makeup.Node temp = list.get(0);
                    list.set(0,list.get(i));
                    list.set(i,temp);
                    break;
                }
            }
            return list;
        }

        //克隆到Goods以上的级别
        public Sale getCopy(){
            Sale sale1 = new Sale();
            sale1.setId(sale.getId());
            sale1.setName(sale.getName());
            sale1.setPrice(sale.getPrice());
            sale1.setSaleMakeup(new Makeup());
            sale1.getSaleMakeup().setSale(sale1);
            sale1.getSaleMakeup().setMakeup(this.getGoodsMakeup());
            return sale1;
        }

        //下下边栏的组件上打印
        public void printToBottom(){;
            saleNameTextField.setText(sale.getName());
            salePriceTextField.setText(""+sale.getPrice());
        }

    }

    JPanel panelBody;
    JTable goodsTable; //主表不能存数据
    TableGoods tableGoods;

    private class TableGoods{
        //list存放未遗弃的所有货品。
        List<Goods> list;
        //与table上陈列的数据实时的下标和行匹配
        //辅助功能，打印到主表
        //因此需要一些值，如组成数量，复杂的状态，要让goods关联sale。
        //但是它不允许调用数据库
        //本类的辅助功能-2

        //用于方便查找goods，与list伴生。
        private Map<Integer,Goods> map;

        public void setList(List<Goods> list) {
            this.list = list;
            if (list == null) {
                map = null;  //同释放
            } else {
                map = new HashMap<>();  //同载入
                for (Goods goods : list) {
                    map.put(goods.getId(),goods);
                }
            }
        }

        //判断货品是否缺货，对于其关联的任一商品。
        private static boolean isShortGoods(Goods goods){
            Set<Goods.Node> compose = goods.getComposeSale();
            int allGoodsN = 0;
            for (Goods.Node node : compose) {
                allGoodsN += node.getN();
            }
            return goods.getC() > allGoodsN;
        }

        public void printToTable(){
            isSystemOperating = true;   //调节冲突
            Set<Goods> ignoreSet = new HashSet<>();
            int i = 0; //用于输出计数。
            //如果是初始状态，则没有在侧树上进行任何选择，oneSale就没有东西。
            if (!oneSale.isEmpty()) {
                //额外输出别选中的商品，后面有的就不输出了。
                List<Makeup.Node> makeup = oneSale.getGoodsMakeupInOrder(); //被选中的货品一定在第一个上。
                for (Makeup.Node node : makeup) {
                    String valueAt4 = "";
                    Goods goods = this.map.get(node.getGoods().getId());
                    if (goods != null) {  //在输出的货品表中有
                        ignoreSet.add(goods); //下面要查
                        valueAt4 = TableGoods.isShortGoods(goods)? "缺货中": "充足";
                    }else {
                        valueAt4 = "已遗弃";
                    }
                    goodsTable.setValueAt(node.getGoods().getName(),i,0);
                    goodsTable.setValueAt(node.getGoods().getType(),i,1);
                    goodsTable.setValueAt(node.getGoods().getC(),i,2);
                    goodsTable.setValueAt(node.getN(),i,3); //这一列值下面没有
                    goodsTable.setValueAt(valueAt4,i,4);
                    i++;
                }
            }

            //继续输出商品选中组中，没有的货品。
            while (i < list.size() && i < TABLE_ROWS){
                Goods goods = list.get(i);
                if (!ignoreSet.contains(goods)){
                    String valueAt4 = TableGoods.isShortGoods(goods)? "缺货中": "充足";
                    goodsTable.setValueAt(goods.getName(),i,0);
                    goodsTable.setValueAt(goods.getType(),i,1);
                    goodsTable.setValueAt(goods.getC(),i,2);
                    goodsTable.setValueAt("无",i,3);
                    goodsTable.setValueAt(valueAt4,i,4);
                    i++;
                }
            }
            //输出完后关闭
            isSystemOperating = false;
        }

    }

    /*以上为tree和table组键的辅助类、函数*/


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
        Object[] columnNames = {"货品名称","货品类型","现存数量","组成数量","状态"};
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        goodsTable = new JTable(rowData,columnNames);
        panelBody.add(goodsTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(goodsTable,BorderLayout.CENTER);
        tableGoods = new TableGoods(); //空初始

        //左侧的树组件
        tree = new JTree();
        tree.setShowsRootHandles(true);
        tree.setEditable(false);
        panelSide.add(tree);
        oneSale = new OneSale(); //空初始
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
    }

    public void init(){
        //界面功能调用-1
        List<Goods> list = user.seeGoods();
        List<Sale> sales = user.seeSale();
        this.printToTree(sales);
        //渲染出初始的，未选中任何商品的，货品栏信息。
        tableGoods.setList(list);
        tableGoods.printToTable();
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

                } else if (userObject instanceof SaleObject saleObject) {
                    System.out.println(saleObject.getSale());
                    //刷新下边栏功能块。
                    /*

                    List<Goods> list = user.seeGoods();
                    SupermarketJFrame.this.printToTable(list,oneSale.getSaleMakeup());*/

                } else if (userObject instanceof GoodsObject goodsObject) {
                    System.out.println(goodsObject.getGoods());
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
