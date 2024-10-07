package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    public AccountDAO accountDAO;

    //no args constructor
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    //used for mocking during testing
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account insertAccount(Account account){
        //check if account with username already exists, if it does, return null
        Account checkUser = accountDAO.getAccountByUsername(account.getUsername());
        if(checkUser != null){ //user was found with same username
            return null;
        }else{ //user was not found with same username
            return accountDAO.insertAccount(account);
        }
    }

    public Account login(Account account){
        return null;
    }
}
