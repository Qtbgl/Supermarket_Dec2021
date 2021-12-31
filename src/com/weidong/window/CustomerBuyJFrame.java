package com.weidong.window;

import com.weidong.entity.Customer;
import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Sale;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.exception.ValueUnreasonException;
import com.weidong.role.CustomerRole;
import com.weidong.role.VisitorRole;
import com.weidong.window.dialog.MyJOptionPane;
import com.weidong.window.dialog.SaleMessageDialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CustomerBuyJFrame extends JFrame {
    Container contentPane;
    JLabel searchLabel;
    JTextField searchTextField;
    JMenuBar customerJMenuBar;
    JMenu customerMenu;
    JMenuItem exitMenuItem;
    JMenuItem customerMenuItem;
    JPanel panelBody;
    JPanel panelSide;
    JPanel panelSide_1;
    JPanel panelSide_2;
    JPanel panelSide_3;
    JTable saleTable;
    final static int TABLE_COLUMNS = 3;
    final static int TABLE_ROWS = 19;
    //主容器上的JTable行列固定
    JLabel buyLabel;
    JComboBox<String> buyComboBox;
    Map<String,Sale> buyComboBoxMap;
    JLabel buyPurchaseSLabel;
    JTextField buyPurchaseSTextField;
    JButton seeButton;
    JButton buyButton;

    //角色：游客。
    //需要依赖注入。
    CustomerRole user;

    public CustomerBuyJFrame() throws HeadlessException {
        //基本属性
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(600,400));
        this.setLocationRelativeTo(null);
        //布局
        this.setLayout(new BorderLayout(10,10));
        contentPane = this.getContentPane();
        panelBody = new JPanel();
        panelSide = new JPanel();
        panelSide_1 = new JPanel();
        panelSide_2 = new JPanel();
        panelSide_3 = new JPanel();
        contentPane.add(panelBody,BorderLayout.CENTER);
        contentPane.add(panelSide,BorderLayout.WEST);
        panelBody.setLayout(new BorderLayout(10,10));
        panelSide.setLayout(new GridLayout(3,1));

        panelSide_1.setLayout(new GridLayout(1,1));
        panelSide.add(panelSide_1);
        panelSide.add(panelSide_2);
        panelSide_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSide.add(panelSide_3);
        //组件
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        Object[] columnNames = {"商品名称","价格","详细信息"};
        saleTable = new JTable(rowData,columnNames);
        panelBody.add(saleTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(saleTable,BorderLayout.CENTER);

        //组件-侧边-额外
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel gridPanel = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        gridPanel.setLayout(new GridLayout(3,1));
        panelSide_1.add(gridPanel);
        gridPanel.add(p1);
        gridPanel.add(p2);
        gridPanel.add(p3);

        buyLabel = new JLabel();
        buyLabel.setText("商品");
        p1.add(buyLabel);
        buyComboBox = new JComboBox<>();  //下拉选择框
        buyComboBoxMap = new HashMap<>(); //Map和ComBox伴生，数据同更新。
        p1.add(buyComboBox);

        buyPurchaseSLabel = new JLabel("数量");
        buyPurchaseSTextField = new JTextField(8);
        buyPurchaseSTextField.setHorizontalAlignment(JTextField.RIGHT);
        p2.add(buyPurchaseSLabel);
        p2.add(buyPurchaseSTextField);
        seeButton = new JButton("查看");
        buyButton = new JButton("购买");
        p3.add(seeButton);
        p3.add(buyButton);


        searchLabel = new JLabel("搜索商品");
        searchTextField = new JTextField(8);
        panelSide_3.add(searchLabel);
        panelSide_3.add(searchTextField);

        //菜单组件
        customerJMenuBar = new JMenuBar();
        this.setJMenuBar(customerJMenuBar);
        customerMenu = new JMenu("您好，顾客名称！");
        customerJMenuBar.add(customerMenu);
        customerMenuItem = new JMenuItem("个人主页");
        exitMenuItem = new JMenuItem("退出");
        customerMenu.add(customerMenuItem);
        customerMenu.add(exitMenuItem);


        //事件响应-1
        buyButton.addActionListener(new BuyRespond());
        //事件响应-2
        searchTextField.getDocument().addDocumentListener(new SearchRespond());
        //事件响应-3
        customerMenuItem.addActionListener(new JumpToCustomerLogRespond());
        //事件响应-4
        exitMenuItem.addActionListener(new JumpToWelcomeRespond());
        //事件响应-5
        seeButton.addActionListener(new SeeRespond());
        //界面功能调用-1
        /*List<Sale> list = user.seeSale();
        this.printToSaleTable(list);
        //渲染到下拉框
        String[] dataArr = new String[list.size()];
        buyComboBoxMap.clear(); //重新加入数据
        for (int i = 0; i < list.size(); i++) {
            String str = (char)('A'+i)+"."+list.get(i).getName();
            dataArr[i] = str;
            buyComboBoxMap.put(str,list.get(i));
        }
        buyComboBox.setModel(new DefaultComboBoxModel<>(dataArr));*/
        //界面功能调用-4
        /*String name = user.getCustomer().getName();
        customerMenu.setText("您好，"+name+"!");*/
    }

    //本类的辅助功能
    private void printToSaleTable(List<Sale> list){
        for (int i = 0; i < list.size() && i < TABLE_ROWS; ++i) {
            Sale sale = list.get(i);
            //商品详细信息，如：草莓1份，橘子2份，袋子1份
            StringBuilder saleDetail = new StringBuilder();
            Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
            for (Makeup.Node node : makeup) {
                Goods goods = node.getGoods();
                int n = node.getN();
                saleDetail.append(goods.getName()).append(n).append("份，");
            }
            saleDetail.deleteCharAt(saleDetail.lastIndexOf("，"));
            saleTable.setValueAt(sale.getName(), i, 0);
            saleTable.setValueAt(sale.getPrice(), i, 1);
            saleTable.setValueAt(saleDetail, i, 2);
        }
    }

    public class BuyRespond implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = buyComboBox.getSelectedIndex();
            String key = buyComboBox.getItemAt(index);
            Sale sale = buyComboBoxMap.get(key);
            String textS = buyPurchaseSTextField.getText();
            System.out.println(key);
            System.out.println(sale);
            System.out.println(textS);
            //界面功能调用-2
            /*if (sale == null){
                JOptionPane.showMessageDialog(buyButton,
                        "暂无上架商品",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else try {
                int S = Integer.parseInt(textS);  //购买数量
                int customerId = user.getCustomer.getId();
                user.buy(customerId,sale.getId(),S);
                JOptionPane.showMessageDialog(buyButton,
                        "购买成功",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
                //暂不切换窗口
            }catch (NumberFormatException numberFormatException){
                JOptionPane.showMessageDialog(
                        buyButton,
                        "数量不是整数！",
                        "购买失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (ItemCountException itemCountException) {
                JOptionPane.showMessageDialog(
                        buyButton,
                        "商品数量不足！",
                        "购买失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (ValueUnreasonException valueUnreasonException) {
                JOptionPane.showMessageDialog(
                        buyButton,
                        "购买数量异常！",
                        "购买失败",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (IdNotFoundException idNotFoundException) {
                idNotFoundException.printStackTrace();
                System.out.println(idNotFoundException.getSign());
                System.out.println("程序问题。");
            }*/

        }
    }

    public class SeeRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Sale> list = new ArrayList<>();
            Sale sale0 = new Sale();
            Sale sale1 = new Sale();
            Sale sale2 = new Sale();
            Sale sale3 = new Sale();
            sale0.setName("牛奶0");
            sale1.setName("牛奶1");
            sale2.setName("牛奶2");
            sale3.setName("牛奶3");
            list.add(sale0);
            list.add(sale1);
            list.add(sale2);
            list.add(sale3);
            MyJOptionPane.showSaleMessageDialog(
                    CustomerBuyJFrame.this,
                    true,
                    seeButton,
                    list
            );
        }
    }

    public class JumpToCustomerLogRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("切换至顾客的个人主页");
            CustomerBuyJFrame.this.dispose();
            CustomerLogJFrame customerLogJFrame = new CustomerLogJFrame();
            customerLogJFrame.setVisible(true);
        }
    }

    public class JumpToWelcomeRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("切换至首页游客窗口");
            CustomerBuyJFrame.this.dispose();
            WelcomeJFrame welcomeJFrame = new WelcomeJFrame();
            welcomeJFrame.setVisible(true);
        }
    }

    class SearchRespond implements DocumentListener {
        private void updateSaleTable(){
            System.out.println("【"+searchTextField.getText()+"】");
            //界面功能调用-3
            /*String info = searchTextField.getText();
            List<Sale> list = user.searchSaleLikeName(info);
            printToSaleTable(list);*/
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            this.updateSaleTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            this.updateSaleTable();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            this.updateSaleTable();
        }
    }

    public static void main(String[] args) {
        CustomerBuyJFrame customerBuyJFrame = new CustomerBuyJFrame();
        customerBuyJFrame.setVisible(true);
    }

    
    /*Object[] columnNames = {"商品名称", "价格", "详细信息"};
        Object[][] rowData = {
                {"尖叫", 8, 240},
                {"脉动", 7, 240},
                {"美汁源果汁", 7, 210},
                {"雀巢咖啡", 8, 240},
                {"云白山凉茶", 6, 210},
                {"八宝粥", 8, 240},
                {"咖啡调糖", 8, 240},
                {"米饼", 8, 240},
                {"贝克啤酒", 8, 210},
                {"百威啤酒", 8, 210},
                {"蜂蜜", 8, 240},
                {"爆米花", 8, 240},
                {"娃哈哈果汁", 6, 210},
                {"果珍", 8, 240},
                {"链子糊", 8, 240},
                {"脉动一箱", 70, 240},
                {"尖叫一瓶", 8, 240},
                {"长城葡萄酒", 9, 240},
                {"核桃粉", 8, 240}
        };*/
//    String[] listData = {"尖叫","脉动","美汁","雀巢","云白","八宝","咖啡"};
}
