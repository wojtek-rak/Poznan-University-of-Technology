package com.sbd.databases.repository;

import com.sbd.databases.model.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Integer>
{

    @Procedure(name = "fillWarehouse")
    void fillWarehouse();

}
