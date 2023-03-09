package com.example.ekwateurProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompatibleOffersDto {
    private String code;
    private double discountValue;
    private String endDate;
    private List<OfferDetailsDto> compatibleOfferList = new ArrayList<>();
}
