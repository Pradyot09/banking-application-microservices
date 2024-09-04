package com.javaexpress.accounts.controller;

import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.dto.CustomerDto;
import com.javaexpress.accounts.service.IAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api")
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public String createAccount(@RequestBody CustomerDto customerDto){

        log.info("AccountsController :: createAccount {} ", customerDto.getMobileNumber());
        iAccountsService.createAccount(customerDto);
        return "Account created successfully";

    }

    @GetMapping("/fetch")
    public CustomerDetailsDto fetchAccount(@RequestParam String mobileNumber){

        log.info("AccountsController :: fetchAccount {} ", mobileNumber);
        return iAccountsService.fetchAccount(mobileNumber);

    }

    @PutMapping("/update")
    public boolean updateAccountDetails(@RequestBody CustomerDetailsDto customerDetailsDto){
        log.info("AccountsController :: updateAccountDetails {} ", customerDetailsDto);
        return iAccountsService.updateAccount(customerDetailsDto);

    }

    @DeleteMapping("/delete")
    public boolean deleteAccount(@RequestParam String mobileNumber){
        log.info("AccountsController :: deleteAccount {} ", mobileNumber);
        return iAccountsService.deleteAccount(mobileNumber);
    }
}