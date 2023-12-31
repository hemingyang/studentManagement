package com.zcx.studentManagement.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

// 数据库工具类
public class DBUtil {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        // 读取配置信息
        try {
            Properties properties = new Properties();
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            // 从配置文件中获取数据库连接信息
            driver = properties.getProperty("driver", "");
            url = properties.getProperty("url", "");
            username = properties.getProperty("username", "");
            password = properties.getProperty("password", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    public static Connection getConnection() {
        try {
            // 加载数据库驱动
            Class.forName(driver);
            // 获取数据库连接
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    // 关闭结果集
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭预处理语句
    public static void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // 关闭数据库连接
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    // 关闭结果集、预处理语句和数据库连接
    public static void close(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        close(resultSet);
        close(statement);
        close(connection);
    }
}
