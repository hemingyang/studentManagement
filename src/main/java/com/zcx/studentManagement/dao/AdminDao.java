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

    public static List<Administrator> getAdministratorByName(String name){  //通过名称获取管理员对象列表
        List<Administrator> administrators = new ArrayList<Administrator>();
        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("select * from administrator where admin_name = ?");
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int admin_id = resultSet.getInt("admin_id");
                    String admin_name = resultSet.getString("admin_name");
                    String admin_password = resultSet.getString("admin_password");

                    Administrator administrator = new Administrator();
                    administrator.setId(admin_id);
                    administrator.setName(admin_name);
                    administrator.setPassword(admin_password);

                    administrators.add(administrator);
                }

                DBUtil.close(resultSet, statement, connection);
            } else {
                System.out.println("Failed to obtain a database connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrators;
    }

    public static boolean insertAdministrator(String name, String password) {
        boolean success = false;

        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {

                if (!isUsernameExists(name, connection)) {

                    String insertQuery = "INSERT INTO administrator (admin_name, admin_password) VALUES (?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        statement.setString(1, name);
                        statement.setString(2, password);

                        int rowsAffected = statement.executeUpdate();
                        success = rowsAffected > 0;
                    }
                } else {
                    System.out.println("Username already exists");
                }

                DBUtil.close(connection);
            } else {
                System.out.println("Failed to obtain a database connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    //
    private static boolean isUsernameExists(String name, Connection connection) throws SQLException {
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
