package com.sbd.databases.service;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.Cart;
import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.AddressDTO;
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
    public CustomerSignUpDTO createCustomer(CustomerSignUpDTO customerSignUpDTO) throws ResponseStatusException
    {
        if (existCustomerByName(customerSignUpDTO.getName()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with such name exists.");
        }
        try
        {
            Customer customer = new Customer();
            customer.setName(customerSignUpDTO.getName());

            AddressDTO addressDTO = customerSignUpDTO.getAddress();
            if (addressDTO != null)
            {
                String address = addressDTO.getStreet()
                        + " "
                        + addressDTO.getHomeNumber()
                        + ", "
                        + addressDTO.getPostcode()
                        + " "
                        + addressDTO.getCity()
                        + ", "
                        + addressDTO.getEmail();

                customer.setAddress(address);
            }

            customer.setPhone(customerSignUpDTO.getPhone());
            String token = jwtTokenUtil.generateToken(customer);
            customer.setToken(token);
            customerSignUpDTO.setToken(token);

            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setConfirmed(false);

            customerRepository.save(customer);
            cartRepository.save(cart);

            return customerSignUpDTO;
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong request.");
        }
    }

    public boolean existCustomerByName(String name)
    {
        return customerRepository.existsCustomerByName(name);
    }

    public Customer getCustomerFromRequest(HttpServletRequest request) throws ResponseStatusException
    {
        String customerName;

        String token = jwtTokenUtil.getTokenFromRequest(request);

        try
        {
            customerName = jwtTokenUtil.getNameFromToken(token);
            Customer customer = customerRepository.getCustomerByName(customerName);

            if (customer.getToken().equals(token))
            {
                return customer;
            }
            else
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not view this page.");
        }
    }

    @SuppressWarnings("Duplicates")
    public String loginCustomer(CustomerLoginDTO customerLoginDTO) throws ResponseStatusException
    {
        Customer customer = customerRepository.getCustomerByName(customerLoginDTO.getName());

        if (customer != null)
        {
            if (customer.getToken() != null)
            {
                return customer.getToken();
            }
            else
            {
                String token = jwtTokenUtil.generateToken(customer);
                customer.setToken(token);
                customerRepository.save(customer);
                return token;
            }
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid customer name.");
        }
    }

    public String logoutCustomer(Customer customer)
    {
        customer.setToken(null);
        customerRepository.save(customer);
        return customer.getName();

    }
}
