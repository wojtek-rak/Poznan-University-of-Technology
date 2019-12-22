package com.sbd.databases.service;

import com.sbd.databases.model.CartProduct;
import com.sbd.databases.repository.CartProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductService
{
    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartProductService(CartProductRepository cartProductRepository)
    {
        this.cartProductRepository = cartProductRepository;
    }

    public void save(CartProduct cartProduct)
    {
        cartProductRepository.save(cartProduct);
    }

    public void delete(CartProduct cartProduct)
    {
        cartProductRepository.delete(cartProduct);
    }
}
