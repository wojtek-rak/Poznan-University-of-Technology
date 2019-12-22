package com.sbd.databases.controller;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.DTO.AddressDTO;
import com.sbd.databases.model.DTO.CartWithProductsDTO;
import com.sbd.databases.model.DTO.CartWithShopOrderDTO;
import com.sbd.databases.service.CartService;
import com.sbd.databases.service.CustomerService;
import com.sbd.databases.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/my-profile")
public class CustomerProfileController
{

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomerService customerService;
    private final CartService cartService;
    private final ShopOrderService shopOrderService;

    @Autowired
    public CustomerProfileController(JwtTokenUtil jwtTokenUtil, CustomerService customerService, CartService cartService, ShopOrderService shopOrderService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customerService = customerService;
        this.cartService = cartService;
        this.shopOrderService = shopOrderService;
    }

    @GetMapping("/cart")
    @ResponseBody
    public CartWithProductsDTO getMyCart(HttpServletRequest request)
    {
        try
        {
            String customerName = jwtTokenUtil.getCustomerNameFromRequest(request);
            return cartService.getNotConfirmedCartOfCustomer(customerService.getCustomerByName(customerName));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart/checkout")
    @ResponseBody
    public CartWithShopOrderDTO checkoutCart(HttpServletRequest request, @RequestBody @Validated AddressDTO addressDTO)
    {
        try
        {
            String customerName = jwtTokenUtil.getCustomerNameFromRequest(request);
            return cartService.checkoutCartOfCustomer(customerService.getCustomerByName(customerName), addressDTO);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shop-orders")
    @ResponseBody
    public List<CartWithShopOrderDTO> getShopOrders(HttpServletRequest request)
    {
        try
        {
            String customerName = jwtTokenUtil.getCustomerNameFromRequest(request);
            return cartService.getCartsOfCustomer(customerService.getCustomerByName(customerName));
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/shop-orders/{id}")
    @ResponseBody
    public CartWithShopOrderDTO getShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        try
        {
            String customerName = jwtTokenUtil.getCustomerNameFromRequest(request);
            return shopOrderService
                    .getCartWithShopOrderByCustomerAndOrderId(customerService.getCustomerByName(customerName), id);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
