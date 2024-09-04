package com.javaexpress.accounts.service.impl;

import com.javaexpress.accounts.dto.AccountsDto;
import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.dto.CustomerDto;
import com.javaexpress.accounts.entities.Accounts;
import com.javaexpress.accounts.entities.Customer;
import com.javaexpress.accounts.exception.CustomerAlreadyExistsException;
import com.javaexpress.accounts.exception.ResourceNotFoundException;
import com.javaexpress.accounts.repository.AccountsRepository;
import com.javaexpress.accounts.repository.CustomerRepository;
import com.javaexpress.accounts.service.IAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        //Convert CustomerDot to customer object
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already Registered with given mobile number" + customerDto.getMobileNumber());
        }

        //Save customer details to the database
         Customer dbCustomer  = customerRepository.save(customer);

        // Save account information in the database
        Accounts accounts = createNewAccount(dbCustomer);
        accountsRepository.save(accounts);

    }

    private Accounts createNewAccount(Customer dbCustomer) {

        Accounts accounts = new Accounts();
        accounts.setCustomerId(dbCustomer.getCustomerId());
        accounts.setAccountType("SAVING");
        accounts.setBranchAddress("Hyderabad");
        Long randomNumber = 100000L + new Random().nextInt(99999);
        accounts.setAccountNumber(randomNumber);
        return accounts;
    }

    @Override
    public CustomerDetailsDto fetchAccount(String mobileNumber) {
        //Account information and customer information
        log.info("AccountsServiceImpl :: fetchAccount {}", mobileNumber);
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Mobile Number not found "+ mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Account not found for mobile number "+ mobileNumber));

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        BeanUtils.copyProperties(customer,customerDetailsDto);

        AccountsDto accountsDto = new AccountsDto();
        BeanUtils.copyProperties(accounts, accountsDto);
        customerDetailsDto.setAccountsDto(accountsDto);

        return customerDetailsDto;
    }

    @Override
    public boolean updateAccount(CustomerDetailsDto customerDetailsDto) {

        AccountsDto accountsDto = customerDetailsDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(()-> new ResourceNotFoundException("Account not found "+ accountsDto.getAccountNumber()));
            BeanUtils.copyProperties(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(()-> new ResourceNotFoundException("Customer not found Exception " + customerId));
            BeanUtils.copyProperties(customerDetailsDto,customer);
            customerRepository.save(customer);
        }else{
            throw new RuntimeException("Account details are missing");

        }

        return true;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found "+mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
