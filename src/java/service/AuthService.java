package com.smarthotel.service;

import com.smarthotel.dao.AccountDAO;
import com.smarthotel.model.Account;

public class AuthService {
    private final AccountDAO accountDAO = new AccountDAO();

    public Account login(String username, String password) {
        return accountDAO.findByUsernameAndPassword(username, password);
    }

    public void registerAccount(Account account, String rawPassword) {
        accountDAO.create(account, rawPassword);
    }
}