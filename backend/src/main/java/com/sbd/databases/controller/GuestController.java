package com.sbd.databases.controller;

import com.sbd.databases.model.DTO.CustomerLoginDTO;
import com.sbd.databases.model.DTO.CustomerSignUpDTO;
import com.sbd.databases.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GuestController
{
    private final CustomerService customerService;

    @Autowired
    public GuestController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated CustomerLoginDTO customer)
    {
        return customerService.loginCustomer(customer);
    }

    @PostMapping("/sign-up")
    public CustomerSignUpDTO signUp(@RequestBody @Validated CustomerSignUpDTO customer)
    {
        customerService.createCustomer(customer);
        return customer;
    }
}
