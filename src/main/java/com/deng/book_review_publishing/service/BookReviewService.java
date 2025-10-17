package com.deng.book_review_publishing.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.deng.book_review_publishing.entity.BookReview;

public interface BookReviewService {
    public BookReview findBookReviewById(Long id);
    public Page<BookReview> findBookReviewByFilters(int pageNum, int pageSize, String sortField, String sortDirection, Long bookId, String reviewAuthor, Long reviewAuthorId, String reviewContent, String reviewTitle, Integer views, byte reviewStatus, byte isDeleted, Integer year, Integer month);
    public Long count();
    public Long publishedBookReviewCount();
    public Long deletedBookReviewCount();
    public Boolean existsById(Long id);
    public Boolean modifyStatusBatch(Long[] ids, byte reviewStatus);
    public Boolean modifyIsDeletedViewBatch(Long[] ids, byte isDeleted);
    public Boolean saveBookReview(BookReview bookReview);
    public Boolean updateBookReview(Long id, BookReview bookReview); 
    public List<BookReview> findRecentBookReviewsByStatus(byte reviewStatus, int limit);
}
