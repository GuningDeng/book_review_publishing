package com.deng.book_review_publishing.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_book_review")
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_review_id")
    private Long id;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "book_review_author")
    private String reviewAuthor;
    @Column(name = "book_review_author_id")
    private Long reviewAuthorId;
    @Column(name = "book_review_content")
    private String reviewContent;
    @Column(name = "book_review_title")
    private String reviewTitle;
    @Column(name = "book_review_views")
    private Integer views;
    @Column(name = "book_review_status")
    private byte reviewStatus;
    @Column(name = "book_review_is_deleted")
    private byte isDeleted;
    @Column(name = "book_review_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Brussels")
    private Date createTime;
    @Column(name = "book_review_update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Brussels")
    private Date updatedTime;
    @Column(name = "book_review_update_by_id")
    private Long updatedBy;
       


    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Long getBookId() {
        return bookId;
    }



    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }



    public String getReviewAuthor() {
        return reviewAuthor;
    }



    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor == null? null : reviewAuthor.trim();
    }



    public Long getReviewAuthorId() {
        return reviewAuthorId;
    }



    public void setReviewAuthorId(Long reviewAuthorId) {
        this.reviewAuthorId = reviewAuthorId;
    }



    public String getReviewContent() {
        return reviewContent;
    }



    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent == null? null : reviewContent.trim();
    }



    public String getReviewTitle() {
        return reviewTitle;
    }



    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle == null? null : reviewTitle.trim();
    }



    public Integer getViews() {
        return views;
    }



    public void setViews(Integer views) {
        this.views = views;
    }



    public byte getReviewStatus() {
        return reviewStatus;
    }



    public void setReviewStatus(byte reviewStatus) {
        this.reviewStatus = reviewStatus;
    }



    public byte getIsDeleted() {
        return isDeleted;
    }



    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }



    public Date getCreateTime() {
        return createTime;
    }



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }



    public Long getUpdatedBy() {
        return updatedBy;
    }



    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }



    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }




    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BookReview [id=");
        builder.append(id);
        builder.append(", bookId=");
        builder.append(bookId);
        builder.append(", reviewAutho=");
        builder.append(reviewAuthor);
        builder.append(", reviewAuthorId=");
        builder.append(reviewAuthorId);
        builder.append(", reviewContent=");
        builder.append(reviewContent);
        builder.append(", reviewTitle=");
        builder.append(reviewTitle);
        builder.append(", reviewStatus=");
        builder.append(reviewStatus);
        builder.append(", isDeleted=");
        builder.append(isDeleted);
        builder.append(", createTime=");
        builder.append(createTime);
        builder.append(", updatedTime=");
        builder.append(updatedTime);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append("]");
        return builder.toString();
    }
    
}
