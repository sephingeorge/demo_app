package com.project.poc.banking.controller;

import com.project.poc.banking.domain.Account;
import com.project.poc.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

@RequestMapping("api/v1")
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return accountService.findAll();
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        Account newAccount = accountService.save(account);
        try {
            return ResponseEntity
                    .created(new URI("/accounts/"+newAccount.getAccountId()))
                    .eTag(Integer.toString(newAccount.getVersion()))
                    .body(newAccount);
        } catch(URISyntaxException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
