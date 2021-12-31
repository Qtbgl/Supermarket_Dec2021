package com.weidong.datebase.impl.superclass;

import java.sql.*;

public class BaseSQL {
    //静态代码块只执行一次，只在BaseSQL类被加载时。
    final public static String sqlDriver;
    final public static String url;
    final public static String user;
    final public static String password;
    static {
        sqlDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        url="jdbc:sqlserver://localhost:1433;DatabaseName=supermarket";
        user="sa";
        password="admin";
        try {
            Class.forName(sqlDriver);
            //加载数据库驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
        Connection conn = null;
        try {
             conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs){
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //关闭连接的完整方式。
}
