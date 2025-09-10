package com.deng.book_review_publishing.controller.admin.adminView;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deng.book_review_publishing.entity.BookGenre;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookGenreService;
import com.deng.book_review_publishing.service.BookService;

@Controller
@RequestMapping("/admin/view/book-genres")
// This class will handle the view-related operations for book genres in the admin panel.
public class BookGenreViewController {
    // This class will handle the view-related operations for book genres in the admin panel.
    // It will interact with the BookGenreService to fetch and display book genre information.
    // The methods will return view names and populate the model with necessary data for rendering views.
    
    private static final Logger logger = LoggerFactory.getLogger(BookGenreViewController.class);
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookGenreService bookGenreService;
    public BookGenreViewController(BookService bookService, AuthorService authorService, BookGenreService bookGenreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookGenreService = bookGenreService;
        logger.debug("BookGenreViewController initialized with services: {}, {}, {}", bookService, authorService, bookGenreService);
    }
    // Example method signatures:
    @GetMapping("/list")
    public String getAllBookGenres(Model model) {
        try {
            logger.debug("Fetching all book genres for admin view");
            List<BookGenre> bookGenres = bookGenreService.findAllBookGenres();
            Long bookGenreCount = bookGenreService.countBookGenres();
            Long bookCount = bookService.count();
            Long authorCount = authorService.countByStatus((byte) 0);
            if (bookGenres.isEmpty()) {
                logger.info("No book genres found");
                model.addAttribute("existing", false);
            } else {
                logger.info("Found {} book genres", bookGenres.size());
                model.addAttribute("existing", true);
            }
            model.addAttribute("bookGenres", bookGenres);
            model.addAttribute("bookGenreCount", bookGenreCount);
            model.addAttribute("bookCount", bookCount);
            model.addAttribute("authorCount", authorCount);
            return "admin/genres"; // Return the view name for displaying book genres
            
        } catch (Exception e) {
            logger.error("Error fetching book genres", e);
            model.addAttribute("errorMessage", "Error fetching book genres");
            return "error"; // Return an error view if something goes wrong
        }
    }
    
    // @GetMapping("/book-genres/{id}")
    // public String getBookGenreById(@PathVariable Long id, Model model) {
    //     // Implementation here
    // }
}
