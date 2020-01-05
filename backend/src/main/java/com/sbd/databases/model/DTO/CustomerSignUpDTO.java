package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CustomerSignUpDTO
{
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 255)
    private String name;
    @NotNull(message = "Can't be empty")
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
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$")
    private String phone;
    @Null
    private String token;
}
