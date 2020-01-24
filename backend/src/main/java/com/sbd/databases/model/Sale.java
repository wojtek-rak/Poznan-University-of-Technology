package com.sbd.databases.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sale
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", insertable = false, updatable = false)
    private Integer id;
    @Column(name = "percentDiscount", nullable = false)
    private Integer percentDiscount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    @JsonBackReference
    private Product product;

    public Sale(Integer percentDiscount)
    {
        this.percentDiscount = percentDiscount;
    }
}
