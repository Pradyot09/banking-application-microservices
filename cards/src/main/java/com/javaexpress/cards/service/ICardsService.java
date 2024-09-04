package com.javaexpress.cards.service;

import com.javaexpress.cards.dto.CardsDto;
import com.javaexpress.cards.dto.LoansDto;
import com.javaexpress.cards.entities.Cards;

public interface ICardsService {

    void createCard(String mobileNumber);

    CardsDto fetchCardDetails(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);

    LoansDto fetchLoanDetails(String mobileNumber);
}
