package com.example.ekwateurProject;

import com.example.ekwateurProject.exception.InvalidCodeException;
import com.example.ekwateurProject.model.CompatibleOffersDto;
import com.example.ekwateurProject.model.PromoCodeDto;
import com.example.ekwateurProject.service.OfferService;
import com.example.ekwateurProject.service.PromoCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class EkwateurProjectApplication implements CommandLineRunner {
	@Autowired
	private PromoCodeService promoCodeService;
	@Autowired
	private OfferService offerService;

	public static void main(String[] args) {
		SpringApplication.run(EkwateurProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			PromoCodeDto promoCode = promoCodeService.getPromoCodeByCode(args[0]);
			if (promoCodeService.isCodeValid(args[0])) {
				CompatibleOffersDto compatibleOffers = offerService.getCompatibleOffers(promoCode);
				if (compatibleOffers != null) {
					log.info("COMPATIBLE_OFFERS EXIST!");
					offerService.createResultFile(compatibleOffers);
				} else {
					log.info("NOT COMPATIBLE_OFFERS FOUND!");
				}
			}
		} catch (InvalidCodeException exception){
			log.error(exception.getMessage());
		}
		catch (IndexOutOfBoundsException e){
			log.warn("FORGET TO ENTER A PROMO CODE!");
		}
	}
}
