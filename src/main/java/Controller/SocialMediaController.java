package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    ObjectMapper mapper;
    

    public SocialMediaController(){
        this.accountService=new AccountService();
        this.messageService=new MessageService();
       this.mapper = new ObjectMapper();
       

    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::addAccountHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::createMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesByUserHandler);


        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void addAccountHandler(Context ctx) throws JsonProcessingException{

       
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        if(addedAccount !=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }


    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException{

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.userLogin(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(401);
        }

    }

    private void createMessagesHandler(Context ctx) throws JsonProcessingException{

       Message message = mapper.readValue(ctx.body(), Message.class);
       Message createdMessage = messageService.createMessage(message);
       if(createdMessage!=null){
        ctx.json(mapper.writeValueAsString(createdMessage));
       }else{
        ctx.status(400);
       }
    }

    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {


        int msgId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(msgId);
        if(message !=null){
            ctx.json(mapper.writeValueAsString(message));
        }

        
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        ctx.status(200);
        if(deletedMessage !=null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
    }

    private void updateMessageByMessageIdHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        JsonNode jsonNode = mapper.readTree(ctx.body());
        String updatedMessage = jsonNode.get("message_text").asText();
        Message message = messageService.updateMessageByMessageId(messageId, updatedMessage);
        if(message !=null){
            ctx.json(mapper.writeValueAsString(message));
        }else
            ctx.status(400);

    }

    private void retrieveAllMessagesByUserHandler(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));

        ctx.json(messageService.retrieveAllMessagesByUser(accountId));
    }


}