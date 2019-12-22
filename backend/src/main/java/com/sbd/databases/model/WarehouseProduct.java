package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "WarehouseProduct")
public class WarehouseProduct
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    private Integer count;
    private Integer warehouseCode;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    @JsonBackReference
    private Product product;
}
