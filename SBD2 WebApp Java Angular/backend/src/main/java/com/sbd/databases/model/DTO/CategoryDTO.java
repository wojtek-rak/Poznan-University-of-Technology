package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Category;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryDTO
{
    private Integer id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Valid
    private SupplierDTO supplier;

    public CategoryDTO(@NotNull @Size(min = 1, max = 255) String name, SupplierDTO supplier)
    {
        this.name = name;
        this.supplier = supplier;
    }

    public CategoryDTO(Category category)
    {
        this.id = category.getId();
        this.name = category.getName();
        this.supplier = new SupplierDTO(category.getSupplier());
    }
}

