package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    
    public MessageDAO messageDAO;

    //no args constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //used for mocking during testing
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message createMessage(Message message){
        Message insertedMessage = messageDAO.createMessage(message);
        if(insertedMessage != null){
            return insertedMessage;
        }
        return null;
    }

    public Message getMessageById(int id){
        Message message = messageDAO.getMessageById(id);
        if(message != null)
            return message;
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
