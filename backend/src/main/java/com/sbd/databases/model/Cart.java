package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private Customer customer;
    @Column(name = "Confirmed", nullable = false)
    private Boolean confirmed;
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartProduct> cartProducts;
    @OneToOne(mappedBy = "cart")
    @JsonManagedReference
    private ShopOrder shopOrder;
}
