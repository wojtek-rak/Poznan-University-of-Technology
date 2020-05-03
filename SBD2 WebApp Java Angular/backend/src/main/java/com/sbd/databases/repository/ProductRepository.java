package com.sbd.databases.repository;

import com.sbd.databases.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    List<Product> findByCategory_Id(Integer categoryId);

    Product findByEan(BigDecimal ean);

    boolean existsByEan(BigDecimal ean);
}