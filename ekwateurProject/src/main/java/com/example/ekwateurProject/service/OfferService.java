package com.example.ekwateurProject.service;

import com.example.ekwateurProject.configuration.ApiConfiguration;
import com.example.ekwateurProject.configuration.ApiConnection;
import com.example.ekwateurProject.exception.IncompatibleOfferException;
import com.example.ekwateurProject.exception.InvalidCodeException;
import com.example.ekwateurProject.model.CompatibleOffersDto;
import com.example.ekwateurProject.model.OfferDetailsDto;
import com.example.ekwateurProject.model.OfferDto;
import com.example.ekwateurProject.model.PromoCodeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OfferService implements IOfferService {

    @Autowired
    private ApiConfiguration apiConfiguration;

    @Autowired
    private ApiConnection connection;

    private PromoCodeService codePromoService;

    @Autowired
    public OfferService(PromoCodeService codePromoService) {
        this.codePromoService = codePromoService;
    }


    /***
     * Retrieve the list of all offers
     * @return { List<OfferDto> }
     */
    public List<OfferDto> getAllOffers() throws IOException {
        //Retrieve Offer's URL
        URL url = new URL(apiConfiguration.GetUrls().getOffers());
        List<OfferDto> offers = Collections.emptyList();
        try {
            offers = new ObjectMapper().readValue(connection.OpenHttpConnection(String.valueOf(url)), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return offers;
    }


    /***
     * extract the offer compatible with a specific promoCode
     * @param {PromoCodeDto} promoCode
     * @return {CompatibleOffersDto} compatibleOffersDto
     */
    @Override
    public CompatibleOffersDto getCompatibleOffers(PromoCodeDto promoCode) throws IOException {
        CompatibleOffersDto compatibleOffersDto = new CompatibleOffersDto();
        List<OfferDto> compatibleOffers;
        if (this.codePromoService.isCodeValid(promoCode.getCode())) {
            compatibleOffers = getAllOffers().stream()
                    .filter(x -> x.getValidPromoCodeList()
                            .contains(promoCode.getCode())).toList();
            if (!compatibleOffers.isEmpty()) {
                for (OfferDto ofr : compatibleOffers) {
                    compatibleOffersDto.setCode(promoCode.getCode());
                    compatibleOffersDto.setEndDate(promoCode.getEndDate());
                    compatibleOffersDto.setDiscountValue(promoCode.getDiscountValue());
                    compatibleOffersDto.getCompatibleOfferList().add(new OfferDetailsDto(ofr.getOfferType(), ofr.getOfferName()));
                }
            }else {
                throw new IncompatibleOfferException("No compatible offer found for this code :" + promoCode.getCode());
            }
        }else {
            throw new InvalidCodeException("Expired or not found promoCode for this code :" + promoCode.getCode());
        }
        return compatibleOffersDto;
    }


    @Override
    public void createResultFile(CompatibleOffersDto offer) {
        ObjectMapper mapper = new ObjectMapper();
        File resultFile = new File("CompatibleOffers.json");
        try {
            mapper.writeValue(resultFile, offer);
            log.info("CompatibleOffers.JSON created");
        } catch (IOException e) {
            log.info("Could not create file");
            throw new RuntimeException(e);
        }
    }
}
