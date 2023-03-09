package com.example.ekwateurProject.service;

import java.io.IOException;

public interface IPromoCodeService {

    Boolean isCodeValid(String code) throws IOException;
}
