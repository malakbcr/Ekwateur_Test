package com.example.ekwateurProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class OfferDetailsDto {
    private OfferType offerType;
    private String offerName;
}
