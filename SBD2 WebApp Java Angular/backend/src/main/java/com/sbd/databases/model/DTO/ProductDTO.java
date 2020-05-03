package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Product;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDTO
{
    @Null
    private Integer id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Digits(integer = 13, fraction = 0)
    private BigDecimal ean;
    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 2)
    private BigDecimal price;
    @NotNull
    @Max(value = 100)
    @Min(value = 0)
    @Digits(integer = 2, fraction = 0)
    private BigDecimal vat;
    @Valid
    @NotNull
    private CategoryDTO category;
    @Valid
    private List<SaleDTO> sales;
    @Null
    private BigDecimal calculatedPrice;

    public ProductDTO(@NotNull @Size(min = 1, max = 255) String name, @NotNull BigDecimal ean, @NotNull @Positive BigDecimal price, @NotNull @Max(value = 100) @Min(value = 0) BigDecimal vat, @Valid @NotNull CategoryDTO category, @Valid List<SaleDTO> sales)
    {
        this.name = name;
        this.ean = ean;
        this.price = price;
        this.vat = vat;
        this.category = category;
        this.sales = sales;
    }

    public ProductDTO(Product product)
    {
        this.id = product.getId();
        this.name = product.getName();
        this.ean = product.getEan();
        this.price = product.getPrice();
        this.vat = product.getVat();
        this.category = new CategoryDTO(product.getCategory());
        this.sales = product.getSales().stream().map(SaleDTO::new).collect(Collectors.toList());
        this.calculatedPrice = this.price.subtract(this.price.multiply(sales
                .stream()
                .map(sale -> new BigDecimal((sale.getPercentDiscount()) / 100.0))
                .reduce(BigDecimal::add)
                .orElseGet(() -> new BigDecimal(0)))
                .setScale(2, RoundingMode.CEILING));
    }
}
