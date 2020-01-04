package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Manager")
public class Manager
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "Username", nullable = false)
    private String username;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column(name = "Token")
    private String token;
    @OneToMany(mappedBy = "manager")
    @JsonManagedReference
    private List<ShopOrder> shopOrders;
}
