package com.deng.book_review_publishing.controller.admin.adminView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookService;
import com.deng.book_review_publishing.utils.ValidateUtil;

@Controller
@RequestMapping("/admin/view/book")
public class BookViewController {
    private static final Logger logger = LoggerFactory.getLogger(BookViewController.class);
    private final BookService bookService;
    private final AuthorService authorService;
    public BookViewController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/findBooksByFilters")
    public String findBooksByFilters(
        Model model,
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String bookName,
        @RequestParam(required = false) String bookISBN,
        @RequestParam(required = false) String bookASIN,
        @RequestParam(required = false) String bookDescription,
        @RequestParam(required = false) String languageName,
        @RequestParam(required = false) String publisher,
        @RequestParam(required = false) Integer publishYear,
        @RequestParam(required = false, defaultValue = "1") byte isPublished) {
        try {
            logger.debug("Fetching all books with pagination and sorting");
            
            // validate parameters
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[]{"id", "bookName", "bookDescription", "languageName", "publisher", "publishYear", "isPublished"}, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            logger.info("Page number: {}, page size: {}, sort field: {}, sort direction: {}", pageNum, pageSize, sortField, sortDirection);

            if (isPublished!= 0 && isPublished!= 1) {
                logger.error("Invalid isPublished value");
                isPublished = 0;    
            }


            Page<Book> books = bookService.findBooksByFilters(pageNum, pageSize, sortField, sortDirection, bookName, bookISBN, bookASIN, bookDescription, languageName, publisher, publishYear, isPublished);
            Long bookCount = bookService.count();
            Long authorCount = authorService.countByStatus((byte) 0);
            System.out.println("books TotalPages: " + books.getTotalPages());
            if (books!= null && books.hasContent()) {
                logger.info("Books found: {}", books.getContent());
                logger.info("Book 1: {}", books.getContent().get(0).getBookName());
                model.addAttribute("books", books);
                model.addAttribute("bookCount", bookCount);
                model.addAttribute("pageNum", pageNum); 
                model.addAttribute("pageSize", pageSize);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDirection", sortDirection);
                model.addAttribute("reSortDirection", sortDirection.equals("asc")? "desc" : "asc");
                model.addAttribute("bookName", bookName);                
                model.addAttribute("languageName", languageName);
                model.addAttribute("publisher", publisher);
                model.addAttribute("publishYear", publishYear);
                // model.addAttribute("isPublished", isPublished);
                model.addAttribute("authorCount", authorCount);
                return "admin/books";
            } else {
                logger.info("No books found");
                model.addAttribute("books", books);
                return "admin/books";
            }
        } catch (Exception e) {
            logger.error("Error fetching books: {}", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String findBookById(Model model, @PathVariable Long id) {
        try {
            logger.debug("Fetching book by id: {}", id);
            Book book = bookService.findBookById(id);
            List<String> AuthorsNames = new ArrayList<>();
            
            if (book != null) {
                logger.info("Book found: {}", book);
                for (Long authorId : book.getBookAuthorIds()) {
                    Author author = authorService.findById(authorId);
                    if (author != null) {
                        String authorName = author.getFirstName() + " " + author.getLastName();
                        AuthorsNames.add(authorName);
                    } else {
                        logger.info("Author not found");
                    }
                }
                model.addAttribute("book", book);
                model.addAttribute("AuthorsNames", AuthorsNames);
                return "admin/book";
            } else {
                logger.info("Book not found");
                return "admin/book";
            }
        } catch (Exception e) {
            logger.error("Error fetching book: {}", e.getMessage());
            return "error";
        }
    }    
    
}
