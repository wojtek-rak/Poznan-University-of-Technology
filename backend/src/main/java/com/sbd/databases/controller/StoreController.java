package com.sbd.databases.controller;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.DTO.AddProductDTO;
import com.sbd.databases.model.DTO.CartWithProductsDTO;
import com.sbd.databases.model.DTO.ProductDTO;
import com.sbd.databases.service.CartService;
import com.sbd.databases.service.CustomerService;
import com.sbd.databases.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController
{
    private final JwtTokenUtil jwtTokenUtil;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartService cartService;

    @Autowired
    public StoreController(JwtTokenUtil jwtTokenUtil, ProductService productService, CustomerService customerService, CartService cartService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
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

    @PutMapping("/products")
    @ResponseBody
    public CartWithProductsDTO addProduct(HttpServletRequest request, @RequestBody AddProductDTO addProductDTO)
    {
        try
        {
            String customerName = jwtTokenUtil.getCustomerNameFromRequest(request);
            return cartService.addProductToCart(addProductDTO, customerService.getCustomerByName(customerName));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
