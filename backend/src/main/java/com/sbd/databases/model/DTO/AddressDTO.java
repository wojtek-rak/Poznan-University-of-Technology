package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddressDTO
{
    @NotNull
    private String street;
    @Pattern(regexp = "[0-9]+[a-zA-Z/][0-9]*")
    private String homeNumber;
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postcode;
    @Pattern(regexp = "[A-Z][a-zA-Z ]*[a-z]")
    private String city;

    @Override
    public String toString()
    {
        return street
                + " "
                + homeNumber
                + ", "
                + postcode
                + " "
                + city;
    }
}
