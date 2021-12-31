package com.weidong.window.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CustomerInputDialog extends JDialog {

    public static class InputResult {
        public String inputName;
        public char[] inputPwd;

        @Override
        public String toString() {
            return "InputResult{" +
                    "inputName='" + inputName + '\'' +
                    ", inputPwd=" + Arrays.toString(inputPwd) +
                    '}';
        }
    }
    //属性
    protected Container contentPane;
    protected JPanel p1;
    protected JPanel p2;
    protected JPanel p3;
    protected JLabel nameLabel;
    protected JLabel pwdLabel;
    protected JTextField nameTextField;
    protected JPasswordField pwdPasswordField;
    protected JButton commitButton;
    protected JButton cancelButton;
    protected InputResult rs;
    //构造方法
    public CustomerInputDialog(Frame owner, boolean modal, Component parentComponent) {
        super(owner,modal);
        //基本设置
        this.setBounds(new Rectangle(300,200));
        this.setLocationRelativeTo(parentComponent);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //布局设置
        contentPane = this.getContentPane();
        contentPane.setLayout(new GridLayout(4,1));
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p1.setLayout(new FlowLayout());
        p2.setLayout(new FlowLayout());
        p3.setLayout(new FlowLayout());
        contentPane.add(p1);
        contentPane.add(p2);
        contentPane.add(p3);
        //组件设置
        nameLabel = new JLabel();
        nameLabel.setText("称呼");
        pwdLabel = new JLabel();
        pwdLabel.setText("密码");
        nameTextField = new JTextField(8);
        pwdPasswordField = new JPasswordField(8);
        p1.add(nameLabel);
        p1.add(nameTextField);
        p2.add(pwdLabel);
        p2.add(pwdPasswordField);
        commitButton = new JButton("提交");
        cancelButton = new JButton("取消");
        p3.add(commitButton);
        p3.add(cancelButton);
        //事件响应
        commitButton.addActionListener(new CommitRespond());
        cancelButton.addActionListener(new CancelRespond());
        //暂不设置：对话框可视
    }

    private class CommitRespond implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerInputDialog.this.rs = new InputResult();
            CustomerInputDialog.this.rs.inputName = CustomerInputDialog.this.nameTextField.getText();
            CustomerInputDialog.this.rs.inputPwd = CustomerInputDialog.this.pwdPasswordField.getPassword();
            //结束对话框
            CustomerInputDialog.this.dispose();
        }
    }

    private class CancelRespond implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerInputDialog.this.rs = null;
            //结束对话框
            CustomerInputDialog.this.dispose();
        }
    }


}
