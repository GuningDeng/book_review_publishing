package com.deng.book_review_publishing.entity.enums;

public enum Country {
    CHINA("China", "CN", "China"),
    USA("United States", "US", "United States of America"),
    JAPAN("Japan", "JP", "Japan"),
    UK("United Kingdom", "UK", "United Kingdom of Great Britain and Northern Ireland"),
    GERMANY("Germany", "DE", "Germany"),
    FRANCE("France", "FR", "France"),
    ITALY("Italy", "IT", "Italy"),
    SPAIN("Spain", "ES", "Spain"),
    RUSSIA("Russia", "RU", "Russia"),
    CANADA("Canada", "CA", "Canada"),
    BRAZIL("Brazil", "BR", "Brazil"),
    AUSTRALIA("Australia", "AU", "Australia");

    private final String displayName;
    private final String code;
    private final String description;

    Country(String displayName, String code, String description) {
        this.displayName = displayName;
        this.code = code;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName; 
    }
    public String getCode() {
        return code; 
    }
    public String getDescription() {
        return description; 
    }
}
