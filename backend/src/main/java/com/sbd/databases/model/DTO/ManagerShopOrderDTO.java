package com.sbd.databases.model.DTO;

import com.sbd.databases.model.ShopOrder;
import lombok.Data;

@Data
public class ManagerShopOrderDTO
{
    private Integer id;
    private String address;
    private Integer customerId;
    private Integer managerId;
    private CartWithProductsDTO cart;

    public ManagerShopOrderDTO(ShopOrder shopOrder)
    {
        this.id = shopOrder.getId();
        this.address = shopOrder.getAddress();
        this.customerId = shopOrder.getCustomer().getId();
        this.managerId = shopOrder.getManager().getId();
        this.cart = new CartWithProductsDTO(shopOrder.getCart());
    }
}
