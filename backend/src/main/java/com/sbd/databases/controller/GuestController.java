package com.sbd.databases.controller;

import com.sbd.databases.model.DTO.CustomerLoginDTO;
import com.sbd.databases.model.DTO.CustomerSignUpDTO;
import com.sbd.databases.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        try
        {
            return customerService.loginCustomer(customer);
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

    @PostMapping("/sign-up")
    public CustomerSignUpDTO signUp(@RequestBody @Validated CustomerSignUpDTO customer)
    {
        try
        {
            customerService.createCustomer(customer);
            return customer;
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
