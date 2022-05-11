package com.emma.bubblenote;

import java.sql.*;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName="DB01";
        String databaseUser="root";
        String databasePassword="root";
        String url="jdbc:mysql://localhost:8889/DB01?serverTimezone=GMT%2B8&characterEncoding=utf8";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }

    //增删改
    public static void close(Connection conn, PreparedStatement ps) {

        close(conn, ps, null);
    }

    //查
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        //如果不进行非空校验，很容易出现空指针异常
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
