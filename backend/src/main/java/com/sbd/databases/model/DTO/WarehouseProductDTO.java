package com.sbd.databases.model.DTO;

import com.sbd.databases.model.WarehouseProduct;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

@Data
public class WarehouseProductDTO
{
    @Null
    private Integer id;
    @NotNull
    @Valid
    private ProductDTO product;
    @NotNull
    @PositiveOrZero
    private Integer count;
    @NotNull
    private Integer warehouseCode;

    public WarehouseProductDTO(WarehouseProduct warehouseProduct)
    {
        this.id = warehouseProduct.getId();
        this.product = new ProductDTO(warehouseProduct.getProduct());
        this.count = warehouseProduct.getCount();
        this.warehouseCode = warehouseProduct.getWarehouseCode();
    }

    public WarehouseProductDTO(@NotNull @Valid ProductDTO product, @NotNull @PositiveOrZero Integer count, @NotNull Integer warehouseCode)
    {
        this.product = product;
        this.count = count;
        this.warehouseCode = warehouseCode;
    }
}
