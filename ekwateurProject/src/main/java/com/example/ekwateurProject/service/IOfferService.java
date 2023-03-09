package com.example.ekwateurProject.service;

import com.example.ekwateurProject.model.CompatibleOffersDto;
import com.example.ekwateurProject.model.PromoCodeDto;

import java.io.IOException;

public interface IOfferService {
    CompatibleOffersDto getCompatibleOffers(PromoCodeDto code) throws IOException;
    void createResultFile(CompatibleOffersDto code) throws IOException;
}
