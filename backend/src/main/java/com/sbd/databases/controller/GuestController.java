package com.sbd.databases.controller;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.Customer;
import com.sbd.databases.service.CustomerService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
public class GuestController
{
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomerService customerService;

    @Autowired
    public GuestController(CustomerService customerService, JwtTokenUtil jwtTokenUtil)
    {
        this.customerService = customerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody Customer customer)
    {
        if (customerService.existCustomerByName(customer.getName()))
        {
            return jwtTokenUtil.generateToken(customer);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer does not exist!");
        }
    }

    @PostMapping("/signup")
    public Customer signup(@RequestBody @Validated Customer customer)
    {
        customerService.addCustomer(customer);
        return customer;
    }
}
