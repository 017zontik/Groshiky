package com.zontik.groshiky.controllers;

import com.zontik.groshiky.model.Account;
import com.zontik.groshiky.model.AccountModel;
import com.zontik.groshiky.model.Transaction;
import com.zontik.groshiky.model.TransactionModel;
import com.zontik.groshiky.service.IAccountService;
import com.zontik.groshiky.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController("/dashboard")
public class AccountController extends BaseController {

    private final IAccountService accountService;
    private final IUserService userService;

    @Autowired
    public AccountController(IAccountService accountService, IUserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping(value = "/addAccount")
    public ResponseEntity addAccount(Account account) {
        account.setUser(userService.findUserById(getUserId()));
        if(accountService.getAccountByName(account.getName())!=null){
           String message =String.format("The account \"%s\" already exists", account.getName());
           return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
        accountService.addAccount(account);
        AccountModel newAccount = new AccountModel(account);
        return new ResponseEntity(newAccount, HttpStatus.OK);
    }

    @GetMapping(value = "/transactions")
    public ResponseEntity getTransactions (Integer accountId){
        List<Transaction> transactionList = (accountService.findAccountById(accountId)).getTransactions();
        List<TransactionModel> transactionModelsList = new ArrayList<TransactionModel>();
        for (Transaction transaction:transactionList) {
            transactionModelsList.add(new TransactionModel(transaction));
        }
        return new ResponseEntity(transactionModelsList, HttpStatus.OK);
    }
  }
