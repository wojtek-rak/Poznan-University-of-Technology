package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class CustomerSignUpDTO
{
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 255)
    private String name;
    private AddressDTO address;
    private String phone;
    @Null
    private String token;

}
