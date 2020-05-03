package com.sbd.databases.service;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.CartWithShopOrderDTO;
import com.sbd.databases.model.DTO.ManagerShopOrderDTO;
import com.sbd.databases.model.Manager;
import com.sbd.databases.model.ShopOrder;
import com.sbd.databases.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (shopOrder == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Such shop order does not exists.");
        }

        return new CartWithShopOrderDTO(shopOrder.getCart());
    }

    public List<ManagerShopOrderDTO> findAll()
    {
        List<ShopOrder> shopOrders = shopOrderRepository.findAll();

        return shopOrders.stream().map(ManagerShopOrderDTO::new).collect(Collectors.toList());
    }

    public ManagerShopOrderDTO assignManager(Manager manager, Integer id)
    {
        Optional<ShopOrder> shopOrderOptional = shopOrderRepository.findById(id);
        if (shopOrderOptional.isPresent())
        {
            ShopOrder shopOrder = shopOrderOptional.get();
            shopOrder.setManager(manager);
            shopOrderRepository.save(shopOrder);

            return new ManagerShopOrderDTO(shopOrder);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find such shop order.");
        }
    }

    public ManagerShopOrderDTO getManagerShopOrderById(Integer id)
    {
        return new ManagerShopOrderDTO(shopOrderRepository.getOne(id));
    }

    public ManagerShopOrderDTO confirmShopOrder(Manager manager, Integer id)
    {
        ShopOrder shopOrder = shopOrderRepository.getFirstByManagerAndId(manager, id);
        shopOrder.setConfirmed(true);
        shopOrderRepository.save(shopOrder);

        return new ManagerShopOrderDTO(shopOrderRepository.getOne(id));
    }
}
