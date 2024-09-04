package com.javaexpress.loans.service.Impl;

import com.javaexpress.loans.LoansDto.LoansDto;
import com.javaexpress.loans.entities.Loans;
import com.javaexpress.loans.exception.LoanAlreadyExists;
import com.javaexpress.loans.exception.ResourceNotFoundException;
import com.javaexpress.loans.repository.LoanRepository;
import com.javaexpress.loans.service.ILoansServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class LoansServiceImpl implements ILoansServices {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void createLoan(String mobileNumber) {

        log.info("LoansServiceImpl :: createLoan {}", mobileNumber);

        Optional<Loans> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);

        if(optionalLoan.isPresent()){
            throw new LoanAlreadyExists("Loan Already Exist with the given mobile number" + mobileNumber);
        }

        // Create loan
       createNewLoan(mobileNumber);
    }

    private void createNewLoan(String mobileNumber) {

        log.info("LoansServiceImpl :: createNewLoan {}", mobileNumber);

        long randomLoanNumber = 100000L  + new Random().nextInt(999999);
        Loans loans = new Loans();
        loans.setMobileNumber(mobileNumber);
        loans.setLoanNumber(String.valueOf(randomLoanNumber));
        loans.setLoanType("HOME_LOAN");
        loans.setTotalLoan(2000000);
        loans.setAmountPaid(0);
        loans.setOutstandingAmount(2000000);
        loanRepository.save(loans);
    }

    @Override
    public LoansDto fetchLoanDetails(String mobileNumber) {

        log.info("LoansServiceImpl :: fetchLoanDetails {}", mobileNumber);

        Loans loansInfo =  loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Loan with the given mobile number does not exists " + mobileNumber));

        LoansDto loansDto = new LoansDto();
        BeanUtils.copyProperties(loansInfo, loansDto);
        return loansDto;
    }

    @Override
    public Boolean updateLoan(LoansDto loansDto) {

        log.info("LoansServiceImpl :: updateLoan {}", loansDto);

        Loans loansInfo =  loanRepository.findByMobileNumber(loansDto.getMobileNumber())
                .orElseThrow(()->new ResourceNotFoundException("Loan with the given mobile number does not exists"));

        BeanUtils.copyProperties(loansDto,loansInfo);

        loanRepository.save(loansInfo);

        return true;
    }

    @Override
    public Boolean deleteLoan(String mobileNumber) {

        log.info("LoansServiceImpl :: deleteLoan {}", mobileNumber);

        Loans loansInfo = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Loan with the given mobile number does not exists "+ mobileNumber));
        loanRepository.deleteById(loansInfo.getLoanId());
        return true;
    }
}
