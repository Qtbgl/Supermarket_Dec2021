package com.weidong.window.dialog;

import com.weidong.entity.Customer;
import com.weidong.entity.Sale;

import java.awt.*;
import java.util.List;

public class MyJOptionPane {
    //顾客对话框
    public static CustomerInputDialog.InputResult showCustomerInputDialog(Frame owner, boolean modal, Component parentComponent){
        CustomerInputDialog dialog = new CustomerInputDialog(owner, modal, parentComponent);
        dialog.setVisible(true);
        return dialog.rs;
    }
    public static CustomerInputDialog.InputResult showLoginInputDialog(Frame owner, boolean modal, Component parentComponent){
        LoginInputDialog dialog = new LoginInputDialog(owner, modal, parentComponent);
        dialog.setVisible(true);
        return dialog.rs;
    }
    public static CustomerInputDialog.InputResult showRegisterInputDialog(Frame owner, boolean modal, Component parentComponent){
        RegisterInputDialog dialog = new RegisterInputDialog(owner, modal, parentComponent);
        dialog.setVisible(true);
        return dialog.rs;
    }
    //商品对话框
    public static void showSaleMessageDialog(Frame owner, boolean modal, Component parentComponent, List<Sale> saleGroup){
        SaleMessageDialog dialog = new SaleMessageDialog(owner,modal,parentComponent,saleGroup);
        dialog.init();
        dialog.setVisible(true);
    }
    public static SaleBuyInputDialog.InputResult showSaleBuyInputDialog(Frame owner, boolean modal, Component parentComponent, List<Sale> saleGroup){
        SaleBuyInputDialog dialog = new SaleBuyInputDialog(owner, modal, parentComponent, saleGroup);
        dialog.init();
        dialog.setVisible(true);
        return dialog.rs;
    }
    /*public static CustomerInputDialog.InputResult showModifyInputDialog(Frame owner, boolean modal, Component parentComponent, Customer customer){
        ModifyInputDialog dialog = new ModifyInputDialog(owner, modal, parentComponent, customer);
        dialog.setVisible(true);
        return dialog.rs;
    }*/
}
