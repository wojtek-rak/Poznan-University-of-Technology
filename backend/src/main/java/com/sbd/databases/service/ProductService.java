package com.sbd.databases.service;

import com.sbd.databases.model.DTO.ProductDTO;
import com.sbd.databases.model.Product;
import com.sbd.databases.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService
{
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository)
    {
        this.repository = repository;
    }

    public List<ProductDTO> findAll()
    {
        return repository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public Product findById(Integer id)
    {
        return repository.findById(id).orElse(null);
    }

    public List<ProductDTO> findByCategoryId(Integer categoryId)
    {
        return repository.findByCategory_Id(categoryId)
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public void save(Product product)
    {
        repository.save(product);
    }

    public Product findByEan(BigDecimal ean)
    {
        return repository.findByEan(ean);
    }

    public boolean existByEan(BigDecimal ean)
    {
        return repository.existsByEan(ean);
    }
}
