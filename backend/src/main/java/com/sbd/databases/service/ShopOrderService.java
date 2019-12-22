package com.sbd.databases.service;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.CartWithShopOrderDTO;
import com.sbd.databases.model.ShopOrder;
import com.sbd.databases.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopOrderService
{
    private final ShopOrderRepository shopOrderRepository;

    @Autowired
    public ShopOrderService(ShopOrderRepository shopOrderRepository)
    {
        this.shopOrderRepository = shopOrderRepository;
    }

    public CartWithShopOrderDTO getCartWithShopOrderByCustomerAndOrderId(Customer customer, Integer id)
    {
        ShopOrder shopOrder = shopOrderRepository.getFirstByCustomerAndId(customer, id);

        return new CartWithShopOrderDTO(shopOrder.getCart());
    }
}
