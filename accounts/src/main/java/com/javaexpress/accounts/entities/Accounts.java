package com.javaexpress.accounts.entities;

import jakarta.persistence.Column;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {

    @Id
    @Column(name = "account_number",nullable = false)
    private Long accountNumber;

    @Column(name = "customer_id",nullable = false)
    private Long customerId;

    @Column(name = "account_type",nullable = false)
    private String accountType;

    @Column(name = "branch_address",nullable = false)
    private String branchAddress;
}
