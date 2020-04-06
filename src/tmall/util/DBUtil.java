package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
数据库工具类，这个类的作用是初始化驱动，并且提供一个getConnection用于获取连接。
 */
public class DBUtil {
    private static String ip  = "127.0.0.1";
    private static int port = 3306;
    private static String dataBase = "tmall";
    private static String userName = "root";
    private static String password = "lzpsyx";
    private static String encoding = "utf-8";

    private static String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",ip,port,dataBase,encoding);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,userName,password);
    }

    public static void main(String[] args) {
        try {
            System.out.println(url);
            DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
