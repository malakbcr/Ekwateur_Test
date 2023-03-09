package com.example.ekwateurProject.service;

import com.example.ekwateurProject.exception.IncompatibleOfferException;
import com.example.ekwateurProject.exception.InvalidCodeException;
import com.example.ekwateurProject.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OfferServiceTest {

    PromoCodeService servicePromoMock = Mockito.mock(PromoCodeService.class);
    OfferService offerServiceMock = new OfferService(servicePromoMock);
    static List<OfferDto> mockOffers;
    static List<PromoCodeDto> mockPromoCodes;
    OfferService serviceSpy = Mockito.spy(offerServiceMock);

    @BeforeAll
    static void setUp() {
        mockOffers = List.of(
                new OfferDto(OfferType.GAS, "gas_offer", "offer of gas"),
                new OfferDto(OfferType.WOOD, "wood_offer", "offer of wood"),
                new OfferDto(OfferType.ELECTRICITY, "electricity_offer", "offer of electricity")
        );
        mockPromoCodes = List.of(
                new PromoCodeDto("first_code", 1.0, "2024-01-01"),
                new PromoCodeDto("second_code", 2.0, "2024-01-01"),
                new PromoCodeDto("third_code", 3.0, "2024-01-01"),
                new PromoCodeDto("fourth_code", 4.0, "2024-01-01")
        );
        mockOffers.get(0).getValidPromoCodeList().add(mockPromoCodes.get(0).getCode());
        mockOffers.get(0).getValidPromoCodeList().add(mockPromoCodes.get(1).getCode());
        mockOffers.get(0).getValidPromoCodeList().add(mockPromoCodes.get(3).getCode());

        mockOffers.get(1).getValidPromoCodeList().add(mockPromoCodes.get(1).getCode());
        mockOffers.get(1).getValidPromoCodeList().add(mockPromoCodes.get(3).getCode());

        mockOffers.get(2).getValidPromoCodeList().add(mockPromoCodes.get(3).getCode());


    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(mockPromoCodes.get(0), List.of(mockOffers.get(0))),
                Arguments.of(mockPromoCodes.get(1), List.of(mockOffers.get(0), mockOffers.get(1))),
                Arguments.of(mockPromoCodes.get(3), List.of(mockOffers.get(0), mockOffers.get(1), mockOffers.get(2)))
        );
    }


    static Stream<Arguments> NoCompatibleOfferTest_generateData() {
        return Stream.of(
                Arguments.of(mockPromoCodes.get(2))
        );
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void CompatibleOffersForValidCodeTest(PromoCodeDto promoCode, List<OfferDto> offers) throws IOException {
        CompatibleOffersDto expectedOffers = new CompatibleOffersDto();
        for (OfferDto offer : offers) {
            expectedOffers.setCode(promoCode.getCode());
            expectedOffers.setEndDate(promoCode.getEndDate());
            expectedOffers.setDiscountValue(promoCode.getDiscountValue());
            expectedOffers.getCompatibleOfferList().add(new OfferDetailsDto(offer.getOfferType(), offer.getOfferName()));
        }
        Mockito.doReturn(mockOffers).when(serviceSpy).getAllOffers();
        Mockito.doReturn(mockPromoCodes).when(servicePromoMock).getAllPromoCodes();
        Mockito.doReturn(true).when(servicePromoMock).isCodeValid(promoCode.getCode());
        CompatibleOffersDto response = serviceSpy.getCompatibleOffers(promoCode);
        assertEquals(expectedOffers, response);
    }
    @ParameterizedTest
    @MethodSource("NoCompatibleOfferTest_generateData")
    void NoCompatibleOfferForValidCodeTest(PromoCodeDto promoCode) throws IOException {
        Mockito.doReturn(mockOffers).when(serviceSpy).getAllOffers();
        Mockito.doReturn(mockPromoCodes).when(servicePromoMock).getAllPromoCodes();
        Mockito.doReturn(true).when(servicePromoMock).isCodeValid(promoCode.getCode());
        Assertions.assertThrows(IncompatibleOfferException.class , () -> {
             serviceSpy.getCompatibleOffers(promoCode);
        });
    }
    @ParameterizedTest
    @MethodSource("NoCompatibleOfferTest_generateData")
    void NoCompatibleOfferForNoValidCodeTest(PromoCodeDto promoCode) throws IOException {
        Mockito.doReturn(mockOffers).when(serviceSpy).getAllOffers();
        Mockito.doReturn(mockPromoCodes).when(servicePromoMock).getAllPromoCodes();
        Mockito.doReturn(false).when(servicePromoMock).isCodeValid(promoCode.getCode());
        Assertions.assertThrows(InvalidCodeException.class , () -> {
             serviceSpy.getCompatibleOffers(promoCode);
        });
    }
    }
