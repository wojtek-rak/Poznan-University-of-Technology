package com.sbd.databases.service;

import com.sbd.databases.model.DTO.WarehouseProductDTO;
import com.sbd.databases.model.WarehouseProduct;
import com.sbd.databases.repository.WarehouseProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseProductService
{
    private final WarehouseProductRepository warehouseProductRepository;

    @Autowired
    public WarehouseProductService(WarehouseProductRepository warehouseProductRepository)
    {
        this.warehouseProductRepository = warehouseProductRepository;
    }

    public List<WarehouseProductDTO> getWarehouseProducts()
    {
        List<WarehouseProduct> collect = warehouseProductRepository.findAll();
        return collect.stream().map(WarehouseProductDTO::new).collect(Collectors.toList());
    }
}
