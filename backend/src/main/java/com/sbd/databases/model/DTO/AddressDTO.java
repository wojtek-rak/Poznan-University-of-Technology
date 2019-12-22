package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddressDTO
{
    @NotNull(message = "Can't be empty")
    private String street;
    @NotNull(message = "Can't be empty")
    @Pattern(regexp = "[0-9]+[a-zA-Z/]?[0-9]*")
    private String homeNumber;
    @NotNull(message = "Can't be empty")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postcode;
    @NotNull(message = "Can't be empty")
    @Pattern(regexp = "[A-Z][a-zA-Z ]*[a-z]")
    private String city;
    @NotNull(message = "Can't be empty")
    @Email
    private String email;

    @Override
    public String toString()
    {
        return street
                + " "
                + homeNumber
                + ", "
                + postcode
                + " "
                + city
                + ", "
                + email;
    }
}
