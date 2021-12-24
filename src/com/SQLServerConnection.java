package com;

import java.sql.*;

public class SQLServerConnection {
    public static void main(String[] args) throws SQLException {
        String sqlDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;DatabaseName=supermarket";
        String user="sa";
        String password="admin";
        try {
            Class.forName(sqlDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(conn);
        if (conn == null) System.exit(24);

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from goods");



        conn.close();
    }
}
