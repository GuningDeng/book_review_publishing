package com.deng.book_review_publishing.service.impl;

import java.util.List;
import java.util.Optional;

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
        try {
            if (countryId <= 0) {
                log.error("Country ID is less than or equal to 0");
                return null;
            }
            CountryEntity countryEntity = countryEntityRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));
            log.info("Country found: {}", countryEntity);
            return countryEntity;
        } catch (Exception e) {
            log.error("Error fetching country by ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Page<CountryEntity> findAllCountriesPage(int pageNum, int pageSize, String sortField, String sortDirection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllCountries'");
    }

    @Override
    public Boolean existsById(Long countryId) {
        try {
            if (countryId <= 0) {
                log.error("Country ID is less than or equal to 0");
                return false;
            }
            return countryEntityRepository.existsById(countryId);
        } catch (Exception e) {
            log.error("Error checking if country exists by ID: {}", e.getMessage());
            return false;
        }
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
        try {
            if (countryId <= 0) {
                log.error("Country ID is less than or equal to 0");
                return false;
            }
            if (!existsById(countryId)) {
                log.error("Country not found with ID: {}", countryId);
                return false;
            }
            countryEntityRepository.deleteById(countryId);
            Boolean isDeleted = !existsById(countryId);
            if (isDeleted) {
                log.info("Country deleted: {}", countryId);
            } else {
                log.error("Country not deleted with ID: {}", countryId);
            }
            return isDeleted;
        } catch (Exception e) {
            log.error("Error deleting country by ID: {}", e.getMessage());
            return false;
        }
    }


    @Override
    public Long countCountries() {
        try {
            log.info("Counting total number of countries in the database");
            Long count = countryEntityRepository.count();
            log.info("Total countries count: {}", count);
            return count;
        } catch (Exception e) {
            log.error("Error counting countries: {}", e.getMessage());
            throw new RuntimeException("Error counting countries", e);
        }
    }


    @Override
    public Boolean existsByCountryName(String countryName) {
        try {
            if (countryName == null || countryName.trim().isEmpty()) {
                log.error("Country name is null or empty");
                return false;
            }
            Optional<CountryEntity> countryEntity = countryEntityRepository.findByCountryName(countryName);
            return countryEntity.isPresent();
        } catch (Exception e) {
            log.error("Error checking if country exists by name: {}", e.getMessage());
            return false;
        }
    }
    

    
}
