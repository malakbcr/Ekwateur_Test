package com.example.ekwateurProject;

import com.example.ekwateurProject.model.PromoCodeDto;
import com.example.ekwateurProject.service.PromoCodeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PromoCodeServiceTest {
    PromoCodeService promoCodeService = new PromoCodeService();
    static List<PromoCodeDto> mockPromoCodes;
    PromoCodeService promoCodeServiceSpy = Mockito.spy(promoCodeService);

    @BeforeAll
    static void setUp() {
        mockPromoCodes = List.of(
                new PromoCodeDto("valid_test", 1.0, "2024-01-01"),
                new PromoCodeDto("invalid_test", 1.0, "2022-01-01")
        );
    }
    @ParameterizedTest
    @CsvSource({ "valid_test, true", "invalid_test, false"})
    void shouldReturnIsValidCode(String code,Boolean expected) throws IOException {
        Mockito.doReturn(mockPromoCodes).when(promoCodeServiceSpy).getAllPromoCodes();
        Boolean response = promoCodeServiceSpy.isCodeValid(code);
        assertEquals(expected, response);
    }
}