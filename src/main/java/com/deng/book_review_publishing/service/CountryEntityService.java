package com.deng.book_review_publishing.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.deng.book_review_publishing.entity.CountryEntity;

public interface CountryEntityService {
    /**
     * Finds a country entity by its ID.
     *
     * @param countryId the ID of the country entity to find
     * @return the found country entity, or null if not found
     */
    CountryEntity findCountryById(Long countryId);

    /**
     * Finds all country entities with pagination and sorting.
     *
     * @param pageNum the page number to retrieve
     * @param pageSize the number of records per page
     * @param sortField the field to sort by
     * @param sortDirection the direction of sorting (asc/desc)
     * @return a paginated list of country entities
     */
    Page<CountryEntity> findAllCountriesPage(int pageNum, int pageSize, String sortField, String sortDirection);

    /**
     * Checks if a country entity exists by its ID.
     *
     * @param countryId the ID of the country entity to check
     * @return true if the country entity exists, false otherwise
     */
    Boolean existsById(Long countryId);

    /**
     * Finds all country entities without pagination.
     *
     * @return a list of all country entities
     */
    List<CountryEntity> findAllCountries();

    /**
     * Creates a new country entity.
     *
     * @param countryEntity the country entity to create
     * @return the created country entity
     */
    CountryEntity createCountryEntity(CountryEntity countryEntity);
    /**
     * Updates an existing country entity.
     *
     * @param countryId the ID of the country entity to update
     * @param countryEntity the updated country entity data
     * @return the updated country entity
     */
    CountryEntity updateCountryEntity(Long countryId, CountryEntity countryEntity);
    /**
     * Deletes a country entity by its ID.
     *
     * @param countryId the ID of the country entity to delete
     * @return true if the deletion was successful, false otherwise
     */
    Boolean deleteCountryEntity(Long countryId);
    /**
     * Counts the total number of country entities.
     *
     * @return the total count of country entities
     */
    Long countCountries();
    /**
     * Checks if a country entity exists by its name.
     *
     * @param countryName the name of the country entity to check
     * @return true if the country entity exists, false otherwise
     */
    Boolean existsByCountryName(String countryName);
}
