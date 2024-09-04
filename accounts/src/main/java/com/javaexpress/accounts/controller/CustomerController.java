package com.javaexpress.accounts.controller;

import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.dto.CustomerDto;
import com.javaexpress.accounts.service.impl.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @GetMapping("fetchCustomerDetails")
    public CustomerDetailsDto fetchCustomerDetails(@RequestParam String mobileNumber){
        log.info("CustomerController :: fetchCustomerDetails" + mobileNumber);
        return customerServiceImpl.fetchCustomerDetails(mobileNumber);
    }

    @PostMapping("createCustomerDetails")
    public String createCustomerDetails(@RequestBody CustomerDto customerDto){
        log.info("CustomerController :: createCustomerDetails" + customerDto);
        customerServiceImpl.createCustomerInformation(customerDto);
        return "Accounts , Cards and Loans created successfully";
    }
}
