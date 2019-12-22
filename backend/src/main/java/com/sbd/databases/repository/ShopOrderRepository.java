package com.sbd.databases.repository;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer>
{
    ShopOrder getFirstByCustomerAndId(Customer customer, Integer id);
}
