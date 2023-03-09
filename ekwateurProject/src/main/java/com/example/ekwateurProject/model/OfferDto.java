package com.example.ekwateurProject.model;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class OfferDto {
    private OfferType offerType;
    private String offerName;
    private String offerDescription;
    @Nullable
    private Set<String> validPromoCodeList;
    public OfferDto(OfferType offerType, String offerName, String offerDescription) {
        this.offerType = offerType;
        this.offerName = offerName;
        this.offerDescription = offerDescription;
        this.validPromoCodeList = new HashSet<>();
    }
}