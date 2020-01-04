package com.sbd.databases.repository;

import com.sbd.databases.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
    boolean existsCustomerByName(String name);
    Customer getCustomerByName(String name);

    Customer getCustomerByToken(String token);
}
