package com.sbd.databases.service;

import com.sbd.databases.model.Sale;
import com.sbd.databases.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService
{
    private final SaleRepository saleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository)
    {
        this.saleRepository = saleRepository;
    }

    public void saveAll(List<Sale> sales)
    {
        saleRepository.saveAll(sales);
    }
}
