package com.sbd.databases.controller;

import com.sbd.databases.model.DTO.WarehouseProductDTO;
import com.sbd.databases.service.WarehouseProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager/secure/warehouse")
public class WarehouseController
{
    private final WarehouseProductService warehouseProductService;

    @Autowired
    public WarehouseController(WarehouseProductService warehouseProductService)
    {
        this.warehouseProductService = warehouseProductService;
    }

    @GetMapping("/products")
    @ResponseBody
    public List<WarehouseProductDTO> getWarehouseProducts()
    {
        List<WarehouseProductDTO> warehouseProducts = warehouseProductService.getWarehouseProducts();
        return warehouseProducts;
    }
}
