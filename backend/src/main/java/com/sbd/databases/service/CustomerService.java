package com.sbd.databases.service;

import com.sbd.databases.model.Cart;
import com.sbd.databases.model.Customer;
import com.sbd.databases.repository.CartRepository;
import com.sbd.databases.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Service
public class CustomerService
{
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository)
    {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    public boolean existCustomerByName(String name)
    {
        return customerRepository.existsCustomerByName(name);
    }

    public void addCustomer(Customer customer)
    {
        if (existCustomerByName(customer.getName()))
        {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Customer with such name exists.");
        }
        else
        {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setConfirmed(false);
            customer.setCarts(Collections.singletonList(cart));

            cartRepository.save(cart);
            customerRepository.save(customer);
        }
    }

    public Customer getCustomerByName(String name)
    {
        return customerRepository.getCustomerByName(name);
    }
}
