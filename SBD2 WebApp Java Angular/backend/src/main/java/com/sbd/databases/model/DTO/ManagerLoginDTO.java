package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerLoginDTO
{
    @NotNull(message = "Can't be empty")
    private String username;
    @NotNull(message = "Can't be empty")
    private String password;
}
