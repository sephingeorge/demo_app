package com.project.poc.banking.controller;

import com.project.poc.banking.domain.Account;
import com.project.poc.banking.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;



@ExtendWith(SpringExtension.class)//Manages application context
@SpringBootTest//populate application context with beans
@AutoConfigureMockMvc//create mock mvc instance and wires into the test path
public class AccountControllerTests {

    @MockBean
    private AccountService accountService;//create mock version of account service

    /*
     * Execute request, validate HTTP Response, HTTP Headers, Response Body
     */
    @Autowired
    MockMvc mockMvc;//autowire a mockmvc instance into the test class

    @BeforeAll
    static void beforeAll(){
        //code to executed before all, called once in test
    }

    @Test
    @DisplayName("GET /accounts - Found")
    void testGetAccountsFound() throws Exception {

        Account mockAccount1 = new Account(1,"Savings",1200);
        Account mockAccount2 = new Account(2, "Current", 1000);
        List<Account> accountMockList = new ArrayList<>(Arrays.asList(mockAccount1,mockAccount2));

        //Setup mocked service
        doReturn(accountMockList).when(accountService).findAll();
        //execute get request
        mockMvc.perform(get("/api/v1/accounts"))
                //validate response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //validate headers

                //validate the returned fields
                .andExpect(jsonPath("$[0].accountId",is(1)))
                .andExpect(jsonPath("$[0].accountType",is("Savings")))
                .andExpect(jsonPath("$[0].balance",is(1200)))
                .andExpect(jsonPath("$[1].accountId",is(2)))
                .andExpect(jsonPath("$[1].accountType",is("Current")))
                .andExpect(jsonPath("$[1].balance",is(1000)));
    }

    @Test
    @DisplayName("GET /accounts - Not Found")
    void testGetAccountsNotFound() throws Exception{

        doReturn(new ArrayList<>()).when(accountService).findAll();

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isNotFound());
    }
}
