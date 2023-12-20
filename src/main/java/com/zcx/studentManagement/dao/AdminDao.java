package com.zcx.studentManagement.dao;

import com.zcx.studentManagement.entity.Administrator;
import com.zcx.studentManagement.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    public static List<Administrator> getAdministratorByName(String name) {
        List<Administrator> administrators = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            if (connection != null) {
                // 查询管理员对象列表
                String query = "SELECT * FROM administrator WHERE admin_name = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int adminId = resultSet.getInt("admin_id");
                            String adminName = resultSet.getString("admin_name");
                            String adminPassword = resultSet.getString("admin_password");

                            Administrator administrator = new Administrator();
                            administrator.setId(adminId);
                            administrator.setName(adminName);
                            administrator.setPassword(adminPassword);

                            administrators.add(administrator);
                        }
                    }
                }
            } else {
                // 记录或抛出连接失败的消息
                System.out.println("获取数据库连接失败");
            }
        } catch (SQLException e) {
            // 记录或处理异常
            e.printStackTrace();
        }
        return administrators;
    }

    public static boolean insertAdministrator(String name, String password) {
        boolean success = false;

        try (Connection connection = DBUtil.getConnection()) {
            if (connection != null) {
                if (!isUsernameExists(name, connection)) {
                    // 插入管理员
                    String insertQuery = "INSERT INTO administrator (admin_name, admin_password) VALUES (?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        statement.setString(1, name);
                        statement.setString(2, password);

                        int rowsAffected = statement.executeUpdate();
                        success = rowsAffected > 0;
                    }
                } else {
                    // 记录或抛出用户名已存在的消息
                    System.out.println("用户名已存在");
                }
            } else {
                // 记录或抛出连接失败的消息
                System.out.println("获取数据库连接失败");
            }
        } catch (SQLException e) {
            // 记录或处理异常
            e.printStackTrace();
        }

        return success;
    }

    private static boolean isUsernameExists(String name, Connection connection) throws SQLException {
        // 检查用户名是否已存在
        String checkQuery = "SELECT COUNT(*) FROM administrator WHERE admin_name = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setString(1, name);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
    }
}
