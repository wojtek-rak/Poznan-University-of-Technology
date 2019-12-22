package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ManagerSignupDTO
{
    @NotNull
    @Size(min = 2, max = 255, message = "Manager name should be between 2 and 255 characters long")
    private String name;
    @NotNull
    @Size(min = 2, max = 255, message = "Manager username should be between 2 and 255 characters long")
    private String username;
    @NotNull
    private String password;
}
