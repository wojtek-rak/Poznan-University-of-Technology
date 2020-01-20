package com.sbd.databases.model.DTO;

import com.sbd.databases.model.CartProduct;
import com.sbd.databases.model.Sale;
import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class CartProductDTO
{
    private Integer id;
    private Integer count;
    private Integer productId;
    private String name;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal priceWithDiscounts;

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
        List<Sale> saleList = cartProduct
                .getProduct()
                .getSales();
        this.priceWithDiscounts = this.price.subtract(cartProduct
                .getProduct()
                .getSales()
                .stream()
                .map(sale -> this.price.multiply(new BigDecimal((sale.getPercentDiscount()) / 100.0)))
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0)))
                .setScale(2, RoundingMode.CEILING);
        ;
    }
}
