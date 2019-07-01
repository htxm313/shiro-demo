package com.yootk.shiro.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    public static final String DBDRIVER = "org.gjt.mm.mysql.Driver" ;
    public static final String DBURL = "jdbc:mysql://localhost:3306/yootk?useUnicode=true&characterEncoding=UTF8" ;
    public static final String USER = "root" ;
    public static final String PASSWORD = "mysqladmin" ;
    public static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>() ;
    private DatabaseConnection(){}
    private static Connection rebuildConnection() {
        Connection conn = null ;
        try {
            Class.forName(DBDRIVER) ;
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn ;
    }
    public static Connection getConnection() {
        Connection conn = THREAD_LOCAL.get() ;
        if (conn == null) {
            conn = rebuildConnection() ;
            THREAD_LOCAL.set(conn);
        }
        return conn ;
    }
    public static void close() {
        Connection conn = THREAD_LOCAL.get() ;
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            THREAD_LOCAL.remove();
        }
    }
}
