package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Customer")
@JsonIgnoreProperties(value = {"productId", "carts", "shopOrders"})
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @Size(min = 2, max = 255, message = "Customer name should be minimum 2 character and maximum 255 character long")
    @NotNull
    @Column(name = "Name", nullable = false)
    private String name;
    @Size(min = 2, max = 255, message = "Address should be minimum 2 character and maximum 255 character long")
    @NotNull
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "Phone")
    private String phone;
    @OneToMany(mappedBy = "customer")
    @JsonBackReference("carts")
    private List<Cart> carts;
    @OneToMany(mappedBy = "customer")
    @JsonBackReference("shopOrders")
    private List<ShopOrder> shopOrders;
}
