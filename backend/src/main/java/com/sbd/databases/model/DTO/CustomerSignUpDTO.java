package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CustomerSignUpDTO
{
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 255)
    private String name;
    @NotNull(message = "Can't be empty")
    @Valid
    private AddressDTO address;
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$")
    private String phone;
    @Null
    private String token;
}
