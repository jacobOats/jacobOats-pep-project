package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class MessageDAO {
    
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"), 
                                              rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int messageId = (int) rs.getLong(1);
                return new Message(messageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Message message = new Message(rs.getInt("message_id"), 
                                          rs.getInt("posted_by"), 
                                          rs.getString("message_text"), 
                                          rs.getLong("time_posted_epoch"));
            return message;
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id){
        return null;
    }

    public Message updateMessage(int id){
        return null;
    }
    
    public List<Message> getAllMessageByAccountId(int accountId){
        return null;
    }
}
