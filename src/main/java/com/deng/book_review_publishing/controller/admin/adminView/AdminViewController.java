package com.deng.book_review_publishing.controller.admin.adminView;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.service.AdminService;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookGenreService;
import com.deng.book_review_publishing.service.BookReviewService;
import com.deng.book_review_publishing.service.BookService;

@Controller
@RequestMapping("/admin/view")
public class AdminViewController {
    private static final Logger logger = LoggerFactory.getLogger(AdminViewController.class);
    private final AdminService adminService;
    private final BookService bookService;
    private final BookReviewService bookReviewService;
    private final AuthorService authorService;
    private final BookGenreService bookGenreService;

    public AdminViewController(AdminService adminService, BookService bookService, BookReviewService bookReviewService, AuthorService authorService, BookGenreService bookGenreService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.bookReviewService = bookReviewService;
        this.authorService = authorService;
        this.bookGenreService = bookGenreService;
        logger.debug("AdminViewController initialized with services: {}, {}, {}, {}", adminService, bookService, authorService, bookGenreService);
    }

    /**
     * Admin dashboard view
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        logger.debug("Rendering admin index page");
        Long bookCount = bookService.count();
        Long authorCount = authorService.countByStatus((byte) 0);
        Long bookGenreCount = bookGenreService.countBookGenres();
        Page<Book> bookPage = bookService.findAllBooksByIsPublishedStatus(0, 10, "id", "asc", (byte) 0);
        Long publishedBooks = bookPage.getTotalElements();
        // Fetch published book reviews page and recent 10 published reviews using stream-based service method
        Page<BookReview> bookReviewPage = bookReviewService.findBookReviewByFilters(0, 10, "id", "asc", null, null, null, null, null, null, (byte)1, (byte)0, null, null); // Fetch published book reviews
        List<BookReview> recentBookReviews = bookReviewService.findRecentBookReviewsByStatus((byte) 2, 10); // Fetch recent 10 published book reviews
        long bookReviewCount = bookReviewService.count();
        long publishedBookReviewCount = bookReviewService.publishedBookReviewCount();
        model.addAttribute("authorCount", authorCount);
        model.addAttribute("bookCount", bookCount);
        model.addAttribute("publishedBooks", publishedBooks);
        model.addAttribute("bookGenreCount", bookGenreCount);
        model.addAttribute("bookReviewPage", bookReviewPage);
        model.addAttribute("recentBookReviews", recentBookReviews);
        model.addAttribute("bookReviewCount", bookReviewCount);
        model.addAttribute("publishedBookReviewCount", publishedBookReviewCount);

        logger.info("Admin index page data: authorCount={}, bookCount={}, publishedBooks={}, bookGenreCount={}", authorCount, bookCount, publishedBooks, bookGenreCount);
        return "admin/dashboard";
    }
    
}
