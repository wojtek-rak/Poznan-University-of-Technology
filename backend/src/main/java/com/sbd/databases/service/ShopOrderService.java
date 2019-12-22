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

        return new CartWithShopOrderDTO(shopOrder.getCart());
    }

    public List<ManagerShopOrderDTO> findAll()
    {
        List<ShopOrder> shopOrders = shopOrderRepository.findAll();

        return shopOrders.stream().map(ManagerShopOrderDTO::new).collect(Collectors.toList());
    }

    public ManagerShopOrderDTO assignManager(Manager manager, Integer id)
    {
        ShopOrder shopOrder = shopOrderRepository.getFirstByManagerAndId(manager, id);
        shopOrder.setManager(manager);
        shopOrderRepository.save(shopOrder);

        return new ManagerShopOrderDTO(shopOrder);
    }

    public ManagerShopOrderDTO getManagerShopOrderById(Integer id)
    {
        return new ManagerShopOrderDTO(shopOrderRepository.getOne(id));
    }

    public ManagerShopOrderDTO confirmShopOrder(Manager manager, Integer id)
    {
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Implement this"); // TODO: 22.12.2019 call procedure on database
    }
}
