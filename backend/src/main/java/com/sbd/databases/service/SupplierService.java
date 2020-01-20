package com.sbd.databases.service;

import com.sbd.databases.model.Supplier;
import com.sbd.databases.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService
{
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository)
    {
        this.supplierRepository = supplierRepository;
    }

    public void save(Supplier supplier)
    {
        supplierRepository.save(supplier);
    }

    public Optional<Supplier> findByName(String name)
    {
        return supplierRepository.findByName(name);
    }
}
