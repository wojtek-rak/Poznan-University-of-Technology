package com.sbd.databases.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Customer")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "Phone")
    private String phone;
    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;
    @OneToMany(mappedBy = "customer")
    private List<ShopOrder> shopOrders;
}
