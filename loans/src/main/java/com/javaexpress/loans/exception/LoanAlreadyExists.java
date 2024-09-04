package com.javaexpress.loans.exception;

public class LoanAlreadyExists extends RuntimeException{
    public LoanAlreadyExists(String message) {
        super(message);
    }
}
