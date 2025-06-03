package com.deng.book_review_publishing.utils;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

public class ValidateUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    /**
     * Validates pagination parameters
     * @param pageNum page number
     * @param pageSize page size
     * @throws IllegalArgumentException if parameters are invalid
     */
    public static void validatePaginationParams(int pageNum, int pageSize) {
        if (pageNum < 0) {
            logger.error("Invalid page number: {}", pageNum);
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (pageSize <= 0) {
            logger.error("Invalid page size: {}", pageSize);
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
    }

    /**
     * Validates and normalizes page number with a default value
     * @param pageNum page number to validate
     * @param defaultValue default value to use if pageNum is invalid
     * @return normalized page number
     */
    public static int validatePaginationParamPageNum(int pageNum, int defaultValue) {
        if (pageNum < 0) {
            logger.warn("Invalid page number: {}. Using default value: {}", pageNum, defaultValue);
            return defaultValue;
        }
        return pageNum;
    }

    /**
     * Validates and normalizes page size with configurable bounds
     * @param pageSize page size to validate
     * @param defaultValue default value to use if pageSize is invalid
     * @param maxSize maximum allowed page size
     * @return normalized page size
     */
    public static int validatePaginationParamPageSize(int pageSize, int defaultValue, int maxSize) {
        if (pageSize <= 0) {
            logger.warn("Invalid page size: {}. Using default value: {}", pageSize, defaultValue);
            return defaultValue;
        }
        if (pageSize > maxSize) {
            logger.warn("Page size {} exceeds maximum limit {}. Using maximum value.", pageSize, maxSize);
            return maxSize;
        }
        return pageSize;
    }

    /**
     * Validates and normalizes sort field
     * @param sortField field to sort by
     * @param validFields list of valid sort fields
     * @param defaultField default sort field
     * @return normalized sort field
     */
    public static String validateAndNormalizeSortField(String sortField, String[] validFields, String defaultField) {
        if (sortField == null || sortField.trim().isEmpty()) {
            logger.debug("Sort field is null or empty, defaulting to '{}'", defaultField);
            return defaultField;
        }
        
        if (!Arrays.asList(validFields).contains(sortField)) {
            logger.debug("Invalid sort field: {}, defaulting to '{}'", sortField, defaultField);
            return defaultField;
        }
        
        return sortField;
    }

    /**
     * Validates and normalizes sort direction
     * @param sortDirection direction to sort
     * @return normalized sort direction
     */
    public static String validateAndNormalizeSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.trim().isEmpty()) {
            logger.debug("Sort direction is null or empty, defaulting to 'asc'");
            return "asc";
        }
        
        String normalizedDirection = sortDirection.trim().toLowerCase();
        if (!normalizedDirection.equals("asc") && !normalizedDirection.equals("desc")) {
            logger.debug("Invalid sort direction: {}, defaulting to 'asc'", sortDirection);
            return "asc";
        }
        
        return normalizedDirection;
    }

    /**
     * Creates a Sort object based on field and direction
     * @param field field to sort by
     * @param direction sort direction
     * @return Sort object
     */
    public static Sort createSort(String field, String direction) {
        return direction.equals("asc") 
            ? Sort.by(field).ascending()
            : Sort.by(field).descending();
    }
}