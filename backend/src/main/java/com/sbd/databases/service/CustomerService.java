package com.sbd.databases.service;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.Cart;
import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.CustomerLoginDTO;
import com.sbd.databases.model.DTO.CustomerSignUpDTO;
import com.sbd.databases.repository.CartRepository;
import com.sbd.databases.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
public class CustomerService
{
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CustomerService(JwtTokenUtil jwtTokenUtil, CustomerRepository customerRepository, CartRepository cartRepository)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void createCustomer(CustomerSignUpDTO customerSignUpDTO) throws ResponseStatusException
    {
        if (existCustomerByName(customerSignUpDTO.getName()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with such name exists.");
        }
        else
        {
            Customer customer = new Customer();
            customer.setName(customerSignUpDTO.getName());
            customer.setAddress(customerSignUpDTO.getAddress() == null ? null : customerSignUpDTO.getAddress().toString());
            customer.setPhone(customerSignUpDTO.getPhone());

            Cart cart = new Cart();
            cart.setCount(0);
            cart.setCustomer(customer);
            cart.setConfirmed(false);

            customerRepository.save(customer);
            cartRepository.save(cart);
        }
    }

    public boolean existCustomerByName(String name)
    {
        return customerRepository.existsCustomerByName(name);
    }

    public Customer getCustomerFromRequest(HttpServletRequest request) throws ResponseStatusException
    {
        Integer customerId;

        try
        {
            customerId = jwtTokenUtil.getIdFromRequest(request);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not view this page.");
        }

        return customerRepository.getOne(customerId);
    }

    public String loginCustomer(CustomerLoginDTO customerLoginDTO) throws ResponseStatusException
    {
        Customer customer = customerRepository.getCustomerByName(customerLoginDTO.getName());

        if (customer != null)
        {
            return jwtTokenUtil.generateToken(customer);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid customer name.");
        }
    }
}
