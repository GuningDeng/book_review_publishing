package com.deng.book_review_publishing.service;

import org.springframework.data.domain.Page;

import com.deng.book_review_publishing.entity.Book;

public interface BookService {
    public Page<Book> findBooksByFilters(int pageNum, int pageSize, String sortField, String sortDirection, String bookName, 
        String bookISBN, String bookASIN, String bookDescription, String languageName, String bookPublisher, Integer publishYear, byte isPublished);
    public Book findBookById(Long bookId);
    public Page<Book> findAllBooksByIsPublishedStatus(int pageNum, int pageSize, String sortField, String sortDirection, byte isPublished);
    public Boolean existsById(Long bookId);
    public Long count();


}
