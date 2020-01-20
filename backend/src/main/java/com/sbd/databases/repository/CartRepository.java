package com.sbd.databases.repository;

import com.sbd.databases.model.Cart;
import com.sbd.databases.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>
{
    Cart getFirstByCustomerAndConfirmed(Customer customer, Boolean confirmed);

    List<Cart> getAllByCustomer(Customer customer);

    @Procedure(name = "calculateCart")
    BigDecimal calculateCart(int cartId);

}
