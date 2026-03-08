package service;

import dao.AccountDAO;
import model.Account;

public class AuthService {
    private final AccountDAO accountDAO = new AccountDAO();

    public Account login(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }

    public void registerAccount(Account account, String rawPassword) {
        accountDAO.create(account, rawPassword);
    }
}