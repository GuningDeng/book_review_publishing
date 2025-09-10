package com.deng.book_review_publishing.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.deng.book_review_publishing.entity.CountryEntity;
import com.deng.book_review_publishing.entity.enums.Country;
import com.deng.book_review_publishing.repository.CountryEntityRepository;

@SpringBootTest
public class CountryEntityServiceImplTest {
    @Mock
    private CountryEntityRepository countryEntityRepository;

    @InjectMocks
    private CountryEntityServiceImpl countryEntityServiceImpl;


    @Test
    void testCountCountries() {

    }

    @Test
    void testCreateCountryEntity() {

    }

    @Test
    void testDeleteCountryEntity() {

    }

    @Test
    void testExistsByCountryName() {

    }

    @Test
    void testExistsById() {

    }

    @Test
    void testFindAllCountriesPage() {

    }

    @Test
    void testFindAllCountries() {
        // Arrange
        List<CountryEntity> mockCountries = new ArrayList<>();
        CountryEntity country1 = new CountryEntity();
        country1.setId(1L);
        country1.setCountryName(Country.USA);

        CountryEntity country2 = new CountryEntity();
        country2.setId(2L);
        country2.setCountryName(Country.CANADA);
        mockCountries.add(country1);
        mockCountries.add(country2);
        // Mock the repository method
        when(countryEntityRepository.save(country1)).thenReturn(country1);
        when(countryEntityRepository.save(country2)).thenReturn(country2);
        when(countryEntityRepository.findAll()).thenReturn(mockCountries);

        // Act
        List<CountryEntity> actualCountries = countryEntityServiceImpl.findAllCountries();
        // Assert
        assertNotNull(actualCountries);
        assertEquals(mockCountries, actualCountries);
        verify(countryEntityRepository, times(1)).findAll();

    }

    @Test
    void testFindCountryById() {

    }

    @Test
    void testUpdateCountryEntity() {

    }
}
