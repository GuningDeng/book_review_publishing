package com.deng.book_review_publishing.controller.admin.adminView;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deng.book_review_publishing.entity.Book;
import com.deng.book_review_publishing.entity.BookReview;
import com.deng.book_review_publishing.service.AuthorService;
import com.deng.book_review_publishing.service.BookGenreService;
import com.deng.book_review_publishing.service.BookReviewService;
import com.deng.book_review_publishing.service.BookService;
import com.deng.book_review_publishing.utils.ValidateUtil;

@Controller
@RequestMapping("/admin/view/bookReview")
public class BookReviewViewController {
    private static final Logger logger = LoggerFactory.getLogger(BookReviewViewController.class);
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookReviewService bookReviewService;
    private final BookGenreService bookGenreService;

     /**
     * Constructor Injection
     * @param bookService
     * @param authorService
     * @param bookReviewService
     * @param bookGenreService
     */
    public BookReviewViewController(BookService bookService, AuthorService authorService, BookReviewService bookReviewService, BookGenreService bookGenreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookReviewService = bookReviewService;
        this.bookGenreService = bookGenreService;
    }

    /**
     * Get bookReviews by filters with pagination and sorting
     * @param pageNum
     * @param pageSize default 10, max 100
     * @param sortField
     * @param sortDirection default asc, other values: desc
     * @param bookId
     * @param reviewAuthor
     * @param reviewAuthorId
     * @param reviewContent
     * @param reviewTitle
     * @param view
     * @param reviewStatus default 0 (draft), Other values: 1 (published), 2 (not published)
     * @param isDeleted default 0 (no deleted), other values: 1 (deleted) 
     * @param year
     * @param month
     * @param sortBy
     * @param sortOrder
     * @return
     */
    @GetMapping("/bookReviewByFilters")
    public String getBookReviewsByFilters(
            Model model,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "reviewId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) Long bookId,
            @RequestParam(required = false) String reviewAuthor,
            @RequestParam(required = false) Long reviewAuthorId,
            @RequestParam(required = false) String reviewContent,
            @RequestParam(required = false) String reviewTitle,
            @RequestParam(required = false) Integer views,
            @RequestParam(required = false, defaultValue = "0") Byte reviewStatus,
            @RequestParam(required = false, defaultValue = "0") Byte isDeleted,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        logger.info("getBookReviewsByFilters: pageNum={}, pageSize={}, sortField={}, sortDirection={}, bookId={}, reviewAuthor={}, reviewAuthorId={}, reviewContent={}, reviewTitle={}, view={}, reviewStatus={}, isDeleted={}, year={}, month={}, sortBy={}, sortOrder={}",
                pageNum, pageSize, sortField, sortDirection, bookId, reviewAuthor, reviewAuthorId, reviewContent, reviewTitle, views, reviewStatus, isDeleted, year, month, sortBy, sortOrder);
        try {
            pageNum = ValidateUtil.validatePaginationParamPageNum(pageNum, 0);
            pageSize = ValidateUtil.validatePaginationParamPageSize(pageSize, 10, 100);
            sortField = ValidateUtil.validateAndNormalizeSortField(sortField, new String[] {"id", "bookId", "reviewAuthor", "reviewAuthorId", "reviewTitle", "views", "reviewStatus", "isDeleted"} , "id");
            sortDirection = ValidateUtil.validateAndNormalizeSortDirection(sortDirection);

            if (reviewStatus != 0 && reviewStatus != 1 && reviewStatus != 2) {
                logger.error("Invalid reviewStatus value: {}", reviewStatus);
                reviewStatus = 1;
            }
            if (isDeleted != 0 && isDeleted != 1) {
                logger.error("Invalid isDeleted value: {}", isDeleted);
                isDeleted = 0;
            }
            // only validate year/month/views if provided, otherwise pass as null to skip filtering
            if (year != null && year < 1900) {
                logger.error("Invalid year value: {}", year);
                model.addAttribute("errorMessage", "Invalid year value");
                return "admin/bookReviews";
            }
            if (month != null && (month < 1 || month > 12)) {
                logger.error("Invalid month value: {}", month);
                model.addAttribute("errorMessage", "Invalid month value");
                return "admin/bookReviews";
            }
            if (views != null && views < 0) {
                logger.error("Invalid views value: {}", views);
                model.addAttribute("errorMessage", "Invalid views value");
                return "admin/bookReviews";
            }
            
            Page<BookReview> bookReviewsPage = bookReviewService.findBookReviewByFilters(
                pageNum, pageSize, sortField, sortDirection, 
                bookId, reviewAuthor, reviewAuthorId, reviewContent, reviewTitle, 
                views, reviewStatus, isDeleted, year, month);
            
            Page<Book> books = bookService.findAllBooksByIsPublishedStatus(0, 10, "id", sortDirection, (byte) 1); // published
            
            long totalBookReviews = bookReviewsPage.getTotalElements();
            long totalBoeks = books.getTotalElements();
            Long authorCount = authorService.countByStatus((byte) 0);
            Long bookGenreCount = bookGenreService.countBookGenres();
            logger.info("Found {} book reviews matching filters", totalBookReviews);
            model.addAttribute("bookReviewsPage", bookReviewsPage);
            // model.addAttribute("totalBookReviews", totalBookReviews);
            model.addAttribute("books", books.getContent());
            model.addAttribute("currentPage", pageNum);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("totalBookreviews", totalBookReviews);
            model.addAttribute("totalBoeks", totalBoeks);
            model.addAttribute("authorCount", authorCount);
            model.addAttribute("bookGenreCount", bookGenreCount);
            
            return "admin/bookReviews";
        } catch (Exception e) {
            logger.error("Error in getBookReviewsByFilters: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while fetching book reviews. Please try again.");
            return "admin/bookReviews";
        }
    }

    /**
     * Get book review by id.
     * @param id the book review id
     * @param model the model to add attributes
     * @return the view name "admin/bookReview"
     */
    @GetMapping("/{id}")
    public String getBookReviewById(@PathVariable("id") Long id, Model model) {
        logger.info("getBookReviewById: id={}", id);
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book review id: {}", id);
                model.addAttribute("errorMessage", "Invalid book review id");
                return "admin/bookReviews";
            }
            BookReview bookReview = bookReviewService.findBookReviewById(id);
            if (bookReview == null) {
                logger.error("Book review not found for id: {}", id);
                model.addAttribute("errorMessage", "Book review not found");
                return "admin/bookReviews";
            }
            Book book = bookService.findBookById(bookReview.getBookId()); // find book by id
            if (book == null) {
                logger.error("Book not found for id: {}", bookReview.getBookId());
                model.addAttribute("errorMessage", "Book not found");
                return "admin/bookReviews";
            }
            model.addAttribute("book", book);
            model.addAttribute("review", bookReview);
            return "admin/bookReview";
        } catch (Exception e) {
            logger.error("Error in getBookReviewById: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while fetching the book review. Please try again.");
            return "admin/bookReviews";
        }
    }

    /**
     * Add book review.
     * @param bookId the book id
     * @param model the model to add attributes
     * @return the view name "admin/addBookReview"
     */
    @GetMapping("/add/{bookId}")
    public String addBookReview(@PathVariable("bookId") Long bookId, Model model) {
        logger.info("addBookReview: bookId={}", bookId);
        try {
            if (bookId == null || bookId <= 0) {
                logger.error("Invalid book id: {}", bookId);
                model.addAttribute("errorMessage", "Invalid book id");
                return "admin/bookReviews";
            }
            Book book = bookService.findBookById(bookId); // find book by id
            if (book == null) {
                logger.error("Book not found for id: {}", bookId);
                model.addAttribute("errorMessage", "Book not found");
                return "admin/bookReviews";
            }
            
            model.addAttribute("book", book);
            model.addAttribute("bookId", bookId);
            return "admin/addBookReview";
        } catch (Exception e) {
            logger.error("Error in addBookReview: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while adding the book review. Please try again.");
            return "admin/bookReviews";
        }
    }

    @PostMapping("/add")
    public String addBookReview(@ModelAttribute("bookId") Long bookId, BookReview bookReview, Model model) {
        logger.info("addBookReview: bookId={}, bookReview={}", bookId, bookReview);
        try {
            if (bookId == null || bookId <= 0) {
                logger.error("Invalid book id: {}", bookId);
                model.addAttribute("errorMessage", "Invalid book id");
                return "admin/addBookReview/" + bookId;
            }
            if (bookReview == null) {
                logger.error("Invalid book review: {}", bookReview);
                model.addAttribute("errorMessage", "Invalid book review");
                return "admin/addBookReview/" + bookId;
            }
            Book book = bookService.findBookById(bookId); // find book by id
            if (book == null) {
                logger.error("Book not found for id: {}", bookId);
                model.addAttribute("errorMessage", "Book not found");
                return "admin/addBookReview/" + bookId;
            }
            bookReview.setBookId(bookId); // set book id    
            bookReview.setReviewAuthor("Test Author"); // set review author, here we use test author for simplicity. in a real scenario, we should get the author name from the login user in session.
            bookReview.setReviewAuthorId(111L); // set review author id, here we use test author id for simplicity. in a real scenario, we should get the author id from the login user in session.
            // bookReview.setReviewStatus((byte) 0);
            bookReview.setIsDeleted((byte) 0);
            bookReviewService.saveBookReview(bookReview);
            logger.info("Book review saved successfully: {}", bookReview);
            return "redirect:/admin/view/bookReview/bookReviewByFilters?pageNum=0&pageSize=10&sortField=id&sortDirection=asc";
        } catch (Exception e) {
            logger.error("Error in saveBookReview: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while saving the book review. Please try again.");
            return "admin/addBookReview";
        }
    }

    
    /**
     * Update book review.
     * @param id the book review id
     * @param bookReview the book review to update
     * @param model the model to add attributes
     * @return the view name "admin/updateBookReview"
     */
    @GetMapping("/update/{id}")
    public String updateBookReview(@PathVariable("id") Long id, Model model) {
        logger.info("updateBookReview: id={}", id);
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book review id: {}", id);
                model.addAttribute("errorMessage", "Invalid book review id");
                return "admin/bookReviews";
            }
            BookReview bookReview = bookReviewService.findBookReviewById(id);
            if (bookReview == null) {
                logger.error("Book review not found for id: {}", id);
                model.addAttribute("errorMessage", "Book review not found");
                return "admin/bookReviews";
            }
            Book book = bookService.findBookById(bookReview.getBookId()); // find book by id
            if (book == null) {
                logger.error("Book not found for id: {}", bookReview.getBookId());
                model.addAttribute("errorMessage", "Book not found");
                return "admin/bookReviews";
            }
            model.addAttribute("book", book);
            model.addAttribute("bookReview", bookReview);
            return "admin/updateBookReview";
        } catch (Exception e) {
            logger.error("Error in updateBookReview: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while updating the book review. Please try again.");
            return "admin/bookReviews";
        }
    }

    @PutMapping("/update/{id}")
    public String updateBookReview(@PathVariable("id") Long id, BookReview bookReview, Model model) {
        logger.info("updateBookReview: id={}, bookReview={}", id, bookReview);
        try {
            if (id == null || id <= 0) {
                logger.error("Invalid book review id: {}", id);
                model.addAttribute("errorMessage", "Invalid book review id");
                return "admin/bookReviews";
            }
            if (bookReview == null) {
                logger.error("Invalid book review: {}", bookReview);
                model.addAttribute("errorMessage", "Invalid book review");
                return "admin/bookReviews";
            }
            BookReview existingBookReview = bookReviewService.findBookReviewById(id);
            if (existingBookReview == null) {
                logger.error("Book review not found for id: {}", id);
                model.addAttribute("errorMessage", "Book review not found");
                return "admin/bookReviews";
            }
            existingBookReview.setReviewTitle(bookReview.getReviewTitle());
            existingBookReview.setReviewContent(bookReview.getReviewContent());
            existingBookReview.setReviewStatus(bookReview.getReviewStatus());
            existingBookReview.setIsDeleted(bookReview.getIsDeleted());
            existingBookReview.setUpdatedTime(new Date());
            bookReviewService.updateBookReview(id, existingBookReview);
            logger.info("Book review updated successfully: {}", existingBookReview);
            return "redirect:/admin/view/bookReview/" + id;
        } catch (Exception e) {
            logger.error("Error in updateBookReview: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while updating the book review. Please try again.");
            return "admin/bookReviews";
        }
    }
}
