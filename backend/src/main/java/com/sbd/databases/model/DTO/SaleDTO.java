package com.sbd.databases.model.DTO;

import com.sbd.databases.model.Sale;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@Data
public class SaleDTO
{
    @Null
    private Integer id;
    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer percentDiscount;

    public SaleDTO(@NotNull @Min(value = 0) @Max(value = 100) Integer percentDiscount)
    {
        this.percentDiscount = percentDiscount;
    }

    public SaleDTO(Sale sale)
    {
        this.id = sale.getId();
        this.percentDiscount = sale.getPercentDiscount();
    }
}
