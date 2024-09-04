package com.javaexpress.accounts.repository;

import com.javaexpress.accounts.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    // DSL methods
    Optional<Customer> findByMobileNumber(String mobileNumber);

    //List<Customer> findByMobileNumber(String mobileNumber);

}
