package com.deng.book_review_publishing.controller.admin.adminAPI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.service.BookService;
import com.deng.book_review_publishing.utils.ValidateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/api/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    // Add API methods here
    @GetMapping("/booksByIsPublishedStatus")
    public ResponseEntity<?> getBooksByIsPublishedStatus(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        try {
            logger.debug("Fetching all books with pagination and sorting");
            // "id", "bookName", "languageName", "publisher", "publishYear"
            
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookName", "languageName", "publisher", "publishYear"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            
            // Call the service method to get the books
            Page<Book> books = bookService.findAllBooksByIsPublishedStatus(pageNum, pageSize, sortField, sortDirection, (byte) 1);
            if (books != null && books.hasContent()) {
                logger.info("Books found: {}", books.getContent());
                return ResponseEntity.ok(books);
            } else {
                logger.warn("No books found");
                return ResponseEntity.noContent().build();
            }
        
        } catch (Exception e) {
            logger.error("Error fetching books: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error fetching books");
        }
    }

    @GetMapping("/booksByFilters")
    public ResponseEntity<?> getBooksByFilters(
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String bookName,
        @RequestParam(required = false) String bookISBN,
        @RequestParam(required = false) String bookASIN,
        @RequestParam(required = false) String bookDescription,
        @RequestParam(required = false) String publisher,
        @RequestParam(required = false) String languageName,
        @RequestParam(required = false) Integer publishYear,
        @RequestParam(required = false, defaultValue = "0") Byte isPublished
    ){
        try {
            logger.debug("Fetching books by filters with pagination and sorting");
            
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookName", "languageName", "publisher", "publishYear"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);

            if(isPublished == null || (isPublished != 0 && isPublished != 1)){
                logger.error("Invalid isPublished value: {}", isPublished);
                isPublished = 1;
            }
            
            // Call the service method to get the books
            Page<Book> books = bookService.findBooksByFilters(pageNum, pageSize, sortField, sortDirection, bookName, bookISBN, bookASIN, bookDescription, languageName, publisher, publishYear, isPublished);
            if (books!= null && books.hasContent()) {
                logger.info("Books found: {}", books.getContent());
                return ResponseEntity.ok(books);   
            } else {
                logger.warn("No books found");
                return ResponseEntity.noContent().build();
            }
        }
        catch (Exception e) {
            logger.error("Error fetching books: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error fetching books");
        }
    }
    
}
