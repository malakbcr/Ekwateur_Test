package com.example.ekwateurProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
public class CompatibleOffersDto {
    @NotNull
    private String code;
    private double discountValue;
    private String endDate;
    private List<OfferDetailsDto> compatibleOfferList;
}
