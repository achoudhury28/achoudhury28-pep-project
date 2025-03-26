package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
                public Account registerAccount(Account account){

                Connection connection = ConnectionUtil.getConnection();
                try {
                String sql = "INSERT INTO ACCOUNT (USERNAME, PASSWORD) VALUES(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());

                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    return new Account(resultSet.getInt(1),account.getUsername(), account.getPassword());
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
            }


            public boolean doesUsernameExist(String username){

                Connection connection = ConnectionUtil.getConnection();

                try {
                    String sql = "SELECT * FROM ACCOUNT WHERE USERNAME=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                return false;
            }



            public Account userLogin(Account account){

                Connection connection = ConnectionUtil.getConnection();

                try {
                    String sql = "SELECT * FROM ACCOUNT WHERE USERNAME=? AND PASSWORD = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, account.getUsername());
                    preparedStatement.setString(2, account.getPassword());
                   ResultSet resultSet = preparedStatement.executeQuery();
                   if(resultSet.next())
                        return new Account(resultSet.getInt("account_id"), resultSet.getString("username"),resultSet.getString("password"));
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                return null;
            }





    
}
