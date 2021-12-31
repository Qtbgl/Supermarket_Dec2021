package com.weidong.window.dialog;

import com.weidong.entity.Customer;

import javax.swing.*;
import java.awt.*;

public class RegisterInputDialog extends CustomerInputDialog{
    JLabel tipLabel;
    JPanel p4;
    public RegisterInputDialog(Frame owner, boolean modal, Component parentComponent) {
        super(owner, modal, parentComponent);
        this.setTitle("注册账号");
        //增加布局
        p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        contentPane.add(p4);
        //增加组件
        tipLabel = new JLabel();
        tipLabel.setText("提示: 密码可以为"+Customer.pwdLength +"位任意的字符。");
        p4.add(tipLabel);
        //更改信息
        this.nameLabel.setText("称呼");
        this.pwdLabel.setText("密码");
        this.commitButton.setText("注册");
        this.cancelButton.setText("取消");
    }
}
