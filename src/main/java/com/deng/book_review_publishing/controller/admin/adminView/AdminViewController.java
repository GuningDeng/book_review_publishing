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
import com.deng.book_review_publishing.service.BookService;

@Controller
@RequestMapping("/admin/view")
public class AdminViewController {
    private static final Logger logger = LoggerFactory.getLogger(AdminViewController.class);
    private final AdminService adminService;
    private final BookService bookService;
    private final AuthorService authorService;

    public AdminViewController(AdminService adminService, BookService bookService, AuthorService authorService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        logger.debug("Rendering admin index page");
        Long bookCount = bookService.count();
        Long authorCount = authorService.countByStatus((byte) 0);
        Page<Book> bookPage = bookService.findAllBooksByIsPublishedStatus(0, 10, "id", "asc", (byte) 0);
        Long publishedBooks = bookPage.getTotalElements();
        model.addAttribute("authorCount", authorCount);
        model.addAttribute("bookCount", bookCount);
        model.addAttribute("publishedBooks", publishedBooks);
        logger.debug("Book count: {}, Author count: {}, Published books: {}", bookCount, authorCount, publishedBooks);
        return "admin/dashboard";
    }
    
}
