package com.sbd.databases.service;

import com.sbd.databases.model.*;
import com.sbd.databases.model.DTO.*;
import com.sbd.databases.repository.WarehouseProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseProductService
{
    private final WarehouseProductRepository warehouseProductRepository;
    private SupplierService supplierService;
    private CategoryService categoryService;
    private ProductService productService;
    private SaleService saleService;

    @Autowired
    public WarehouseProductService(WarehouseProductRepository warehouseProductRepository, SupplierService supplierService, CategoryService categoryService, ProductService productService, SaleService saleService)
    {
        this.warehouseProductRepository = warehouseProductRepository;
        this.supplierService = supplierService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.saleService = saleService;
    }

    public List<WarehouseProductDTO> getWarehouseProducts()
    {
        List<WarehouseProduct> collect = warehouseProductRepository.findAll();
        return collect.stream().map(WarehouseProductDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public WarehouseProductDTO addProductToWarehouse(WarehouseProductDTO warehouseProductDTO)
    {
        if (productService.existByEan(warehouseProductDTO.getProduct().getEan())
                || warehouseProductRepository.existsByWarehouseCode(warehouseProductDTO.getWarehouseCode()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with such ean or warehouse code exists.");
        }

        ProductDTO productDTO = warehouseProductDTO.getProduct();
        CategoryDTO categoryDTO = productDTO.getCategory();
        SupplierDTO supplierDTO = categoryDTO.getSupplier();
        List<SaleDTO> saleDTOS = productDTO.getSales();

        Optional<Category> categoryOptional = categoryService.findByName(categoryDTO.getName());
        Category category;

        if (categoryOptional.isPresent())
        {
            category = categoryOptional.get();
        }
        else
        {
            Optional<Supplier> supplierOptional = supplierService.findByName(supplierDTO.getName());
            Supplier supplier;
            if (supplierOptional.isPresent())
            {
                supplier = supplierOptional.get();
            }
            else
            {
                supplier = new Supplier();
                supplier.setName(supplierDTO.getName());
                supplierService.save(supplier);
            }

            category = new Category();
            category.setName(categoryDTO.getName());
            category.setSupplier(supplier);
            categoryService.save(category);
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setVat(productDTO.getVat());
        product.setEan(productDTO.getEan());
        product.setCategory(category);
        productService.save(product);

        if (saleDTOS != null)
        {
            List<Sale> sales = saleDTOS.stream().map(SaleDTO::getPercentDiscount).map(Sale::new).collect(Collectors.toList());
            sales.forEach(sale -> sale.setProduct(product));
            saleService.saveAll(sales);
        }

        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setCount(warehouseProductDTO.getCount());
        warehouseProduct.setProduct(product);
        warehouseProduct.setWarehouseCode(warehouseProductDTO.getWarehouseCode());
        warehouseProductRepository.save(warehouseProduct);

        return warehouseProductDTO;
    }

    public List<WarehouseProductDTO> fillWarehouse()
    {
        try
        {
            warehouseProductRepository.fillWarehouse();
        }
        catch (Exception e)
        {
            System.out.println(e.getClass());
        }

        return warehouseProductRepository.findAll().stream().map(WarehouseProductDTO::new).collect(Collectors.toList());
    }

    public void updateProduct(CartProduct cartProduct)
    {
        WarehouseProduct warehouseProduct = cartProduct.getProduct().getWarehouseProduct();
        int newCount = warehouseProduct.getCount() - cartProduct.getCount();

        if (newCount >= 0)
        {
            warehouseProduct.setCount(newCount);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There is too small amount of products in a warehouse!");
        }

        warehouseProductRepository.save(warehouseProduct);
    }

    public List<WarehouseProductDTO> remove(Integer id)
    {
        warehouseProductRepository.deleteById(id);
        return warehouseProductRepository.findAll().stream().map(WarehouseProductDTO::new).collect(Collectors.toList());
    }
}
