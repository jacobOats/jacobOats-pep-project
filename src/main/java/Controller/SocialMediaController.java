package Controller;

import Service.*;
import Model.*;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        //check user and pass
        if(account.getUsername().length() < 1 || account.getPassword().length() < 4){
            //cannot add user, return status 400
            context.status(400);
        }else{
            //username and password verified
            Account accountInserted = accountService.insertAccount(account);
            if(accountInserted == null){ //user was found with same username
                context.status(400);
            }else{ //account was inserted.
                context.status(200);
                context.json(accountInserted);
            }
        }
    }

    private void loginAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account accountReturned = accountService.login(account);
        //check if account was not found, or did not match credentials
        if(accountReturned == null || !(accountReturned.getUsername().equals(account.getUsername()))
                    || !(accountReturned.getPassword().equals(account.getPassword()))){
            context.status(401);
        }else{ //user credentials match
            context.status(200);
            context.json(accountReturned);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        //check if message is empty or greater than 255
        if(message.getMessage_text().length() < 1 || message.getMessage_text().length() > 255){
            context.status(400);
        }else{
            //check account is a real account in database
            Account account = accountService.getAccountById(message.getPosted_by());
            if(account == null || account.getAccount_id() != message.getPosted_by()){
                context.status(400);
            }else{
                Message insertedMessage = messageService.createMessage(message);
                if(insertedMessage == null){//check if insertion occured
                    context.status(400);
                }else{ //message inserted successfully
                    context.status(200);
                    context.json(insertedMessage);
                }
            }
        }
    }

    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if(message != null){ //message was found
            context.status(200);
            context.json(message);
        }else{ //message was not found
            context.json("");
        }
    }

    private void deleteMessageByIdHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if(message != null){ //message was found
            context.status(200);
            context.json(message);
        }else{ //message was not found
            context.json("");
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));
        //check if message is empty or greater than 255
        if(message.getMessage_text().length() < 1 || message.getMessage_text().length() > 255){
            context.status(400);
        }else{
            Message updatedMessage = messageService.updateMessage(id, message.getMessage_text());
            if(updatedMessage != null){ //check message was updated
                context.status(200);
                context.json(updatedMessage);
            }else{
                context.status(400);
            }
        }
    }

    private void getAllMessagesByUserHandler(Context context){
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessageByAccountId(accountId);
        context.json(messages);
    }
}