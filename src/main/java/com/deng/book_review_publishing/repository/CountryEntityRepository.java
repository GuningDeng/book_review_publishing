package com.deng.book_review_publishing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deng.book_review_publishing.entity.CountryEntity;

@Repository
public interface CountryEntityRepository extends JpaRepository<CountryEntity, Long> {
    
    // Custom query methods can be added here if needed
    // For example, to find by country name:
    // Optional<CountryEntity> findByCountryName(Country countryName);
    
    // Or to find by country code:
    // Optional<CountryEntity> findByCountryCode(String countryCode);
    
}
