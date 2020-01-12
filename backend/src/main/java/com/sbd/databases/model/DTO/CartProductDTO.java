package com.sbd.databases.model.DTO;

import com.sbd.databases.model.CartProduct;
import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class CartProductDTO
{
    private Integer id;
    private Integer count;
    private Integer productId;
    private String name;
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;


    public CartProductDTO(CartProduct cartProduct)
    {
        this.id = cartProduct.getId();
        this.productId = cartProduct.getProduct().getId();
        this.count = cartProduct.getCount();
        this.name = cartProduct.getProduct().getName();
        this.price = cartProduct
                .getProduct()
                .getPrice()
                .multiply(new BigDecimal(count))
                .setScale(2, RoundingMode.CEILING);
    }
}
