package com.weidong.window;

import com.weidong.entity.*;
import com.weidong.exception.AlreadyExistedAddException;
import com.weidong.exception.IdNotFoundException;
import com.weidong.exception.ItemCountException;
import com.weidong.exception.ValueUnreasonException;
import com.weidong.role.CustomerRole;
import com.weidong.window.dialog.CustomerInputDialog;
import com.weidong.window.dialog.MyJOptionPane;
import com.weidong.window.dialog.SaleBuyInputDialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class CustomerLogJFrame extends JFrame {
    public static class SaleItem{
        Sale sale;

        public SaleItem() {
        }

        public SaleItem(Sale sale) {
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
    JPanel panelTop;
    JPanel panelTopLeft;
    JPanel panelTopRight;
    JLabel nameLabel;
    JLabel vipLabel;
    JTextField nameTextField;
    JTextField vipTextField;
    JButton modifyNameButton;
    JButton modifyPwdButton;
    JButton disposeButton;

    JPanel panelBody;
    JTable purchaseTable;
    final static int TABLE_COLUMNS = 5;
    final static int TABLE_ROWS = 19;
    JPanel panelBottom;
    JLabel searchLabel;
    JTextField searchTextField;
    JLabel chooseLabel;
    JComboBox<SaleItem> chooseComboBox;
    JButton saleContinueButton;
    //菜单栏
    JMenuBar customerJMenuBar;
    JMenu customerMenu;
    JMenuItem buyMenuItem;
    JMenuItem exitMenuItem;

    //角色：顾客
    CustomerRole user;

    public CustomerLogJFrame() throws HeadlessException {
        //基本属性
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(600,400));
        this.setLocationRelativeTo(null);
        //布局
        contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        panelTop = new JPanel();
        panelBody = new JPanel();
        panelTop.setLayout(new GridLayout(1,3));
        panelBody.setLayout(new BorderLayout());
        panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(panelTop,BorderLayout.NORTH);
        contentPane.add(panelBody,BorderLayout.CENTER);
        contentPane.add(panelBottom,BorderLayout.SOUTH);

        panelTopLeft = new JPanel();
        panelTopRight = new JPanel();
        panelTopLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTopRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTop.add(panelTopLeft);
        panelTop.add(panelTopRight);

        //组件-body上部
        nameLabel = new JLabel("顾客");
        nameTextField = new JTextField(8);
        nameTextField.setEditable(false);
        vipLabel = new JLabel("vip等级");
        vipTextField = new JTextField(8);
        vipTextField.setEditable(false);
        panelTopLeft.add(nameLabel);
        panelTopLeft.add(nameTextField);
        panelTopLeft.add(vipLabel);
        panelTopLeft.add(vipTextField);
        modifyNameButton = new JButton("修改名称");
        modifyPwdButton = new JButton("修改密码");
        disposeButton = new JButton("账号注销");
        panelTopRight.add(modifyNameButton);
        panelTopRight.add(modifyPwdButton);
        panelTopRight.add(disposeButton);
        //组件-body下部
        searchLabel = new JLabel("搜索商品");
        searchTextField = new JTextField(8);
        panelBottom.add(searchLabel);
        panelBottom.add(searchTextField);

        chooseComboBox = new JComboBox<SaleItem>();
        chooseLabel = new JLabel("选择一项");
        saleContinueButton = new JButton("查看商品 / 继续购买");
        panelBottom.add(chooseComboBox);
        panelBottom.add(chooseLabel);
        panelBottom.add(saleContinueButton);

        //组件-body-主表
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        Object[] columnNames = {"商品名称","商品详情","购买人","价格","日期"};
        purchaseTable = new JTable(rowData,columnNames);
        panelBody.add(purchaseTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(purchaseTable,BorderLayout.CENTER);
        
        customerJMenuBar = new JMenuBar();
        this.setJMenuBar(customerJMenuBar);
        customerMenu = new JMenu("您好，顾客名称！");
        customerJMenuBar.add(customerMenu);
        buyMenuItem = new JMenuItem("购买主页");
        exitMenuItem = new JMenuItem("退出");
        customerMenu.add(buyMenuItem);
        customerMenu.add(exitMenuItem);

        //事件响应-1
        buyMenuItem.addActionListener(new JumpToCustomerBuyResponse());
        //事件响应-2
        saleContinueButton.addActionListener(new ContinueRespond());
        //事件响应-3
        searchTextField.getDocument().addDocumentListener(new SearchRespond());
        //事件响应-4
        modifyNameButton.addActionListener(new ModifyNameRespond());
        //事件响应-5
        modifyPwdButton.addActionListener(new ModifyPwdRespond());
        //事件响应-6
        disposeButton.addActionListener(new DisposeRespond());


        //界面功能调用-1
        /*List<Purchase> list = user.getCustomerPurchaseRecords(user.getCustomer().getId());
        //主表数据渲染
        this.printToPurchaseTable(list);
        //下拉框数据渲染
        this.printToChooseComboBox(list);*/
        //界面功能调用-2
        /*String name = user.getCustomer().getName();
        customerMenu.setText("您好，"+name+"!");
        nameTextField.setText(name);
        vipTextField.setText(""+user.getCustomer().getVip());*/
    }

    private void printToPurchaseTable(List<Purchase> list){
        for (int i = 0; i < list.size() && i < TABLE_ROWS; ++i) {
            Purchase purchase = list.get(i);
            Sale sale = purchase.getSale();
            Customer customer = purchase.getCustomer();
            //商品详细信息，如：草莓1份，橘子2份，袋子1份
            StringBuilder saleDetail = new StringBuilder();
            Set<Makeup.Node> makeup = sale.getSaleMakeup().getMakeup();
            for (Makeup.Node node : makeup) {
                Goods goods = node.getGoods();
                int n = node.getN();
                saleDetail.append(goods.getName()).append(n).append("份，");
            }
            saleDetail.deleteCharAt(saleDetail.lastIndexOf("，"));
            purchaseTable.setValueAt(sale.getName(), i, 0);
            purchaseTable.setValueAt(saleDetail, i, 1);
            purchaseTable.setValueAt(customer.getName(), i, 2);
            purchaseTable.setValueAt(sale.getPrice(), i, 3);
            purchaseTable.setValueAt(purchase.getDate(), i, 4);
        }
    }

    private void printToChooseComboBox(List<Purchase> list){
        Map<Integer,Sale> sales = new HashMap<>(); //筛选出不重复的商品组。
        List<Sale> saleList = new ArrayList<>(); //为了在筛选后保持原序。
        for (Purchase purchase : list) {
            Sale s = purchase.getSale();
            if (!sales.containsKey(s.getId())){
                sales.put(s.getId(),s);
                saleList.add(s);
            }
        }
        SaleItem[] dataArr = new SaleItem[saleList.size()];
        for (int i = 0; i < saleList.size(); i++) {
            dataArr[i] = new SaleItem(saleList.get(i));
        }
        chooseComboBox.setModel(new DefaultComboBoxModel<>(dataArr));
    }

    class SearchRespond implements DocumentListener {
        private void updateSaleTable(){
            System.out.println("【"+searchTextField.getText()+"】");
            //界面功能调用-3
            /*String info = searchTextField.getText();
            List<Purchase> list = user.searchPurchaseLikeName(info);
            //主表和下拉框渲染
            CustomerLogJFrame.this.printToPurchaseTable(list);
            CustomerLogJFrame.this.printToChooseComboBox(list);*/
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
    
    public class JumpToCustomerBuyResponse implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerLogJFrame.this.dispose();
            CustomerBuyJFrame customerBuyJFrame = new CustomerBuyJFrame();
            customerBuyJFrame.setVisible(true);
        }
    }

    public class ContinueRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = chooseComboBox.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(saleContinueButton,
                        "暂无上架商品",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else {
                //界面功能调用-4
                try {
                    SaleItem itemAt = chooseComboBox.getItemAt(index); //选中一件商品，上架的。
                    Sale sale = itemAt.getSale();
                    List<Sale> saleGroup = user.getPurchasedSaleModifyRecords(sale.getId());
                    saleGroup.add(sale);//迭代品的id一定比新代小
                    saleGroup.sort(new Comparator<Sale>() {
                        @Override
                        public int compare(Sale o1, Sale o2) { //id大的排前
                            return o2.getId() - o1.getId();
                        }
                    });
                    SaleBuyInputDialog.InputResult rs = MyJOptionPane.showSaleBuyInputDialog(  //查看或继续购买
                            CustomerLogJFrame.this,
                            true,
                            saleContinueButton,
                            saleGroup
                    );
                    System.out.println(rs);
                    if (rs != null){ //继续购买
                        Sale frontSale = rs.frontSale;
                        String purchaseSText = rs.purchaseSText;
                        int purchaseS = Integer.parseInt(purchaseSText);
                        int saleId = frontSale.getId();
                        int customerId = user.getCustomer().getId();
                        user.buy(customerId,saleId,purchaseS);
                    }
                    //仅仅查看了商品，则不做处理，最后都不切换窗口。
                } catch (IdNotFoundException ex) {
                    ex.printStackTrace();
                    //程序逻辑问题，多种……
                } catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(
                            saleContinueButton,
                            "数量不是整数！",
                            "购买失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (ItemCountException itemCountException) {
                    JOptionPane.showMessageDialog(
                            saleContinueButton,
                            "商品数量不足！",
                            "购买失败",
                            JOptionPane.WARNING_MESSAGE
                        );
                } catch (ValueUnreasonException valueUnreasonException) {
                    JOptionPane.showMessageDialog(
                            saleContinueButton,
                            "购买数量异常！",
                            "购买失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                //测试数据
                /*List<Sale> list = new ArrayList<>();
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
                list.add(sale3);*/
            }
        }
    }

    public class ModifyNameRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //界面功能调用-5
            /*String rsName = JOptionPane.showInputDialog(
                    modifyNameButton,
                    "新称呼",
                    user.getCustomer().getName()
            );
            System.out.println(rsName);
            if (rsName != null){
                if (rsName.equals(user.getCustomer().getName())){
                    JOptionPane.showMessageDialog(
                            modifyNameButton,
                            "名称未变化！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }else{
                    try {
                        user.modifyCustomerName(user.getCustomer().getId(),rsName);
                        JOptionPane.showMessageDialog(
                                modifyNameButton,
                                "修改名称成功！",
                                "提示",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (AlreadyExistedAddException ex) {
                        JOptionPane.showMessageDialog(
                                modifyNameButton,
                                "该名称已存在！",
                                "修改失败",
                                JOptionPane.WARNING_MESSAGE
                        );
                    } catch (IdNotFoundException ex) {
                        ex.printStackTrace();
                        //再不找到原用户，就是程序问题。
                    }
                }
                //都返回，不切换窗口。
            }
            //修改信息，点取消，不处理。*/
        }
    }

    public class ModifyPwdRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String rsPwd = JOptionPane.showInputDialog( //不显示加密的密码框
                    modifyPwdButton,
                    "新密码"
            );
            System.out.println(rsPwd);
            //界面功能调用-6
            /*if (rsPwd != null) {
                try {
                    user.modifyCustomerPassword(user.getCustomer().getId(),rsPwd);
                    JOptionPane.showMessageDialog(
                            modifyPwdButton,
                            "修改密码成功！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //返回原来窗口。
                } catch (ValueUnreasonException ex) {
                    JOptionPane.showMessageDialog(
                            modifyPwdButton,
                            "密码不规范！",
                            "修改失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                } catch (IdNotFoundException ex) {
                    ex.printStackTrace();
                    //再不找到原用户，就是程序问题。
                }
                //都返回，不跳转窗口。
            }
            //更新密码，点取消，不处理。*/
        }
    }
    
    public class DisposeRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int rsConfirm = JOptionPane.showConfirmDialog(
                    disposeButton,
                    "您的账号将被注销，无法再登入系统。是否继续？",
                    "提示",
                    JOptionPane.YES_NO_OPTION
            );
            System.out.println(rsConfirm);
            if (rsConfirm == JOptionPane.YES_OPTION){
                //界面功能调用-7
                /*try {
                    user.deregister(user.getCustomer().getId());
                    JOptionPane.showMessageDialog(
                            disposeButton,
                            "您已注销账号，将返回游客首页！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //切换窗口。
                    user.setCustomer(null);
                    //更新内存的顾客记录。
                    CustomerLogJFrame.this.dispose();
                    WelcomeJFrame welcomeJFrame = new WelcomeJFrame();
                    welcomeJFrame.setVisible(true);
                    //切换的游客窗口。
                } catch (IdNotFoundException ex) {
                    ex.printStackTrace();
                    //再不找到，是程序问题。
                }*/
            }
            //是否继续，点否，不处理。
        }
    }

    public static void main(String[] args) {
        CustomerLogJFrame customerLogJFrame = new CustomerLogJFrame();
        customerLogJFrame.setVisible(true);
    }
}
