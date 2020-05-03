package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartWithShopOrderDTO
{
    private CartWithProductsDTO cart;
    private ShopOrderDTO shopOrder;

    public CartWithShopOrderDTO(Cart cart)
    {
        this.cart = new CartWithProductsDTO(cart);
        this.shopOrder = cart.getShopOrder() == null ? null : new ShopOrderDTO(cart.getShopOrder());
    }
}
