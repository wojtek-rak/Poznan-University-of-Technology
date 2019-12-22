package com.sbd.databases.model.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimpleProductDTO
{
    private Integer id;
    private String name;
    private BigDecimal ean;
    private BigDecimal price;
    private BigDecimal vat;
    private String categoryName;
}
