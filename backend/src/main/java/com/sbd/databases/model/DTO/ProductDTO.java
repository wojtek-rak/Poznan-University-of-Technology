package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDTO
{
    private Integer id;
    private String name;
    private BigDecimal ean;
    private BigDecimal price;
    private BigDecimal vat;
    private CategoryDTO category;
    private List<SaleDTO> sales;
    private BigDecimal calculatedPrice;

    public ProductDTO(Product product)
    {
        this.id = product.getId();
        this.name = product.getName();
        this.ean = product.getEan();
        this.price = product.getPrice();
        this.vat = product.getVat();
        this.category = new CategoryDTO(product.getCategory());
        this.sales = product.getSales().stream().map(SaleDTO::new).collect(Collectors.toList());
        this.calculatedPrice = calculatePrice();
    }

    private BigDecimal calculatePrice()
    {
        BigDecimal calculatedPrice = new BigDecimal(String.valueOf(price));

        for (SaleDTO sale : sales)
        {
            BigDecimal discountMultiplier = new BigDecimal(String.valueOf((new BigDecimal("100.00")
                    .subtract(new BigDecimal(
                            String.valueOf(sale.getPercentDiscount()))))
                    .divide(new BigDecimal("100.00"), BigDecimal.ROUND_CEILING)));
            calculatedPrice = calculatedPrice.multiply(discountMultiplier);
        }

        return calculatedPrice.setScale(2, RoundingMode.CEILING);
    }
}
