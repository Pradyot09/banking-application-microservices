package com.javaexpress.cards.controller;

import com.javaexpress.cards.dto.CardsDto;
import com.javaexpress.cards.dto.LoansDto;
import com.javaexpress.cards.entities.Cards;
import com.javaexpress.cards.service.ICardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Slf4j
public class CardsController {

    private final ICardsService iCardsService;

    public CardsController(ICardsService iCardsService) {
        this.iCardsService = iCardsService;
    }

    @PostMapping(value="/createCard")
    public String createCard(@RequestParam String mobileNumber){
        log.info("CardsController :: createCard {}", mobileNumber);
        iCardsService.createCard(mobileNumber);
        return "Card created successfully";
    }

    @GetMapping("/fetch")
    public CardsDto fetchCardDetails(@RequestParam String mobileNumber){
        log.info("CardsController :: fetchCardsDetails {}", mobileNumber);
        return iCardsService.fetchCardDetails(mobileNumber);
    }

    @PutMapping("/update")
    public boolean updateCardDetails(@RequestBody CardsDto cardsDto){
        log.info("CardsController :: updateCardDetails {}",cardsDto.getMobileNumber());
        return iCardsService.updateCard(cardsDto);
    }

    @DeleteMapping("/delete")
    public boolean deleteCardDetails(@RequestParam String mobileNumber){
        log.info("CardsController :: deleteCardDetails {}",mobileNumber);
        return iCardsService.deleteCard(mobileNumber);
    }

    @GetMapping("/fetchLoanDetails")
    public LoansDto fetchLoanDetails(String mobileNumber){
        log.info("CardsController :: fetchLoanDetails {}",mobileNumber);
        return iCardsService.fetchLoanDetails(mobileNumber);
    }
}
