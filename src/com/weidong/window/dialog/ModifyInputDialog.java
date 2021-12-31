package com.weidong.window.dialog;

import com.weidong.entity.Customer;

import java.awt.*;

public class ModifyInputDialog extends RegisterInputDialog{
    public ModifyInputDialog(Frame owner, boolean modal, Component parentComponent, Customer customer) {
        super(owner, modal, parentComponent);
        this.setTitle("更改信息");
        this.nameLabel.setText("称呼");
        this.nameTextField.setText(customer.getName());
        this.pwdLabel.setText("新密码");
        this.commitButton.setText("修改");
        this.cancelButton.setText("取消");
    }
}
