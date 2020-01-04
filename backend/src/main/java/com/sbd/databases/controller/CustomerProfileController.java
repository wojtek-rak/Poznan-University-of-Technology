package com.sbd.databases.controller;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.*;
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
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/my-profile")
public class CustomerProfileController
{

    private final CustomerService customerService;
    private final CartService cartService;
    private final ShopOrderService shopOrderService;

    @Autowired
    public CustomerProfileController(CustomerService customerService, CartService cartService, ShopOrderService shopOrderService)
    {
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
            Customer customer = customerService.getCustomerFromRequest(request);

            return cartService.getNotConfirmedCartOfCustomer(customer);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @PutMapping("/cart/checkout")
    @ResponseBody
    public CartWithShopOrderDTO checkoutCart(HttpServletRequest request, @RequestBody @Validated AddressDTO addressDTO)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);
            return cartService.checkoutCartOfCustomer(customer, addressDTO);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @DeleteMapping("/cart/{cartProductId}")
    @ResponseBody
    public CartWithProductsDTO deleteProductFromCart(HttpServletRequest request, @PathVariable Integer cartProductId)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);
            return cartService.deleteProductFromCartOfCustomer(customer, cartProductId);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @PutMapping("/cart/{cartProductId}")
    @ResponseBody
    public CartWithProductsDTO changeProductCount(HttpServletRequest request, @RequestBody @Validated AddProductDTO addProductDTO, @PathVariable Integer cartProductId)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);

            return cartService.addProductToCart(cartProductId, addProductDTO, customer);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @GetMapping("/shop-orders")
    @ResponseBody
    public List<CartWithShopOrderDTO> getShopOrders(HttpServletRequest request)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);
            List<CartWithShopOrderDTO> cartWithShopOrderDTOS = cartService.getCartsOfCustomer(customer);

            return cartWithShopOrderDTOS
                    .stream()
                    .filter(cartWithShopOrderDTO -> cartWithShopOrderDTO.getShopOrder() != null)
                    .collect(Collectors.toList());
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @GetMapping("/shop-orders/{id}")
    @ResponseBody
    public CartWithShopOrderDTO getShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        try
        {
            Customer customer = customerService.getCustomerFromRequest(request);

            return shopOrderService.getCartWithShopOrderByCustomerAndOrderId(customer, id);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @PostMapping("/logout")
    public String logout(@RequestBody @Validated CustomerLoginDTO customer)
    {
        try
        {
            return customerService.logoutCustomer(customer);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }
}
