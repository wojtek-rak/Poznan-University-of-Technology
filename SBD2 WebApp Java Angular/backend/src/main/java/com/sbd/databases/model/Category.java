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
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "Name", nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierId", nullable = false)
    @JsonBackReference
    private Supplier supplier;
    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;
}
