package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Sale;
import lombok.Data;

@Data
public class SaleDTO
{
    private Integer id;
    private Integer percentDiscount;

    public SaleDTO(Sale sale)
    {
        this.id = sale.getId();
        this.percentDiscount = sale.getPercentDiscount();
    }
}
