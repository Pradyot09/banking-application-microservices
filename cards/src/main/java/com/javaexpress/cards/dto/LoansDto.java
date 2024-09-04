package com.javaexpress.cards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoansDto {

    private String mobileNumber;
    private String loanNumber;
    private String loanType;  // Enum
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
}
