package com.deng.book_review_publishing.controller.admin.adminView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.service.AdminService;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookGenreService;
import com.deng.book_review_publishing.service.BookService;

@Controller
@RequestMapping("/admin/view")
public class AdminViewController {
    private static final Logger logger = LoggerFactory.getLogger(AdminViewController.class);
    private final AdminService adminService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookGenreService bookGenreService;

    public AdminViewController(AdminService adminService, BookService bookService, AuthorService authorService, BookGenreService bookGenreService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookGenreService = bookGenreService;
        logger.debug("AdminViewController initialized with services: {}, {}, {}, {}", adminService, bookService, authorService, bookGenreService);
    }

    @GetMapping("/index")
    public String index(Model model) {
        logger.debug("Rendering admin index page");
        Long bookCount = bookService.count();
        Long authorCount = authorService.countByStatus((byte) 0);
        Long bookGenreCount = bookGenreService.countBookGenres();
        Page<Book> bookPage = bookService.findAllBooksByIsPublishedStatus(0, 10, "id", "asc", (byte) 0);
        Long publishedBooks = bookPage.getTotalElements();
        model.addAttribute("authorCount", authorCount);
        model.addAttribute("bookCount", bookCount);
        model.addAttribute("publishedBooks", publishedBooks);
        model.addAttribute("bookGenreCount", bookGenreCount);
        logger.info("Admin index page data: authorCount={}, bookCount={}, publishedBooks={}, bookGenreCount={}", authorCount, bookCount, publishedBooks, bookGenreCount);
        return "admin/dashboard";
    }
    
}
