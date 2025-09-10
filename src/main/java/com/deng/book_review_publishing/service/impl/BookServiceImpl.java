package com.deng.book_review_publishing.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.repository.BookRepository;
import com.deng.book_review_publishing.service.BookService;
import com.deng.book_review_publishing.utils.ValidateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> findBooksByFilters(int pageNum, int pageSize, String sortField, String sortDirection, String bookName,
            String bookISBN, String bookASIN, String bookDescription, String languageName, String bookPublisher, Integer publishYear, byte isPublished) {
                
        try {
            // validate parameters
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookName", "bookISBN", "bookASIN", "bookDescription", "languageName", "bookPublisher", "publishYear", "isPublished"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);

            if (isPublished!= 0 && isPublished!= 1) {
                logger.error("Invalid isPublished value");
                isPublished = 0;
            }
            
            // Create sort object with case-insensitive direction comparison
            Pageable pageable = PageRequest.of(pageNum, pageSize, ValidateUtil.createSort(sortField, sortDirection));
            Page<Book> books = bookRepository.findBooksByFilters(bookName, bookISBN, bookASIN, 
                    bookDescription, languageName, bookPublisher, publishYear, isPublished, pageable);
            logger.info("Books found: {}", books);
            return books;
        }
        catch (Exception e) {
            logger.error("Error in findAllBooks: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Book findBookById(Long bookId) {
        try {
            if (bookId == null) {
                logger.error("Book ID is null");
                return null;
            }
            if (bookId <= 0) {
                logger.error("Book ID is less than or equal to 0");
                return null;    
            }
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Book not found"));
            logger.info("Book found: {}", book);
            return book;
        } catch (Exception e) {
            logger.error("Error finding book by ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Page<Book> findAllBooksByIsPublishedStatus(int pageNum, int pageSize, String sortField, String sortDirection,
            byte isPublished) {
        try {
            // validate parameters
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookName", "bookISBN", "bookASIN", "bookDescription", "languageName", "bookPublisher", "publishYear", "isPublished"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);

            if (isPublished != 0 && isPublished != 1) {
                logger.error("Invalid isPublished value");
                isPublished = 0;
            }
            
            // Create sort object with case-insensitive direction comparison
            Pageable pageable = PageRequest.of(pageNum, pageSize, ValidateUtil.createSort(sortField, sortDirection));
            Page<Book> books = bookRepository.findAllBooksByIsPublishedStatus(isPublished, pageable);
            logger.info("Books found: {}", books);
            return books;  
        } catch (Exception e) {
            logger.error("Error finding books by isPublished status: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean existsById(Long bookId) {
        try {
            if (bookId == null) {
                logger.error("Book ID is null");
                return false;
            }
            if (bookId <= 0) {
                logger.error("Book ID is less than or equal to 0");
                return false;
            }
            Boolean exists = bookRepository.existsById(bookId);
            logger.info("Book exists: {}", exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking if book exists: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Cacheable("bookCount")
    public Long count() {
        try {
            Long count = bookRepository.count();
            logger.info("Book count: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Error counting books: {}", e.getMessage());
            return null;
        }
    }
}
