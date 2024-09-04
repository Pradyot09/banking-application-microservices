package com.javaexpress.cards.service.Impl;

import com.javaexpress.cards.dto.CardsDto;
import com.javaexpress.cards.dto.LoansDto;
import com.javaexpress.cards.entities.Cards;
import com.javaexpress.cards.exception.CardAlreadyExistException;
import com.javaexpress.cards.exception.ResourceNotFoundException;
import com.javaexpress.cards.feignclients.LoansFeignClient;
import com.javaexpress.cards.repository.CardRepository;
import com.javaexpress.cards.service.ICardsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class CardsServiceImpl implements ICardsService {

    private final CardRepository cardRepository;
    private final LoansFeignClient loansFeignClient;

    public CardsServiceImpl(CardRepository cardRepository, LoansFeignClient loansFeignClient) {
        this.cardRepository = cardRepository;
        this.loansFeignClient = loansFeignClient;
    }

    @Override
    public void createCard(String mobileNumber) {

        log.info("CardServiceImpl :: createCard {}", mobileNumber);
        Optional<Cards> optionalCards = cardRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistException("Card Already exist with the given mobile number " + mobileNumber);
        }

        createNewCard(mobileNumber);

    }

    private void createNewCard(String mobileNumber) {
        log.info("CardServiceImpl :: createNewCard {}", mobileNumber);
        //Create card details
        String cardType = "CREDIT";
        int totalLimit = 100000;
        int amountUsed = 0;
        int availableAmount = totalLimit - amountUsed;
        long randomCardNumber = 100000000000000L  + new Random().nextInt(999999999);

        //Set card details
        Cards cards = new Cards();
        cards.setMobileNumber(mobileNumber);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setCardType(cardType);
        cards.setTotalLimit(totalLimit);
        cards.setAmountUsed(amountUsed);
        cards.setAvailableAmount(availableAmount);
        cardRepository.save(cards);
    }

    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        log.info("CardServiceImpl :: fetchCardDetails {}", mobileNumber);
        Cards cardDetailsInfo =  cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Card number does not exists "+ mobileNumber));
        CardsDto cardsDto = new CardsDto();

        BeanUtils.copyProperties(cardDetailsInfo,cardsDto);
        return  cardsDto;
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {

        log.info("CardServiceImpl :: updateCard {}", cardsDto.getMobileNumber());

        Cards cardsDetailsInfo = cardRepository.findByCardNumber(cardsDto.getCardNumber())
                   .orElseThrow(()->new ResourceNotFoundException("Card does not exist with this Id "+ cardsDto.getCardNumber()));
        cardsDetailsInfo.setMobileNumber(cardsDto.getMobileNumber());
        cardRepository.save(cardsDetailsInfo);

        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        log.info("CardServiceImpl :: deleteCard {}", mobileNumber);

        Cards cardsDetailsInfo = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Mobile number not associated with any cards "+ mobileNumber));
        cardRepository.deleteById(cardsDetailsInfo.getCardId());
        return true ;
    }

    @Override
    public LoansDto fetchLoanDetails(String mobileNumber) {

        log.info("CardServiceImpl :: fetchLoanDetails {}", mobileNumber);

        try {
            return loansFeignClient.fetchLoanDetails(mobileNumber);
        }
        catch(ResourceNotFoundException ex){
            throw new ResourceNotFoundException("Loan does not exits with the given mobile number" + mobileNumber);
        }

    }
}
