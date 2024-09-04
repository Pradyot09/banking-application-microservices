package com.javaexpress.accounts.service;

import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDetailsDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDetailsDto customerDetailsDto);

    boolean deleteAccount(String mobileNumber);
}

