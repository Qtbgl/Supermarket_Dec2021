package com.weidong.window.dialog;

import java.awt.*;

public class LoginInputDialog extends CustomerInputDialog{
    public LoginInputDialog(Frame owner, boolean modal, Component parentComponent) {
        super(owner, modal, parentComponent);
        this.setTitle("顾客登录");
        this.nameLabel.setText("账号");
        this.pwdLabel.setText("密码");
        this.commitButton.setText("登录");
        this.cancelButton.setText("取消");
    }
}
