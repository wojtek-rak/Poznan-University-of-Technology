package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerLoginDTO
{
    @Size(min = 2, max = 255)
    @NotNull(message = "Can't be empty")
    private String name;
}
