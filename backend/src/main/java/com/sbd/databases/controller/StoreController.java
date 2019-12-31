package com.sbd.databases.controller;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.AddProductDTO;
import com.sbd.databases.model.DTO.CartWithProductsDTO;
import com.sbd.databases.model.DTO.ProductDTO;
import com.sbd.databases.service.CartService;
import com.sbd.databases.service.CustomerService;
import com.sbd.databases.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/store")
public class StoreController
{
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartService cartService;

    @Autowired
    public StoreController(ProductService productService, CustomerService customerService, CartService cartService)
    {
        this.productService = productService;
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @GetMapping("/products")
    @ResponseBody
    public List<ProductDTO> getProducts(@RequestParam(required = false) Integer categoryId)
    {
        if (categoryId != null)
        {
            return productService.findByCategoryId(categoryId);
        }

        return productService.findAll();
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    public CartWithProductsDTO addProduct(HttpServletRequest request, @RequestBody @Validated AddProductDTO addProductDTO, @PathVariable Integer id)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);
            return cartService.addProductToCart(id, addProductDTO, customer);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
