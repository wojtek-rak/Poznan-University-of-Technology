package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "ean", nullable = false, unique = true)
    private BigDecimal ean;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "vat", nullable = false)
    private BigDecimal vat;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartProduct> cartProducts;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Sale> sales;
    @OneToOne(mappedBy = "product")
    @JsonManagedReference
    private WarehouseProduct warehouseProduct;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    @JsonBackReference
    private Category category;
}
