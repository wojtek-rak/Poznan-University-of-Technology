package com.sbd.databases.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CalculatedPriceDTO
{
    @Digits(integer = 10, fraction = 2)
    private BigDecimal calculatedPrice;
}
