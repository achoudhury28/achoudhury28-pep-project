package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
       public Message createMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO MESSAGE (MESSAGE_TEXT, TIME_POSTED_EPOCH) VALUES(?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setLong(2, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                return new Message(resultSet.getInt(1),message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public boolean registeredAccount(int id){

        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet resultSet= preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public List<Message> getAllMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM MESSAGE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                messages.add(new Message(resultSet.getInt("message_id"), 
                resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch")));

                return messages;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM MESSAGE WHERE MESSAGE_ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
               Message message= new Message(resultSet.getInt("message_id"), 
                resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Message deleteMessageByMessageId(int messageId){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM MESSAGE WHERE MESSAGE_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Message message = null;

            if(resultSet.next()){
                message= new Message(resultSet.getInt("message_id"), 
                resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
                return message;

            }

            String sql2 = "DELETE FROM MESSAGE WHERE MESSAGE_ID = ?";
            PreparedStatement preparedStatement2= connection.prepareStatement(sql2);
            preparedStatement.setInt(1, messageId);
            int affectedRows = preparedStatement2.executeUpdate();
            if(affectedRows>0){
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Message updateMessageByMessageId(int messageId, String text){

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE MESSAGE SET MESSAGE_TEXT=? WHERE MESSAGE_ID=?";

            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, messageId);
            preparedStatement.executeUpdate();
            return getMessageById(messageId);
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> retrieveAllMessagesByUserId(int accountId){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT *FROM MESSAGE WHERE POSTED_BY=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

           ResultSet resultSet = preparedStatement.executeQuery();
           while(resultSet.next()){
            messages.add(new Message(resultSet.getInt("message_id"), 
                resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch")));

                return messages;

           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    
}
