package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ManagerSignUpDTO
{
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 characters long.")
    private String name;
    @NotNull(message = "Can't be empty")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 characters long.")
    private String username;
    @NotNull(message = "Can't be empty")
    @Size(min = 8, max = 20, message = "Password should be between 2 and 20 characters long.")
    private String password;
}
