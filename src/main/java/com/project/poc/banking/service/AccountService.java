package com.project.poc.banking.service;

import com.project.poc.banking.domain.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();
    Account save(Account account);
}
