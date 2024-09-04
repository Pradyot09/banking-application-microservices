package com.javaexpress.accounts.feignclients;

import com.javaexpress.accounts.dto.LoansDto;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public LoansDto fetchLoanDetails(String mobileNumber) {
        return null;
    }

    @Override
    public void createLoan(String mobileNumber) {

    }
}
