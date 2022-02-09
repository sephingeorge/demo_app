package com.project.poc.banking.service;

import com.project.poc.banking.domain.Account;
import com.project.poc.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account save(Account account){
        return accountRepository.save(account);
    }
}
