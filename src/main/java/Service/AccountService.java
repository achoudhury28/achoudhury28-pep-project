package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO= new AccountDAO();
    }

    public Account addAccount(Account account){

        if(accountDAO.doesUsernameExist(account.getUsername())|| account.getUsername()=="" || account.getPassword().length()<4)
        return null;

        return accountDAO.registerAccount(account);

    }
    
    public Account userLogin(Account account){
        return accountDAO.userLogin(account);
    }
    
}
