package com.javaexpress.accounts.feignclients;

import com.javaexpress.accounts.dto.LoansDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="LOANS", fallback = LoansFallback.class)
@LoadBalancerClient("LOANS")
public interface LoansFeignClient {

    @GetMapping("api/fetch")
    public LoansDto fetchLoanDetails(@RequestParam String mobileNumber);

    @PostMapping("api/create")
    public void createLoan(@RequestParam String mobileNumber);
}
