package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class SupplierDTO
{
    @Null
    private Integer id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    public SupplierDTO(String name)
    {
        this.name = name;
    }

    public SupplierDTO(Supplier supplier)
    {
        this.id = supplier.getId();
        this.name = supplier.getName();
    }
}
