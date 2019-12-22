package com.sbd.databases.model.DTO;

import com.sbd.databases.model.ShopOrder;
import lombok.Data;

@Data
public class ShopOrderDTO
{
    private Integer id;
    private String address;
    private Boolean confirmed;

    public ShopOrderDTO(Integer id, AddressDTO addressDTO)
    {
        this.id = id;
        this.address = addressDTO.toString();
        this.confirmed = true; // TODO: 21.12.2019 Shop order should be confirmed in database
    }

    public ShopOrderDTO(ShopOrder shopOrder)
    {
        this.id = shopOrder.getId();
        this.address = shopOrder.getAddress();
        this.confirmed = true; // TODO: 21.12.2019 Shop order should be confirmed in database
    }
}
