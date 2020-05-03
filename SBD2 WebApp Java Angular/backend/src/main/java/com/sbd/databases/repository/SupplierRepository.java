package com.sbd.databases.repository;

import com.sbd.databases.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>
{
    Optional<Supplier> findByName(String name);
}
