package com.deng.book_review_publishing.entity.enums;

public enum Language {
    ENGLISH("English", "EN", "English Language"), 
    CHINESE("Chinese", "CN", "Chinese Language"), 
    JAPANESE("Japanese", "JP", "Japanese Language"), 
    SPANISH("Spanish", "SP", "Spanish Language"), 
    GERMAN("German", "DE", "German Language"), 
    FRENCH("French", "FR", "French Language"), 
    ITALIAN("Italian", "IT", "Italian Language"), 
    RUSSIAN("Russian", "RU", "Russian Language"), 
    ARABIC("Arabic", "AR", "Arabic Language");    
    
    private final String displayName;
    private final String code;
    private final String description;
    
    Language(String displayName, String code, String description) {
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
