package com.sbd.databases.repository;

import com.sbd.databases.model.Cart;
import com.sbd.databases.model.Customer;
import com.sbd.databases.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>
{
    Cart getFirstByCustomerAndConfirmed(Customer customer, Boolean confirmed);

    List<Cart> getAllByCustomer(Customer customer);

    Cart getFirstByCustomerAndShopOrder(Customer customer, ShopOrder one);
}
