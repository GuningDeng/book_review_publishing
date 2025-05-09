package com.deng.book_review_publishing.entity.enums;

/**
 * Enum representing different book genres.
 * Used for categorizing books in the system.
 */
public enum Genre {
    FICTION("Fiction", "Imaginative stories and narratives that are not based on real events"),
    NON_FICTION("Non-Fiction", "Factual writing based on real events, people, and information"),
    MYSTERY("Mystery", "Stories involving crime, suspense, and detective work"),
    SCIENCE_FICTION("Science Fiction", "Stories based on imagined future scientific or technological advances"),
    FANTASY("Fantasy", "Stories featuring magical and supernatural elements"),
    ROMANCE("Romance", "Stories focusing on romantic relationships and love"),
    THRILLER("Thriller", "Fast-paced, suspenseful stories designed to excite and entertain"),
    HORROR("Horror", "Stories intended to frighten, scare, or startle readers"),
    BIOGRAPHY("Biography", "Detailed descriptions of a person's life and achievements"),
    HISTORY("History", "Books about past events, people, and periods"),
    POETRY("Poetry", "Literary work in which special intensity is given to expression"),
    DRAMA("Drama", "Works intended for theatrical performance or dramatic composition"),
    CHILDREN("Children's Books", "Literature specifically written for young readers"),
    YOUNG_ADULT("Young Adult", "Literature targeted at teenagers and young adults"),
    SELF_HELP("Self-Help", "Books focused on personal development and improvement"),
    BUSINESS("Business", "Books about commerce, management, and entrepreneurship"),
    TECHNOLOGY("Technology", "Books about computers, software, and technological advances"),
    EDUCATION("Education", "Books focused on learning and academic subjects"),
    TRAVEL("Travel", "Books about places, cultures, and travel experiences"),
    COOKING("Cooking", "Books containing recipes and culinary techniques");

    private final String displayName;
    private final String description;

    Genre(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}