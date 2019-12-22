package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Cart;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartWithProductsDTO
{
    private Integer id;
    private List<CartProductDTO> cartProducts;
    private BigDecimal totalPrice;

    public CartWithProductsDTO(Cart cart)
    {
        this.id = cart.getId();
        this.cartProducts = cart.getCartProducts()
                .stream()
                .map(CartProductDTO::new)
                .collect(Collectors.toList());
        this.totalPrice = cartProducts // // TODO: 21.12.2019 use procedure in database NIE UWZGLĘDNIA PROMOCJI
                .stream()
                .map(cartProduct -> cartProduct.getPrice().multiply(new BigDecimal(cartProduct.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.CEILING);
    }
}
