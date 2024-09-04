package com.javaexpress.accounts.feignclients;

import com.javaexpress.accounts.dto.CardsDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="CARDS", fallback = CardsFallback.class) // http:localhost:9004
@LoadBalancerClient("CARDS")
public interface CardsFeignClient {

    @GetMapping("api/fetch")
    public CardsDto fetchCardDetails(@RequestParam String mobileNumber);

    @PostMapping(value="api/createCard")
    public String createCard(@RequestParam String mobileNumber);
}