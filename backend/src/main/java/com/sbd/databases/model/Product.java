package com.sbd.databases.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    private String name;
    private BigDecimal ean;
    private BigDecimal price;
    private BigDecimal vat;
    private Integer categoryId;
    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts;
    @OneToMany(mappedBy = "product")
    private List<Sale> sales;
    @OneToOne(mappedBy = "product")
    private WarehouseProduct warehouseProduct;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;
}
