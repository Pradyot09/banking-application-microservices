package com.javaexpress.loans.controller;

import com.javaexpress.loans.LoansDto.LoansDto;
import com.javaexpress.loans.service.ILoansServices;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoanController {

    @Autowired
    private ILoansServices iloansServices;


    @Autowired
    private Environment environment;

    @Value ("{$build.version}")
    private String buildVersion;

    @PostMapping("/create")
    public String createLoan(@RequestParam String mobileNumber){

        log.info("LoanController :: createLoan {}", mobileNumber);

        iloansServices.createLoan(mobileNumber);

        return "Loan created successfully";
    }

    @GetMapping("/fetch")
    public LoansDto fetchLoanDetails(@RequestParam String mobileNumber){

        log.info("LoanController :: fetchLoanDetails {}", mobileNumber);

        return iloansServices.fetchLoanDetails(mobileNumber);
    }

    @PutMapping("/update")
    public boolean updateLoanDetails(@RequestBody LoansDto loansDto){

        log.info("LoanController :: updateLoanDetails {}", loansDto);
        return iloansServices.updateLoan(loansDto);
    }

    @DeleteMapping("/delete")
    public Boolean deleteLoanDetails(@RequestParam String mobileNumber){

        log.info("LoanController :: deleteLoanDetails {}", mobileNumber);
        return iloansServices.deleteLoan(mobileNumber);
    }

    @GetMapping("/build-info")
    public String getBuildVersion(){
        return buildVersion;
    }

    @GetMapping("/version")
    public String getVersion(){
        return environment.getProperty("JAVA_HOME");
    }
}
