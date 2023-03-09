package com.example.ekwateurProject.service;

import com.example.ekwateurProject.configuration.ApiConfiguration;
import com.example.ekwateurProject.configuration.ApiConnection;
import com.example.ekwateurProject.model.PromoCodeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PromoCodeService implements IPromoCodeService {
    @Autowired
    private ApiConfiguration apiConfiguration;
    @Autowired
    private ApiConnection connection;

    public List<PromoCodeDto> getAllPromoCodes() throws IOException {
        URL url = new URL(apiConfiguration.GetUrls().getCodes());
        List<PromoCodeDto> promoCodes = Collections.emptyList();
        try {
            promoCodes = new ObjectMapper().readValue(connection.OpenHttpConnection(String.valueOf(url)), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promoCodes;
    }

    /***
     *  Retrieve PromoCode by code
     * @param   {String} code
     * @return  {PromoCodeDto} promoCodeDto
     */
    public PromoCodeDto getPromoCodeByCode(String code) throws IOException {
        Optional<PromoCodeDto> pCode = this.getAllPromoCodes().stream().filter(
                x -> x.getCode().equals(code)).findAny();
        return pCode.orElse(null);
    }


    /***
     * check if the promoCode is valid By LocalDate (Not Expired)
     * @param { PromoCodeDto } promoCode
     * @return { Boolean }
     */
    private Boolean isValidByDate(PromoCodeDto promoCode) {
        boolean valid = false;
        DateTimeFormatter dateModel = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate promCode_endDate = LocalDate.parse(promoCode.getEndDate(), dateModel);

        if (promCode_endDate.isAfter(LocalDate.now())) {
            valid = true;
            log.info("Valid Code Promotion! ");
        } else {
            log.warn("Expired Code Promotion! ");
        }
        return valid;
    }

    /***
     * check if the promoCode is valid By LocalDate && check if it exists or not
     * @param { String } code
     * @return { Boolean }
     */
    @Override
    public Boolean isCodeValid(String code) throws IOException {
        PromoCodeDto promoCode =  getPromoCodeByCode(code);
        return getAllPromoCodes().contains(promoCode) && isValidByDate(promoCode);
    }
}
