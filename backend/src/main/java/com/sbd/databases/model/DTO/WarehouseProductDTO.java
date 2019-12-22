package com.sbd.databases.model.DTO;

import com.sbd.databases.model.WarehouseProduct;
import lombok.Data;

@Data
public class WarehouseProductDTO
{
    private Integer id;
    private ProductDTO product;
    private Integer count;
    private Integer warehouseCode;

    public WarehouseProductDTO(WarehouseProduct warehouseProduct)
    {
        this.id = warehouseProduct.getId();
        this.product = new ProductDTO(warehouseProduct.getProduct());
        this.count = warehouseProduct.getCount();
        this.warehouseCode = warehouseProduct.getWarehouseCode();
    }
}
