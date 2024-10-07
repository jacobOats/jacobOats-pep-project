package Controller;

import Service.*;
import Model.*;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        if(accountReturned == null || !(accountReturned.getUsername().equals(account.getUsername()))
                    || !(accountReturned.getPassword().equals(account.getPassword()))){
            context.status(401);
        }else{
            context.status(200);
            context.json(accountReturned);
        }
    }

    private void postMessageHandler(Context context){

    }

    private void getAllMessagesHandler(Context context){

    }

    private void getMessageByIdHandler(Context context){

    }

    private void deleteMessageByIdHandler(Context context){

    }

    private void updateMessageHandler(Context context){

    }

    private void getAllMessagesByUserHandler(Context context){

    }
}