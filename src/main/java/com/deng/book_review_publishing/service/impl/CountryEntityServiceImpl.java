package com.deng.book_review_publishing.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.deng.book_review_publishing.entity.CountryEntity;
import com.deng.book_review_publishing.repository.CountryEntityRepository;
import com.deng.book_review_publishing.service.CountryEntityService;

@Service
public class CountryEntityServiceImpl implements CountryEntityService {
    // Implementation of methods from CountryEntityService will go here
    // This class will interact with CountryEntityRepository to perform CRUD operations on CountryEntity
    // It will also handle pagination and sorting for country entities
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final CountryEntityRepository countryEntityRepository;
    public CountryEntityServiceImpl(CountryEntityRepository countryEntityRepository) {
        this.countryEntityRepository = countryEntityRepository;
    }


    @Override
    public CountryEntity findCountryById(Long countryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findCountryById'");
    }

    @Override
    public Page<CountryEntity> findAllCountriesPage(int pageNum, int pageSize, String sortField, String sortDirection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllCountries'");
    }

    @Override
    public Boolean existsById(Long countryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public List<CountryEntity> findAllCountries() {
        try {
            log.info("Fetching all countries from the database");
            List<CountryEntity> countries = countryEntityRepository.findAll();
            log.info("Total countries found: {}", countries.size());
            return countries;
        } catch (Exception e) {
            log.error("Error fetching countries: {}", e.getMessage());
            throw new RuntimeException("Error fetching countries", e);
        }
    }


    @Override
    public CountryEntity createCountryEntity(CountryEntity countryEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCountryEntity'");
    }


    @Override
    public CountryEntity updateCountryEntity(Long countryId, CountryEntity countryEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCountryEntity'");
    }


    @Override
    public Boolean deleteCountryEntity(Long countryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCountryEntity'");
    }


    @Override
    public Long countCountries() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countCountries'");
    }


    @Override
    public Boolean existsByCountryName(String countryName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByCountryName'");
    }
    

    
}
