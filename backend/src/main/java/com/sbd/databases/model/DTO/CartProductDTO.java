package com.sbd.databases.model.DTO;

import com.sbd.databases.model.CartProduct;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDTO
{
    private Integer id;
    private Integer count;
    private Integer productId;
    private String name;
    private BigDecimal price;

    public CartProductDTO(CartProduct cartProduct)
    {
        this.id = cartProduct.getId();
        this.productId = cartProduct.getProduct().getId();
        this.count = cartProduct.getCount();
        this.name = cartProduct.getProduct().getName();
        this.price = cartProduct.getProduct().getPrice();
    }
}
