package com.sbd.databases.repository;

import com.sbd.databases.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer>
{
}
