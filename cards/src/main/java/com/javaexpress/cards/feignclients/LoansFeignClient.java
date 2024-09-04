package com.javaexpress.cards.feignclients;

import com.javaexpress.cards.dto.LoansDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LOANS")
@LoadBalancerClient("LOANS")
public interface LoansFeignClient {

    @GetMapping("api/fetch")
    public LoansDto fetchLoanDetails(@RequestParam String mobileNumber);
}
