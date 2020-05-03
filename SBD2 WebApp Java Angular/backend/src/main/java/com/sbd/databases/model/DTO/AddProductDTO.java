package com.sbd.databases.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AddProductDTO
{
    @NotNull(message = "Can't be empty")
    @Positive(message = "Must be positive.")
    private Integer count;
}
