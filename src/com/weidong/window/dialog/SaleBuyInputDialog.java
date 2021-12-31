package com.weidong.window.dialog;

import com.weidong.entity.Sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SaleBuyInputDialog extends SaleMessageDialog{
    public static class InputResult{
        public Sale frontSale; //新代，从saleGroup中。
        public String purchaseSText;

        @Override
        public String toString() {
            return "InputResult{" +
                    "frontSale=" + frontSale +
                    ", purchaseSText='" + purchaseSText + '\'' +
                    '}';
        }
    }

    JPanel panelBottom;
    JLabel buyPurchaseSLabel;
    JTextField buyPurchaseSTextField;
    JButton buyButton;
    JButton notBuyButton;
    InputResult rs;

    //saleGroup: 新代商品排前
    public SaleBuyInputDialog(Frame owner, boolean modal, Component parentComponent, List<Sale> saleGroup) {
        //父类构造
        super(owner, modal, parentComponent, saleGroup);
        this.setBounds(new Rectangle(400,250));
        this.setLocationRelativeTo(parentComponent);
        //窗口更换Bounds后，重新设置location
        this.setTitle("购买最新商品");
        //增加布局
        panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1,3));
        contentPane.add(panelBottom,BorderLayout.SOUTH);
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelBottom.add(p1);
        panelBottom.add(p2);
        panelBottom.add(p3);

        //增加组件
        buyButton = new JButton("继续购买");
        buyPurchaseSLabel = new JLabel("数量");
        buyPurchaseSTextField = new JTextField(8);
        p1.add(buyButton);
        p2.add(buyPurchaseSLabel);
        p2.add(buyPurchaseSTextField);
        notBuyButton = new JButton("返回");
        p3.add(notBuyButton);

        //事件响应-1
        buyButton.addActionListener(new BuyRespond());
        //事件响应-2
        notBuyButton.addActionListener(new NotBuyRespond());
    }

    private class BuyRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            SaleBuyInputDialog.this.rs = new InputResult();
            if (saleGroup.size()>=1){
                rs.frontSale = saleGroup.get(0);
                rs.purchaseSText = buyPurchaseSTextField.getText();
            }
            //结束对话框
            SaleBuyInputDialog.this.dispose();
        }
    }

    private class NotBuyRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            SaleBuyInputDialog.this.rs = null;
            //结束对话框
            SaleBuyInputDialog.this.dispose();
        }
    }
}
