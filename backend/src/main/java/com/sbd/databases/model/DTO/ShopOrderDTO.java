package com.sbd.databases.model.DTO;

import com.sbd.databases.model.ShopOrder;
import lombok.Data;

@Data
public class ShopOrderDTO
{
    private Integer id;
    private String address;
    private Boolean confirmed;

    public ShopOrderDTO(ShopOrder shopOrder)
    {
        this.id = shopOrder.getId();
        this.address = shopOrder.getAddress();
        this.confirmed = shopOrder.getConfirmed();
    }
}
