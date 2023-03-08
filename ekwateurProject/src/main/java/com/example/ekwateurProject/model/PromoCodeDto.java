package com.example.ekwateurProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class PromoCodeDto {
    @NotNull
    private String code;
    private double discountValue;
    private String endDate;
}