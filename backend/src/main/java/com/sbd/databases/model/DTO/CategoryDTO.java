package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Category;
import lombok.Data;

@Data
public class CategoryDTO
{
    private Integer id;
    private String name;
    private SupplierDTO supplier;

    public CategoryDTO(Category category)
    {
        this.id = category.getId();
        this.name = category.getName();
        this.supplier = new SupplierDTO(category.getSupplier());
    }
}

