package com.sbd.databases.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Cart")
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;
    @Column(name = "Count", nullable = false)
    private Integer count;
    @Column(name = "Confirmed", nullable = false)
    private Boolean confirmed;
    @OneToMany(mappedBy = "cart")
    private List<CartProduct> cartProducts;
}
