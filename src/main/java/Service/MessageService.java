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
        return null;
    }

    public Message createMessage(Message message){
        return null;
    }

    public Message getMessageById(int id){
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
