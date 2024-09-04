package com.javaexpress.accounts.service.impl;

import com.javaexpress.accounts.dto.*;
import com.javaexpress.accounts.entities.Accounts;
import com.javaexpress.accounts.entities.Customer;
import com.javaexpress.accounts.exception.ResourceNotFoundException;
import com.javaexpress.accounts.feignclients.CardsFeignClient;
import com.javaexpress.accounts.feignclients.LoansFeignClient;
import com.javaexpress.accounts.repository.AccountsRepository;
import com.javaexpress.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    private AccountsServiceImpl accountsServiceimpl;


    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer Mobile Number Not found " + mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException(("Account not found for Mobile Number " + mobileNumber)));

        CardsDto cardsDto = cardsFeignClient.fetchCardDetails(mobileNumber);

        LoansDto loansDto = loansFeignClient.fetchLoanDetails((mobileNumber));

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        BeanUtils.copyProperties(customer, customerDetailsDto);

        AccountsDto accountsDto = new AccountsDto();
        BeanUtils.copyProperties(accounts, accountsDto);

        customerDetailsDto.setAccountsDto(accountsDto);

        if(cardsDto!=null){
            customerDetailsDto.setCardsDto(cardsDto);
        }

        if(loansDto!=null){
            customerDetailsDto.setLoansDto(loansDto);
        }

        return customerDetailsDto;
    }

    public void createCustomerInformation(CustomerDto customerDto){
        accountsServiceimpl.createAccount(customerDto);
        cardsFeignClient.createCard(customerDto.getMobileNumber());
        loansFeignClient.createLoan(customerDto.getMobileNumber());
    }
}
