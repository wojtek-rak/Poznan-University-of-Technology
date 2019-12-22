package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO
{
    private Integer id;
    private String name;
    private SupplierDTO supplier;

    public CategoryDTO(Integer id, String name, SupplierDTO supplier)
    {
        this.id = id;
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

