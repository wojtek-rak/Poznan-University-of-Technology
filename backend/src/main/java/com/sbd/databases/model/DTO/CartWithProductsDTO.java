package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Cart;
import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartWithProductsDTO
{
    private Integer id;
    private List<CartProductDTO> cartProducts;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalPrice;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalPriceWithDiscounts;

    public CartWithProductsDTO(Cart cart)
    {
        this.id = cart.getId();
        this.cartProducts = cart.getCartProducts()
                .stream()
                .map(CartProductDTO::new)
                .collect(Collectors.toList());
        this.totalPrice = cartProducts
                .stream()
                .map(CartProductDTO::getPriceWithDiscounts)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.CEILING);
    }
}
