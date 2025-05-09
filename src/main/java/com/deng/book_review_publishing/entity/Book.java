package com.deng.book_review_publishing.entity;

import com.deng.book_review_publishing.entity.enums.Language;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long Id;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "book_author_id")
    private Long[] bookAuthorIds;
    @Column(name = "book_ISBN")
    private String bookISBN;
    @Column(name = "book_ASIN")
    private String bookASIN;
    @Column(name = "book_description")
    private String bookDescription;
    @Column(name = "book_genre_ids")
    private Long[] bookGenreIds;
    @Column(name = "book_price")
    private double bookPrice;
    @Column(name = "book_stock")
    private int bookStock;
    @Column(name = "book_cover_image_url")
    private String coverImageURL;
    @Column(name = "book_format")
    private String format;
    @Column(name = "book_language")
    @Enumerated(EnumType.STRING)
    private Language languageName;
    @Column(name = "book_publisher")
    private String publisher;
    @Column(name = "book_publish_year")
    private int publishYear;
    @Column(name = "book_is_published")
    private byte isPublished;
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }
    public Long[] getBookAuthorIds() {
        return bookAuthorIds; 
    }
    public void setBookAuthorIds(Long[] bookAuthorIds) {
        this.bookAuthorIds = bookAuthorIds; 
    }
    public String getBookISBN() {
        return bookISBN;
    }
    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }
    public String getBookASIN() {
        return bookASIN;
    }
    public void setBookASIN(String bookASIN) {
        this.bookASIN = bookASIN;
    }
    public String getBookDescription() {
        return bookDescription;
    }
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
    public Long[] getBookGenreIds() {
        return bookGenreIds;
    }
    public void setBookGenreIds(Long[] bookGenreIds) {
        this.bookGenreIds = bookGenreIds;
    }
    public double getBookPrice() {
        return bookPrice;
    }
    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }
    public int getBookStock() {
        return bookStock;
    }
    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }
    public String getCoverImageURL() {
        return coverImageURL;
    }
    public void setCoverImageURL(String coverImageURL) {
        this.coverImageURL = coverImageURL == null ? null : coverImageURL.trim();
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format == null? null : format.trim();
    }
    public Language getLanguageName() {
        return languageName;
    }
    public void setLanguageName(Language languageName) {
        this.languageName = languageName;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher == null? null : publisher.trim();
    }
    public int getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    public byte getIsPublished() {
        return isPublished;
    }
    public void setIsPublished(byte isPublished) {
        this.isPublished = isPublished;
    }
       

    
    
}
