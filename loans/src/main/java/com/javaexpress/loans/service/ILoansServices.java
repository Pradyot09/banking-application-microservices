package com.javaexpress.loans.service;

import com.javaexpress.loans.LoansDto.LoansDto;

public interface ILoansServices {

    void createLoan(String mobileNumber);

    LoansDto fetchLoanDetails(String mobileNumber);

    Boolean updateLoan(LoansDto loansDto);

    Boolean deleteLoan(String mobileNumber);
}
