package com.javaexpress.accounts.feignclients;

import com.javaexpress.accounts.dto.CardsDto;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        CardsDto cardsDto = new CardsDto();
        cardsDto.setStatus("Cards service is not available");
        return cardsDto;
    }

    @Override
    public String createCard(String mobileNumber) {
        return "";
    }
}
