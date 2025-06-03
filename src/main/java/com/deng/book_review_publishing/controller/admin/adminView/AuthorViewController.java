package com.deng.book_review_publishing.controller.admin.adminView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deng.book_review_publishing.entity.Author;
import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookService;
import com.deng.book_review_publishing.utils.ValidateUtil;

@Controller
@RequestMapping("/admin/view/author")
public class AuthorViewController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorViewController.class);
    private final BookService bookService;
    private final AuthorService authorService;

    public AuthorViewController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/allByConditions")
    public String getAllByConditions(
        Model model,
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize,
        @RequestParam(defaultValue = "id") String sortField,
        @RequestParam(defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String countryName,
        @RequestParam(required = false) Byte gender,
        @RequestParam(required = false) Byte isDeleted,
        @RequestParam(required = false, defaultValue = "0") Byte authorStatus
    ){

        logger.info("Entering getAllActiveAuthors method");
        try {
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField,
                    new String[] { "id", "firstName", "lastName", "countryName" }, "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);
            
            long totalAuthors = authorService.count();
            // Page<Author> authors = authorService.findAllByConditions(pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, gender, isDeleted, authorStatus);
            Page<Author> authors = authorService.findAllByConditions(pageNum, pageSize, sortField, sortDirection, firstName, lastName, countryName, gender, isDeleted, authorStatus);
            long activeAuthors = authors.getTotalElements();
            long publishedBooks = bookService.findAllBooksByIsPublishedStatus(pageNum, pageSize, "id", sortDirection, (byte) 1).getTotalElements();
            
            model.addAttribute("authors", authors);
            model.addAttribute("authorPage", authors);
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("totalAuthors", totalAuthors);
            model.addAttribute("activeAuthors", activeAuthors);
            model.addAttribute("publishedBooks", publishedBooks);

            return "admin/authors";
        } catch (Exception e) {
            logger.error("Error in getAllActiveAuthors method: {}", e.getMessage(), e);
            return "error";
        }
            
    }

    @GetMapping("/{id}")
    public String getAuthorById(Model model, @RequestParam("id") Long id) {
        try {
            logger.info("Entering getAuthorById method");
            if (id == null) {
                logger.info("Author id is null");
                return "admin/author";    
            }
            if (id <= 0) {
                logger.info("Author id is less than or equal to 0");
                return "admin/author";    
            }
            // boolean authorExists = false;
            Author author = authorService.findById(id);
            logger.info("Author found: {}", author);            
            if (author != null) {
                model.addAttribute("author", author);
                model.addAttribute("authorExists", true);
                return "admin/author";
            }
            else {
                logger.info("Author not found");
                model.addAttribute("authorExists", false);
                return "admin/author";
            }
        } catch (Exception e) {
            logger.error("Error fetching author: {}", e.getMessage());
            return "error";
        }
        
    }
    
}
