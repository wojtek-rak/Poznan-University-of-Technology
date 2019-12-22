package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerLoginDTO
{
    @NotNull
    private String username;
    @NotNull
    private String password;
}
