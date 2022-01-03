package com.weidong.window;

import com.weidong.entity.Goods;
import com.weidong.entity.Makeup;
import com.weidong.entity.Sale;
import com.weidong.role.VisitorRole;
import com.weidong.window.dialog.CustomerInputDialog;
import com.weidong.window.dialog.MyJOptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class WelcomeJFrame extends JFrame {
    Container contentPane;
    //上面板
    JPanel panelTop;
    JPanel panelTopLeft;
    JPanel panelTopRight;
    JLabel searchLabel;
    JTextField searchTextField;
    JButton loginButton;
    JButton registerButton;
    //主面板
    JPanel panelBody;
    JTable saleTable;
    final static int TABLE_COLUMNS = 3;
    final static int TABLE_ROWS = 17;
    //主容器上的JTable行列固定

    //角色：游客
    VisitorRole user;

    public WelcomeJFrame() throws HeadlessException {
        //基本属性
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(600,400));
        this.setLocationRelativeTo(null);
        //布局方式-1
        this.setLayout(new BorderLayout(10,10));
        contentPane = this.getContentPane();
        panelTop = new JPanel();
        panelBody = new JPanel();
        contentPane.add(panelTop,BorderLayout.NORTH);
        contentPane.add(panelBody,BorderLayout.CENTER);
        //布局方式-2
        panelTop.setLayout(new GridLayout(1,2));
        panelTopLeft = new JPanel();
        panelTopLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(panelTopLeft,BorderLayout.WEST);
        panelTopRight = new JPanel();
        panelTopRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelTop.add(panelTopRight,BorderLayout.EAST);
        //布局方式-3
        panelBody.setLayout(new BorderLayout(10,10));

        //加入组件-1
        searchLabel = new JLabel();
        searchLabel.setText("搜索商品");
        panelTopLeft.add(searchLabel);
        searchTextField = new JTextField(8);
        panelTopLeft.add(searchTextField);
        //加入组件-2
        loginButton = new JButton();
        loginButton.setText("登录");
        panelTopRight.add(loginButton);
        registerButton = new JButton();
        registerButton.setText("注册");
        panelTopRight.add(registerButton);
        //加入组件-3
        Object[][] rowData = new Object[TABLE_ROWS][TABLE_COLUMNS];
        Object[] columnNames = {"商品名称","价格","详细信息"};
        saleTable = new JTable(rowData,columnNames);
        panelBody.add(saleTable.getTableHeader(),BorderLayout.NORTH);
        panelBody.add(saleTable,BorderLayout.CENTER);

        //事件监听-1
        searchTextField.getDocument().addDocumentListener(new SearchRespond());
        //事件监听-2
        loginButton.addActionListener(new LoginRespond());
        //事件监听-3
        registerButton.addActionListener(new registerRespond());

    }

    private void init(){
        //界面功能调用-1
        /*List<Sale> list = user.seeSale();
        this.printToSaleTable(list);*/
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

    public static void main(String[] args) {
        WelcomeJFrame welcomeJFrame = new WelcomeJFrame();
        welcomeJFrame.setVisible(true);
    }

    class SearchRespond implements DocumentListener {
        private void updateSaleTable(){
            System.out.println("【"+searchTextField.getText()+"】");
            //界面功能调用-2
            String info = searchTextField.getText();
            List<Sale> list = user.searchSaleLikeName(info);
            WelcomeJFrame.this.printToSaleTable(list);
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

    class LoginRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerInputDialog.InputResult rs = MyJOptionPane.showLoginInputDialog(
                    WelcomeJFrame.this,
                    true,
                    loginButton
            );
            System.out.println(rs);
            //界面功能调用-3
            /*try {
                Customer login = user.login(rs.inputName, Arrays.toString(rs.inputPwd));
                JOptionPane.showMessageDialog(
                        loginButton,
                        "登陆成功！",
                        "提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
                //切换窗口
                WelcomeJFrame.this.dispose();
                CustomerBuyJFrame customerBuyJFrame = new CustomerBuyJFrame();
                new CustomerRole(...,login);
                //需要依赖注入
                customerBuyJFrame.setVisible(true);
            } catch (IdNotFoundException idNotFoundException) {
                JOptionPane.showMessageDialog(
                        loginButton,
                        "登入失败，账号不存在或者已注销！",
                        "警告",
                        JOptionPane.WARNING_MESSAGE
                );
                //返回主窗口
            } catch (PassFailedException passFailedException) {
                JOptionPane.showMessageDialog(
                        loginButton,
                        "登入失败，密码不正确！",
                        "警告",
                        JOptionPane.WARNING_MESSAGE
                );
                //返回主窗口
            }*/
        }
    }

    class registerRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerInputDialog.InputResult rs = MyJOptionPane.showRegisterInputDialog(
                    WelcomeJFrame.this,
                    true,
                    registerButton
            );
            System.out.println(rs);
            //界面功能调用-4
            /*if (rs != null){
                try {
                    user.register(rs.inputName, Arrays.toString(rs.inputPwd));
                    JOptionPane.showMessageDialog(
                            registerButton,
                            "注册成功！",
                            "提示",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    //返回，不切换窗口
                } catch (AlreadyExistedAddException ex) {
                    JOptionPane.showMessageDialog(
                            registerButton,
                            "该名称已存在！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    //返回，不切换窗口
                } catch (ValueUnreasonException ex) {
                    JOptionPane.showMessageDialog(
                            registerButton,
                            "密码不规范！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE
                    );
                    //返回，不切换窗口
                }
            }
            //注册，点取消，不处理。*/

        }

    }

    //游客见到的商品集合
    public static class SaleMessage{
        //商品名称
        //详细组成信息
        //商品价格
        //**商品销售数量**。顾客。
        //商品状态：售完和未售完
    }
}
