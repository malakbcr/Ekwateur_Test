package com.example.ekwateurProject.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class OfferDto {
    private OfferType offerType;
    private String offerName;
    private String offerDescription;
    @Nullable
    private Set<String> validPromoCodeList;
}