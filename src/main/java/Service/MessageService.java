package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO= new MessageDAO();
    }

    public Message createMessage(Message message){

        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() <255 && messageDAO.registeredAccount(message.getPosted_by())){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
 
    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int messageId){
        return messageDAO.deleteMessageByMessageId(messageId);
    }

    public Message updateMessageByMessageId(int messageId, String text){
        if(text !=null && !text.isBlank() && text.length() < 255)
            return messageDAO.updateMessageByMessageId(messageId, text);

        return null;
    }

    public List<Message> retrieveAllMessagesByUser(int accountId){
        return messageDAO.retrieveAllMessagesByUserId(accountId);
    }

}
