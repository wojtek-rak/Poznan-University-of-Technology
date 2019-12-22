package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Supplier;
import lombok.Data;

@Data
public class SupplierDTO
{
    private Integer id;
    private String name;

    public SupplierDTO(Supplier supplier)
    {
        this.id = supplier.getId();
        this.name = supplier.getName();
    }
}
