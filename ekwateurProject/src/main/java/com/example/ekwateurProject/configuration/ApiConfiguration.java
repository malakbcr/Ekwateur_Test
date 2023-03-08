package com.example.ekwateurProject.configuration;

import com.example.ekwateurProject.model.Urls;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ApiConfiguration implements IApiConfiguration{

    /***
     * Retrieve Urls from the config.json to have access to Offers and PromoCode
     * @return { Urls }
     */
    @Override
    public Urls GetUrls() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Urls> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/config.json");

        return mapper.readValue(inputStream,typeReference);
    }
}
